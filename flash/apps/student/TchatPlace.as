package apps.student {

import flash.display.Sprite;



    public class TchatPlace extends Sprite {
    
    var cont:Sprite;
    
    
        public function TchatPlace(){
                
            
            
            cont = new Sprite();
            addChild(cont);
            
               
            
        }
                                                          
    
    
        public function add_smile(coord:Object, smile:Object):void{
            var sm = new TchatSmileWin(smile);
            cont.addChild(sm);
            sm.x = coord.x;
            sm.y = coord.y-5;
        } 
    
    
    
        public function set_scrool(par:Number):void{
        trace("scrool "+par);
            
            cont.y = -((par-1)*16);
        }
    
    
       
    
    }


}