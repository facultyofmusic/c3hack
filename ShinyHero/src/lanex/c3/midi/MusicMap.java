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
	
	private List<Track> trackList;
	private double millisPerTick;
	
	private MusicMap() {
		trackList = new ArrayList<Track>();
	}
	
	public List<Track> getTrackList() {
		return trackList;
	}
	
	public double getMillisPerTick() {
		return millisPerTick;
	}
	
	private void addChannel(Track channel) {
		trackList.add(channel);
	}
	
	public static MusicMap fromPath(String path) {
		MusicMap map = null;
		
		try {
			Sequence sequence = MidiSystem.getSequence(new File(path));
			map = new MusicMap();
			//TODO this only works if there is a single BPM for the whole song
			map.millisPerTick = (double)sequence.getMicrosecondLength() / sequence.getTickLength() / 1000;
			
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
		Note currentNote = null;
            
        for (int i=0; i < track.size(); i++) { 
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();
            
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                long startTick = event.getTick();
                if (sm.getCommand() == NOTE_ON) {
                    int pitch = sm.getData1();
                    int velocity = sm.getData2();
                    currentNote = noteOn(c3Track, currentNote, startTick, pitch, velocity);
                } else if (sm.getCommand() == NOTE_OFF) {
                    int pitch = sm.getData1();
                    int velocity = sm.getData2();
                    currentNote = noteOff(c3Track, currentNote, startTick, pitch, velocity);
                }
            }
        }
        System.out.println("Generated track: " + trackNumber);
        
		return c3Track;
	}
	
	private static Note noteOn(Track track, Note currentNote, long tick, int pitch, int velocity) {
		Note pressedNote = new Note(tick, pitch, velocity);
		if (currentNote != null) {
			//if the two notes start at the same time (part of the same chord)
			if (currentNote.getStartTick() == tick) {
				//if the first parsed note has a lower pitch than the new note
				if (currentNote.compareTo(pressedNote) < 0) {
					//replace the first parsed note with the new note
					currentNote = pressedNote;
				}
			} else { //otherwise, close the first note and add start the new note
				currentNote.noteOff(tick);
				track.addNote(currentNote);
				currentNote = pressedNote;
			}
		}
		return pressedNote;
		
	}
	
	private static Note noteOff(Track track, Note currentNote, long tick, int pitch, int velocity) {
		if (currentNote != null) {
			//if it is the same note being pressed as is being released
			if (currentNote.getPitch() == pitch) {
				//remove the note and add it to the notes list
				currentNote.noteOff(tick);
				track.addNote(currentNote);
				//return null, since no note is being pressed anymore
				return null;
			} else {
				//return currentNote, since we ignore the released note if it isn't the same
				return currentNote;
			}
		}
		//there should have been a note playing; ignore this noteOff call
		return null;
	}

	
    public static void main(String[] args) throws Exception {
    	MusicMap.fromPath("data/music/for_elise_by_beethoven.mid");
    }
}
