package com.window.system {

import flash.display.Sprite;

    public class ConfirmWindow extends Sprite {
    
    var mod:Sprite;
    var plashka:Sprite;
    
        public function ConfirmWindow(){
             
            
        }
    
    
    
        public function show(title:String, cont:String, method:Function){
            
            mod = new Modal();
            addChild(mod);
            mod.x = 0;
            mod.y = 0;
            mod.width = stage.stageWidth;
            mod.height = stage.stageHeight;
            
            plashka = new SysWinConfirm(title, cont, function(r){
                                                    if(r){method.call(null);}
                                                    remove();
                                                    });
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