package lanex.c3;

import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class C3Game extends ScreenPage {

	public C3Game() {
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		g.setColor(Color.white);
		g.drawString("WE PUT SHIT HERE!!!!", 100, 100);
		
		//System.out.println("WE ARE DRAWING THE AME");
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
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		
	}

}
