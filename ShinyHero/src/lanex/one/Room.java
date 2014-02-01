package lanex.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Room {

	public final static float ROOM_X_SIZE = 720f;
	public final static float ROOM_Y_SIZE = 720f;
	public int indX, indY;
	protected ArrayList<Enemy> mobs;
	protected ArrayList<StaticEntity> items;

	public Room(int x, int y) {
		indX = x;
		indY = y;
		mobs = new ArrayList<Enemy>();
		items = new ArrayList<StaticEntity>();
	}

	public void update(Player p) {
		for (Enemy e : mobs) {
			e.update(p);
		}
		for (StaticEntity i : items) {
			i.update(p);
		}
		for (int i = 0; i < mobs.size(); i++) {
			Enemy m = mobs.get(i);
			if (m.health <= 0) {
				ERM.sfx("monster_death.se.ogg");
				mobs.remove(m);
				p.score += 20;
				addItem(m.position.x, m.position.y);
			}
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).flag) {
				
				if(items.get(i) instanceof BuffItem){
					BuffItem buff = (BuffItem) items.get(i);
					if (buff.type == ItemTypes.DAMAGE){
						p.weapons++;
					} else if (buff.type == ItemTypes.HEALTH){
						p.potions++;
					} else if (buff.type == ItemTypes.SIZE){
						p.armors++;
					} else if (buff.type == ItemTypes.SPEED){
						p.boots++;
					}
				}
				
				items.remove(items.get(i));
				p.score += 10;
				ERM.sfx("item.se.ogg");
			}
		}
		if (p.health <= 0) {
			OneApp.splash.reset();	
			OneApp.setPage("gameover");		
			ERM.sfx("death.se.ogg");
			
			ArrayList<Integer> scores = new ArrayList<Integer>();
			int i = 0;
			try{
				BufferedReader in = new BufferedReader(new FileReader("hs.txt"));
				String line = null;
				while((line = in.readLine()) != null && i < 10){
					scores.add(Integer.parseInt(line));
					i++;
				}
				in.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
			scores.add(Player.score);
			Collections.sort(scores, new Comparator<Integer>(){
				@Override
				public int compare(Integer arg0, Integer arg1) {
					// TODO Auto-generated method stub
					return -arg0.intValue()+arg1.intValue();
				}
			});
			
			System.out.println("Player score: " + Player.score);
			System.out.println(scores);
			
			// PRINT
			try{
				PrintWriter out = new PrintWriter(new FileWriter("hs.txt"));
				
				i = 0;
				for(Integer s : scores){
					out.println(s);
					i++;
					if (i == 10) break;
				}
				
				out.close();
				
				
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
		}
		// System.exit (0);
	}

	public void render(Graphics g) {
		g.drawImage(ERM.getImage("room.png"), 0, 0);

		// DRAW DOORS
		Image door = ERM.getImage("door.png");
		door.setRotation(0);
		g.drawImage(door, 322.5f, 0); // TOP
		door.setRotation(180);
		g.drawImage(door, 322.5f, 645); // BOTTOM
		door.setRotation(270);
		g.drawImage(door, 0, 322.5f); // LEFT
		door.setRotation(90);
		g.drawImage(door, 645, 322.5f); // RIGHT

		for (Enemy e : mobs) {
			e.render(g);
		}
		for (StaticEntity i : items) {
			i.render(g);
		}
	}

	public void addMob() {
		int str = (int) (Math.random() * 3);
		if (str == 0)
			mobs.add(new Enemy(MobTypes.LARGE_MOB));
		else if (str == 1)
			mobs.add(new Enemy(MobTypes.MEDIUM_MOB));
		else
			mobs.add(new Enemy(MobTypes.SMALL_MOB));
	}

	public void addItem(float x, float y) {
		int str = (int) (Math.random() * 4);
		if (str == 0)
			items.add(new BuffItem(x, y, 20, "weapon.png", ItemTypes.DAMAGE, 1f));
		else if (str == 1)
			items.add(new BuffItem(x, y, 20, "potion.png", ItemTypes.HEALTH,
					50f));
		else if (str == 2)
			items.add(new BuffItem(x, y, 20, "armor.png", ItemTypes.SIZE, 1f));
		else
			items.add(new BuffItem(x, y, 20, "boots.png", ItemTypes.SPEED, 0.5f));
	}

	public void addItem() {
		addItem((float) Math.random() * 720, (float) Math.random() * 720);
	}
}
