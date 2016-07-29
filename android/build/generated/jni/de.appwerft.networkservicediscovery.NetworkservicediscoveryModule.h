/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2011-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

/** This is generated, do not edit by hand. **/

#include <jni.h>

#include "Proxy.h"

		namespace de {
		namespace appwerft {
		namespace networkservicediscovery {


class NetworkservicediscoveryModule : public titanium::Proxy
{
public:
	explicit NetworkservicediscoveryModule(jobject javaObject);

	static void bindProxy(v8::Handle<v8::Object> exports);
	static v8::Handle<v8::FunctionTemplate> getProxyTemplate();
	static void dispose();

	static v8::Persistent<v8::FunctionTemplate> proxyTemplate;
	static jclass javaClass;

private:
	// Methods -----------------------------------------------------------
	static v8::Handle<v8::Value> start(const v8::Arguments&);
	static v8::Handle<v8::Value> initDiscovery(const v8::Arguments&);

	// Dynamic property accessors ----------------------------------------

};

		} // networkservicediscovery
		} // appwerft
		} // de
