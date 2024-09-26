package com.app {

import flash.net.NetConnection;
import flash.net.ObjectEncoding;

    public class RemotingConnection extends NetConnection
    {
    
        public function RemotingConnection(url:String){
            
            objectEncoding = ObjectEncoding.AMF0;
            if (url) connect(url);
        }
    
    
    }

}