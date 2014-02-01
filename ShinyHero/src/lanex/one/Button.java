package lanex.one;

import org.newdawn.slick.Graphics;

public class Button {
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected String img;

	public Button(float x, float y, float width, float height, String img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}

	public boolean ifOnButton(float mx, float my) {
		if (mx > x && mx < x + width && my > y && my < y + height){
			ERM.sfx("button.se.ogg");
			return true;
		}
		return false;
	}

	public void render(Graphics g) {
		ERM.getImage(img).draw(x, y);
	}
}