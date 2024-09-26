package apps.student {

    import flash.events.Event; 
    import flash.net.URLLoader;
    import flash.net.URLLoaderDataFormat;
    import flash.net.URLRequest;
    import flash.net.URLRequestMethod;
    import flash.net.URLVariables;
    
    
    
    public class Log_remote
    {
    
        
        var curl:String;
        var method:Function;
        
        
        function Log_remote(url:String){
            curl = url;
        }
    
    
        public function load(obj:Object, callback:Function){
            method = callback;
            
            var str = "req=1";
            
            for(var i in obj){str += "&"+i+"="+obj[i];}
            
            var variables:URLVariables = new URLVariables(str); 
            var request:URLRequest = new URLRequest(curl); 
            
            request.method = URLRequestMethod.POST; 
            request.data = variables; 
            var loader:URLLoader = new URLLoader(); 
            loader.dataFormat = URLLoaderDataFormat.VARIABLES; 
            loader.addEventListener(Event.COMPLETE, completeHandler); 
            loader.load(request);
        }
    
    
        private function completeHandler(e:Event){
            if(method!=null){method.call(null, e.target.data);}
        }
        
        
    
    }
    


}