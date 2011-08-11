/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.petrifiednightmares.TCPRemote.Setup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.petrifiednightmares.TCPRemote.Main.R;
import com.petrifiednightmares.TCPRemote.SavedConnections.SavedConnection;
import com.petrifiednightmares.TCPRemote.SavedConnections.SavedConnectionsHelper;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class ListConnectionsActivity extends Activity
{
	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	// Return Intent extra
	public static String EXTRA_IP_ADDRESS = "ip_address";

	// Member fields
	private ArrayAdapter<String> savedConnectionsArrayAdapter;

	private ArrayList<SavedConnection> savedConnections;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.list_connections);

		// Set result CANCELED in case the user backs out
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				v.setVisibility(View.GONE);
			}
		});

		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		savedConnectionsArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.list_connections);

		ListView savedConnectionsListView = (ListView) findViewById(R.id.saved_ips);
		
		savedConnectionsListView.setAdapter(savedConnectionsArrayAdapter);
		savedConnectionsListView.setOnItemClickListener(savedConnectionsClickListener);



		SavedConnectionsHelper savedConnectionsHelper = new SavedConnectionsHelper(this);
		savedConnections = savedConnectionsHelper.getSavedConnections();
		
		
		// If there are paired devices, add each one to the ArrayAdapter
		if (savedConnections.size() > 0)
		{
			findViewById(R.id.title_saved_connections).setVisibility(View.VISIBLE);
			for (SavedConnection connection : savedConnections)
			{
				savedConnectionsArrayAdapter.add(connection.getName() + "\n"
						+ connection.getIp());
			}
		}
		else
		{
			String noDevices = getResources().getText(R.string.none_saved)
					.toString();
			savedConnectionsArrayAdapter.add(noDevices);
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}


	// The on-click listener for all devices in the ListViews
	private OnItemClickListener savedConnectionsClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
		{
			// Get the device MAC address, which is the last 17 chars in the
			// View
			String info = ((TextView) v).getText().toString();
			String[] splitUpString = info.split("\n");
			String ipAddress = splitUpString[1];

			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(EXTRA_IP_ADDRESS, ipAddress);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

}
