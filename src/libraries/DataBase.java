package libraries;

import unb.mdsgpp.qualcurso.QualCurso;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Class Name: DataBase.
 * This class creates the Database opening and closing the connection to the same.
 */
public class DataBase extends SQLiteAssetHelper {

	// Database version.
	private static final int DATABASE_VERSION = 1;
	
	//  SQLiteDatabase has methods to create, delete, execute SQL commands.
	protected SQLiteDatabase database;
	
	// Extending superclass constructor SQLiteAssetHelper.
	public DataBase() {
		super(QualCurso.getInstance(), QualCurso.getInstance().getDatabaseName(), null, DATABASE_VERSION);
	}
	
	/**
	 * This method opens the connection with the database.
	 */
	protected void openConnection(){
		database = this.getReadableDatabase();
	}
	
	/**
	 * This method close connection to the database.
	 */
	protected void closeConnection(){
		database.close();
	}
	
}
