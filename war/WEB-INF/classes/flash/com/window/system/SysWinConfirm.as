package com.window.system {

import flash.display.Sprite;
import flash.events.MouseEvent;

    public class SysWinConfirm extends Sprite {
        
        var method:Function;
        
    
    
        public function SysWinConfirm(title:String, cont:String, callback:Function){
            
            txt_title.text = title;
            txt_content.text = cont;
            method = callback;
            
            butok.addEventListener(MouseEvent.CLICK, handlerok);
            butcancel.addEventListener(MouseEvent.CLICK, handlercancel);  
            
        }
    
    
    
        private function handlerok(e:MouseEvent){
            method.call(null, true);
        }
        
        
        private function handlercancel(e:MouseEvent){
            method.call(null, false);
        }
        
    }


}