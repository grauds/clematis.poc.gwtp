package apps.lector {

import flash.display.Sprite;
                                                         
import flash.events.MouseEvent;
import flash.events.TextEvent;
import fl.events.ComponentEvent;
import flash.events.Event;

import flash.text.StyleSheet;
import flash.geom.Rectangle;

import flash.utils.*;


import apps.lector.TchatWindow;
import apps.lector.Smiles_data;


    public class Tchat extends Sprite
    {
    
        
        var smile_path:String;
        
        var end_index:int = -1;
        
        var wcss:StyleSheet;
        
        public static var tinst:Tchat;
        
        var tologin:String; 
    
        public function Tchat(){
        
            tinst = this;
        
            txt_chat.addEventListener(TextEvent.LINK, onLink);
            txt_message.addEventListener(ComponentEvent.ENTER, onEnter);
            txt_chat.addEventListener(Event.SCROLL, onScroll);
            but_send.addEventListener(MouseEvent.CLICK, onSend);
            but_smile.addEventListener(MouseEvent.CLICK, onSmile);
            
            method_timeout(10, function(){
                txt_chat.htmlText="";
                txt_chat.mouseWheelEnabled = true;
                txt_chat.wordWrap = true;
                scbar.setScrollProperties(15, 1, txt_chat.maxScrollV, 1);
                init_styles();
                smile_path = "http://"+Main.inst.chost+"/"+Main.inst.context_path+"/web/emoticons/";
            }); 
        
        }
    
    
        public function add_message(from:Object, message:String):void{
            
            var attr:Object = parse_message(from, message);
            
            txt_chat.scrollV = txt_chat.maxScrollV;
            tplace.set_scrool(txt_chat.scrollV);
            scbar.maxScrollPosition = txt_chat.maxScrollV;
            
            method_timeout(10, function(){
                txt_chat.htmlText+=attr.message;
            
           
            
                if(attr.smiles.length){
                    for(var i=0; i<attr.smiles.length; i++){
                        var pobj:Rectangle = txt_chat.getCharBoundaries(end_index+attr.smiles[i].pos+1);
                        var coord:Object = {x:pobj.x, y:pobj.y};
                        tplace.add_smile(coord, Smiles_data.get_smile(attr.smiles[i].id));
                    }
                }
            
                txt_chat.scrollV = txt_chat.maxScrollV;
                tplace.set_scrool(txt_chat.scrollV);
                end_index = txt_chat.length-1;
                trace("numLines "+txt_chat.numLines);
                scbar.maxScrollPosition = txt_chat.maxScrollV;
            });
             
            	
        }                                                 
    
    
        public function set_to(par:*):void{
            if(typeof(par)=="object"){
                tologin = par.login;
                txt_message.text = par.nick+" ";
            }else{
                var t_arr = par.split("###");
                tologin = t_arr[1];
                txt_message.text = t_arr[0]+" ";
            }
            
            txt_message.setFocus();
            txt_message.setSelection(txt_message.text.length, txt_message.text.length);
        }
    
    
        public function diactivate():void{
            txt_chat.removeEventListener(TextEvent.LINK, onLink);
            txt_message.removeEventListener(ComponentEvent.ENTER, onEnter);
            txt_message.visible = false;
            but_send.visible = false;
            but_smile.visible = false;
        }
    
        
        private function onLink(e:TextEvent):void{
            set_to(e.text);
        }
    
    
    
        private function onSend(e:MouseEvent):void{
            send_message();
        }
    
    
    
        private function onEnter(e:ComponentEvent):void{
           send_message();
            
        }
        
        private function onScroll(e:Event):void{
            tplace.set_scrool(txt_chat.scrollV);
        }
    
        private function send_message():void{
            if(txt_message.text!=""){
                var mess = txt_message.text;
                
                Main.inst.add_tchat_message(tologin, mess);
                txt_message.text = "";
                tologin = "";
            }
        }
        
    
        private function onSmile(e:MouseEvent):void{
//            smile_path = "http://"+Main.inst.chost+"/web/emoticons/";
            var win:TchatWindow = new TchatWindow();
            addChild(win);
            win.show(function(resp){
                if(resp){
                    
                    txt_message.text+= "(:SM"+resp+":)";
                    txt_message.setFocus();
                    txt_message.setSelection(txt_message.text.length, txt_message.text.length);
                }
            });
        }
        
        
        private function parse_message(from:Object, message:String):Object{
            var endpos:int = from.nick.length+1;
            var mess:String = "";
            var smiles:Array = [];
            var wpos:int = message.indexOf("(:SM");
            if(wpos!=-1){
                var sind:int = 0;
                var lpos:int = message.length - 1;
                while(wpos!=-1){
                    mess+= message.substring(sind, wpos);
                    var smpos:int = endpos+mess.length+1;
                    mess+="          ";
                    var wpos2:int = message.indexOf(":)", wpos);
                    if(wpos2!=-1){
                        var smid:int = int(message.substring(wpos+4, wpos2));
                        smiles.push({id:smid, pos:smpos});
                        sind = wpos2+2;
                        if(sind>=lpos){wpos = -1;}
                        else{wpos = message.indexOf("(:SM", sind);}
                    }else{
                        sind = wpos+1;
                        if(sind>=lpos){wpos = -1;}
                        else{wpos = message.indexOf("(:SM", sind);}
                    }    
                }
                if(sind<lpos){mess+= message.substring(sind);}
            }else{mess = message;}
            
            var rmes = "<p>";
            if(from.login!=Main.inst.login){rmes+=get_other(from);}
            else{rmes+=get_my(from.nick);}
            
            rmes+=get_message(mess)+"</p><br>";
            
            return {message:rmes, smiles:smiles};
            
        }
        
        
        private function init_styles():void{
            var tfmy:Object = {
                                fontFamily:"Arial",
                                fontSize:12,
                                color:"#FF0000",
                                fontWeight:"bold"
                                };
                                
            var tfnonmy:Object = {
                                fontFamily:"Arial",
                                fontSize:12,
                                color:"#0000FF",
                                fontWeight:"bold"
                                };
                                
            var ctext:Object = {
                                fontFamily:"Arial",
                                fontSize:12,
                                color:"#000000",
                                fontWeight:"normal"
                                };
           
            
            wcss = new StyleSheet();
            wcss.setStyle(".mylogin", tfmy);
            wcss.setStyle(".nonmylogin", tfnonmy);
            wcss.setStyle(".ctext", ctext);
            txt_chat.styleSheet = wcss;
        }
        
        
       
        
        
    
        private function get_my(from:String):String{
            return "<span class=\"mylogin\">"+from+": </span>";
        }
    
    
        private function get_other(from:Object):String{
            return "<a href=\"event:"+from.login+"###"+from.nick+"\" class=\"nonmylogin\">"+from.nick+": </a>";
        }
    
    
        private function get_message(mes:String):String{
            return "<span class=\"ctext\">"+mes+"</span>";           
        }
    
    
        private function method_timeout(dt:uint, method:Function):void{
            var dtint:uint = setInterval(function(){
                                clearInterval(dtint);
                                method.call(null);
                                }, dt);
        }
        
        
    
    }


}