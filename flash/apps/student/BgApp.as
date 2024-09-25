package apps.student {

import flash.display.Sprite;   
import flash.display.Graphics;




    public class BgApp extends Sprite
    {
    
        private var _color:uint = 0x000000;
        
    
        public function BgApp(){
        
            
            
        }
    
    
        public function set color(par:uint):void{
            _color = par;
            this.graphics.beginFill(_color);
            this.graphics.drawRect(0, 0, this.width, this.height);	
            this.graphics.endFill();
        }
        
        
        public function get color():uint{
            return _color;
        }


    
    }
}