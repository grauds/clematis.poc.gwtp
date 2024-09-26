package apps.lector {

import flash.display.Sprite;
import flash.events.MouseEvent;
import flash.geom.Rectangle;

import apps.lector.Smiles_data;

    public class SmileWin extends Sprite {
    
    var cont:Sprite;
    var method:Function;
    var drag:Boolean = false;
    var slider_y0:Number;
    var slider_x0:Number;
    var slider_maxy:Number;
    var kof:Number = 1;
    
        public function SmileWin(callback:Function){
                
            method = callback;
            
            cont = new Sprite();
            browser.addChild(cont);
            
            init();     
            
        }
                                                          
    
        private function init():void{
            var x0 = 5;
            var y0 = x0;
            var dx = 45;
            var dy = dx;
            var row_count = 5;
            var row_pos = 0;
            
            for(var i in Smiles_data.data){
                var sm = new TchatSmile(Smiles_data.data[i]);
                cont.addChild(sm);
                sm.addEventListener(MouseEvent.CLICK, onSmile);
                sm.x = x0;
                sm.y = y0;
                row_pos++;
                
                x0+=dx;
                
                if(row_pos==row_count){
                    x0 = 5;
                    y0+=dy;
                    row_pos = 0;
                }
            }
            
            
            init_place();
            
        }
        
        
        
        private function init_place():void{
            slider_y0 = slider.y;
            slider_x0 = slider.x;
            trace("cont.height= "+cont.height);
            trace("browser.height= "+browser.height);
            var dh = cont.height - 170;
            trace("dh= "+dh);
            var max_scrool_y = -dh;
            var slider_maxheight = slider.height;
            var slheight = slider.height - dh;
             trace(slheight);
            if(slheight<=10){slider.height = 10;}
            else{slider.height = slheight;}
            
            slider_maxy = slider_y0+(slider_maxheight-slider.height);
            kof = (slider_maxy-slider_y0)/Math.abs(max_scrool_y);
            
            slider.addEventListener(MouseEvent.MOUSE_DOWN, handMouseDown);
            slider.addEventListener(MouseEvent.MOUSE_MOVE, handMouseMove);
            slider.addEventListener(MouseEvent.MOUSE_UP, handMouseUp);
            addEventListener(MouseEvent.ROLL_OUT, handMouseUp);
            
            but_close.addEventListener(MouseEvent.CLICK, onClose);
            
        }
        
        
        private function handMouseDown(e:MouseEvent){
            slider.startDrag(false, new Rectangle(slider_x0, slider_y0, 0, slider_maxy));
            drag = true;
        }
        
        
        private function handMouseUp(e:MouseEvent){
            if(drag){
                drag = false;
                slider.stopDrag();
            }            
        }
        
        private function handMouseMove(e:MouseEvent){
            if(drag){
                cont.y = -((slider.y-slider_y0)/kof);
            }
            
        }
        
        
        private function onSmile(e:MouseEvent):void{
            method.call(null, e.currentTarget.id);
        }
        
        private function onClose(e:MouseEvent):void{
            method.call(null, false);
        }
    
    }


}