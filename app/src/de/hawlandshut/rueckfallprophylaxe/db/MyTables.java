package de.hawlandshut.rueckfallprophylaxe.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
/* Tables
 * `spl_emergency_case`
 * `spl_ec_risk_situation`
 * `spl_ec_safety_action`
 * `spl_ec_safety_thought`
 * `spl_ec_help_person`
 * `spl_ec_limit_relapse`
 * `spl_maxim`
 * `spl_emotion`
 * `spl_distraction`
 * `spl_place_to_go`
 * `spl_diary_entry`
 * `spl_diary_entry_has_media`
 * `spl_diary_entry_has_mood`
 */
import android.content.ContentValues;
import android.util.Log;

/**
 * Tables and database functions
 *
 */
public class MyTables {
	
	private SQLiteDatabase sqldatabase;

	//constructor with the database as parameter
	public MyTables(SQLiteDatabase sqldatabase) {
		this.sqldatabase=sqldatabase;
		createTables();
		Log.d(this.toString(),queryNumberTables()+" tables counted.");

	}
	
	//updates database, needs table hashmap with attributs, index and the column name of id
	public boolean update(String table, HashMap<String, String> hashMap,
			String index, String columnName) {
		
		ContentValues values=new ContentValues();
		for(Map.Entry<String, String> entry: hashMap.entrySet()) {
			Log.d("dbUpdate", "key: "+entry.getKey()+", val: "+entry.getValue());
			values.put(entry.getKey(),entry.getValue());
		}
		
		return sqldatabase.update(table,values ,columnName+" = "+index, null)>0;
	}
	
	
	//inserts in database, needs a hashmap with attributs
	public boolean insert(String table, HashMap<String, String> hashMap) {
		ContentValues values=new ContentValues();
		for(Map.Entry<String, String> entry: hashMap.entrySet()){
			values.put(entry.getKey(),entry.getValue());
		}
		
		return sqldatabase.insert(table, null, values)>0;
	}
	
	//special insert: inserts in diary entry has picture table, with entryid and blob
	public boolean insertEntryPicture(int entryid, byte[] imageblob) {
		String table="spl_diary_entry_has_picture";
		ContentValues values=new ContentValues();
		values.put("entryID",String.valueOf(entryid));
		values.put("picture",imageblob);		
		return sqldatabase.insert(table, null, values)>0;
	}

	//deletes entry in database, needs id of entry and the column name of id
	public boolean delete(String table, String id, String columnName) {
		return sqldatabase.delete(table, columnName+" = "+id, null)>0;
	}
	
	//deletes table
	public boolean delete(String table) {
		return sqldatabase.delete(table, null, null)>0;
	}

	//returns list of attributs from a table
	public List<String> query(String table, String value) {
		List<String> result= new ArrayList<String>();
		Cursor c = sqldatabase.rawQuery("SELECT "+value+" FROM "+table, null);

		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		    	result.add(c.getString(c.getColumnIndex(value)));
		        c.moveToNext();
		    }
		}
		c.close();
		 
		return result;
	}
	
	//returns list of all entries with all attributs from a table
	public List<List<String>> queryFullTable(String table) {
		List<List<String>> result= new ArrayList<List<String>>();
		Cursor c = sqldatabase.rawQuery("SELECT * FROM "+table, null);

		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		    	List<String> resultRow=new ArrayList<String>();
		    	for(int i=0;i<c.getColumnCount();i++){
		    		resultRow.add(c.getString(i));
		    	}
		        c.moveToNext();
		        result.add(resultRow);
		    }
		}
		c.close();
		 
		return result;
	}	
	
	//query with where condition
	public List<String> query(String table, String value, String comparison) {
		List<String> result= new ArrayList<String>();
		Cursor c = sqldatabase.rawQuery("SELECT "+value+" FROM "+table+" WHERE "+comparison, null);

		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		    	result.add(c.getString(c.getColumnIndex(value)));
		        c.moveToNext();
		    }
		}
		c.close();
		 
		return result;
	}

	//counts number of tables
	private int queryNumberTables() {
		Cursor c = sqldatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
		int i=0;
		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		        Log.d("db" ,"Table Name=> "+c.getString(0));
		        c.moveToNext();
		        i++;
		    }
		    Log.d("db","number tables: "+i);
		}
		c.close();
		return i;
		
	}
	
	//special query: query with blob for entrypicture
	public List<List<Object>> queryFullTableEntryPicture() {
/*		String table="spl_diary_entry_has_picture";
		List<List<Object>> result= new ArrayList<List<Object>>();
		Cursor c = sqldatabase.rawQuery("SELECT * FROM "+table , null);

		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		    	List<Object> resultRow=new ArrayList<Object>();
		    	for(int i=0;i<c.getColumnCount()-1;i++){
		    		resultRow.add(c.getString(i));
		    	}
		    	resultRow.add(c.getBlob(c.getColumnCount()-1));
		        c.moveToNext();
		        result.add(resultRow);
		    }
		}
		c.close();
		 
		return result;*/
		String table="spl_diary_entry_has_picture";
		List<List<Object>> result= new ArrayList<List<Object>>();
		Cursor c = sqldatabase.rawQuery("SELECT id FROM "+table , null);
		List<Integer> ids=new ArrayList<Integer>();
		if (c.moveToFirst()) {
		    while ( !c.isAfterLast() ) {
		    	ids.add(Integer.parseInt(c.getString(0)));
		        c.moveToNext();
		    }
		}
		c.close();
		
		for(int id :ids){
			Cursor cId = sqldatabase.rawQuery("SELECT * FROM "+table + " WHERE id ="+id, null);
			if (cId.moveToFirst()) {
		    	List<Object> resultRow=new ArrayList<Object>();
		    	for(int i=0;i<cId.getColumnCount()-1;i++){
		    		resultRow.add(cId.getString(i));
		    	}
		    	resultRow.add(cId.getBlob(cId.getColumnCount()-1));
		        result.add(resultRow);
			}
			cId.close();
		}
		 
		return result;
	}

	//creates all tables
	public void createTables(){	
		sqldatabase.execSQL("PRAGMA foreign_keys = OFF");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_emergency_case\"( \"ecID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"addict_drughotline\" VARCHAR(45),\"prop_advice_centre\" VARCHAR(45),\"my_therapist\" VARCHAR(45),\"emergency_casecol\" VARCHAR(45),\"risk_danger\" TINYTEXT,\"risk_situation\" TINYTEXT,\"risk_temptation\" TINYTEXT,\"temptation_thought\" TINYTEXT,\"temptation_thought_abstinence\" TINYTEXT,\"temptation_behaviour\" TINYTEXT)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_ec_risk_situation\"(\"ersID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"text\" TINYTEXT)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_ec_safety_action\"(\"esaID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"text\" TINYTEXT)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_ec_safety_thought\"(\"estID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"text\" TINYTEXT)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_ec_help_person\"(\"ehpID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"name\" VARCHAR(45),\"phone_number\" VARCHAR(45))");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_ec_limit_relapse\"(\"elrID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"text\" TINYTEXT);");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_maxim\"(\"maximID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"text\" INTEGER)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_emotion\"(\"emotionID\" INTEGER PRIMARY KEY NOT NULL,\"emotion\" VARCHAR(45))");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_place_to_go\"(\"ptgID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"name\" VARCHAR(45),\"street\" VARCHAR(100),\"plz\" VARCHAR(10),\"town\" VARCHAR(45),\"phone_number\" VARCHAR(45),\"email\" VARCHAR(45), \"lat\" REAL, \"lng\" REAL)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_diary_entry\"(\"id\" INTEGER PRIMARY KEY NOT NULL,\"title\" VARCHAR(45),\"content\" TEXT,\"created\" DATETIME)");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_diary_entry_has_picture\"( \"id\" INTEGER PRIMARY KEY NOT NULL, \"entryID\" INTEGER, \"picture\" BLOB, CONSTRAINT \"id\" FOREIGN KEY(\"entryID\") REFERENCES \"spl_diary_entry\"(\"id\"))");
		sqldatabase.execSQL("CREATE INDEX IF NOT EXISTS \"spl_diary_entry_has_picture.id_idx\" ON \"spl_diary_entry_has_picture\"(\"entryID\")");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_diary_entry_has_mood\"( \"entryID\" INTEGER NOT NULL, \"emotionID\" INTEGER NOT NULL, CONSTRAINT \"entryID\" FOREIGN KEY(\"entryID\") REFERENCES \"spl_diary_entry\"(\"id\"), CONSTRAINT \"emotionID\" FOREIGN KEY(\"emotionID\") REFERENCES \"spl_emotion\"(\"emotionID\"))");
		sqldatabase.execSQL("CREATE INDEX IF NOT EXISTS \"spl_diary_entry_has_mood.fk_spl_diary_entry_has_mood_1_idx\" ON \"spl_diary_entry_has_mood\"(\"entryID\")");
		sqldatabase.execSQL("CREATE INDEX IF NOT EXISTS \"spl_diary_entry_has_mood.emotionID_idx\" ON \"spl_diary_entry_has_mood\"(\"emotionID\")");
		sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS \"spl_distraction\"( \"distractionID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, \"emotionID_fk\" INTEGER NOT NULL, \"text\" VARCHAR(45), CONSTRAINT \"fk_distraction_emotion1\" FOREIGN KEY(\"emotionID_fk\") REFERENCES \"spl_emotion\"(\"emotionID\"))");
		sqldatabase.execSQL("CREATE INDEX IF NOT EXISTS \"spl_distraction.fk_distraction_emotion1_idx\" ON \"spl_distraction\"(\"emotionID_fk\")");
			
	}

}
