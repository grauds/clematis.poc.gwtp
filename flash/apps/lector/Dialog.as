package apps.lector {

import flash.net.NetConnection;
import flash.net.SharedObject;


    public class Dialog
    {
        var nc:NetConnection;
        var core:SharedObject;
        
        
        public function Dialog():void{
        
            nc = Main.inst.nc;
            
            create_core();
        }
        
        public function add_student(login:String):void{
            var data:Object = core.data.students;
            data[login] = login;
            core.setProperty("students", data);
            core.send("onAddStudent", login);
        }
        
        public function delete_student(login:String):void{
            var data:Object = core.data.students;
            delete(data[login]);
            core.setProperty("students", data);
            core.send("onDeleteStudent", login);
        }
        
        
        public function stop():void{
            core.setProperty("students",{});
            core.send("onStop");    
        }
        
    
        private function create_core():void{
        
            core = SharedObject.getRemote("core_dialog", nc.uri, true);
            
            var obj = {};
            core.client = obj;
            
            obj.onAddStudent = function(login:String){
            
            };
            
            obj.onDeleteStudent = function(login:String){
            
            };
            
            
            obj.onStop = function(){
            
            };
        
            core.connect(nc);
        }
    
    
    
    }
}