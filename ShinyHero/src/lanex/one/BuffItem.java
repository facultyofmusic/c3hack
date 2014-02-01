package lanex.one;

public class BuffItem extends StaticEntity {

	
	
	protected ItemTypes type;
	protected float buff;
	protected Player player;

	public BuffItem(float x, float y, float radius, String img, ItemTypes type, float buff) {
		super(x, y, radius, img);
		this.type = type;
		this.buff = buff;
	}
	
	public void onHit()
	{
		if (type == ItemTypes.HEALTH){
			player.health += buff;
		}if (type == ItemTypes.DAMAGE){
			player.damage += buff;
		}if (type == ItemTypes.SIZE){
			player.mass += buff;
		}if (type == ItemTypes.SPEED){
			player.MAX_SPEED += buff;
		}
	}
	
	public void update (Player p)
	{
		player = p;
		super.update(p);
	}
}
