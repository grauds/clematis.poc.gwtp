package apps.lector {

import flash.display.Sprite;

    public class ProgressBar extends Sprite
    {
    
        public function ProgressBar(){
            
            xmask.width = 0;
            
        }
    
    
    
        public function set_progress(val:Number){
            xmask.width = 310.5/100*val;
        }
    
    
    }

}