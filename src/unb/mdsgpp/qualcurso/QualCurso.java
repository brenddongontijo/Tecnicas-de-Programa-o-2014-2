package unb.mdsgpp.qualcurso;

import android.app.Application;
import android.content.Context;

public class QualCurso extends Application {
	
	private static QualCurso instance;
	private static String databaseName;
	
	public QualCurso(){
		databaseName = "database.sqlite3.db";
		instance = this;
	}
	
	public void setDatabaseName(String databaseName){
		this.databaseName = databaseName;
	}
	
	public String getDatabaseName(){
		return databaseName;
	}
	
	public static QualCurso getInstance(){
		return instance;
	}
}
