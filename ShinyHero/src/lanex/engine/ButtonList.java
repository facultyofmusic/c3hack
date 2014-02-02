package lanex.engine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ButtonList<T> {
	private float startX, startY, width;

	private int numMembers;

	private List<Button> buttons;
	private List<T> members;

	private T currentMember;
	private String currentName;

	public ButtonList(float x, float y, float width) {
		this.startX = x;
		this.startY = y;
		this.width = width;

		numMembers = 0;

		buttons = new ArrayList<>();
		members = new ArrayList<>();
		currentMember = null;
		currentName = null;
	}

	public T getSelected() {
		return currentMember;
	}
	
	public String getSelectedName() {
		return currentName;
	}
	
	public void checkButtons(float mx, float my) {
		for (int i = 0; i < numMembers; i++) {
			if (buttons.get(i).ifOnButton(mx, my)) {
				currentMember = members.get(i);
				currentName = buttons.get(i).img;
			}
		}
	}

	public void addMember(T member, String memberName) {
		members.add(member);
		buttons.add(new Button(startX, startY + numMembers * 22, width, 20,
				memberName));
		numMembers++;
	}

	public void render(Graphics g) {
		for (int i = 0; i < numMembers; i++) {
			Button b;
			(b = buttons.get(i)).render(g);
			if (members.get(i) == (currentMember))
				g.setColor(Color.red);
				g.drawRect(b.x, b.y, b.width, b.height);
		}
	}

}
