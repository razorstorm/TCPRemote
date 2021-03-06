package com.petrifiednightmares.TCPRemote.Setup;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.petrifiednightmares.TCPRemote.Communications.TCPCommunicator;
import com.petrifiednightmares.TCPRemote.Input.InputActivity;
import com.petrifiednightmares.TCPRemote.Main.R;
import com.petrifiednightmares.TCPRemote.SavedConnections.SavedConnection;
import com.petrifiednightmares.TCPRemote.SavedConnections.SavedConnectionsSQLiteHelper;

public class SetupActivity extends Activity
{
	// ArrayList<SavedConnection> savedConnections;
	private static final int REQUEST_CONNECT_IP = 1;
	public static final String EXTRA_SAVED_CONNECTIONS = "saved_connections";
	private Button useASavedConnectionButton, makeNewConnectionButton;
	private EditText newConnectionName, newConnectionIp;
	ProgressDialog dialog;
	private SavedConnection savedConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.setup_activity);

		newConnectionName = (EditText) findViewById(R.id.new_connection_name_text_view);
		newConnectionIp = (EditText) findViewById(R.id.new_connection_ip_text_view);

		this.useASavedConnectionButton = (Button) findViewById(R.id.use_a_saved_connection_button);
		this.useASavedConnectionButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startListConnectionsActivity();
			}
		});

		this.makeNewConnectionButton = (Button) findViewById(R.id.make_new_connection_button);
		this.makeNewConnectionButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				setupNewConnection();
			}
		});
	}


	private void setupNewConnection()
	{
		String name = newConnectionName.getText().toString();
		String ip = newConnectionIp.getText().toString();
		savedConnection = new SavedConnection(name, ip);
		attemptConnection();
		saveNewConnection();
		gotoInputActivity();
	}

	private void saveNewConnection()
	{
		SavedConnectionsSQLiteHelper savedConnectionSqliteHelper = new SavedConnectionsSQLiteHelper(this);
		System.out.println(savedConnection);
		savedConnectionSqliteHelper.insertIntoTable(savedConnection);
	}

	private void startListConnectionsActivity()
	{
		Intent serverIntent = new Intent(this, ListConnectionsActivity.class);

		startActivityForResult(serverIntent, REQUEST_CONNECT_IP);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case REQUEST_CONNECT_IP:
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK)
				{
					// Try to forge a connection
					savedConnection = new SavedConnection(null, data.getExtras().getString(ListConnectionsActivity.EXTRA_IP_ADDRESS));
					attemptConnection();
					gotoInputActivity();
				}
				else
				{
					Toast.makeText(this, "Request canceled", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	private void attemptConnection()
	{
		String ip = savedConnection.getIp();
		disableDisplay();
		try
		{
			TCPCommunicator.setupSocket(ip);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			Toast.makeText(this, "Host not found: " + e, Toast.LENGTH_LONG).show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Toast.makeText(this, "IO Exception: " + e, Toast.LENGTH_LONG).show();
		}
		enableDisplay();
	}

	private void gotoInputActivity()
	{
		Intent serverIntent = new Intent(this, InputActivity.class);

		startActivity(serverIntent);
	}

	private void enableDisplay()
	{
		if (dialog != null)
		{
			dialog.dismiss();
			dialog = null;
		}
	}

	private void disableDisplay()
	{
		// fade the list and put a "loading" indicator over it.
		dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true, true, new OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				TCPCommunicator.destroySocket();
				enableDisplay();
			}
		});
	}
}
