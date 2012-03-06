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

		System.out.println(results.getCount());
		if (results.getCount() > 0)
		{
			results.moveToFirst();
			while (true)
			{
				System.out.println(results.getColumnCount());
				for (String name : results.getColumnNames())
				{
					System.out.println(name);
				}
				int connectionColumnId = results.getColumnIndexOrThrow(SavedConnectionsSQLiteHelper.getConnectionColumnName());
				int ipColumnId = results.getColumnIndexOrThrow(SavedConnectionsSQLiteHelper.getConnectionColumnIP());
				System.out.println(connectionColumnId);
				System.out.println(ipColumnId);

				String name = results.getString(connectionColumnId);
				String ip = results.getString(ipColumnId);
				SavedConnection savedConnection = new SavedConnection(name, ip);

				resultArrayList.add(savedConnection);

				if (results.isLast())
				{
					break;
				}
				
				results.moveToNext();
				
			}
		}
		savedConnectionsDb.getReadableDatabase().close();
		results.close();
		return resultArrayList;
	}

	public void delete(String name, String ipAddress)
	{
		savedConnectionsDb.delete(name,ipAddress);
		
	}
}
