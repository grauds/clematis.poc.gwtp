package apps.student {

import flash.display.Sprite;

    public class TchatWindow extends Sprite {
    
    var mod:Sprite;
    var plashka:Sprite;
    
        public function TchatWindow(){
             
            
        }
                                                          
    
    
        public function show(callback:Function){
            mod = new ModalWin();
            addChild(mod);
            mod.x = 0;
            mod.y = 0;
            mod.width = parent.width;
            mod.height = parent.height;
            
            plashka = new SmileWin(function(resp){
                                        callback.call(null, resp);
                                        remove();
                                    });
            
           
            
            addChild(plashka);
            plashka.x = parent.width/2-plashka.width/2;
            plashka.y =  339/2 - 200/2;
        }
    
    
        
        public function remove(){
            removeChild(plashka);
            removeChild(mod);
            parent.removeChild(this);
        }
        
    
    }


}