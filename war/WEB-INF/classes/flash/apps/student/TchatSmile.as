package apps.student {

import flash.display.Sprite;
import com.Load_photo;



    public class TchatSmile extends Sprite
    {
    
        var id:uint = 0;
        var url:String="";
        
         
    
        public function TchatSmile(obj:Object){
        
            id = obj.id;
            url = obj.url;    
        
            load_image();
        }
    
        private function load_image():void{
            var obj:Load_photo = new Load_photo(Tchat.tinst.smile_path+url, photo);
        }
        
    
    }


}