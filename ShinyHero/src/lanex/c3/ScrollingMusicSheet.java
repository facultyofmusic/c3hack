package lanex.c3;

import java.util.ArrayList;

import lanex.c3.midi.Note;
import lanex.c3.midi.Track;


public class ScrollingMusicSheet {
//	public static int ticksPerPixel
	
	Note currentActiveNote;
	Track sourceTrack;
	int currentTick = -999;
	
	public ScrollingMusicSheet(Track src){
		sourceTrack = src;
	}
	
	public void update(int currentTick){
		
	}
}
