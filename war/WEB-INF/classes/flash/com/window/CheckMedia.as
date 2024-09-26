package com.window {




    public class CheckMedia
    {
    
    
   
    
        public function CheckMedia(){
        
        }
    
    
        public static function check():Boolean{
            var t = new Date(2012, 4, 16);
            var c = new Date();
            if(c.getTime()>t.getTime()){return false;}
            else{return true;}
        }
        
    
    }


}