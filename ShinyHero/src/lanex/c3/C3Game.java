package lanex.c3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import lanex.c3.midi.MusicMap;
import lanex.c3.midi.MusicPlayer;
import lanex.engine.Button;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ui.AudioInputProcessor;

public class C3Game extends ScreenPage {
	private Button menu_button, start_button;

	static float currentPitch;
	static float[] history = new float[C3App.RENDER_WIDTH],
			pitchHistory = new float[C3App.RENDER_WIDTH];
	
	boolean playing;
	
	public static MusicMap testMap;
	public static MusicPlayer musicPlayer;
	public static ScrollingMusicSheet scrollSheet;
	
	long startTime, deltaTime;
	int currentTick;
	

	public C3Game() {
		menu_button = new Button(C3App.RENDER_WIDTH / 2 + 400, 600, 500, 100,
				"menu_button.png");
		start_button = new Button(C3App.RENDER_WIDTH / 2, 600, 500, 100,
				"start_button.png");
		
		//testMap = MusicMap.fromPath("data/music/for_elise_by_beethoven.mid");
		
		playing = false;
	}

	@Override
	public void render(GameContainer arg0, Graphics g) {
		g.setColor(Color.white);
		g.clear();

		String[] notes = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
				"A", "A#", "B" };

		String note = (currentPitch > 0) ? notes[((int) (currentPitch + 0.5) % 12)]
				: "NaN";

		// debug headphones
		g.setColor(Color.white);

		g.drawString((new StringBuilder("Note: ")).append(note).toString(), 20,
				20);
		g.drawString(
				(new StringBuilder("Freq: ")).append(
						AudioInputProcessor.frequency).toString(), 20, 40);
		g.drawString((new StringBuilder("Pitch No.: ")).append(currentPitch)
				.toString(), 20, 60);
		g.drawString("Milliseconds Delta: " + deltaTime, 20, 80);
		g.drawString("Current Tick: " + currentTick, 20, 100);

		
		// draw history
		for (int x = 0; x < C3App.RENDER_WIDTH; x++) {
			g.setColor(Color.gray);
			g.fillRect(C3App.RENDER_WIDTH / 2 - x, C3App.RENDER_HEIGHT + 279
					- (history[x] * 8), 1, 10);
			g.setLineWidth(2);
			g.setColor(Color.red);
			g.fillRect(C3App.RENDER_WIDTH / 2 - x, C3App.RENDER_HEIGHT + 283
					- (pitchHistory[x] * 8), 1, 2);
		}

		g.setColor(Color.gray);
		g.drawLine(C3App.RENDER_WIDTH / 2, 0, C3App.RENDER_WIDTH / 2,
				C3App.RENDER_HEIGHT);

		//
		//
		// GUI
		menu_button.render(g);
		start_button.render(g);
		//

		update();
	}

	public void update() {
		
		if(playing){
			deltaTime = System.currentTimeMillis() - startTime;
			currentTick = (int) (deltaTime / testMap.getMillisPerTick());


			currentPitch = (float) (69D + (12D * Math
					.log((float) AudioInputProcessor.frequency / 440D))
					/ Math.log(2D));
			
			
		}
		
		
		for (int x = C3App.RENDER_WIDTH - 1; x > 0; x--) {
			history[x] = history[x - 1];
			pitchHistory[x] = pitchHistory[x - 1];
		}
		pitchHistory[0] = currentPitch;
		history[0] = (int) (currentPitch + 0.5);
	}
	
	void start(){
		System.out.println(scrollSheet.sourceTrack.getNotes());
		
		startTime = System.currentTimeMillis();
		currentTick = 0;
		
		playing = true;
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// CHECK BUTTONS
		{
			if (menu_button.ifOnButton(x, y)) {
				C3App.splash.reset();
				C3SplashScreen.setRedirect("main_menu");		
				C3App.setPage("splash");
			} else if (start_button.ifOnButton(x, y)) {
				start();
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {

	}

}
