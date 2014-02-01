package lanex.c3;

import lanex.engine.Button;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class C3Gameover extends ScreenPage{
	
	private Button menu_button;
	
	public C3Gameover(){
		menu_button = new Button(C3App.RENDER_WIDTH/2 - 200,  550, 400, 100, "menu_button.png");
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		
		
		g.clear();
		g.setColor(Color.white);
		
		//ERM.listRes();
		//System.out.println("IMAGE: " + ERM.getImage("room.png"));

		
		g.drawImage(ERM.getImage("gameover_back.png"), 0, 0);
		
		menu_button.render(g);
		
		
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
		//CHECK BUTTONS
		{
			if (menu_button.ifOnButton(x, y)){
				C3App.splash.reset();
				C3SplashScreen.setRedirect("main_menu");		
				C3App.setPage("splash");					
			}
		}
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
