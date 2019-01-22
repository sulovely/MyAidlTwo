// IDemandManager.aidl
package com.hd.myaidlone;
import com.hd.myaidlone.MessageBean;
import com.hd.myaidlone.IDemandListener;

// Declare any non-default types here with import statements

interface IDemandManager {
     MessageBean getDemand();

     void setDemandIn(in MessageBean msg);//客户端->服务端

     //out和inout都需要重写MessageBean的readFromParcel方法
     void setDemandOut(out MessageBean msg);//服务端->客户端

     void setDemandInOut(inout MessageBean msg);//客户端<->服务端

     void registerListener(IDemandListener listener);

     void unregisterListener(IDemandListener listener);
}

