package apps.lector {

import flash.display.Sprite;

import flash.events.MouseEvent;
import fl.events.SliderEvent;
import flash.events.NetStatusEvent;

import flash.net.NetConnection;
import flash.net.NetStream;
import flash.media.Video;

import com.Load_photo;
import flash.utils.*;


    public class Student extends Sprite
    {
    
        var login:String;
        var params:Object;
        var ns:NetStream;
        
        var is_act:Boolean = false;
        var is_ruka:Boolean = false;
        var nplay:uint = 0;
    
        public function Student(par:Object){
        
            params = par;
            login = params.login;
            
            but_noact.addEventListener(MouseEvent.CLICK, onNoact);
            but_ruka.addEventListener(MouseEvent.CLICK, onRuka);
            but_act.addEventListener(MouseEvent.CLICK, onAct);
            but_chat.addEventListener(MouseEvent.CLICK, onChat);
            
            mact.visible = false;
            
                    
             init();
        }
    
    
        private function init(){
            mact.visible = false;
            ns = new NetStream(Main.inst.nc);
            ns.addEventListener(NetStatusEvent.NET_STATUS, onNetStatus);
            ns.client = {};
            ns.client.onMetaData = function(item:Object):void{};
            
            ns.receiveAudio(false);
            var obj:Load_photo = new Load_photo(params.photo, photo);
            txt_nick.text = params.nick;
            ns.play(login, -1);
            vid.attachNetStream(ns);
            apply_vstate(params.cam_state);
        }
    
    
        private function onNoact(e:MouseEvent):void{
            but_noact.visible = false;
            but_ruka.visible = false;
            mact.visible = true;
            ns.receiveAudio(true);
            is_act = true;
            if(Main.inst.lmode){Classroom.cinst.ruka_down(login);}
            Main.inst.response_ruka(login);  
        }
        
        private function onRuka(e:MouseEvent):void{
            but_noact.visible = false;
            but_ruka.visible = false;
            mact.visible = true;
            ns.receiveAudio(true);
            is_act = true;
            if(Main.inst.lmode){Classroom.cinst.ruka_down(login);}
            Main.inst.response_ruka(login);     
        }
        
        private function onAct(e:MouseEvent):void{
            but_noact.visible = true;
            but_ruka.visible = true;
            mact.visible = false;
            ns.receiveAudio(false);
            is_act = false;
            is_ruka = false;
            Main.inst.response_lector_ruka_down(login);    
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
                    else{Classroom.cinst.delete_student(login);}
                    break;
            
            }
            
        }
        
    
        public function request_ruka():void{
            mact.visible = true;
            but_noact.visible = false;
            but_ruka.visible = true;
            is_ruka = true;
        }
        
        
        public function set_normal(){
            but_noact.visible = true;
            but_ruka.visible = true;
            mact.visible = false;
            ns.receiveAudio(false);
            if(is_act){Main.inst.response_ruka_down(login);}
            is_act = false;
            is_ruka = false;    
        }
        
        
        public function set_cam_state(state:Boolean):void{
            apply_vstate(state);
        }
        
        public function set_mic_state(state:Boolean):void{
            
        }
    
    
        public function close():void{
            ns.close();
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