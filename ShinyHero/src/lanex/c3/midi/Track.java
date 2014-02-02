package lanex.c3.midi;

public class Track {
	private Channel[] channels;
	private int trackNumber;
//	private LinkedList<Note> notes;
	
	public Track(int trackNumber, int numberOfChannels) {
		channels = new Channel[numberOfChannels];
		this.trackNumber = trackNumber;
	}
	
	public void addNoteToChannel(int channelNumber, Note note) {
		//notes.add(note);
		if (channels[channelNumber] == null) {
			channels[channelNumber] = new Channel(channelNumber);
		}
		channels[channelNumber].addNote(note);
	}
	
	//returns whether any of the channels contain any meaningful notes
	public boolean hasNotes() {
		for (Channel channel : channels) {
			if (channel != null) {
				return true;
			}
		}
		return false;
	}
	
	public Channel getChannel(int channelNumber) {
		return channels[channelNumber];
	}
}
