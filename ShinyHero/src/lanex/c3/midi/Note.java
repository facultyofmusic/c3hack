package lanex.c3.midi;

public class Note implements Comparable<Note> {
	private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	private long startTick;
	private long stopTick;
	private int pitch;
	private int octave;
	
	public Note(long startTick, int pitch, int velocity) {
		this.startTick = startTick;
		this.pitch = pitch;
		
		octave = (pitch / 12) - 1;
	}
	
	public void noteOn(long tick) {
		startTick = tick;
	}
	
	public void noteOff(long tick) {
		stopTick = tick;
	}
	
	public long getStartTick() {
		return startTick;
	}
	
	public long getStopTick() {
		return stopTick;
	}
	
	public int getPitch() {
		return pitch;
	}
	
	@Override
	public int compareTo(Note note) {
		return pitch - note.getPitch();
	}
	
	@Override
	public String toString() {
        return NOTE_NAMES[pitch % 12] + octave + " [" + startTick + " " + stopTick + "] ";
	}
}
