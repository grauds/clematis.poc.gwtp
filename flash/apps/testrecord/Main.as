package apps.testrecord {
	

	 import com.app.AppBase;

    
    import flash.events.NetStatusEvent;
    import flash.events.SyncEvent;
    import flash.events.AsyncErrorEvent;
    import flash.events.Event;
	import flash.events.MouseEvent;
    
    
	
    import flash.net.NetConnection;
    import flash.net.NetStream;
    import flash.net.Responder;
	
    import flash.media.Video;
    import flash.media.Microphone;
    /*
    import flash.media.SoundCodec;
    import flash.media.MicrophoneEnhancedMode;
    import flash.media.MicrophoneEnhancedOptions;
   */ 
    import flash.media.Camera;
    
	
	import flash.utils.*;
    import flash.net.SharedObject;
    
   
    
    

	
	
	public class Main extends AppBase
	{
		
		
		
		var nc:NetConnection;
        var nsmy:NetStream;
        var nsto:NetStream;
        
        var id:int = 64;
        
		
		var shcore:SharedObject;
		
		var pub:Boolean = false;
        var rec:Boolean = false;
	

        var cam:Camera;
        var mic:Microphone;
		
		
		var ns_name:String;
        
        
        public static var inst:Main;
		
		
        
        
		
		                                               
	
        
		

        var server_addr:String = "rtmp://77.91.193.156/trec";

        
        
        
        
		
		
		
		public function Main() {
		    ns_name = "victor";
            init_cam();
                    init_mic();
            nc = new NetConnection();
            nc.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
          
            
            but.addEventListener(MouseEvent.CLICK, clhandler);
            but2.addEventListener(MouseEvent.CLICK, clhandler2);
            trace("start");
           
            nc.connect(server_addr);
            
             
            
             
            
		}
		
	
        
       
        private function clhandler(e:MouseEvent){
            
            if(rec){
                nc.call("record_stop", new Responder(function(resp){
                rec = false;
                but.label = "Record";
                }));
                
            }else{
                nc.call("record_start", new Responder(function(resp){
                rec = true;
                but.label = "Stop";
                }), ns_name);
            }
            
        }
        
        
        private function clhandler2(e:MouseEvent){
            
            nc.call("record_continue", new Responder(function(resp){}), ns_name);
            
            
            
        }
        
        
         private function netStatusHandler(event:NetStatusEvent):void
        {
            trace(event.info.code);
            switch (event.info.code)
            {
                case "NetConnection.Connect.Success":

                    
                    
                    publishMy();      

	                break;
					
				case "NetConnection.Connect.Close":
	                
	                break;
					
				case "NetStream.Play.Start":
					
	                break;
					
				case "NetStream.Play.Stop":
					
	                break;
					
				case "NetStream.Play.StreamNotFound":
					
	                
	                break;
                    
                case "NetStream.Publish.Start":
					
//                     viewTo();
//                     create_sh();
	     
	                break;
					
				case "NetStream.Unpublish.Success":
					
	                break;
	        }
        }
        
        
       
        private function create_sh(){
            shcore = SharedObject.getRemote("user_"+id, nc.uri, true);
            var obj = {};
            obj.view_trace = function(par){trace("Вызван метод SharedObject - view_trace с параметром "+par);};
            shcore.addEventListener(SyncEvent.SYNC, sync_handler);
            shcore.addEventListener(AsyncErrorEvent.ASYNC_ERROR, error_handler);
            shcore.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
            shcore.client = obj;
            shcore.connect(nc);
            
        }
        
        
        private function error_handler(e:AsyncErrorEvent){
            trace(e.error.toString());
            
        }
        
        private function sync_handler(e:SyncEvent){
        trace("sync handler");
           for(var i in shcore.data){
            trace(i+"="+shcore.data[i]);
           }
            
        }
        
        
        private function stop_dialog():void{
//            nsto.play(false);
            nsmy.publish(null);
            pub = false;
//			nsmy.publish(null);
//            nsto.close();
            	
//			shcore.close();
			}
            
            
        private function start_dialog():void{
//            nsto.play(false);
            nsmy.publish(ns_name, "live");
            pub = true;
//			nsmy.publish(null);
//            nsto.close();
            	
//			shcore.close();
			}
        
        
       
       private function publishMy():void {
			
			nsmy = new NetStream(nc);
            nsmy.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
            nsmy.attachCamera(cam);
            nsmy.attachAudio(mic);			
			start_dialog();
            
			}
		
/*		
		private function viewTo():void{
			nsto = new NetStream(nc);
            nsto.client = {};
            nsto.client.onMetaData = function(it:Object){};
			nsto.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
			tovideo.attachNetStream(nsto);
//            nsto.bufferTime = 0.5;
//            nsto.bufferTimeMax = 1;
			nsto.play(ns_name, -2);
			
			}
       
       
       
             */
        
       
       private function init_cam():void {
			cam = Camera.getCamera();
            if(cam){
                cam.setMode(640,480,15);
			    cam.setQuality(0,95);
                myvideo.attachCamera(cam);
                }
			}
		
		
		private function init_mic():void {
            
            mic = Microphone.getMicrophone();
            mic.rate = 22;
            mic.setSilenceLevel(0);
    /*        
            var options:MicrophoneEnhancedOptions = new MicrophoneEnhancedOptions();
            options.mode = MicrophoneEnhancedMode.FULL_DUPLEX;
            options.autoGain = false;
            options.echoPath = 128;
            options.nonLinearProcessing = true;
            mic.enableVAD = true;
            mic.encodeQuality = 3;
            mic.enhancedOptions = options;
            mic.codec = SoundCodec.SPEEX;
            */
            mic.setUseEchoSuppression(true);
            
            
			} 
        
		
	}
	
}
