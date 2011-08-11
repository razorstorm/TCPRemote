package com.petrifiednightmares.TCPRemote.Communications;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPCommunicator implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 505498261491712104L;

	Socket mainSocket;

	public static int PORT_NUMBER = 80;

	public TCPCommunicator()
	{

	}

	public void setupSocket(String ip) throws UnknownHostException, IOException
	{
		mainSocket = new Socket(ip, PORT_NUMBER);
	}

	public void sendMessage(String msg) throws IOException
	{
		OutputStream outputStream = mainSocket.getOutputStream();
		PrintWriter output = new PrintWriter(outputStream);
		output.println(msg);
	}
}
