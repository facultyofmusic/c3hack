package lanex.c3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import lanex.c3.midi.Note;
import lanex.c3.midi.Track;


public class ScrollingMusicSheet {
//	public static int ticksPerPixel
	public float pixelsPerTick = 0.2f;
	public Note currentActiveNote;
	public Track sourceTrack;
	public LinkedList<Note> activeNotes;
	public int currentTick = -999;
	
	public ScrollingMusicSheet(Track src){
		sourceTrack = src;
		activeNotes = new LinkedList<Note>();
		//notes = sourceTrack.getNotes();
	}
	
	void renderList(){
		Iterator<Note> activeListIterator = activeNotes.iterator();
		
		while(activeListIterator.hasNext()){
			Note temp = activeListIterator.next();
			
			if(temp.getStopTick() < currentTick-getOffscreenTickDelta()){
				System.out.println("Deleting Note with end time: " + temp.getStopTick());
				activeListIterator.remove();
			}
			
			
		}
		
		Iterator<Note> sourceListIterator = sourceTrack.getNotes().iterator();
		
		while(sourceListIterator.hasNext()){
			Note temp = sourceListIterator.next();
			if(temp.getStartTick() > currentTick+getOffscreenTickDelta()){
				break;
			}
			System.out.println("Adding Note with start time: " + temp.getStartTick());
			sourceListIterator.remove();
			activeNotes.push(temp);
			
		}
		
	}
	
	int getOffscreenTickDelta(){
		return (int) (C3App.RENDER_WIDTH*1.0/2/pixelsPerTick);
	}
	
	public void update(int currentTick){
		this.currentTick = currentTick;
		
		renderList();
		
//		if(notes.peek().getStartTick() < currentTick){
//			if(notes.peek() != currentActiveNote){
//				System.out.println("New Active Note: " + notes.peek());
//				currentActiveNote = notes.poll();
//			}
//		}
	}
}
