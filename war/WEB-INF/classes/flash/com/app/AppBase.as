package com.app {

import flash.display.MovieClip;
import flash.utils.*;
import com.window.system.SystemWindow;
import com.window.system.AlertWindow;
import com.window.system.ConfirmWindow;
import com.app.AmfPhp;
import com.adobe.crypto.MD5;

    public class AppBase extends MovieClip
    {
    
        
        
        public function AppBase(){
            
        }
    
    
    
        protected function put_message(cont:String, title:String=""):void{
            if(!title) title="Внимание!";
            var win:AlertWindow = new AlertWindow();
            addChild(win);
            win.show(title, cont);
        }
    
    
    
        protected function put_error(cont:String, title:String="Ошибка!!!"):void{
            put_message(cont, title);
        }
    
    
    
        protected function put_confirm(title:String, cont:String, callback:Function){
            var win:ConfirmWindow = new ConfirmWindow();
            addChild(win);
            win.show(title, cont, callback);
        }
    
    
        protected function php_exec(func_name:String, params:Array, callback:Function, oamf:Object, ltitle:String="Системное сообщение", lcont:String="Выполняется загрузка..."):void{
            var obj:AmfPhp = new AmfPhp(oamf.host, oamf.class_name, oamf.prefix);
            var win = put_system_window(ltitle, lcont);
            obj.exec(func_name, params, function(resp){
                                            win.remove();
                                            if(resp.error){put_error(resp.error);}
                                            else{callback.call(null, resp.result);}
                                        });
        }
        
    
        protected function put_system_window(title:String, cont:String):SystemWindow{
            var win:SystemWindow = new SystemWindow();
            addChild(win);
            win.show(title, cont);
            return win;
        }
        
      
    
        protected function get_param(par:String){
            return stage.loaderInfo.parameters[par];
        }
    
    
        protected function get_count_props(obj:Object):uint{
            if(typeof(obj)=="object"){
                var res:uint = 0;
                for(var i in obj){res++;}
                return res;
            }else return 0;
            
        }
        
    
        protected function get_chost():String{
			var temp = stage.loaderInfo.url;
			var ind1 = temp.indexOf("/");
			if(ind1==-1){return "";}
			ind1+=2;
			var ind2 = temp.indexOf("/",ind1);
			if(ind2==-1){return "";}
			return temp.substring(ind1,ind2);
		}
    
    
        protected function method_timeout(dt:uint, method:Function):void{
            var dtint:uint = setInterval(function(){
                                clearInterval(dtint);
                                method.call(null);
                                }, dt);
        }
    
    
    
        protected function get_random_num(min:uint=10, max:uint=1000000000):uint{
            return Math.round(Math.random()*(max-min))+min;

        }
        
    
        protected function get_random_idname(par:String=""):String{
            return MD5.hash(par+get_ts()+get_random_num());
        }
        
        
        protected function get_ts():uint{
			var c = new Date();
			return c.valueOf();
			}
            
        
        protected function in_array(val:String, arr:Array):int{
        
            for(var i=0; i<arr.length; i++){
            
                if(val == arr[i]){return i;}
            }
            
            
            return -1;
        }

        
    }
}