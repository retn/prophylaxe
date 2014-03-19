package de.hawlandshut.rueckfallprophylaxe.db;
import java.io.File;

import android.content.Context;
import android.util.Log;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Datenbank-Verbindungs Klasse
 *
 * TODO
 * * implementieren
 */
public class Database {
	private Context context;
	private SQLiteDatabase sqldatabase;
	public Database(Context context) {
		this.context=context;
		InitializeSQLCipher();
        }
	
    private void InitializeSQLCipher() {
        SQLiteDatabase.loadLibs(context);
        File databaseFile = context.getDatabasePath("daten.db");
        if(!databaseFile.exists()){
        	databaseFile.mkdirs();
        	
        }
        //databaseFile.delete();
        
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "user", null);
        this.sqldatabase=database;
        //MyTables tables=new MyTables(sqldatabase);
    }

	public void insertExample() {
		
        sqldatabase.execSQL("insert into t1(a, b) values(?, ?)", new Object[]{"one for the money",
                                                                        "two for the show"});
		
	}

	public void makeExampleQuery() {
		String query="select * from t1";
        Cursor c=sqldatabase.rawQuery(query, null);
        if(c!=null){
        	int i=0;
        	if(c.moveToFirst()){
        		do{
        			//String text=c.getString(c.getColumnIndex("a"));
        			i++;
        		}
        		while(c.moveToNext());
        	}
        	Log.d("query", ""+i);
        }
	}
}

/*
 * example table+query
        database.execSQL("create table t1(a , b)");
        database.execSQL("insert into t1(a, b) values(?, ?)", new Object[]{"one for the money",
                                                                        "two for the show"});
        String query="select * from t1";
        Cursor c=database.rawQuery(query, null);
        if(c!=null){
        	if(c.moveToFirst()){
        		do{
        			String text=c.getString(c.getColumnIndex("a"));
        			Toast.makeText(context, text, Toast.LENGTH_LONG).show();;
        		}
        		while(c.moveToNext());
        	}
        }
*/
