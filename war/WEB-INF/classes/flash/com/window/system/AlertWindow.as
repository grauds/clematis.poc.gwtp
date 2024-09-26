package com.window.system {

import flash.display.Sprite;

    public class AlertWindow extends Sprite {
    
    var mod:Sprite;
    var plashka:Sprite;
    
        public function AlertWindow(){
             
            
        }
    
    
    
        public function show(title:String, cont:String){
            mod = new Modal();
            addChild(mod);
            mod.x = 0;
            mod.y = 0;
            mod.width = stage.stageWidth;
            mod.height = stage.stageHeight;
            
            plashka = new SysWinAlert(title, cont, function(){remove();});
            addChild(plashka);
            plashka.x = stage.stageWidth/2-plashka.width/2;
            plashka.y =  stage.stageHeight/2 - plashka.height/2;
        }
    
    
        
        public function remove(){
            removeChild(plashka);
            removeChild(mod);
            parent.removeChild(this);
        }
        
    
    }


}