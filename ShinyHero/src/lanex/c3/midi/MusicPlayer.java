package lanex.c3.midi;

import java.io.File;
import java.util.Locale;

import javax.sound.midi.*;

public class MusicPlayer {
	
	private Sequencer sequencer;
	
	public boolean isPlaying()
	{
		if (sequencer == null)
			return false;
		return sequencer.isRunning();
	}
	
	public Sequencer getSequencer()
	{
		return sequencer;
	}
	
	public void stop() // Stops playing when window is closed or at end of song
	{
		if (sequencer != null) {
			sequencer.stop();
		}
	}
	
	public void play(MusicMap map)
	{
		try
		{
			MidiDevice receivingDevice = getReceivingDevice();
	        receivingDevice.open();
			
			File file = new File(map.getPath());
	
	        Sequence sequence1 = MidiSystem.getSequence(file);
	        sequencer = MidiSystem.getSequencer(false);
	        Transmitter tx1 = sequencer.getTransmitter();
	        Receiver rx1 = receivingDevice.getReceiver();
	        tx1.setReceiver(rx1);
	
	        sequencer.open();
	        sequencer.setSequence(sequence1);
	
	        sequencer.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public MusicPlayer ()
	{
		sequencer = null;
	}
	
	private static MidiDevice getReceivingDevice()
	        throws MidiUnavailableException {
	        for (MidiDevice.Info mdi: MidiSystem.getMidiDeviceInfo()) {
	            MidiDevice dev = MidiSystem.getMidiDevice(mdi);
	            if (dev.getMaxReceivers() != 0) {
	                String lcName = mdi.getName();
	                System.out.println(lcName);
	                if (lcName != null && !lcName.equals(""))
	                	lcName = lcName.toLowerCase(Locale.ENGLISH);
	                if (lcName.contains("gervill")) {
	                    return dev;
	                }
	            }
	        }
	        return null;
	}
	
	public static void main(String[] args) // REMOVE WHEN DONE TESTING
	{
		MusicPlayer mp = new MusicPlayer();
		MusicMap map = MusicMap.fromPath("mid/for_elise_by_beethoven.mid");
		mp.play(map);
		try
		{
			Thread.sleep(5000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//mp.stop();
	}
	
	
}
