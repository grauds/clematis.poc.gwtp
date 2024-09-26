package com {
	
	
    import flash.media.Sound;;
    import flash.net.URLRequest;
    import flash.media.SoundChannel;
    import flash.events.Event;
    
	
	public class Load_sound
	{
		
		private var snd:Sound = new Sound();
        private var channel:SoundChannel = new SoundChannel();
		private var is_load:Boolean = false;
		private var is_play:Boolean = false;
		
		
		public function Load_sound(host, sname) {
			var url = "http://"+host+"/static/sounds/"+sname+".mp3";
			var req:URLRequest = new URLRequest(url);
			snd.load(req);
			snd.addEventListener(Event.COMPLETE, completeHandler);
		}
		
		
		public function play(){
			if(is_load){
				channel = snd.play(0, 100);
				is_play = true;
				}			
			}
			
		public function stop(){
			if(is_play){
				channel.stop();
				is_play = false;
				}			
			}
			
		private function completeHandler(e:Event){
			is_load = true;
			}
		
	}
	
}
