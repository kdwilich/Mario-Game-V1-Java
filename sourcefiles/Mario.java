

import java.awt.Graphics;
import java.util.Iterator;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

class Mario extends Sprite
{
	int prev_x;
	int prev_y;
	int coin_total;
	int frames_since_ground;
	boolean collides_on_bottom;
	boolean grounded;
	static Image[] mario_images = null;

	Mario(Model m)
	{
		super(m);
		x = 250;
		y = 0;
		w = 65;
		h = 90;
		num_hits = 0;
		frames_since_ground = 0;
		collides_on_bottom = false;
		grounded = false;


		// lazy loading
		if(mario_images == null)
		{
			mario_images = new Image[5];
			try
			{
				mario_images[0] = ImageIO.read(new File("./images/mario1.png"));
				mario_images[1] = ImageIO.read(new File("./images/mario2.png"));
				mario_images[2] = ImageIO.read(new File("./images/mario3.png"));
				mario_images[3] = ImageIO.read(new File("./images/mario4.png"));
				mario_images[4] = ImageIO.read(new File("./images/mario5.png"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}

	}

	Mario(Json ob, Model m)
	{
		super(m);
		type = ob.getString("type");
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		vert_vel = (double)ob.getDouble("vertvel");

		if(mario_images == null)
		{
			mario_images = new Image[5];
			try
			{
				mario_images[0] = ImageIO.read(new File("./images/mario1.png"));
				mario_images[1] = ImageIO.read(new File("./images/mario2.png"));
				mario_images[2] = ImageIO.read(new File("./images/mario3.png"));
				mario_images[3] = ImageIO.read(new File("./images/mario4.png"));
				mario_images[4] = ImageIO.read(new File("./images/mario5.png"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	void rememberPrevStep()
	{
		prev_x = x;
		prev_y = y;
	}

	boolean doesCollide(int _x, int _y, int _w, int _h)
	{
		if(x + w <= _x) // my right in your left
			return false;
		else if(x >= _x + _w) // my left in your right
			return false;
		else if(y + h <= _y) // my bottom in your top
			return false;
		else if(y >= _y + _h) // my top in your bottom
			return false;
		else
			return true;
	}

	void getOut(int _x, int _y, int _w, int _h)
	{
		if(x + w > _x && prev_x + w <= _x) // collides with left
		{
			x = _x - w;
		}
	 	else if(x < _x + _w && prev_x >= _w + _x) // collides with right
		{
			x = _x + _w;
		}
		else if(y + h > _y && prev_y + h <= _y) // collides with top
		{
			y = _y - h;
			vert_vel = 0;
			frames_since_ground = 0;
			grounded = true;
		}
		else if(y < _y + _h && prev_y <= _y + _h) // collides with bottom
		{
			collides_on_bottom = true;
			y = _y + _h;
			vert_vel = 0;
		}
	}

	boolean update()
	{
		model.scrollPos = x - 250;

		vert_vel += 3.5;
		y += vert_vel;

		if(y >= 500)
		{
			y = 500; // snap back to reality
			vert_vel = 0.0;
			frames_since_ground = 0;
			grounded = true;
		}
		else
			grounded = false;

		//iterator
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			if(s.am_i_a_brick())
			{
				if(doesCollide(s.x, s.y, s.w, s.h))
				{
					getOut(s.x, s.y, s.w, s.h);
				}
			}

			if(s.am_i_a_coin_block())
			{
				int _x = s.x;
				int _y = s.y;
				if(doesCollide(s.x, s.y, s.w, s.h))
				{
					getOut(s.x, s.y, s.w, s.h);
					if(collides_on_bottom)
					{
						s.num_hits++;
						if(s.num_hits <= 5) {
							CoinBlock cb = (CoinBlock)s;
							cb.pop_out(model, _x, _y);
							coin_total++;
						}
						collides_on_bottom = false;
					}


				}
			}
		}
		frames_since_ground++;

		return true;
	}

	void draw(Graphics g)
	{
		// draw Mario
		int marioFrame = (Math.abs(x) / 20) % 5; //20 = rate of running, 5 = frames of mario
		g.drawImage(mario_images[marioFrame], x - model.scrollPos, y, null);

	}

	Json marshall()
	{
		Json ob = Json.newObject();
		ob.add("type", "Mario");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("vertvel", vert_vel);

		return ob;
	}

	boolean am_i_mario()
	{
		return true;
	}

}
