package apps.lector {

import flash.display.MovieClip;

import com.Load_photo;



    public class TchatSmileWin extends MovieClip
    {
    
        var id:uint = 0;
        var url:String="";
        
        
         
    
        public function TchatSmileWin(obj:Object){
        
            id = obj.id;
            url = obj.url;    
            
            load_image();
        }
    
        private function load_image():void{
            var obj:Load_photo = new Load_photo(Tchat.tinst.smile_path+url, this);
        }
        
    
    }


}