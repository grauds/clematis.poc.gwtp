package apps.student {

import flash.net.NetConnection;
import flash.net.SharedObject;
import flash.net.NetStream;


    public class Dialog
    {
        var nc:NetConnection;
        var core:SharedObject;
        var students:Object = {};
        var is_act:Boolean = false;
        var login:String;
        
        
        public function Dialog(_login:String):void{
        
            nc = Main.inst.nc;
            login = _login;
            create_core();
        }
        
        
        
    
        private function create_core():void{
        
            core = SharedObject.getRemote("core_dialog", nc.uri, true);
            
            var obj = {};
            core.client = obj;
            
            obj.onAddStudent = function(_login:String){
                if(!is_act && login==_login){
                    is_act = true;
                    var data:Object = core.data.students;
                    
                    for(var i in data){
                        if(i!= login){add_student(i);}
                    }
                }else if(is_act){add_student(_login);}
            };
            
            obj.onDeleteStudent = function(_login:String){
                if(is_act){
                    if(login==_login){
                        for(var i in students){
                            students[i].close();
                            delete(students[i]);
                        }
                        is_act = false;
                    }else{
                        delete_student(_login);
                    }
                }
            };
            
            
            obj.onStop = function(){
                if(is_act){
                    for(var i in students){
                        students[i].close();
                        delete(students[i]);
                    }
                    is_act = false;
                }
            };
        
            core.connect(nc);
        }
    
        
        private function add_student(_login:String):void{
            var ns = new NetStream(nc);
            ns.receiveVideo(false);
            ns.play(_login, -1);
            students[_login] = ns;
        }
        
        
        private function delete_student(_login:String):void{
            students[_login].close();
            delete(students[_login]);
        }
    
    
        
    
    
    }
}