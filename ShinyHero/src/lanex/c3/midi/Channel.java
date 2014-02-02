package lanex.c3.midi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Channel implements Iterable<Note> {
	private final static String[] INSTRUMENT_NAMES = new String[] {
		"Acoustic Grand Piano"
		,"Soprano Sax"
		,"Bright Acoustic Piano"
		,"Alto Sax"
		,"Electric Grand Piano"
		,"Tenor Sax"
		,"Honky-tonk Piano"
		,"Baritone Sax"
		,"Electric Piano 1"
		,"Oboe"
		,"Electric Piano 2"
		,"English Horn"
		,"Harpsichord"
		,"Bassoon"
		,"Clavi"
		,"Clarinet"
		,"Celesta"
		,"Piccolo"
		,"Glockenspiel"
		,"Flute"
		,"Music Box"
		,"Recorder"
		,"Vibraphone"
		,"Pan Flute"
		,"Marimba"
		,"Blown Bottle"
		,"Xylophone"
		,"Shakuhachi"
		,"Tubular Bells"
		,"Whistle"
		,"Dulcimer"
		,"Ocarina"
		,"Drawbar Organ"
		,"Lead 1 (square)"
		,"Percussive Organ"
		,"Lead 2 (sawtooth)"
		,"Rock Organ"
		,"Lead 3 (calliope)"
		,"Church Organ"
		,"Lead 4 (chiff)"
		,"Reed Organ"
		,"Lead 5 (charang)"
		,"Accordion"
		,"Lead 6 (voice)"
		,"Harmonica"
		,"Lead 7 (fifths)"
		,"Tango Accordion"
		,"Lead 8 (bass + lead)"
		,"Acoustic Guitar (nylon)"
		,"Pad 1 (new age)"
		,"Acoustic Guitar (steel)"
		,"Pad 2 (warm)"
		,"Electric Guitar (jazz)"
		,"Pad 3 (polysynth)"
		,"Electric Guitar (clean)"
		,"Pad 4 (choir)"
		,"Electric Guitar (muted)"
		,"Pad 5 (bowed)"
		,"Overdriven Guitar"
		,"Pad 6 (metallic)"
		,"Distortion Guitar"
		,"Pad 7 (halo)"
		,"Guitar harmonics"
		,"Pad 8 (sweep)"
		,"Acoustic Bass"
		,"FX 1 (rain)"
		,"Electric Bass (finger)"
		,"FX 2 (soundtrack)"
		,"Electric Bass (pick)"
		,"FX 3 (crystal)"
		,"Fretless Bass"
		,"FX 4 (atmosphere)"
		,"Slap Bass 1"
		,"FX 5 (brightness)"
		,"Slap Bass 2"
		,"FX 6 (goblins)"
		,"Synth Bass 1"
		,"FX 7 (echoes)"
		,"Synth Bass 2"
		,"FX 8 (sci-fi)"
		,"Violin"
		,"Sitar"
		,"Viola"
		,"Banjo"
		,"Cello"
		,"Shamisen"
		,"Contrabass"
		,"Koto"
		,"Tremolo Strings"
		,"Kalimba"
		,"Pizzicato Strings"
		,"Bag pipe"
		,"Orchestral Harp"
		,"Fiddle"
		,"Timpani"
		,"Shanai"
		,"String Ensemble 1"
		,"Tinkle Bell"
		,"String Ensemble 2"
		,"Agogo"
		,"SynthStrings 1"
		,"Steel Drums"
		,"SynthStrings 2"
		,"Woodblock"
		,"Choir Aahs"
		,"Taiko Drum"
		,"Voice Oohs"
		,"Melodic Tom"
		,"Synth Voice"
		,"Synth Drum"
		,"Orchestra Hit"
		,"Reverse Cymbal"
		,"Trumpet"
		,"Guitar Fret Noise"
		,"Trombone"
		,"Breath Noise"
		,"Tuba"
		,"Seashore"
		,"Muted Trumpet"
		,"Bird Tweet"
		,"French Horn"
		,"Telephone Ring"
		,"Brass Section"
		,"Helicopter"
		,"SynthBrass 1"
		,"Applause"
		,"SynthBrass 2"
		,"Gunshot"
	};
	
	private Queue<Note> notes;
	private int channelNumber;
	private String instrumentNames;
	
	public Channel(int channelNumber) {
		this.channelNumber = channelNumber;
		
		instrumentNames = null;
		notes = new LinkedList<Note>();
	}
	
	public int getChannelNumber() {
		return channelNumber;
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public Queue<Note> getNotes() {
		return notes;
	}
	
	public int getNotesSize() {
		return notes.size();
	}
	
	public void addInstrument(int instrumentCode) {
		if (instrumentNames == null) {
			instrumentNames = INSTRUMENT_NAMES[instrumentCode];
		} else {
			instrumentNames += INSTRUMENT_NAMES;
		}
	}
	
	@Override
	public Iterator<Note> iterator() {
		return notes.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(instrumentNames + " (" + channelNumber + "): ");
		for (Note note : this) {
			sb.append(note + " ");
		}
		return sb.toString();
	}
}
