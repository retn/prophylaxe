package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.RiskSituation;

/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 14.12.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class EmergencyCaseActivity extends Activity {


	private static final int SELECT_IMAGE = 0;
	private String mSelectedImagePath;
	private File imageFile;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_1);
		imageFile = new File(getExternalFilesDir(null),"profile.jpg");
        
		
		List<RiskSituation> situations=new ArrayList<RiskSituation>(); 
		situations = ControllerData.getRiskSituation();
		

        final ListView listview = (ListView) findViewById(R.id.list_view_koffer);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < situations.size(); ++i) {
            list.add(situations.get(i).getText());
        }
        final CaseArrayAdapter adapter = new CaseArrayAdapter(this,
                R.id.message_text, list);
        listview.setAdapter(adapter);
        
        //starts second ec activity when clicking on a list item
        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                Intent intent = new Intent(EmergencyCaseActivity.this, EmergencyCaseTwoActivity.class);
                EmergencyCaseActivity.this.startActivity(intent);
				
			}
		});

        final ImageView imageView=(ImageView) findViewById(R.id.image_koffer);
        //loads profile picture if one is chosen
        if(imageFile.exists()){
        	imageView.setBackgroundColor(0x00000000);
        	imageView.setImageBitmap(loadImage(imageFile.getPath()));
        }        
        //opens gallery
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(EmergencyCaseActivity.this, "W�hle Foto", Toast.LENGTH_LONG);
                toast.show();
                imageFromGallery();
                copyImage();
                
                
            }

        });
        
        Button button=(Button)findViewById(R.id.emergency);
        //starts third emergency case activity when clicking on emergency button
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent intent = new Intent(EmergencyCaseActivity.this, EmergencyCaseThreeActivity.class);
                EmergencyCaseActivity.this.startActivity(intent);
				
			}
		});

    }
    
	
	//Opens gallery and let choose a image and save it as profile picture
    public void imageFromGallery() {
        Intent getImageFromGalleryIntent = 
          new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(getImageFromGalleryIntent, SELECT_IMAGE);
    }
    
    //result of gallery choose
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode){ 
            case SELECT_IMAGE:
                mSelectedImagePath = getPath(data.getData());
                break;
            default:
            	break;
            }
        
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

	@SuppressWarnings("resource")
	private void copyImage() {
		try {
                File source= new File(mSelectedImagePath);
        		Toast toast1=Toast.makeText(EmergencyCaseActivity.this, R.string.bild_ausgewaehlt, Toast.LENGTH_LONG);
                toast1.show();
                if (source.exists()) {
                	InputStream is=new FileInputStream(source);
                	OutputStream os=new FileOutputStream(imageFile);
                	byte[] daten=new byte[is.available()];
                	is.read(daten);
                	os.write(daten);
                	is.close();
                	os.close();
           } 
        } catch (Exception e) {}
		
	}  
	
	private Bitmap loadImage(String imgPath) {
	    BitmapFactory.Options options;
	    try {
	        options = new BitmapFactory.Options();
	        options.inSampleSize = 2;
	        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
	        return bitmap;
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
