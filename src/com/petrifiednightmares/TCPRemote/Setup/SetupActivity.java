package com.petrifiednightmares.TCPRemote.Setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

//import com.petrifiednightmares.BTRemote.SavedConnections.SavedConnection;
//import com.petrifiednightmares.BTRemote.SavedConnections.SavedConnectionsHelper;

public class SetupActivity extends Activity
{
//	ArrayList<SavedConnection> savedConnections;
	private static final int REQUEST_CONNECT_IP = 1;
	public static final String EXTRA_SAVED_CONNECTIONS = "saved_connections";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
//		SavedConnectionsHelper savedConnectionsHelper = new SavedConnectionsHelper(this);
//		savedConnections = savedConnectionsHelper.getSavedConnections();

		super.onCreate(savedInstanceState);

	}

	private void startListConnectionsActivity()
	{
		Intent serverIntent = new Intent(this, ListConnectionsActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_IP);
	}

}
