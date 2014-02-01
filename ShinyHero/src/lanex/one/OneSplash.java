package lanex.one;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class OneSplash extends ScreenPage{
	private Image backgroundImage;
	private Image black;
	static String REDIRECT = null;
	
	public static void setRedirect(String s){
		REDIRECT = s;
		ERM.set("data/" + REDIRECT + ".rlist");
	}
	
	public OneSplash(){
		try{
			backgroundImage = new Image("data/splash.png");
			black = new Image("data/black.png");
			
			reset();
		}catch(SlickException ex){
			ex.printStackTrace();
		}
	}
	
	public void reset(){
		black.setAlpha(0);	
		backgroundImage.setAlpha(0);
		LoadingIndicator.reset();	
	}

	@Override
	public void render(GameContainer container, Graphics g)
			{
		g.clear();
		backgroundImage.draw(0, 0);
		if (backgroundImage.getAlpha() < 1.0){
			backgroundImage.setAlpha(backgroundImage.getAlpha() + 0.11f);
		}else{			
			LoadingIndicator.draw(g);

			LoadingIndicator.draw(g);
			if (ERM.isDoneLoading()) {
				black.setAlpha(black.getAlpha() + 0.02f);

				if (black.getAlpha() >= 1) {
					OneApp.setPage(REDIRECT);
				}

				g.drawImage(black, 0, 0, OneApp.RENDER_WIDTH, OneApp.RENDER_HEIGHT,
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
