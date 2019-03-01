
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	int mouseDownX;
	int mouseDownY;

	Controller(Model m)
	{
		model = m;
	}

	void setView(View v)
	{
		view = v;
	}

	public void mousePressed(MouseEvent e)
	{
    mouseDownX = e.getX();
    mouseDownY = e.getY();
	}

	public void mouseReleased(MouseEvent e)
	{
		int x1 = mouseDownX;
    int x2 = e.getX();
    int y1 = mouseDownY;
    int y2 = e.getY();
    int left = Math.min(x1, x2);
    int right = Math.max(x1, x2);
    int top = Math.min(y1, y2);
    int bottom = Math.max(y1, y2);

	model.addBrick(left + model.scrollPos, top, right - left, bottom - top);




	}

	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
			{
				case KeyEvent.VK_RIGHT: keyRight = true; break;
				case KeyEvent.VK_LEFT: keyLeft = true; break;
				case KeyEvent.VK_UP: keyUp = true; break;
				case KeyEvent.VK_DOWN: keyDown = true; break;
				case KeyEvent.VK_SPACE: keySpace = true; break;
				case KeyEvent.VK_S: model.saveMap("map.json"); break;
				case KeyEvent.VK_L: model.loadMap("map.json"); break;
				case KeyEvent.VK_E: model.addCoinBlock(model.mario.x, model.mario.y); break;
			}
		}

		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_RIGHT: keyRight = false; break;
				case KeyEvent.VK_LEFT: keyLeft = false; break;
				case KeyEvent.VK_UP: keyUp = false; break;
				case KeyEvent.VK_DOWN: keyDown = false; break;
				case KeyEvent.VK_SPACE: keySpace = false; break;

			}
		}

		public void keyTyped(KeyEvent e)
		{
		}

		void update()
		{
			model.rememberPrevStep();

	    if(keyRight)
			{
				model.mario.x += 12.5;
			}
			if(keyLeft)
			{
				model.mario.x -= 12.5;
			}
			if(keySpace)
			{
				if(model.mario.frames_since_ground < 5){
					model.mario.vert_vel -= 10.1;
				}
				if(model.mario.grounded) {
					model.mario.grounded = false;
				}
			}
		}

}
