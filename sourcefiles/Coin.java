

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class Coin extends Sprite
{
  static BufferedImage coin = null;

  //coin constructor
  Coin(Model m, int _x, int _y, double vv, double hv)
  {
    super(m);
    x = _x + 15;
    y = _y - 25;
    w = 60;
    h = 60;
    vert_vel = vv;
    horiz_vel = hv;

    if(coin == null)
    {
      try
      {
      	coin = ImageIO.read(new File("./images/coin.png"));
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
    //model.scrollPos = x - 250;

    x += horiz_vel;

		vert_vel += 3.5;
		y += vert_vel;

    if(y > 560)
      return false;

    return true;
  }

  void draw(Graphics g)
  {
    g.drawImage(coin, x - model.scrollPos, y, w, h, null);
  }

  Json marshall()
  {
    return null;
  }


}
