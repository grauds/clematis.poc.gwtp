package apps.lector {


import apps.lector.Javascript;
import apps.lector.Log_remote;

    public class Log
    {
    
        var power:Boolean = true;
        var classId:String;
        var host:String;
        var api_path:String = "/events";
        var xml_content:String = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        var xml_body:String="";
    
        public function Log(chost:String, clid:String){
        
            host = chost;
            classId = clid;    
            
        }
    
    
    
        public function lesson_start():void{
            var type:String = "lesson_begin";
            var time:String = get_format_date();
            if(power){
                add_remote({classId:classId, type:type, time:time});
                Javascript.callMethod("onLessonStart", [classId, time]);
            }
            xml_content+= "<lesson id=\""+classId+"\" start=\""+time+"\" ";
        }
    
    
    
        public function lesson_end():void{
            var type:String = "lesson_end";
            var time:String = get_format_date();
            xml_content+= "end=\""+time+"\">\n";
            xml_body+= "</lesson>\n";
            var protocol:String = xml_content+xml_body;
            if(power){
                add_remote({classId:classId, type:type, time:time, protocol:protocol});
                Javascript.callMethod("onLessonEnd", [classId, time]);
            }
        }
        
        
        public function add_student(uobj:Object, fsend:Boolean=true):void{
            var type:String = "student_add";
            var time:String = get_format_date();
            
            if(power && fsend){
                add_remote({classId:classId, type:type, time:time, userId:uobj.login});
                Javascript.callMethod("onUserAdd", [classId, uobj.login, time]);
            }
            
            xml_body+= "<join id=\""+uobj.login+"\" name=\""+uobj.nick+"\" role=\"student\" time=\""+time+"\" />\n";
        }
        
        
        public function out_student(userId:String):void{
            var type:String = "student_unadd";
            var time:String = get_format_date();
            
            if(power){
                add_remote({classId:classId, type:type, time:time, userId:userId});
                Javascript.callMethod("onUserUnadd", [classId, userId, time]);
            }
            xml_body+= "<out id=\""+userId+"\" time=\""+time+"\" />\n";
        }
    
    
        public function student_speak(userId:String):void{
            var time:String = get_format_date();
            xml_body+= "<speak id=\""+userId+"\" time=\""+time+"\" />\n";
        }
    
       
        private function add_remote(data:Object):void{
            var obj = new Log_remote("http://"+host+api_path);
            obj.load(data, null);
        }
        
        
        private function get_format_date(){
            var d = new Date();
            var year = d.fullYear;
            var month = d.month+1;
            if(month<10){month = "0"+month};
            var day = d.date;
            if(day<10){day = "0"+day;}
            var hour = d.hours;
            if(hour<10){hour = "0"+hour;}
            var min = d.minutes;
            if(min<10){min = "0"+min;}
            var sec = d.seconds;
            if(sec<10){sec = "0"+sec;}
            var dt = d.timezoneOffset/60;
            
            return ""+year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec+" "+dt;
            
        }
        
        
    }
}