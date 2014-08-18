package libraries;

import unb.mdsgpp.qualcurso.QualCurso;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBase extends SQLiteAssetHelper{

	private static final String DATABASE_NAME = "database.sqlite3.db";
	private static final int DATABASE_VERSION = 1;
	protected SQLiteDatabase database;
	
	public DataBase() {
		super(QualCurso.getInstance(), QualCurso.getInstance().getDatabaseName(), null, DATABASE_VERSION);
	}
	
	protected void openConnection(){
		database = this.getReadableDatabase();
	}
	
	protected void closeConnection(){
		database.close();
	}
	
}
