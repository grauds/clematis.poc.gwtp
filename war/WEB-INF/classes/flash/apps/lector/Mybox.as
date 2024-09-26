package apps.lector {

import flash.display.Sprite;

import flash.events.MouseEvent;
import flash.media.Video;
import flash.media.Camera;




    public class Mybox extends Sprite
    {
    
        
    
        public function Mybox(){
        
            but_micon.addEventListener(MouseEvent.CLICK, onMicon);
            but_micoff.addEventListener(MouseEvent.CLICK, onMicoff);
            but_camon.addEventListener(MouseEvent.CLICK, onCamon);
            but_camoff.addEventListener(MouseEvent.CLICK, onCamoff);
            but_set.addEventListener(MouseEvent.CLICK, onSet);    
           
        }
    
    
        public function init(){
            if(Main.inst.cam_use){
                if(!Main.inst.cam_state){but_camon.visible = false;}
                vid.attachCamera(Main.inst.cam);    
            }else{
                but_camon.visible = false;
                but_camoff.enabled = false;    
            }
            
            if(!Main.inst.mic_state){but_micon.visible = false;}
            volpan.init(Main.inst.mic, Main.inst.tconfig.data.mic_volume);
        }
    
        
    
        public function clear_video(){
            vid.attachCamera(null);
            vid.visible = false;
        }
        
    
        private function onMicon(e:MouseEvent):void{
            but_micon.visible = false;
            Main.inst.set_lector_mic_state(false);
        }
        
        private function onMicoff(e:MouseEvent):void{
            but_micon.visible = true;
            Main.inst.set_lector_mic_state(true);
        }
        
        
        private function onCamon(e:MouseEvent):void{
            but_camon.visible = false;
            vid.visible = false; 
            Main.inst.set_lector_cam_state(false);
        }
        
        
        private function onCamoff(e:MouseEvent):void{
            but_camon.visible = true;
            vid.visible = true;
            Main.inst.set_lector_cam_state(true);
        }
        
        private function onSet(e:MouseEvent):void{
            
            Main.inst.onSetting();
        }
        
    
    }
}