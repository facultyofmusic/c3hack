package lanex.one;

import java.util.Arrays;

public class Floor {
	private Room [][] rooms;
	private int maxMobs;
	private int maxItems;
	public int size;
	
	public Floor (int level)
	{
		size = Math.min (3+level/5, 5);
		
		rooms = new Room [size][size];
		
		maxMobs = Math.min(level/5 + 2, 4);
		
		maxItems = maxMobs;
		
		generate();
	}
	
	public Room getRoom(int x, int y){
		return rooms[x][y];
	}
	
	public void generate()
	{
		int a = (int) (Math.random()*size);
		int b = (int) (Math.random()*size);
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				rooms[x][y] = new Room(x, y);
				if (x == a && y == b)
					rooms[x][y].items.add(new Stairs (360,360,50, "stairs.png"));
				do
				{
					rooms[x][y].addMob();
				}while (Math.random() < 0.3 && rooms[x][y].mobs.size() < maxMobs);
				do
				{
					rooms[x][y].addItem();
				}while (Math.random() < 0.3 && rooms[x][y].items.size() <= maxItems);
			}
		}
		
	}
	
	
}
