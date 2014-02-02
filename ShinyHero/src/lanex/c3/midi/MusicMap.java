package lanex.c3.midi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class MusicMap {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	
	public static final int NUMBER_OF_CHANNELS = 16;
	public static final int NOTE_ON_0 = 0x90;
	public static final int NOTE_ON_F = 0x9F;
	public static final int NOTE_OFF_0 = 0x80;
	public static final int NOTE_OFF_F = 0x8F;
	public static final int INSTRUMENT = 255;
	
	private String filePath;
	
	private List<Track> trackList;
	private double millisPerTick;
	
	private MusicMap() {
		trackList = new ArrayList<Track>();
		filePath = "";
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
	
	public String getPath()
	{
		return filePath;
	}
	
	public static MusicMap fromPath(String path) {
		MusicMap map = null;
		final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		try {
			Sequence sequence = MidiSystem.getSequence(new File(path));
			Synthesizer synth = MidiSystem.getSynthesizer();
			map = new MusicMap();
			map.filePath = path;
			//TODO this only works if there is a single BPM for the whole song
			map.millisPerTick = (double)sequence.getMicrosecondLength() / sequence.getTickLength() / 1000;
			
	        int trackNumber = 0;
	        System.out.println(path);
	       	System.out.println("Number of tracks: " + sequence.getTracks().length);
	       	System.out.println("Number of patches: " + sequence.getPatchList().length);
	        for (javax.sound.midi.Track track :  sequence.getTracks()) {
	        	Track toAdd = generateTrackFromTrack(track, trackNumber);
	        	if (toAdd.hasNotes()) {
	        		map.addChannel(toAdd);
	        	}
	        	trackNumber++;
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
        return map;
	}
	
	private static Track generateTrackFromTrack(javax.sound.midi.Track track, int trackNumber) {
		Track c3Track = new Track(trackNumber, NUMBER_OF_CHANNELS);
		Note[] currentChannelNote = new Note[NUMBER_OF_CHANNELS];
            
        for (int i=0; i < track.size(); i++) { 
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();

            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                long startTick = event.getTick();
                if (sm.getCommand() == NOTE_ON) {
                    int pitch = sm.getData1();
                    int velocity = sm.getData2();
                    int channel = sm.getChannel();
                    currentChannelNote[channel] = noteOn(
                    		c3Track, channel, currentChannelNote[channel], startTick, pitch, velocity);
                } else if (sm.getCommand() >= NOTE_OFF) {
                    int pitch = sm.getData1();
                    int velocity = sm.getData2();
                    int channel = sm.getChannel();
                    currentChannelNote[channel] = noteOff(
                    		c3Track, channel, currentChannelNote[channel], startTick, pitch, velocity);
                } else if (sm.getCommand() == INSTRUMENT) {
                	System.out.println("Instrument: " + sm.getData1());
                }
            }
        }
        System.out.println("Generated track: " + trackNumber);
        
		return c3Track;
	}
	
	private static Note noteOn(Track track, int channel, Note currentNote, long tick, int pitch, int velocity) {
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
				track.addNoteToChannel(channel, currentNote);
				currentNote = pressedNote;
			}
		}
		return pressedNote;
		
	}
	
	private static Note noteOff(Track track, int channel, Note currentNote, long tick, int pitch, int velocity) {
		if (currentNote != null) {
			//if it is the same note being pressed as is being released
			if (currentNote.getPitch() == pitch) {
				//remove the note and add it to the notes list
				currentNote.noteOff(tick);
				track.addNoteToChannel(channel, currentNote);
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
    	MusicMap.fromPath("mid/test_th.midi");
    	//MusicMap.fromPath("mid/th08_10.mid");
    	//MusicMap.fromPath("mid/k-on-fuwa-fuwa-time.mid");
    }
}
