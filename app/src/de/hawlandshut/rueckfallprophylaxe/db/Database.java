package de.hawlandshut.rueckfallprophylaxe.db;
import java.io.File;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

/**
 * Datenbank-Verbindungs Klasse
 *
 */
public class Database {
	private Context context;
	private SQLiteDatabase sqldatabase;
	private File databaseFile;
	private MyTables tables;
	
	//get database with path
	public Database(Context context) {
		this.context=context;
		databaseFile = context.getDatabasePath("daten.db");
        }
	
	public MyTables getTables() {
		return tables;
	}
	
	//initialize, creates file if not exists
    public void InitializeSQLCipher(String pass) {
        SQLiteDatabase.loadLibs(context);
        if(!databaseFile.exists()){
        	databaseFile.getParentFile().mkdirs(); 	
        }
        
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile.getPath(), pass, null);
        this.sqldatabase=database;
        tables = new MyTables(sqldatabase);
    }

	
	public boolean databaseExists() {
		if(databaseFile.exists()){
        	return true;
        }
		return false;
	}
	
	public void close() {
		sqldatabase.close();
	}
}

