package apps.student {


import apps.student.Javascript;
import apps.student.Log_remote;

    public class Log
    {
    
        var power:Boolean = true;
        var classId:String;
        var host:String;
        var api_path:String = "/events";
        
    
        public function Log(chost:String, clid:String){
        
            host = chost;
            classId = clid;    
            
        }
    
    
    
        
        
        public function add_student(userId:String):void{
            var type:String = "student_add";
            var time:String = get_format_date();
            
            if(power){
                add_remote({classId:classId, type:type, time:time, userId:userId});
                Javascript.callMethod("onUserAdd", [classId, userId, time]);
            }
        }
        
        
        public function out_student(userId:String):void{
            var type:String = "student_unadd";
            var time:String = get_format_date();
            
            if(power){
                add_remote({classId:classId, type:type, time:time, userId:userId});
                Javascript.callMethod("onUserUnadd", [classId, userId, time]);
            }
        }
    
    
         public function student_not_present(userId:String, desc:String):void{
            var type:String = "student_inactiv";
            var time:String = get_format_date();
            
            if(power){
                add_remote({classId:classId, type:type, time:time, userId:userId, description:desc});
                Javascript.callMethod("onUserInactiv", [classId, userId, desc, time]);
            }
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