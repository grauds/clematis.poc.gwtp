package apps.lector {

import flash.display.Sprite;

import flash.events.MouseEvent;



    public class Classroom extends Sprite
    {
    
        var students:Object = {};
        public static var cinst:Classroom;
        
        var sizes:Array;
        
        const LAB_LECTOR = "Говорит лкетор";
        const LAB_STUDENTS = "Говорят студенты";
    
        public function Classroom(){
        
            cinst = this;
            but_reset.addEventListener(MouseEvent.CLICK, onReset);
            but_mode.addEventListener(MouseEvent.CLICK, onMode);    
            make_sizes();
        }
    
    
        public function add_student(params:Object):void{
            students[params.login] = new Student(params); 
            addChild(students[params.login]);
            update_place();
        }
    
        
        public function delete_student(login:String):void{
            students[login].close();
            removeChild(students[login]);
            delete(students[login]);
            update_place();
        }
        
        
        public function request_ruka(from:String):void{
            students[from].request_ruka();
        }
        
        
        public function set_cam_state(from:String, state:Boolean):void{
            students[from].set_cam_state(state);
        }
        
        
        public function set_mic_state(from:String, state:Boolean):void{
            students[from].set_mic_state(state);
        }
        
        public function ruka_down(only:String):void{
            for(var i in students){
                if(students[i].is_act && i!=only){students[i].set_normal();}
            }
        }
        
        
        public function get_act():String{
            for(var i in students){
                if(students[i].is_act){return i;}
                
            }
            
            return "";
        }
        
        
        public function reset():void{
            var f:Boolean = false;
            for(var i in students){
                if(students[i].is_act){f=true;}
                students[i].set_normal();
            }
            
            if(Main.inst.lmode && f){Main.inst.after_reset();}
        }
        
        
        private function onReset(e:MouseEvent):void{
            reset();
            
        }
        
        
        private function onMode(e:MouseEvent):void{
            if(Main.inst.lmode){but_mode.label = LAB_STUDENTS;}
            else{but_mode.label = LAB_LECTOR;}
            
            Main.inst.set_lmode(!Main.inst.lmode);    
        }
        
        
    
        private function update_place(){
            
            var conf:Object = sizes[get_count_props(students)];
            
            var x0:Number = conf.x0;
            
            var xpos:Number = x0;
            var ypos:Number = conf.y0;
            var dx:Number = conf.width+conf.dd;
            var dy:Number = conf.height+conf.dd;
            var row_count:uint = conf.row_count;
            
            var row_pos:uint = 0;
            
            for(var i in students){
                students[i].x = xpos;
                students[i].y = ypos;
                students[i].width = conf.width;
                students[i].height = conf.height;
                xpos+=dx;
                row_pos++;
                if(row_pos==row_count){
                    row_pos = 0;
                    xpos = x0;
                    ypos+=dy;
                }
            }            
        }


        private function make_sizes():void{
            sizes = [
                        {},
                        {x0:305, y0:130, dd:0, width:240, height:220, row_count:1},
                        {x0:180, y0:130, dd:10, width:240, height:220, row_count:2},
                        {x0:55, y0:130, dd:10, width:240, height:220, row_count:3},
                        {x0:180, y0:5, dd:10, width:240, height:220, row_count:2},
                        {x0:22, y0:5, dd:10, width:240, height:220, row_count:3},
                        {x0:22, y0:5, dd:10, width:240, height:220, row_count:3},
                        {x0:5, y0:48, dd:5, width:200, height:180, row_count:4},
                        {x0:5, y0:48, dd:5, width:200, height:180, row_count:4},
                        {x0:5, y0:88, dd:5, width:160, height:140, row_count:5},
                        {x0:5, y0:88, dd:5, width:160, height:140, row_count:5}
                        ];
        }


        private function get_count_props(obj:Object):uint{
            if(typeof(obj)=="object"){
                var res:uint = 0;
                for(var i in obj){res++;}
                return res;
            }else return 0;            
        }

    
    }
}