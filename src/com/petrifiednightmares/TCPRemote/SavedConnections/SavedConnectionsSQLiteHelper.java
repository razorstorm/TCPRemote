package com.petrifiednightmares.TCPRemote.SavedConnections;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SavedConnectionsSQLiteHelper extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME = "bt_remote_db";
	private static final int DATABASE_VERSION = 2;
	private static final String PRIMARY_ID = "id";
	private static final String CONNECTION_COLUMN_NAME = "name";
	private static final String CONNECTION_COLUMN_IP = "ip";
	private static final String CONNECTIONS_TABLE_NAME = "saved_connections";
	private static final String CONNECTIONS_TABLE_CREATE = "CREATE TABLE " + CONNECTIONS_TABLE_NAME + " (" + PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ CONNECTION_COLUMN_NAME + " TEXT, " + CONNECTION_COLUMN_IP + " TEXT);";

	public void insertIntoTable(SavedConnection input)
	{
		ContentValues values = new ContentValues();
		values.put(CONNECTION_COLUMN_NAME, input.getName());
		values.put(CONNECTION_COLUMN_IP, input.getIp());
		getReadableDatabase().insert(CONNECTIONS_TABLE_NAME, null, values);
	}

	public static String getConnectionsTableName()
	{
		return CONNECTIONS_TABLE_NAME;
	}

	public static String getConnectionColumnName()
	{
		return CONNECTION_COLUMN_NAME;
	}

	public static String getConnectionColumnIP()
	{
		return CONNECTION_COLUMN_IP;
	}

	public SavedConnectionsSQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CONNECTIONS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	public void delete(String name, String ipAddress)
	{
		getReadableDatabase().delete(CONNECTIONS_TABLE_NAME, CONNECTION_COLUMN_NAME+"= ? AND "+CONNECTION_COLUMN_IP+"= ?", new String[] {name,ipAddress});	
	}
}
