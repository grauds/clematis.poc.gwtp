package com.app {

import flash.net.Responder;
import com.app.RemotingConnection;


    public class AmfPhp
    {
    
        var gateway : RemotingConnection;
        var class_name:String;
        var prefix:String;
        var callback:Function;
        
        
        public function AmfPhp(host:String, cname:String, pref:String){
            class_name = cname;
            prefix = pref;
            gateway = new RemotingConnection("http://"+host+"/services/gateway.php");
        }
    
    
        public function exec(method:String, params:Array, cbmethod:Function){
            callback = cbmethod;
            
            var responder:Responder = new Responder(onResult, onFault);
            
            gateway.call(class_name+".exec", responder, method, params, prefix);
            
        }
    
    
        public function onResult(res){
            var r:Object = {result:res};
            callback.call(null, r);
        }
    
    
        
        public function onFault(fe:Object){

            var r:Object = {error:fe.description};
            callback.call(null, r);
        }
    
    
    }




}