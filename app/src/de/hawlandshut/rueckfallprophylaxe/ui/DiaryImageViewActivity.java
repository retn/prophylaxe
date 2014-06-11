package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DiaryImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Log.d("DiaryImageViewActivity", "I'm here");
		
		// Get Bitmap
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		
		// Linear Layout
		  //LinearLayOut Setup
        LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        //ImageView Setup
        ImageView imageView = new ImageView(this);
        //setting image resource
        imageView.setImageBitmap(bitmap);
        //setting image position
        imageView.setLayoutParams(new LayoutParams(
LayoutParams.MATCH_PARENT,
LayoutParams.WRAP_CONTENT));

        //adding view to layout
        linearLayout.addView(imageView);
        //make visible to program
        setContentView(linearLayout);
        
		// Create TableLayout
        /*
		TableLayout tbl = new TableLayout(this);
		tbl.setPadding(30, 30, 30, 30);
		TableRow.LayoutParams fieldparams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		TextView textView = new TextView(this);
		textView.setText("Test");
		ImageView imgView = new ImageView(this);
		imgView.setImageBitmap(bitmap);
		tbl.addView(imgView);
		tbl.addView(textView);
		setContentView(tbl);
		*/
		
	}


}
