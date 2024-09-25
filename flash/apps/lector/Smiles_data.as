package apps.lector {




    public class Smiles_data
    {
    
    
    public static var data:Object = {
                                        id1:{id:1, url:"angry.gif"},
                                        id2:{id:2, url:"aysmile.gif"},
                                        id3:{id:3, url:"baby.gif"},
                                        id4:{id:4, url:"ban.gif"},
                                        id5:{id:5, url:"bash.gif"},
                                        id6:{id:6, url:"bath.gif"},
                                        id7:{id:7, url:"beer.gif"},
                                        id8:{id:8, url:"biggrin.gif"},
                                        id9:{id:9, url:"blush.gif"},
                                        id10:{id:10, url:"book.gif"},
                                        id11:{id:11, url:"bored.gif"},
                                        id12:{id:12, url:"bounce.gif"},
                                        id13:{id:13, url:"boxed.gif"},
                                        id14:{id:14, url:"boxing.gif"},
                                        id15:{id:15, url:"censored.gif"},
                                        id16:{id:16, url:"cheers.gif"},
                                        id17:{id:17, url:"clap.gif"},
                                        id18:{id:18, url:"cool.gif"},
                                        id19:{id:19, url:"cool.png"},
                                        id20:{id:20, url:"cry.gif"},
                                        id21:{id:21, url:"cry.png"},
                                        id22:{id:22, url:"crybaby.gif"},
                                        id23:{id:23, url:"crying.gif"},
                                        id24:{id:24, url:"disgust.gif"},
                                        id25:{id:25, url:"drool.gif"},
                                        id26:{id:26, url:"drunk.gif"},
                                        id27:{id:27, url:"duel.gif"},
                                        id28:{id:28, url:"eek.gif"},
                                        id29:{id:29, url:"evil.gif"},
                                        id30:{id:30, url:"excl.gif"},
                                        id31:{id:31, url:"flex.gif"},
                                        id32:{id:32, url:"flowers.gif"},
                                        id33:{id:33, url:"frown.gif"},
                                        id34:{id:34, url:"fuckoff.gif"},
                                        id35:{id:35, url:"furious.gif"},
                                        id36:{id:36, url:"goodnight.gif"},
                                        id37:{id:37, url:"handshake.gif"},
                                        id38:{id:38, url:"help.gif"},
                                        id39:{id:39, url:"hmm.gif"},
                                        id40:{id:40, url:"holiday.gif"},
                                        id41:{id:41, url:"idea.gif"},
                                        id42:{id:42, url:"in_love.gif"},
                                        id43:{id:43, url:"innocent.gif"},
                                        id44:{id:44, url:"kicking.gif"},
                                        id45:{id:45, url:"laugh.gif"},
                                        id46:{id:46, url:"laugh.png"},
                                        id47:{id:47, url:"lol.gif"},
                                        id48:{id:48, url:"lol_sign.gif"},
                                        id49:{id:49, url:"love.gif"},
                                        id50:{id:50, url:"mad.gif"},
                                        id51:{id:51, url:"mobile.gif"},
                                        id52:{id:52, url:"naughty.gif"},
                                        id53:{id:53, url:"no.gif"},
                                        id54:{id:54, url:"nono.gif"},
                                        id55:{id:55, url:"notworthy.gif"},
                                        id56:{id:56, url:"oops.gif"},
                                        id57:{id:57, url:"phone.gif"},
                                        id58:{id:58, url:"photo.gif"},
                                        id59:{id:59, url:"pimp.gif"},
                                        id60:{id:60, url:"punk.gif"},
                                        id61:{id:61, url:"question.gif"},
                                        id62:{id:62, url:"ranting.gif"},
                                        id63:{id:63, url:"rip.gif"},
                                        id64:{id:64, url:"rockon.gif"},
                                        id65:{id:65, url:"rtfm.gif"},
                                        id66:{id:66, url:"sad.gif"},
                                        id67:{id:67, url:"sarcblink.gif"},
                                        id68:{id:68, url:"shocking.gif"},
                                        id69:{id:69, url:"shutup.gif"},
                                        id70:{id:70, url:"sick.gif"},
                                        id71:{id:71, url:"sleep.gif"},
                                        id72:{id:72, url:"smartass.gif"},
                                        id73:{id:73, url:"smoke.gif"},
                                        id74:{id:74, url:"spam.gif"},
                                        id75:{id:75, url:"surrender.gif"},
                                        id76:{id:76, url:"thumbdown.gif"},
                                        id77:{id:77, url:"thumbup.gif"},
                                        id78:{id:78, url:"toilet.gif"},
                                        id79:{id:79, url:"tongue.gif"},
                                        id80:{id:80, url:"tv.gif"},
                                        id81:{id:81, url:"unsure.gif"},
                                        id82:{id:82, url:"wallbash.gif"},
                                        id83:{id:83, url:"wazuuup.gif"},
                                        id84:{id:84, url:"wc.gif"},
                                        id85:{id:85, url:"whistle.gif"},
                                        id86:{id:86, url:"wink.gif"},
                                        id87:{id:87, url:"wow.gif"},
                                        id88:{id:88, url:"yawn.gif"},
                                        id89:{id:89, url:"yes.gif"}
                                    };
    
        public function Smiles_data(){
        
        }
    
    
        public static function get_smile(id:int):Object{
            return data["id"+id];
        }
        
    
    }


}