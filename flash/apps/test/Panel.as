package apps.test {
	
	import flash.display.Sprite;
	import flash.events.MouseEvent;
    import apps.test.events.ChangeVolume;
    import flash.events.EventDispatcher;
   
	
	
	public class Panel extends Sprite
	{
		
		
		
	
		
		
		public function Panel() {
		  
          but.addEventListener(MouseEvent.CLICK, respond);
          
  
            
		}
		
		
        private function respond(e:ChangeVolume):void{
            exec_event();    
        }
        
        private function exec_event():void{
            dispatchEvent( new ChangeVolume( ChangeVolume.VOLUME_CHANGE, 50 ) );
        }
		
		
	}
	
}
