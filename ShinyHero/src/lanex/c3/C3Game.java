package lanex.c3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import lanex.c3.midi.MusicMap;
import lanex.c3.midi.MusicPlayer;
import lanex.c3.midi.Note;
import lanex.c3.midi.ScrollingMusicSheet;
import lanex.engine.Button;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ui.AudioInputProcessor;

public class C3Game extends ScreenPage {
	private Button menu_button, start_button, pause_button;

	static float currentPitch, pitchDifference;
	static float[] history, pitchHistory;

	static boolean playing;

	static boolean paused;

	public static MusicMap testMap;
	public static MusicPlayer musicPlayer;
	public static ScrollingMusicSheet scrollSheet;

	int currentTick;

	public C3Game() {
		menu_button = new Button(C3App.RENDER_WIDTH - 309, 50, 256, 85,
				"btn_mainmenu.png", Color.red);
		start_button = new Button(C3App.RENDER_WIDTH / 2 - 225, 520, 450, 150,
				"btn_start.png", Color.green);
		pause_button = new Button(C3App.RENDER_WIDTH - 565, 50, 256, 85,
				"btn_pause2.png", Color.cyan);

		// testMap = MusicMap.fromPath("data/music/for_elise_by_beethoven.mid");

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

		if (scrollSheet.currentActiveNote != null)
			g.drawString("accuracy: " + pitchDifference, 20, 80);

		g.drawString("Current Tick: " + currentTick, 20, 100);
		g.drawString("Current Note: " + scrollSheet.currentActiveNote, 20, 120);
		g.drawString("Active Note Count: " + scrollSheet.activeNotes.size(),
				20, 140);
		g.drawString("Offscreen Delta: " + scrollSheet.getOffscreenTickDelta(),
				20, 160);

		if (history != null) {

			// draw history
			for (int x = 0; x < C3App.RENDER_WIDTH; x++) {
				g.setColor(Color.gray);
				g.fillRect(C3App.RENDER_WIDTH / 2 - x, C3App.RENDER_HEIGHT
						+ 279 - (history[x] * 8), 1, 10);
				g.setLineWidth(2);
				g.setColor(Color.red);
				g.fillRect(C3App.RENDER_WIDTH / 2 - x, C3App.RENDER_HEIGHT
						+ 283 - (pitchHistory[x] * 8), 1, 2);
			}

			g.setColor(Color.gray);
			g.drawLine(C3App.RENDER_WIDTH / 2, 0, C3App.RENDER_WIDTH / 2,
					C3App.RENDER_HEIGHT);

		}

		//
		//
		// GUI
		menu_button.render(g);
		if (!paused && playing)
			pause_button.render(g);
		else if (!playing && paused)
			;// impossible
		else
			start_button.render(g);
		//

		g.setLineWidth(0.5f);

		Iterator<Note> activeListIterator = scrollSheet.activeNotes.iterator();

		while (activeListIterator.hasNext()) {
			Note temp = activeListIterator.next();

			float pieceLength = (1.0f * (temp.getStopTick() - temp
					.getStartTick()) * scrollSheet.pixelsPerTick)
					/ temp.collisionHistory.length;

			for (int x = 0; x < temp.collisionHistory.length; x++) {
				short brightness = temp.collisionHistory[x];

				g.setColor(new Color(brightness, brightness, brightness));
				g.fillRect(
						(int) (C3App.RENDER_WIDTH / 2
								- (currentTick - temp.getStartTick())
								* scrollSheet.pixelsPerTick + x * pieceLength),
						C3App.RENDER_HEIGHT + 279 - (temp.getPitch() * 8),
						pieceLength + 1, 10);

			}

			g.setColor(Color.gray);
			g.drawRect(
					C3App.RENDER_WIDTH / 2
							- (currentTick - temp.getStartTick())
							* scrollSheet.pixelsPerTick, C3App.RENDER_HEIGHT
							+ 279 - (temp.getPitch() * 8),
					(temp.getStopTick() - temp.getStartTick())
							* scrollSheet.pixelsPerTick, 10);

		}

		update();
	}

	public void update() {

		if (playing && !paused) {

			if (scrollSheet.done) {
				C3App.setPage("gameover");
			}

			currentTick = (int) this.musicPlayer.getSequencer()
					.getTickPosition();

			currentPitch = (float) (69D + (12D * Math
					.log((float) AudioInputProcessor.frequency / 440D))
					/ Math.log(2D));

			if (scrollSheet.currentActiveNote != null) {
				pitchDifference = scrollSheet.currentActiveNote.getPitch()
						- currentPitch;
			} else {
				pitchDifference = 1;
			}
			scrollSheet.update(currentTick, pitchDifference);

			for (int x = C3App.RENDER_WIDTH - 1; x > 0; x--) {
				history[x] = history[x - 1];
				pitchHistory[x] = pitchHistory[x - 1];
			}
			pitchHistory[0] = currentPitch;
			history[0] = (int) (currentPitch + 0.5);
		}
	}

	void start() {
		System.out.println(scrollSheet.sourceChannel.getNotes());

		history = new float[C3App.RENDER_WIDTH];
		pitchHistory = new float[C3App.RENDER_WIDTH];

		musicPlayer.play(C3Game.testMap);
		// currentTick = -scrollSheet.getOffscreenTickDelta();
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
		menu_button.updateHoverStatus(newx, newy);
		start_button.updateHoverStatus(newx, newy);
		pause_button.updateHoverStatus(newx, newy);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// CHECK BUTTONS
		{
			if (menu_button.ifOnButton(x, y)) {

				// static float currentPitch;
				// static float[] history = new float[C3App.RENDER_WIDTH],
				// pitchHistory = new float[C3App.RENDER_WIDTH];
				//
				// boolean playing;
				//
				// public static MusicMap testMap;
				// public static MusicPlayer musicPlayer;
				// public static ScrollingMusicSheet scrollSheet;
				//
				// long startTime, deltaTime;
				// int currentTick;
				// float pixelsPerTick = 2;

				// public static MusicMap testMap;
				// public static MusicPlayer musicPlayer;
				// public static ScrollingMusicSheet scrollSheet;
				if (musicPlayer.getSequencer() != null)
					musicPlayer.stop();
				playing = false;

				currentPitch = 0;

				C3App.splash.reset();
				C3SplashScreen.setRedirect("main_menu");
				C3App.setPage("splash");
			} else if (start_button.ifOnButton(x, y)) {
				if (!paused && !playing)
					start();
				else if (paused && playing) {
					paused = false;
					musicPlayer.resume();
				}
			} else if (pause_button.ifOnButton(x, y)) {
				musicPlayer.stop();
				paused = true;
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {

	}

}
