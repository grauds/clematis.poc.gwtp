package apps.student {
	
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
    
    import fl.data.DataProvider;
	
    import apps.student.Log;
    import apps.student.Server_data;
    import apps.student.Tconfig;
    import apps.student.Dialog;
    import apps.student.SettingWindow;
	
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
        
		
		
		var login:String;
        var nick:String = "";
		var photo:String;
        
        
        
		var classId:String;
        var lessonId:String;
        
        
        var chost:String;
		
		var lessb:Boolean = false;
        var first:Boolean = true;
        var reject:Boolean = false;
		
	
		
		var server_addr:String = "rtmp://77.91.193.156/classroom";
        
        var log:Log;
        var dialog:Dialog;
        var tconfig:Tconfig;
        
        var context_path:String = "";
        
        
        public static var inst:Main;
        
       
		
		
		
		public function Main() {
		  
          inst = this;
          
          stage.scaleMode = StageScaleMode.NO_SCALE; 
		  stage.align = StageAlign.TOP_LEFT;
		  stage.addEventListener(FullScreenEvent.FULL_SCREEN, full_screen_handler);
  
        chost = get_chost();
        
//        chost = "77.91.193.156:81";
        
          var t = get_param("classId").split("-");
          
          classId = t[0];
          lessonId = t[1];    
    /*     
          classId = "english1";
          lessonId = "t20120416122224";         */
         
          login = get_param("userId");
          nick = get_param("nick");
          photo = get_param("photo");
          context_path = get_param("context_path");      
          
  /*        
          login = "victor";
          nick = "vitor";
          photo = "http://domain.com/photo.jpg";
          context_path = "el-prototype";
              */
         
            
            init();
            
		}
		
		
        
        private function init(){
            
            log = new Log(chost+"/"+context_path, classId+"-"+lessonId);
            tconfig = new Tconfig();
            bg_app.color = tconfig.data.bgcolor;
            cams = Camera.names;
            mics = Microphone.names;    
                
            if(mics.length){
                
                init_cam();
                init_mic();
                mybox.init();
                slist.addEventListener(Event.CHANGE, function(e:Event){
                                                        var it = slist.selectedItem;
                                                        set_tchat_to({login:it.data, nick:it.label});
                                                        });
                                                        
                var mint = setInterval(function(){mybox.pbar.set_progress(mic.activityLevel);}, 100);
                
                var obj = new Server_data(chost+"/"+context_path);
                obj.load(function(resp){
                    if(resp.error){put_error("Произошла ошибка при соединении!\n"+resp.error+"\nПопробуйте позже!");}
                    else{
                        server_addr = "rtmp://"+resp.server+"/classroom";
                         trace("server_addr "+server_addr);
                        create_connection(function(e:NetStatusEvent){
                        trace(e.info.code);
                            switch (e.info.code){
                                    
                                    case "NetConnection.Connect.Success":
                                    publishMy();
                                    create_core(); 
	                                break;
					
				                    case "NetConnection.Connect.Close":
	                                   if(first && !reject){put_error("Не удалось установить соединение с сервером!");}
	                                   break;
                               
                                    case "NetConnection.Connect.Rejected":
                                        reject = true;
	                                   if(e.info.application.message=="limit max students"){
                                            log.student_not_present(login, "Limit overflow");
                                            mybox.clear_video();
                                            blocking();
                                            put_error("Превышен лимит студентов в классе!");
                                        }else{
                                            if(e.info.application.message=="user exists"){
                                                mybox.clear_video();
                                                blocking();
                                                put_error("Дублирование сессии не допустимо!");
                                            }else{
                                                mybox.clear_video();
                                                blocking();
                                                put_error("Вход отклонен по техническим причинам!");    
                                            }    
                                        }
	                                   break;
	                            }
                        });
                    }
                });
                
                
                
            
            }else{
                log.student_not_present(login, "Microphone not found");
                mybox.clear_video();
                blocking();
                put_error("Microphone not found!");
            }    
         
                
        }
        
        
        
        
		private function create_connection(callback:Function):void{
			nc = new NetConnection();
			nc.addEventListener(NetStatusEvent.NET_STATUS, callback);
            
            nc.connect(server_addr+"/"+classId+"_"+lessonId, 
                        {login:login, type:'student', nick:nick, photo:photo, cam_state:cam_state});
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
//                core.send("response_states", from, cam_state, mic_state);           
            };
            
            
            obj.response_states = function(to:String, cstate:Boolean, mstate:Boolean){
                lector.set_cam_state(cstate);
                lector.set_mic_state(mstate);   
            };
            
            obj.message_end = function(){
                ns.close();
                nc.close();
                mybox.clear_video();
                blocking();
                put_message("Урок завершен!");    
            };
            
            obj.request_ruka = function(from:String){
//                classroom.request_ruka(from);       
            };
            
            obj.response_ruka = function(to:String){
                if(to==login){
                    lector.response_ruka();
                }        
            };
            
            obj.response_ruka_down = function(to:String){
                if(to==login){
                    lector.response_ruka_down();
                }       
            };
            
            
            obj.set_lector_cam_state = function(state:Boolean){
                lector.set_cam_state(state);        
            };
            
            obj.set_lector_mic_state = function(state:Boolean){
                lector.set_mic_state(state);        
            };
            
            obj.set_cam_state = function(from:String, state:Boolean){
//                classroom.set_cam_state(from, state);       
            };
            
            obj.set_mic_state = function(from:String, state:Boolean){
//                classroom.set_mic_state(from, state);        
            };
            
            
            core.addEventListener(SyncEvent.SYNC, corehandler);
			core.connect(nc);
       }
	   
       
       private function corehandler(e:SyncEvent):void{
            
            var data:Object = core.data;
            
            if(!lessb){
                dialog = new Dialog(login);
                if(data.lector){
                    lector.init(data.lector);
                    lessb = true;
                }else{
                    if(first){
                        first = false;
                        log.add_student(login);
                    }
                }
            }
            
           init_list(data.students);
            
       }
       
       
       
       private function init_list(obj:*):void{
            if(obj){
                var arr:Array = [];
                for(var i in obj){
                    if(i!=login){
                        arr.push({label:obj[i].nick, data:i});
                    }
                }
                slist.dataProvider = new DataProvider(arr);
            }
            
       }
       
       private function full_screen_handler(e:FullScreenEvent):void{
            if(e.fullScreen){
                
                
               
            }else{
                
                
                
                
            }
        }	
		
///////////////////////////////////////////////////////////////////////////////////////////////////////

    public function onChatState():void{
        tchat.visible = !tchat.visible;
    }

    public function onEndLesson():void{
        if(!lessb){log.out_student(login);}
        ns.close();
        nc.close();
        mybox.clear_video();
        blocking();
    }
   


    public function onFullMode():void{
    
    }

    public function set_cam_state(state:Boolean):void{
        cam_state = state;
        
        core.send("set_cam_state", login, state);
    }
    
    public function set_mic_state(state:Boolean):void{
        mic_state = state;
        core.send("set_mic_state", login, state);
    }


    public function add_tchat_message(to:String, message:String){
        core.send("add_message", {login:login, nick:nick}, to, message);
    }


    public function set_tchat_to(obj:Object):void{
        tchat.set_to(obj);
    }

    public function request_ruka():void{
        core.send("request_ruka", login);
    }
    
    public function request_states():void{
        core.send("request_states", login);
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
