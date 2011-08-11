package com.petrifiednightmares.TCPRemote.SavedConnections;

public class SavedConnection
{
	private String name;
	private String ip;

	public SavedConnection(String name, String ip)
	{
		this.name = name;
		this.ip = ip;
	}

	public String getIp()
	{
		return ip;
	}

	public String getName()
	{
		return name;
	}

}
