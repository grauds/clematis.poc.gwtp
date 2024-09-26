package com.window.system {

import flash.display.Sprite;
import flash.events.MouseEvent;

    public class SysWinAlert extends Sprite {
        
        var method:Function;
    
    
        public function SysWinAlert(title:String, cont:String, callback:Function){
            
            txt_title.text = title;
            txt_content.text = cont;
            method = callback;
            butok.addEventListener(MouseEvent.CLICK, handler);   
            
        }
    
    
    
        private function handler(e:MouseEvent){
            method.call(null);
        }
        
    }


}