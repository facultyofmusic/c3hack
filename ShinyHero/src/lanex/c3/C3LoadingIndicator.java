package lanex.c3;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class C3LoadingIndicator {
	private static Image snow, english;
	private static boolean eP, sP;

	public static void reset() {
		try {
			if (snow == null) {
				Image loading = new Image("data/images/loading.png");
				english = loading.getSubImage(0, 0, 128, 32);
				snow = loading.getSubImage(0, 32, 80, 80);
			}

			snow.setAlpha(0);
			english.setAlpha(0);
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}

	public static void draw(Graphics g){
		g.drawImage(snow, 1050, 600);
		snow.setRotation(snow.getRotation() + 2f);
		if (snow.getAlpha() >= 0.6) {
			sP = false;
		} else if (snow.getAlpha() <= 0.2f) {
			sP = true;
		}

		if (sP) {
			snow.setAlpha(snow.getAlpha() + 0.014f);
		} else {
			snow.setAlpha(snow.getAlpha() - 0.014f);
		}

		g.drawImage(english, 1080, 650);
		if (english.getAlpha() >= 1) {
			eP = false;
		} else if (english.getAlpha() <= 0.3f) {
			eP = true;
		}

		if (eP) {
			english.setAlpha(english.getAlpha() + 0.022f);
		} else {
			english.setAlpha(english.getAlpha() - 0.022f);
		}
	}
}