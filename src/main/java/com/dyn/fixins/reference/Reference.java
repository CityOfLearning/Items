package com.dyn.fixins.reference;

public class Reference {

	// we cant use a pipe charater here since the resource location cannot
	// contain
	// them and then all of our textures will be missing
	public static final String MOD_ID = "dynfixins";
	public static final String VERSION = "1.0";
	public static final String MOD_NAME = "DYN Fixins";

	public static final String SERVER_PROXY_CLASS = "com.dyn.fixins.proxy.Server";
	public static final String CLIENT_PROXY_CLASS = "com.dyn.fixins.proxy.Client";
}
