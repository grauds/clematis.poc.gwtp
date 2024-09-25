package apps.student {

    import flash.events.Event;
    import flash.events.IOErrorEvent;
    import flash.events.HTTPStatusEvent; 
    import flash.net.URLLoader;
    import flash.net.URLLoaderDataFormat;
    import flash.net.URLRequest;
    import flash.net.URLRequestMethod;
    import flash.net.URLVariables;
    
    
    
    public class Server_data
    {
    
        var api_url:String = '/video';
        var curl:String;
        var method:Function;
        
        
        function Server_data(host:String){
            curl = "http://"+host+api_url;
            trace("curl "+curl);
        }
    
    
        public function load(callback:Function){
            method = callback;
            
            var variables:URLVariables = new URLVariables(); 
            var request:URLRequest = new URLRequest(curl); 
            
            request.method = URLRequestMethod.GET; 
            request.data = variables; 
            var loader:URLLoader = new URLLoader(); 
            loader.dataFormat = URLLoaderDataFormat.VARIABLES; 
            loader.addEventListener(Event.COMPLETE, completeHandler);
            try{loader.load(request);}
            catch(err:Error){
                var obj = {error:err.message};
                method.call(null, obj);
            } 
            
        }
    
    
        private function completeHandler(e:Event){
            method.call(null, e.target.data);
        }
        
        
        private function errorHandler(e:IOErrorEvent){
            trace("Unable to load URL: " + e.text);
            var obj = {error:e.text};
                method.call(null, obj);
        }
        
        private function statusHandler(e:HTTPStatusEvent){
            trace("Status: " + e.status);
            if(e.status==404){
                var obj = {error:"Error context path"};
                method.call(null, obj);
            }
            
        }
           
    
    }
}