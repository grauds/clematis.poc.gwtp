package apps.lector {

import flash.external.ExternalInterface;

    public class Javascript
    {
    
        public function Javascript(){
        
        }
    
    
        public static function callMethod(method:String, params:Array=null):void{
            if(method){
            
                switch(params.length){
                    
                    case 1: ExternalInterface.call(method, params[0]); break;
                    case 2: ExternalInterface.call(method, params[0], params[1]); break;
                    case 3: ExternalInterface.call(method, params[0], params[1], params[2]); break;
                    case 4: ExternalInterface.call(method, params[0], params[1], params[2], params[3]); break;
                    case 5: ExternalInterface.call(method, params[0], params[1], params[2], params[3], params[4]); break;
                    default: ExternalInterface.call(method); break;
                }
                
            }else{ExternalInterface.call("alert", "Javascript calling");}
        
        }

    
    }
}