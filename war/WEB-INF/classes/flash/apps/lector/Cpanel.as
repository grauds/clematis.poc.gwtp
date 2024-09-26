package apps.lector {

import flash.display.Sprite;

import flash.events.MouseEvent;



    public class Cpanel extends Sprite
    {
    
    
        public function Cpanel(){
        
            
            but_chat.addEventListener(MouseEvent.CLICK, onChat);
            but_end.addEventListener(MouseEvent.CLICK, onEnd);
            but_full.addEventListener(MouseEvent.CLICK, onFull);
            
        }
    
    
        
    
    
        private function onChat(e:MouseEvent):void{
            Main.inst.onChatState();
        }
        
        private function onEnd(e:MouseEvent):void{
            Main.inst.onEndLesson();
        }
    
        private function onFull(e:MouseEvent):void{
            Main.inst.onFullMode();
        }
    
        
    
    }


}