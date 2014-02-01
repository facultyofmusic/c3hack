package lanex.c3.midi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

public class MusicMap {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	
	private List<Track> channels;
	
	private MusicMap() {
		channels = new ArrayList<Track>();
	}
	
	public void addChannel(Track channel) {
		channels.add(channel);
	}
	
	public static MusicMap fromPath(String path) {
		MusicMap map = null;
		
		try {
			Sequence sequence = MidiSystem.getSequence(new File(path));
			map = new MusicMap();
	        int trackNumber = 0;
	        for (javax.sound.midi.Track track :  sequence.getTracks()) {
	        	Track toAdd = generateTrackFromTrack(track, trackNumber);
	        	if (toAdd.getNotesSize() > 0) {
	        		map.addChannel(toAdd);
	        		System.out.println("Added channel " + trackNumber);
	        		System.out.println(toAdd);
	        	}
	        	trackNumber++;
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} 
        
        return map;
	}
	
	private static Track generateTrackFromTrack(javax.sound.midi.Track track, int trackNumber) {
		Track c3Track = new Track(trackNumber);
//		Map<Integer, Note> playingNotes = new HashMap<Integer, Note>();
		Note currentNote = null;
            
        for (int i=0; i < track.size(); i++) { 
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();
            
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                long startTick = event.getTick();
                if (sm.getCommand() == NOTE_ON) {
                    int key = sm.getData1();
                    int velocity = sm.getData2();
                    Note note = new Note(startTick, key, velocity);
                    if (currentNote != null) {
                    	//if part of the same chord
                    	if (currentNote.getStartTick() == note.getStartTick()) {
                    		//if the current note is lower than the new note
                    		if (currentNote.getKey() < note.getKey()) {
                    			//System.err.println("Warning: chord not dealt with");
                    			c3Track.removeLastNote();
                    		}
                    	} else {
	                    	currentNote.noteOff(event.getTick());
	                    	System.err.println("a note was started and cut off a previous one!");
                    	}
                    }
                    currentNote = note;
                    c3Track.addNote(currentNote);
                } else if (sm.getCommand() == NOTE_OFF) {
                    if (currentNote == null) {
                    	System.err.println("a note was ended without starting!");
                    } else {
                    	currentNote.noteOff(startTick);
                    	currentNote = null;
                    }
                }
            }
        }
        System.out.println("Generated track: " + trackNumber);
        
		return c3Track;
	}
	
	public Note getNextNote() {
		return null;
	}
	
    public static void main(String[] args) throws Exception {
    	MusicMap.fromPath("data/music/for_elise_by_beethoven.mid");
    }
}
