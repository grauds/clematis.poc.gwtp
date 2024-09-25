package apps.lector {

import flash.display.Sprite;
import flash.events.MouseEvent;
import fl.events.SliderEvent;
import fl.data.DataProvider;                 
import flash.utils.*;


    public class SettingWin extends Sprite
    {
    
        
        var method:Function;
        
       
        
       
        
        
        
        public function SettingWin(callback:Function){
           
            
            method = callback;
            
            method_timeout(10, function(){init();});
            
        }
    
    
    
        private function init(){
            init_cams();
            init_mics();
            
            colpic.selectedColor = Main.inst.tconfig.data.bgcolor;
            
            sl_quality.value = Main.inst.tconfig.data.vquality;
            txt_quality.text = ""+sl_quality.value+"%";
            sl_quality.addEventListener(SliderEvent.CHANGE, function(e:SliderEvent){txt_quality.text = ""+e.value+"%";});
            sl_volume.value = Main.inst.tconfig.data.mic_volume;
            txt_volume.text = ""+sl_volume.value+"%";
            sl_volume.addEventListener(SliderEvent.CHANGE, function(e:SliderEvent){txt_volume.text = ""+e.value+"%";});
            
            but_ok.addEventListener(MouseEvent.CLICK, onSave);
            but_cancel.addEventListener(MouseEvent.CLICK, onCancel);
            colpic.addEventListener(MouseEvent.CLICK, function(e:MouseEvent){colpic.open();});
        }
    
        private function init_cams(){
            var arr = [];
            arr.push({label:"none", data:-1});
            
            for(var i=0; i<Main.inst.cams.length; i++){
                arr.push({label:Main.inst.cams[i], data:i});
            }
            
            var dp:DataProvider = new DataProvider(arr);
            cb_cams.dataProvider = dp;
            
            if(Main.inst.tconfig.data.vdev!="none"){cb_cams.selectedIndex = in_array(Main.inst.tconfig.data.vdev, Main.inst.cams)+1;}
            
            
        }
        
        
        private function init_mics(){
            var arr = [];
           
            
            for(var i=0; i<Main.inst.mics.length; i++){
                arr.push({label:Main.inst.mics[i], data:i});
            }
            
            var dp:DataProvider = new DataProvider(arr);
            cb_mics.dataProvider = dp;
            
            cb_mics.selectedIndex = in_array(Main.inst.tconfig.data.adev, Main.inst.mics);
            
            
        }
        
        
       
        
    
        
        private function onSave(e:MouseEvent){
            
            var obj:Object = {
                                mic_volume:sl_volume.value,
                                bgcolor:colpic.selectedColor,
                                vdev:cb_cams.selectedItem.label,
                                adev:cb_mics.selectedItem.label,
                                vquality:sl_quality.value
                                };
                                
            Main.inst.tconfig.set_params(obj);
            method.call(null, true);
        }
        
        
        private function onCancel(e:MouseEvent){
            method.call(null, false);
        }
        
        
       
    
        private function in_array(val:String, arr:Array):int{
        
            for(var i=0; i<arr.length; i++){
            
                if(val == arr[i]){return i;}
            }
            
            
            return -1;
        }
    
    
        private function method_timeout(dt:uint, method:Function):void{
            var dtint:uint = setInterval(function(){
                                clearInterval(dtint);
                                method.call(null);
                                }, dt);
        }
       
        
    }


}