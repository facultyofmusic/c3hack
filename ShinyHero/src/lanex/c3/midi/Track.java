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
	
	public void addInstrumentToChannel(int channelNumber, int instrumentCode) {
		//System.out.println("Add instrument to channel " + channelNumber + " (" + instrumentCode + ")");
		if (channels[channelNumber] == null) {
			channels[channelNumber] = new Channel(channelNumber);
		}
		channels[channelNumber].addInstrument(instrumentCode);
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
	
	public void clearEmptyChannels() {
		for (int i = 0; i < channels.length; i++) {
			if (channels[i] != null && channels[i].getNotesSize() == 0) {
				channels[i] = null;
			}
		}
	}
	
	public Channel getChannel(int channelNumber) {
		return channels[channelNumber];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < channels.length; i++) {
			if (channels[i] != null) {
				sb.append(channels[i] + "\n");
			}
		}
		return sb.toString();
	}
}
