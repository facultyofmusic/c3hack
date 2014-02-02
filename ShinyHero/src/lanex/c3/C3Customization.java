package lanex.c3;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import lanex.c3.midi.Channel;
import lanex.c3.midi.MusicMap;
import lanex.c3.midi.MusicPlayer;
import lanex.c3.midi.Track;
import lanex.engine.Button;
import lanex.engine.ButtonList;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class C3Customization extends ScreenPage {

	private Button start_button, exit_button, help_button, easy_button;

	private ButtonList<ButtonList<Channel>> songList;
	private ButtonList<Channel> channelList;

	private File currentSong;
	private Object currentTrack;

	public C3Customization() {
		start_button = new Button(C3App.RENDER_WIDTH / 2 - 225, 520, 450, 150,
				"btn_start.png");
		exit_button = new Button(C3App.RENDER_WIDTH - 309, 585, 256, 85,
				"btn_mainmenu.png");
		help_button = new Button(53, 585, 256, 85, "btn_help.png");

		File mid = new File("mid/");
		File[] mids = mid.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".mid");
			}
		});
		songList = new ButtonList<>(10, 10, C3App.RENDER_WIDTH / 2 - 150);
		for (File f : mids) {
			List<Track> temp = MusicMap.fromPath(f.getPath()).getTrackList();
			channelList = new ButtonList<>(C3App.RENDER_WIDTH / 2 - 130, 10,
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
		help_button.render(g);

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
		// TODO Auto-generated method stub

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
			if (channelList != null)
				channelList.checkButtons(x, y);
			if (start_button.ifOnButton(x, y)) {
				C3Game.testMap = MusicMap.fromPath(songList.getSelectedName());
				C3Game.musicPlayer = new MusicPlayer();
				C3Game.scrollSheet = new ScrollingMusicSheet(
						channelList.getSelected());
				System.out.println("SETTING: "
						+ C3Game.scrollSheet.sourceChannel.getNotes());

				C3App.splash.reset();
				C3SplashScreen.setRedirect("game");
				C3App.setPage("splash");
			} else if (exit_button.ifOnButton(x, y)) {
				System.exit(0);
			} else if (help_button.ifOnButton(x, y)) {
				C3App.setPage("help");
			}
		}

	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub

	}
}
