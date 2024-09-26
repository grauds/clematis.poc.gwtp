package apps.student {

import flash.net.SharedObject;


    public class Tconfig
    {
    
        var so:SharedObject;
        var data:Object;
    
        public function Tconfig(){
        
            so = SharedObject.getLocal("student_config2", '/');
            
            if(so.data.config!=undefined){
                data = so.data.config;
            }else{
                data = {
                                    mic_volume:70,
                                    bgcolor:0x000000,
                                    vdev:"",
                                    adev:"",
                                    vquality:95
                                    };
                                    
                save();
            }
        
        }
    
    
        public function set_param(par:String, val:*):void{
            data[par] = val;
            save();
        }
    
        
        public function set_params(o:Object):void{
            for(var i in o){data[i] = o[i];}
            
            save();
        }
    
        private function save():void{
            so.data.config = data;
            so.flush();
        }
    
    }


}