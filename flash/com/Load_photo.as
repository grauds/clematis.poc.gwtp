package com {
	
	import flash.display.*; 
	import flash.net.URLRequest; 
	import flash.events.Event; 
	
	public class Load_photo extends MovieClip
	{
		
		var container:Sprite = new Sprite();
		var ldr:Loader;
		var father:MovieClip;
		
		
		public function Load_photo(url:String, mcl:MovieClip) {
			father = mcl;
			ldr = new Loader();
            ldr.contentLoaderInfo.addEventListener(Event.COMPLETE, imgLoaded);
            
            try{ldr.load(new URLRequest(url));}
			catch(e:Error){trace("trace error "+e.message);}
			
		}
		
		
		
		private function imgLoaded(e:Event){
			container.addChild(ldr.content);
			var sarr = get_size(container.width, container.height, father.width, father.height);
			container.width = sarr[0];
			container.height = sarr[1];
			father.addChild(container);
			container.x = Math.round(father.width/2-container.width/2);
			container.y = Math.round(father.height/2-container.height/2);
			}
		
		
		private function get_size(w:int, h:int, wmax:int, hmax:int):Array{
			var kof:Number;
			
			var daspect = wmax/hmax;
			var saspect = w/h;
			
			if(saspect>=daspect){
				if(w>wmax){kof = w/wmax;}
				else{kof = 1;}
				}else{
					
					if(h>hmax){kof = h/hmax}
					else{kof = 1;}
						
					}
			
			
			var wres = Math.floor(w/kof);
			var hres = Math.floor(h/kof);
			var res = new Array(wres,hres);
			return res;
			}

	}
	
}
