package lanex.crystaline;

import java.awt.Color;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

import ui.AudioInputProcessor;

public class SIApp extends BasicGame {

	public static AppGameContainer app;
	
	
	public SIApp() {
		super("Hello World");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		
		int pitch = (int) AudioInputProcessor.frequency;
		
		// TODO Auto-generated method stub
		g.drawString("" + AudioInputProcessor.frequency, 100, 100);
		g.drawString("" + app.getFPS(), 100, 130);
		

		float detectedNote = (float) (69D + (12D * Math.log(pitch / 440D)) / Math.log(2D));
		
		String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"}; 
		String note = notes[((int) (detectedNote+0.5))%12];

		g.drawString((new StringBuilder("Note: ")).append(note).toString(), 20, 20);
		g.drawString((new StringBuilder("Freq: ")).append(pitch).toString(), 20, 40);
		
		g.drawLine(0, (detectedNote * 3 + 200), 100, (detectedNote * 3 + 200));
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	public static void main(String args[]) {
	AudioInputProcessor aiprocessor = new AudioInputProcessor();
	(new Thread(aiprocessor)).start();
		
		// start game with exception handler
		try {
			app = new AppGameContainer(new ScalableGame(new SIApp(), 640, 480, false));
			
			// additional settings
			app.setShowFPS(false);
			app.setAlwaysRender(true);
			app.setUpdateOnlyWhenVisible(true);
			app.setClearEachFrame(true);
			app.setTargetFrameRate(60);
			app.setSmoothDeltas(true);
			app.setMusicOn(true);
			app.setVerbose(true);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
