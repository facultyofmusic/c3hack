package lanex.c3.midi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Track implements Iterable<Note> {
	private LinkedList<Note> notes;
	
	public Track(int number) {
		notes = new LinkedList<Note>();
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public void removeLastNote() {
		notes.removeLast();
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
		for (Note note : this) {
			sb.append(note + " ");
		}
		return sb.toString();
	}
}
