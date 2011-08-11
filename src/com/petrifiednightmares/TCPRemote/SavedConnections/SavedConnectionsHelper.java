package com.petrifiednightmares.TCPRemote.SavedConnections;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class SavedConnectionsHelper
{
	private SavedConnectionsSQLiteHelper savedConnectionsDb;
	
	public SavedConnectionsHelper(Context context)
	{
		savedConnectionsDb = new SavedConnectionsSQLiteHelper(context);
	}
	
	public ArrayList<SavedConnection> getSavedConnections()
	{
		Cursor results = savedConnectionsDb.getReadableDatabase().query(true, SavedConnectionsSQLiteHelper.getConnectionsTableName(), null, null, null, null, null, null, null);

		ArrayList<SavedConnection> resultArrayList = new ArrayList<SavedConnection>();

		while (!results.isLast())
		{
			String name = results.getString(results.getColumnIndexOrThrow(SavedConnectionsSQLiteHelper.getConnectionColumnName()));
			String ip = results.getString(results.getColumnIndexOrThrow(SavedConnectionsSQLiteHelper.getConnectionColumnIP()));
			SavedConnection savedConnection = new SavedConnection(name, ip);

			resultArrayList.add(savedConnection);
		}

		return resultArrayList;
	}
}
