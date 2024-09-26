package apps.student {

import flash.display.Sprite;

import flash.events.MouseEvent;
import fl.events.SliderEvent;

import flash.utils.*;
import flash.media.SoundTransform;
import flash.net.NetStream;



    public class VolPanelStream extends Sprite
    {
    
        private var _volume:uint = 80;
        var ns:NetStream;
        var strans:SoundTransform;
    
        public function VolPanelStream(){
        
            
            but_volon.addEventListener(MouseEvent.CLICK, onClick);
            but_voloff.addEventListener(MouseEvent.CLICK, onClick);
            sl.addEventListener(SliderEvent.CHANGE, onChange);
             
            method_timeout(10, function(){_init();});
        }
    
    
        private function _init(){
             trace("init lector volpan");
            but_voloff.visible = false;
            but_volon.enabled = false;
            sl.visible = false;
        }
    
        public function init(_ns:NetStream):void{
            ns = _ns;
            strans = new SoundTransform();
            strans.volume = _volume/100;
            ns.soundTransform = strans;
            but_volon.enabled = true;
        }
    
    
        public function set volume(par:uint):void{
            _volume = par;
            sl.value = _volume;
        }
       
    
        public function get volume():uint{
            return _volume;
        }
    
        public function diactivate():void{
            but_volon.enabled = false;
            but_voloff.visible = false;
            sl.visible = false;
        }
    
    
        private function onClick(e:MouseEvent):void{
            sl.visible = !sl.visible;
        }
        
        private function onChange(e:SliderEvent):void{
            _volume = e.value;
            
            if(_volume){
                but_volon.visible = true;
                but_voloff.visible = false;
            }else{
                but_volon.visible = false;
                but_voloff.visible = true;
            }
            
            strans.volume = _volume/100;
            ns.soundTransform = strans;
        }
    
    
         private function method_timeout(dt:uint, method:Function):void{
            var dtint:uint = setInterval(function(){
                                clearInterval(dtint);
                                method.call(null);
                                }, dt);
        }
    
        
    
    }


}