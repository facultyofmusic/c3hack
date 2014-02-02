package lanex.c3;

import java.util.Iterator;

import lanex.c3.midi.MusicMap;
import lanex.c3.midi.MusicPlayer;
import lanex.c3.midi.Note;
import lanex.c3.midi.ScrollingMusicSheet;
import lanex.engine.Button;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ui.AudioInputProcessor;

public class C3Game extends ScreenPage {
	private Button menu_button, start_button, pause_button;

	static float currentPitch, pitchDifference;

	static boolean playing;

	static boolean paused;

	public static MusicMap testMap;
	public static MusicPlayer musicPlayer;
	public static ScrollingMusicSheet scrollSheet;
	
	
	int tickMakeUp = 50;
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
		float powerColor = 1.0f*scrollSheet.powerLevel/1280;
		Color darkFilter = new Color(powerColor, powerColor, powerColor);
		
		g.setColor(Color.white);
		g.drawImage(ERM.getImage("game_background.png"), 0, 0, 0, 0, 1280, 720, darkFilter);

		String[] notes = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
				"A", "A#", "B" };

		String note = (currentPitch > 0) ? notes[((int) (currentPitch + 0.5) % 12)]
				: "NaN";

		// debug headphones
		g.setColor(Color.black);

		g.drawString((new StringBuilder("Note: ")).append(note).toString(), 180,
				20);
		g.drawString(
				(new StringBuilder("Freq: ")).append(
						AudioInputProcessor.frequency).toString(), 20, 200);
		
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
		
		
		//drawIndicators();
		g.drawImage(ERM.getImage("scroll_overlay.png"), 0, 250);
		
		g.setColor(Color.gray);
		g.drawLine(C3App.RENDER_WIDTH/2 - scrollSheet.pixelsPerTick * this.tickMakeUp, 120, C3App.RENDER_WIDTH/2 - scrollSheet.pixelsPerTick * this.tickMakeUp, 600);
		
		

		drawStaff(g);
		
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


		// draw the scrolling music sheet
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
								C3App.RENDER_HEIGHT - getDistanceFromBottom(temp.getPitch()),
						pieceLength + 1, 10);

			}

			g.setColor(Color.lightGray);
			g.drawRect(
					C3App.RENDER_WIDTH / 2
							- (currentTick - temp.getStartTick())
							* scrollSheet.pixelsPerTick, C3App.RENDER_HEIGHT
							- getDistanceFromBottom(temp.getPitch()),
					(temp.getStopTick() - temp.getStartTick())
							* scrollSheet.pixelsPerTick, 10);
			
			//draw sharp symbol
			int semiTones = temp.getPitch() % 12;
			if (semiTones == 1 || semiTones == 3 || semiTones == 6 || semiTones == 8 || semiTones == 10) {
				g.setColor(Color.lightGray);
				g.drawString("#", C3App.RENDER_WIDTH / 2
							- (currentTick - temp.getStartTick())
							* scrollSheet.pixelsPerTick - 10, C3App.RENDER_HEIGHT
							- getDistanceFromBottom(temp.getPitch()));
			}

		}
		
		g.drawImage(ERM.getImage("power_bar.png"), 0, 688, 0, 0, 1280, 32, darkFilter);
		g.setColor(Color.black);
		g.fillRect(scrollSheet.powerLevel, 688, 1280, 720);

		update();
	}
	
	private void drawStaff(Graphics g) {
		g.setColor(Color.white);
		g.setLineWidth(2);
		for (int i = 0; i < 5; i++) {
			g.drawLine(0, C3App.RENDER_HEIGHT - 292 - 16 * i, 
					C3App.RENDER_WIDTH, C3App.RENDER_HEIGHT - 292 - 16 * i);
		}
	}
	
	private int getDistanceFromBottom(int pitch) {
		final int GAP_HEIGHT = 8;
		int octaveHeight = 7 * GAP_HEIGHT;
		int leftoverPitch = pitch % 12;
		int semitoneOffset;
		switch (leftoverPitch) {
		//C,C#
		case 0:
		case 1:
			semitoneOffset = 0;
			break;
		//D,D#
		case 2:
		case 3:
			semitoneOffset = GAP_HEIGHT;
			break;
		//E
		case 4:
			semitoneOffset = 2 * GAP_HEIGHT;
			break;
		//F, F#
		case 5:
		case 6:
			semitoneOffset = 3 * GAP_HEIGHT;
			break;
		//G,G#
		case 7:
		case 8:
			semitoneOffset = 4 * GAP_HEIGHT;
			break;
		//A,A#
		case 9:
		case 10:
			semitoneOffset = 5 * GAP_HEIGHT;
			break;
		//B
		default:
			semitoneOffset = 6 * GAP_HEIGHT;
			break;
		}
		int result = octaveHeight * (pitch / 12) + semitoneOffset;
		
		return result;
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
			scrollSheet.update(currentTick + 50, pitchDifference);
			
		}
	}

	void start() {
		musicPlayer.play(C3Game.testMap);
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
				// go back to menu
				if (musicPlayer.getSequencer() != null)
					musicPlayer.stop();
				playing = false;

				currentPitch = 0;

				C3App.splash.reset();
				C3SplashScreen.setRedirect("main_menu");
				C3App.setPage("splash");
				
			} else if (start_button.ifOnButton(x, y)) {
				// start the music
				if (!paused && !playing)
					start();
				else if (paused && playing) {
					paused = false;
					musicPlayer.resume();
				}
			} else if (pause_button.ifOnButton(x, y)) {
				// stop the music
				musicPlayer.stop();
				paused = true;
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {

	}

}
