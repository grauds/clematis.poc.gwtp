package apps.student {

import flash.display.Sprite;

import flash.events.MouseEvent;

import flash.events.NetStatusEvent;

import flash.net.NetConnection;
import flash.net.NetStream;
import flash.media.Video;
import flash.utils.*;
import com.Load_photo;



    public class Lector extends Sprite
    {
    
        var login:String = "lector";
        var params:Object;
        var ns:NetStream;
        
        var nplay:uint = 0;
        
    
        public function Lector(){
        
            
            but_ruka.addEventListener(MouseEvent.CLICK, onRuka);
            but_chat.addEventListener(MouseEvent.CLICK, onChat);
            but_ruka.enabled = false;
            but_chat.enabled = false;
            
        }
    
    
        public function init(par){
            params = par;
            
            
            ns = new NetStream(Main.inst.nc);
            ns.addEventListener(NetStatusEvent.NET_STATUS, onNetStatus);
            ns.client = {};
            ns.client.onMetaData = function(item:Object):void{};
            
            var obj:Load_photo = new Load_photo(params.photo, photo);
//            txt_nick.text = params.nick;
            ns.play(login, -1);
            vid.attachNetStream(ns);
            Main.inst.request_states();
            but_ruka.enabled = true;
            but_chat.enabled = true;
        }
    
    
        
        
        private function onRuka(e:MouseEvent):void{
            
            Main.inst.request_ruka();     
        }
        
       
        private function onChat(e:MouseEvent):void{
            
            Main.inst.set_tchat_to({login:login, nick:params.nick});     
        }
        
        
        private function onNetStatus(e:NetStatusEvent){
            switch(e.info.code){
            
                case "NetStream.Play.Start":
                    volpan.init(ns);
                    break;
                    
                case "NetStream.Play.StreamNotFound":
                    nplay++;
                    if(nplay<=20){method_timeout(2000, function(){ns.play(login, -1);});}
                   
                    break;
            
            }
            
        }
        
    
        public function response_ruka():void{
            
            but_ruka.visible = false;
        }
        
        
        public function response_ruka_down():void{
            
            but_ruka.visible = true;
        }
        
        
        
        
        
        public function set_cam_state(state:Boolean):void{
            apply_vstate(state);
        }
        
        public function set_mic_state(state:Boolean):void{
            ns.receiveAudio(state);    
        }
    
    
        private function apply_vstate(par:Boolean){
            ns.receiveVideo(par);
            if(par){
                vid.visible = true;                
                photo.visible = false;
            }else{
                vid.visible = false;                
                photo.visible = true;
            }
        }
    
    
        private function method_timeout(dt:uint, method:Function):void{
            var dtint:uint = setInterval(function(){
                                clearInterval(dtint);
                                method.call(null);
                                }, dt);
        } 
        
    
    
    }
}