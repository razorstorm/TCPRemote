package com.petrifiednightmares.TCPRemote.Input;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;

import com.petrifiednightmares.TCPRemote.Communications.TCPCommunicator;

public class InputActivity extends Activity
{
	// ArrayList<SavedConnection> savedConnections;
	public static final String EXTRA_SAVED_CONNECTIONS = "saved_connections";
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.input_activity);

	}
	@Override
	public void onBackPressed()
	{
		TCPCommunicator.destroySocket();
		super.onBackPressed();
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return super.onKeyUp(keyCode, event);
		}
		sendKey(keyCode);

		return true;
	}

	private void sendKey(final int keyCode)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				String msg = "[key]: " + keyCode;
				try
				{
					TCPCommunicator.sendMessage(msg);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
