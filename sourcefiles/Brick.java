
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class Brick extends Sprite
{
	BufferedImage brick = null;


	Brick(Model m, int _x, int _y, int _w, int _h)
	{
		super(m);
		x = _x;
		y = _y;
		w = _w;
		h = _h;

		if(brick == null)
		{
		  try
		  {
		  	brick = ImageIO.read(new File("./images/brick1.png"));
		  }
		  catch(Exception e)
		  {
		  	e.printStackTrace(System.err);
		  	System.exit(1);
		  }
		}
  }

  Brick(Json ob, Model m)
  {
    super(m);
    type = ob.getString("type");
    x = (int)ob.getLong("x");
    y = (int)ob.getLong("y");
    w = (int)ob.getLong("w");
    h = (int)ob.getLong("h");

		if(brick == null)
		{
		  try
		  {
		  	brick = ImageIO.read(new File("./images/brick1.png"));
		  }
		  catch(Exception e)
		  {
		  	e.printStackTrace(System.err);
		  	System.exit(1);
		  }
		}
  }

  boolean update()
  {
		return true;
  }

  void draw(Graphics g)
  {
    //draw bricks
    g.drawImage(brick, x - model.scrollPos, y, w, h, null);
  }

  boolean am_i_a_brick()
  {
    return true;
  }


  Json marshall()
  {
    Json ob = Json.newObject();
    ob.add("type", "Brick");
    ob.add("x", x);
    ob.add("y", y);
    ob.add("w", w);
    ob.add("h",h);

    return ob;
  }




}
