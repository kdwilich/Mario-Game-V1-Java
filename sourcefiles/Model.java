import java.util.ArrayList;


class Model
{
	ArrayList<Sprite> sprites;
	int scrollPos;

	//redundant mario & brick reference for controller
	Mario mario;

	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>();

		sprites.add(mario);
	}

	void rememberPrevStep()
	{
		mario.rememberPrevStep();
	}

	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			boolean keep = s.update();
			if(!keep)
			{
				sprites.remove(i);
				i--;
			}
		}
	}

	public void addBrick(int x, int y, int w, int h)
	{
		Sprite s = new Brick(this, x, y, w, h);
		sprites.add(s);
	}

	//called from model in order to pass from controller to sprites
	public void addCoinBlock(int x, int y)
	{
		Sprite cb = new CoinBlock(this, x, y);
		sprites.add(cb);
	}


	void unmarshall(Json ob)
  {
		sprites.clear();
    Json json_sprites = ob.get("sprites");
    for(int i = 0; i < json_sprites.size(); i++)
    {
    	Json j = json_sprites.get(i);
		String spr = j.getString("type");
		Sprite s;

		if(spr.equals("Brick"))
		{
			s = new Brick(j, this);
		  sprites.add(s);
		}
		else if(spr.equals("CoinBlock"))
		{
			s = new CoinBlock(j, this);
			sprites.add(s);
		}
		else //(spr.equals("Brick"))
		{
			mario = new Mario(j, this);
			sprites.add(mario);
		}

  	  //Systems.out.println("Sprite being added: " + sprites.add(s));
    }
  }

  Json marshall()
  {
    Json ob = Json.newObject();
    Json json_sprites = Json.newList();
    ob.add("sprites", json_sprites);
    for(int i = 0; i < sprites.size(); i++)
    {
      Sprite s = sprites.get(i);
      Json j = s.marshall();
      json_sprites.add(j);
    }
    return ob;
  }

  void saveMap(String filename)
  {
    Json ob = marshall();
    ob.save(filename);
  }

  void loadMap(String filename)
  {
    Json ob = Json.load(filename);
		unmarshall(ob);
  }
}
