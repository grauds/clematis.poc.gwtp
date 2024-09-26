package apps.lector {
	
	import com.app.AppBase;
	

    import flash.events.NetStatusEvent;
    import flash.events.Event;
	import flash.events.MouseEvent;
    import com.window.CheckMedia;
    import flash.events.FullScreenEvent;
    import flash.events.SyncEvent;         
    import flash.events.AsyncErrorEvent;
    
    
    
   
    
	
    import flash.net.NetConnection;
    import flash.net.NetStream;
    import flash.net.SharedObject;
    import flash.net.Responder;
	
    import flash.media.Microphone;
    import flash.media.SoundCodec;
    import flash.media.MicrophoneEnhancedMode;
    import flash.media.MicrophoneEnhancedOptions;
    
    import flash.media.Camera;
    
    import flash.display.Stage;
    import flash.display.StageDisplayState;
    import flash.display.StageAlign;
    import flash.display.StageScaleMode;
	
	import flash.utils.*;
	
    import apps.lector.Log;
    import apps.lector.Server_data;
    import apps.lector.Tconfig;
    import apps.lector.Dialog;
    import apps.lector.SettingWindow;
	
	public class Main extends AppBase
	{
		
		
		
		var nc:NetConnection;
        var ns:NetStream;
        

        var cam:Camera;
        var cam_state:Boolean = true;
        var cam_use:Boolean = true;
        var cams:Array;
        
        var mic:Microphone;        
        var mic_state:Boolean = true;
        var mics:Array;
		
		var core:SharedObject;
        
		
		
		var login:String = "lector";
        var nick:String = "";
		var photo:String;
        var userId:String;
        
        
		var classId:String;
        var lessonId:String;
        var max_students:uint = 16;
        
        var chost:String;
		
        var first:Boolean = true;
        var reject:Boolean = false;
		
		var tconfig:Tconfig;
        
        var lmode:Boolean = true;
        var lrec:Boolean = true;
	
		
		var server_addr:String = "rtmp://77.91.193.156/classroom";
        
        var context_path:String = "";
        
        
        var log:Log;
        var dialog:Dialog;
        
        public static var inst:Main;
        
       
		
		
		
		public function Main() {
		 
          inst = this;
         
        stage.scaleMode = StageScaleMode.NO_SCALE; 
		stage.align = StageAlign.TOP_LEFT;
		stage.addEventListener(FullScreenEvent.FULL_SCREEN, full_screen_handler);
       
        chost = get_chost();
//        chost = "77.91.193.156:8118";
         
  //     var clid = "english2-t20120422081012";
         
          var t = get_param("classId").split("-");
//        var t = clid.split("-");
           
          classId = t[0];
          
          lessonId = t[1];
          
          
          
          userId = get_param("userId");
//          userId = "victor";
          nick = get_param("nick");
//          nick = "Victor";
          photo = get_param("photo");
//          photo = "http://domain.com/photo.jpg";
          
          var max = get_param("max_users");
//          var max = 10;
          if(max){max_students = uint(max);}
          
          context_path = get_param("context_path");
          
//          context_path = "el-prototype2";
          
            
            init();
            
		}
		
		
        
        private function init(){
            
            if(Microphone.names.length){
                tconfig = new Tconfig();
                bg_app.color = tconfig.data.bgcolor;
                cams = Camera.names;
                init_cam();
                mics = Microphone.names;
                init_mic();
                
                mybox.init();
                
                log = new Log(chost+"/"+context_path, classId+"-"+lessonId);
                var mint = setInterval(function(){mybox.pbar.set_progress(mic.activityLevel);}, 100);
               
                var obj = new Server_data(chost+"/"+context_path);
                obj.load(function(resp){
                    if(resp.error){put_error("Произошла ошибка при соединении!\n"+resp.error+"\nПопробуйте позже!");}
                    else{
                        server_addr = "rtmp://"+resp.server+"/classroom";
                        
                        create_connection(function(e:NetStatusEvent){
                            trace(e.info.code);
                            switch (e.info.code){                                   
                            
                            case "NetConnection.Connect.Success":
                              
                               create_core(); 
	                           break;
					
				            case "NetConnection.Connect.Close":
	                           if(first && !reject){put_error("Не удалось установить соединение с сервером!");}
	                           break;
                               
                                                                               
                            case "NetConnection.Connect.Rejected":
                                        reject = true;
	                                   
                                            if(e.info.application.message=="user exists"){
                                                mybox.clear_video();
                                                blocking();
                                                put_error("Дублирование сессии не допустимо!");
                                            }else{
                                                mybox.clear_video();
                                                blocking();
                                                put_error("Вход отклонен по техническим причинам!");    
                                            }    
                                        
	                                   break;
                               
	                           }
                        });
                    }
                });
               
                
            
            }else{
                mybox.clear_video();
                blocking();
                put_error("Microphone not found!");
            }    
         
                
        }
        
        
        
        
		private function create_connection(callback:Function):void{
			nc = new NetConnection();
			nc.addEventListener(NetStatusEvent.NET_STATUS, callback);
            
            var obj = {};
            nc.client = obj;
            
            obj.add_student = function(obj:Object):void{
                classroom.add_student(obj);
                log.add_student(obj);
            };
            
            obj.delete_student = function(login:String):void{
                classroom.delete_student(login);
                log.out_student(login);
            };
            
           
            
            nc.connect(server_addr+"/"+classId+"_"+lessonId, 
                        {login:userId, type:'lector', nick:nick, photo:photo, max_students:max_students, room:classId+"_"+lessonId});
			}
		
		
	   private function create_core():void{
       
            core = SharedObject.getRemote("core", nc.uri, true);
            
            var obj = {};
            core.client = obj;
            
            obj.add_message = function(from:Object, to:String, message:String){
//                if(from.login==login || to==login){
                    tchat.add_message(from, message);
//                }
            };
            
            obj.request_states = function(from:String){
                core.send("response_states", from, cam_state, mic_state);           
            };
            
            
            obj.response_states = function(to:String, cstate:Boolean, mstate:Boolean){
                
            };
            
            obj.message_end = function(){
                
            };
            
            obj.request_ruka = function(from:String){
                classroom.request_ruka(from);       
            };
            
            obj.response_ruka = function(to:String){
                    
            };
            
            obj.response_ruka_down = function(to:String){
                    
            };
            
            obj.set_lector_cam_state = function(state:Boolean){
                    
            };
            
            obj.set_lector_mic_state = function(state:Boolean){
                    
            };
            
            obj.set_cam_state = function(from:String, state:Boolean){
                classroom.set_cam_state(from, state);       
            };
            
            obj.set_mic_state = function(from:String, state:Boolean){
                classroom.set_mic_state(from, state);        
            };
            
            
            core.addEventListener(SyncEvent.SYNC, corehandler);
			core.connect(nc);
       }
	   
       
       private function corehandler(e:SyncEvent):void{
            core.removeEventListener(SyncEvent.SYNC, corehandler);
            dialog = new Dialog();
            var data:Object = core.data;
           first = false; 
           lesson_start(data.students);
            
       }
       
       
       private function full_screen_handler(e:FullScreenEvent):void{
            if(e.fullScreen){
                
                
               
            }else{
                
                
                
                
            }
        }
	   
       private function record_continue(method:Function, sn:String="lector"):void{
            nc.call("record_continue", new Responder(function(par){
                                                lrec = (sn=="lector")? true: false;
                                                method.call(null);
                                                }), sn);
       }
       	
       	
		
///////////////////////////////////////////////////////////////////////////////////////////////////////

    public function onChatState():void{
        tchat.visible = !tchat.visible;
    }


    public function onEndLesson():void{
        nc.call("record_stop",
                new Responder(function(par){
                    mybox.recstat.status = false;
                    core.send("message_end");
                    log.lesson_end();
                    ns.close();
                    nc.close();
                    mybox.clear_video();
                    blocking();
                })
                );
        
    }


    public function onFullMode():void{
    
    }

    public function set_lector_cam_state(state:Boolean):void{
        cam_state = state;
        core.send("set_lector_cam_state", state);
    }
    
    public function set_lector_mic_state(state:Boolean):void{
        mic_state = state;
        core.send("set_lector_mic_state", state);
    }


    public function add_tchat_message(to:String, message:String){
        core.send("add_message", {login:login, nick:nick}, to, message);
    }


    public function set_tchat_to(obj:Object):void{
        tchat.set_to(obj);
    }

    public function response_ruka(to:String):void{
        log.student_speak(to);
        if(lmode){
            mybox.recstat.status = false;
        
            record_continue(function(){
                        mybox.recstat.status = true;
                        core.send("response_ruka", to);
                        }, to);
        }else{
            dialog.add_student(to);
            core.send("response_ruka", to);
        }
        
                
    }
    
    public function response_ruka_down(to:String):void{
        if(!lmode){dialog.delete_student(to);}
        
        core.send("response_ruka_down", to);
    }
    
    
    public function response_lector_ruka_down(to:String):void{
        if(lmode){
            mybox.recstat.status = false;
            record_continue(function(){
                mybox.recstat.status = true;
                core.send("response_ruka_down", to);
            });
        }else{
            dialog.delete_student(to);
            core.send("response_ruka_down", to);
            }
    }


    public function after_reset():void{
        mybox.recstat.status = false;
        record_continue(function(){
            mybox.recstat.status = true;
            
        });
        
    }

    public function set_lmode(par:Boolean):void{
        if(par){
            classroom.reset();
            dialog.stop();
            lmode = par;
        }else{
            if(!lrec){
                mybox.recstat.status = false;
                record_continue(function(){
                    var act = classroom.get_act();
                    if(act){dialog.add_student(act);}
                    lmode = par;
                });    
            }else{lmode = par;}   
        }
    }

    public function onSetting():void{
        var win:SettingWindow = new SettingWindow();
        addChild(win);
        win.show(function(){
            init_cam();
            init_mic(false);
            bg_app.color = tconfig.data.bgcolor;
        });
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////	
		
        private function lesson_start(sobj:Object):void{
            log.lesson_start();
            publishMy();
            
            for(var i in sobj){
                classroom.add_student(sobj[i]);
                log.add_student(sobj[i], false);
            }
            
            nc.call("record_start", new Responder(function(par){mybox.recstat.status = true;}));
        }
        
        
        
	    private function publishMy(){
            ns = new NetStream(nc);
            if(cam_use){ns.attachCamera(cam);}
            ns.attachAudio(mic);
            ns.publish(login, "live");
        }
		
		private function init_cam():void {
            if(cams.length){
                if(tconfig.data.vdev!="none"){
                    var cam_index:int;
                    
                    if(!tconfig.data.vdev){cam_index = 0;}
                    else{cam_index= in_array(tconfig.data.vdev, cams);}
                    
                    cam = Camera.getCamera(String(cam_index));
                    if(cam){
                        cam.setMode(320,240,15);
                        cam.setKeyFrameInterval(15);
			            cam.setQuality(0, tconfig.data.vquality);
                        if(!tconfig.data.vdev){tconfig.set_param("vdev", cams[cam_index]);}
                    }else{
                            cam_use = false;
                            cam_state = false;
                    }
                    
                }else{
                    cam_use = false;
                    cam_state = false;
                }                
            }else{
                    cam_use = false;
                    cam_state = false;
                } 
			}
		
		
		private function init_mic(f:Boolean=true):void {
            if(mics.length){
                var mic_index:int;
                if(!tconfig.data.adev){mic_index=0;}
                else{mic_index = in_array(tconfig.data.adev, mics);}
                
                mic = Microphone.getEnhancedMicrophone();
//              mic = Microphone.getMicrophone();
                if(mic){
                    mic.rate = 11;
                    mic.setSilenceLevel(0);

              
                    var options:MicrophoneEnhancedOptions = new MicrophoneEnhancedOptions();
                    options.mode = MicrophoneEnhancedMode.FULL_DUPLEX;
                    options.autoGain = false;
                    options.echoPath = 128;
                    options.nonLinearProcessing = true;
                    mic.enableVAD = true;
                    mic.encodeQuality = 9;
                    mic.enhancedOptions = options;
                    mic.codec = SoundCodec.SPEEX;      
                    mic.setUseEchoSuppression(true);       
                    if(!tconfig.data.adev){tconfig.set_param("adev", mics[mic_index]);}
                    if(!f){mybox.volpan.volume = tconfig.data.mic_volume;}    
                }else{put_error("Microphone not found!");}
                
            }else{put_error("Microphone not found!");}
		}
		
	  private function blocking():void{
            var mod = new ModalWin();
            addChild(mod);
            mod.x = 0;
            mod.y = 0;
            mod.width = stage.stageWidth;
            mod.height = stage.stageHeight;
      }
	
    
    	
		
	}	
}
