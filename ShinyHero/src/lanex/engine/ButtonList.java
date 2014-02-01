package lanex.engine;

import java.util.ArrayList;
import java.util.List;

public class ButtonList<T> {
	private float startX, startY, width;
	
	private int numMembers;
	
	private List<Button> buttons;
	private List<T> members;

	T currentMember;

	public ButtonList(float x, float y, float width) {
		this.startX = x;
		this.startY = y;
		this.width=width;
		
		numMembers = 0;
		
		buttons = new ArrayList<>();
		members = new ArrayList<>();
		currentMember = null;
	}

	public void addMember(T member) {
		members.add(member);
		buttons.add(new Button(,,,,))
	}

}
