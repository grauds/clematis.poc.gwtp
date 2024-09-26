package apps.lector {

import flash.display.Sprite;




    public class RecStatus extends Sprite
    {
    
       private var _rstat:Boolean = false;
    
        public function RecStatus(){
        
            
        }
    
        
        
        public function set status(par:Boolean):void{
            _rstat = par;
            if(_rstat){recoff.visible = false;}
            else{recoff.visible = true;}
        }
        
    
    }


}