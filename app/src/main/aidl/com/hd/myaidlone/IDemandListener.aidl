// IDemandListener.aidl
package com.hd.myaidlone;
import com.hd.myaidlone.MessageBean;
// Declare any non-default types here with import statements

interface IDemandListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onDemandReceiver(in MessageBean msg);//客户端->服务端
}
