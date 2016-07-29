/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package de.appwerft.networkservicediscovery;

import java.net.InetAddress;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

@Kroll.module(name = "Networkservicediscovery", id = "de.appwerft.networkservicediscovery")
public class NetworkservicediscoveryModule extends KrollModule {
	private static final String LCAT = "BONJOUR 😈";
	Context ctx;
	NsdManager nsdManager;
	private KrollFunction onFoundCallback = null;
	private KrollFunction onLostCallback = null;
	String dnsType;
	// You can define constants with @Kroll.constant, for example:
	@Kroll.constant
	public static final String TYPE_AIRLINO = "_dockset._tcp.";
	@Kroll.constant
	public static final String TYPE_HTTP = "_http._tcp.";
	@Kroll.constant
	public static final String TYPE_INTERNET_PRINTING_PROTOCOL = "_ipp._tcp.";
	@Kroll.constant
	public static final String TYPE_GOOGLECAST = "_googlecast._tcp.";
	public NsdManager.ResolveListener resolveListener;

	public NetworkservicediscoveryModule() {
		super();
		resolveListener = new NsdManager.ResolveListener() {
			@Override
			public void onResolveFailed(NsdServiceInfo serviceInfo,
					int errorCode) {
				// Called when the resolve fails. Use the error
				// code to debug.
				Log.e(LCAT, "Resolve failed" + errorCode);
			}

			@Override
			public void onServiceResolved(NsdServiceInfo serviceInfo) {
				Log.e(LCAT, "Resolve Succeeded. " + serviceInfo);
				if (onFoundCallback != null)
					onFoundCallback.call(getKrollObject(),
							parseNsdServiceInfo(serviceInfo));

			}
		};
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

	@Kroll.method
	public void start(KrollDict opt) {
		initDiscovery(opt);
	}

	@Kroll.method
	public void initDiscovery(KrollDict opt) {
		Object fcallback;
		Object lcallback;
		if (opt.containsKeyAndNotNull("dnsType")) {
			dnsType = opt.getString("dnsType");
		}
		if (opt.containsKeyAndNotNull("onFound")) {
			fcallback = opt.get("onFound");
			if (fcallback instanceof KrollFunction) {
				onFoundCallback = (KrollFunction) fcallback;
			}
		}
		if (opt.containsKeyAndNotNull("onLost")) {
			lcallback = opt.get("onFound");
			if (lcallback instanceof KrollFunction) {
				onLostCallback = (KrollFunction) lcallback;
			}
		}
		this.initializeDiscoveryListener();
	}

	private void initializeDiscoveryListener() {
		ctx = TiApplication.getInstance().getApplicationContext();
		nsdManager = (NsdManager) ctx.getSystemService(Context.NSD_SERVICE);
		Log.d(LCAT, "initializeDiscoveryListener = " + nsdManager.toString());

		NsdManager.DiscoveryListener discListener = new NsdManager.DiscoveryListener() {
			@Override
			public void onDiscoveryStarted(String regType) {
				Log.d(LCAT, "Service discovery started");
			}

			@Override
			public void onServiceFound(NsdServiceInfo service) {
				nsdManager.resolveService(service, resolveListener);
			}
			
			
			@Override
			public void onServiceLost(NsdServiceInfo service) {
				if (onLostCallback != null)
					onLostCallback.call(getKrollObject(),
							parseNsdServiceInfo(service));
			}

			@Override
			public void onDiscoveryStopped(String serviceType) {
				Log.i(LCAT, "Discovery stopped: " + serviceType);
			}

			@Override
			public void onStartDiscoveryFailed(String serviceType, int errorCode) {
				Log.e(LCAT, "Discovery failed: Error code:" + errorCode);
				nsdManager.stopServiceDiscovery(this);
			}

			@Override
			public void onStopDiscoveryFailed(String serviceType, int errorCode) {
				Log.e(LCAT, "Discovery failed: Error code:" + errorCode);
				nsdManager.stopServiceDiscovery(this);
			}
		};
		nsdManager.discoverServices(dnsType, NsdManager.PROTOCOL_DNS_SD,
				discListener);

	}

	private KrollDict parseNsdServiceInfo(NsdServiceInfo so) {
		KrollDict dict = new KrollDict();
		Log.d(LCAT, so.toString());
		InetAddress address = so.getHost();
		if (address != null) {
			dict.put("ip", address.getHostAddress());
		}
		dict.put("port", so.getPort());
		dict.put("name", so.getServiceName());
		dict.put("type", so.getServiceType());
		Log.d(LCAT, dict.toString());

		return dict;
	}
}
