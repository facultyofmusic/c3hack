package lanex.c3;

import lanex.engine.Button;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class C3Customization extends ScreenPage{
	
	private Button hell_button, menu_button;
	
	public C3Customization(){
		hell_button = new Button(C3App.RENDER_WIDTH/2 - 200,  550, 400, 100, "start_button.png");
		menu_button = new Button(C3App.RENDER_WIDTH/2 - 600,  550, 400, 100, "menu_button.png");
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		
		
		g.clear();
		
		//ERM.listRes();
		//System.out.println("IMAGE: " + ERM.getImage("room.png"));

		
		g.drawImage(ERM.getImage("custom_back.png"), 0, 0);
		
		hell_button.render(g);
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
			if (hell_button.ifOnButton(x, y)){
				C3App.splash.reset();
				C3SplashScreen.setRedirect("game");		
				C3App.setPage("splash");				
			}else if (menu_button.ifOnButton(x, y)){
				C3App.splash.reset();
				C3App.setPage("main_menu");				
			}
		}
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
