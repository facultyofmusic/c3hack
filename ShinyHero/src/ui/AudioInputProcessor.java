package ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import ui.Yin.DetectedPitchHandler;


/**
 * This class gets the pitch currently inputted into the microphone.
 * @author fogSANA
 *
 */
public class AudioInputProcessor implements Runnable {

	private final int sampleRate;
	private final double audioBufferSize;
	public static float frequency = 0;

	public AudioInputProcessor(){
		sampleRate = 29000; //Hz
		audioBufferSize = 0.2;//Seconds
	}

	public void run() {
		// get a list of input devices (they suck at working perperly.  so hope the first one works)
		ArrayList<javax.sound.sampled.Mixer.Info> capMixers = new ArrayList<javax.sound.sampled.Mixer.Info>();
		javax.sound.sampled.Mixer.Info mixers[] = AudioSystem.getMixerInfo();
		for (int i = 0; i < mixers.length; i++) {
			javax.sound.sampled.Mixer.Info mixerinfo = mixers[i];
			if (AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0)
				capMixers.add(mixerinfo);
		}
		
		javax.sound.sampled.Mixer.Info selected =  capMixers.get(0);
		
		if (selected == null)
			return;
		try {
			Mixer mixer = AudioSystem.getMixer(selected);
			AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,false);
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,format);
			TargetDataLine line = (TargetDataLine) mixer.getLine(dataLineInfo);
			int numberOfSamples = (int) (audioBufferSize * sampleRate);
			line.open(format, numberOfSamples);
			line.start();
			AudioInputStream ais = new AudioInputStream(line);
			AudioFloatInputStream afis = AudioFloatInputStream.getInputStream(ais);
			processAudio(afis);
			line.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processAudio(AudioFloatInputStream afis) throws IOException, UnsupportedAudioFileException {

		Yin.processStream(afis,new DetectedPitchHandler() {
			@Override
			public void handleDetectedPitch(float time,float pitch) {
				frequency = pitch;
			}
		});
	}
}
