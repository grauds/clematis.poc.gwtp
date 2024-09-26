package apps.test.events 
{
	import flash.events.Event;

	public class ChangeVolume extends Event
	{
		static public var VOLUME_CHANGE:String = "volumechange";
		
		public var volume:int;
		
		public function ChangeVolume( p_type:String, p_volume:int, p_bubbles:Boolean=false, p_cancelable:Boolean=false ) 
		{
			super( p_type, p_bubbles, p_cancelable );
			
			volume = p_volume;
		}
		
	}
	
}