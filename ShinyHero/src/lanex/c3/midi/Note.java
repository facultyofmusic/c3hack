package lanex.c3.midi;

public class Note {
	private final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	private long startTick;
	private long stopTick;
	private int key;
	private int octave;
	
	public Note(long startTick, int key, int velocity) {
		this.startTick = startTick;
		this.key = key;
		
		octave = (key / 12) - 1;
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
	
	public int getKey() {
		return key;
	}
	
	@Override
	public String toString() {
        return NOTE_NAMES[key % 12] + octave;
	}
}
