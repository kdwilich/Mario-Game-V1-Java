
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.Color;

class View extends JPanel
{
	Model model;
	BufferedImage bg = null;

	View(Controller c, Model m)
	{
		model = m;
		try
		{

			bg = ImageIO.read(new File("./images/bgClouds.png"));

		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}

	}

	public void paintComponent(Graphics g)
	{

		//clear screen
		for(int i = 0; i < 7680; i+=1920)
		{
			g.drawImage(bg, -model.scrollPos+i-100, 0, null);
		}
		//g.setColor(new Color(255,0,0));
		//g.fillRect(0, 0, 900, 700);

		// draw ground
		g.setColor(new Color(119,70,38));
		g.fillRect(0, 595, 900, 700);


		// draw sprites
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g);
		}

	}
}
