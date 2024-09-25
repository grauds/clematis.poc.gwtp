package apps.student {

import flash.display.Sprite;

    public class SettingWindow extends Sprite {
    
    var mod:Sprite;
    var plashka:Sprite;
    
        public function SettingWindow(){
             
            
        }
                                                          
    
    
        public function show(callback:Function){
            mod = new ModalWin();
            addChild(mod);
            mod.x = 0;
            mod.y = 0;
            mod.width = stage.stageWidth;
            mod.height = stage.stageHeight;
            
            plashka = new SettingWin(function(resp){
                                        if(resp){callback.call(null);}
                                        remove();
                                    });
           
            
            
            addChild(plashka);
            plashka.x = stage.stageWidth/2-plashka.width/2;
            plashka.y =  stage.stageHeight/2 - plashka.height/2 - 30;
        }
    
    
        
        public function remove(){
            removeChild(plashka);
            removeChild(mod);
            parent.removeChild(this);
        }
        
    
    }


}