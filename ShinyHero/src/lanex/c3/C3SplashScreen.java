package lanex.c3;


import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class C3SplashScreen extends ScreenPage{
	private Image backgroundImage;
	private Image black;
	static String REDIRECT = null;
	
	float fadeTime = 0.1f;
	
	public static void setRedirect(String s){
		REDIRECT = s;
		ERM.set("data/" + REDIRECT + ".rlist");
	}
	
	public C3SplashScreen(){
		try{
			backgroundImage = new Image("data/c3images/def_background.png");
			black = new Image("data/black.png");
			
			reset();
		}catch(SlickException ex){
			ex.printStackTrace();
		}
	}
	
	public void reset(){
		black.setAlpha(0);	
		backgroundImage.setAlpha(0);
		C3LoadingIndicator.reset();	
	}

	@Override
	public void render(GameContainer container, Graphics g)
			{
		g.clear();
		backgroundImage.draw(0, 0);
		if (backgroundImage.getAlpha() < 1.0){
			backgroundImage.setAlpha(backgroundImage.getAlpha() + fadeTime);
		}else{			
			//hacky way to double rotation speed
			C3LoadingIndicator.draw(g);
			C3LoadingIndicator.draw(g);
			
			if (ERM.isDoneLoading()) {
				black.setAlpha(black.getAlpha() + fadeTime);

				if (black.getAlpha() >= 1) {
					C3App.setPage(REDIRECT);
				}

				g.drawImage(black, 0, 0, C3App.RENDER_WIDTH, C3App.RENDER_HEIGHT,
						0, 0, 40, 40);
			} else {
				ERM.loadNext();
			}
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
