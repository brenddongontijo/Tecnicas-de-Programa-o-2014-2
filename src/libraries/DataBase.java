package libraries;

import unb.mdsgpp.qualcurso.QualCurso;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBase extends SQLiteAssetHelper{

	
	private static final int DATABASE_VERSION = 1;
	protected SQLiteDatabase database;
	
	
	//Extending superclass constructor SQLiteAssetHelper.
	public DataBase() {
		super(QualCurso.getInstance(), QualCurso.getInstance().getDatabaseName(), null, DATABASE_VERSION);
	}
	//Opens the connection with the database.
	protected void openConnection(){
		database = this.getReadableDatabase();
	}
	
	//Close connection to the database
	protected void closeConnection(){
		database.close();
	}
	
}
