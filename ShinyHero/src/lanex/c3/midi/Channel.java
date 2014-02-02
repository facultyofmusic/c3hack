package lanex.c3.midi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Channel implements Iterable<Note> {
	private Queue<Note> notes;
	private int channelNumber;
	
	public Channel(int channelNumber) {
		this.channelNumber = channelNumber;
		
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
	
	@Override
	public Iterator<Note> iterator() {
		return notes.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CHANNEL " + channelNumber + ": ");
		for (Note note : this) {
			sb.append(note + " ");
		}
		return sb.toString();
	}
}
