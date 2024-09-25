package apps.test {
	
	import com.app.AppBase;
	
    import apps.test.events.ChangeVolume;
   
	
	
	public class Main extends AppBase
	{
		
		
		
	
		
		
		public function Main() {
		  
          pan.addEventListener(ChangeVolume.VOLUME_CHANGE, respond);
          
  
            
		}
		
		
        private function respond(e:ChangeVolume):void{
            trace(e.volume);
        }
        
        
		
		
	}
	
}
