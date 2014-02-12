package lanex.c3;

import java.awt.Font;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import lanex.c3.midi.Channel;
import lanex.c3.midi.MusicMap;
import lanex.c3.midi.MusicPlayer;
import lanex.c3.midi.ScrollingMusicSheet;
import lanex.c3.midi.Track;
import lanex.engine.Button;
import lanex.engine.ButtonList;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class C3Customization extends ScreenPage {
	protected static final TrueTypeFont TEXT_FONT = new TrueTypeFont(new Font("sans-serif", Font.BOLD, 20), true);

	
	private Button start_button, exit_button, 
			increase_speed_button, decrease_speed_button;

	private ButtonList<ButtonList<Channel>> songList;
	private ButtonList<Channel> channelList;
	
	private float speed = 0.5f;

	private File currentSong;
	private Object currentTrack;

	public C3Customization() {
		start_button = new Button(C3App.RENDER_WIDTH / 2 - 225, 520, 450, 150,
				"btn_start.png", Color.green);
		exit_button = new Button(C3App.RENDER_WIDTH - 309, 585, 256, 85,
				"btn_exit.png", Color.red);
		increase_speed_button = new Button(C3App.RENDER_WIDTH - 150, 50, 100,
				100, "btn_plus.png", Color.cyan);
		decrease_speed_button = new Button(C3App.RENDER_WIDTH - 350, 50,
				100, 100, "btn_minus.png", Color.magenta);

		File mid = new File("mid/");
		File[] mids = mid.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".mid");
			}
		});
		songList = new ButtonList<>(50, 50, C3App.RENDER_WIDTH / 2 - 200);
		for (File f : mids) {
			List<Track> temp = MusicMap.fromPath(f.getPath()).getTrackList();
			channelList = new ButtonList<>(C3App.RENDER_WIDTH / 2 - 130, 50,
					250); // Temporary to save
							// declaration space. Is
							// nulled later.
			for (Track t : temp)
				for (Channel c : t.getChannels())
					if (c != null)
						channelList.addMember(c, c.getName());
			songList.addMember(channelList, f.getPath());
		}
		channelList = null;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING

		g.clear();

		// ERM.listRes();
		// System.out.println("IMAGE: " + ERM.getImage("room.png"));

		g.drawImage(ERM.getImage("def_background.png"), 0, 0);

		songList.render(g);
		if (channelList != null)
			channelList.render(g);
		start_button.render(g);
		exit_button.render(g);
		increase_speed_button.render(g);
		decrease_speed_button.render(g);

		TEXT_FONT.drawString(C3App.RENDER_WIDTH - 230, 75, "SPEED");
		float width = TEXT_FONT.getWidth(speed*2 + "x");
		TEXT_FONT.drawString(C3App.RENDER_WIDTH - 200 - width/2, 100, speed*2 + "x");
		
		// WIDTH SHOULD BE 400
		// HEIGHT SHOULD BE 100

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
		exit_button.updateHoverStatus(newx, newy);
		increase_speed_button.updateHoverStatus(newx, newy);
		decrease_speed_button.updateHoverStatus(newx, newy);

		if (channelList != null) {
			// only update the start hover if the channel is selected
			if (channelList.getSelected() != null) {
				start_button.updateHoverStatus(newx, newy);
			}
			channelList.updateButtonHoverStatuses(newx, newy);
		}
		if (songList != null) {
			songList.updateButtonHoverStatuses(newx, newy);
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// CHECK BUTTONS
		{
			songList.checkButtons(x, y);
			channelList = songList.getSelected();

			if (channelList != null) {
				channelList.checkButtons(x, y);
			}

			if (increase_speed_button.ifOnButton(x, y)) {
				speed += 0.125;
			}
			if (decrease_speed_button.ifOnButton(x, y)) {
				speed -= 0.125;
			}

			if (start_button.ifOnButton(x, y) && channelList != null
					&& channelList.getSelected() != null) {
				C3Game.testMap = MusicMap.fromPath(songList.getSelectedName());
				C3Gameover.lastName = songList.getSelectedName();
				C3Game.scrollSheet = new ScrollingMusicSheet(
						channelList.getSelected());
				C3Game.musicPlayer = new MusicPlayer();
				C3Gameover.lastChannel = channelList.getSelected();
				
				C3Game.scrollSheet.pixelsPerTick = speed;

				System.out.println("SETTING: "
						+ C3Game.scrollSheet.sourceChannel.getNotes());

				
				
				C3Game.paused = false;
				C3Game.playing = false;

				C3App.splash.reset();
				C3SplashScreen.setRedirect("game");
				C3App.setPage("splash");
			} else if (exit_button.ifOnButton(x, y)) {
				System.exit(0);
			}
		}

	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub

	}
}
