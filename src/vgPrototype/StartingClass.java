package vgPrototype;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import animation.framework.Animation;


public class StartingClass extends Applet implements Runnable, KeyListener {
	
	enum GameState {
		Running, Dead
	}


    GameState state = GameState.Running;
	private static Robot robot;
	public static Heliboy hb, hb2;
	public static int score = 0;
    private Font font = new Font(null, Font.BOLD, 30);
	private Image image, currentSprite, revcurrentSprite, character, character_reversed, character2,  character2_reversed, character3, character3_reversed, characterDown,
	characterJumped, background, heliboy, heliboy_reversed, heliboy2, heliboy2_reversed, heliboy3, heliboy3_reversed, heliboy4, heliboy4_reversed, heliboy5, heliboy5_reversed;
	private static Boolean reversed = false;
	
    public static Image tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, tiledirt;
	
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, anim2, hanim, hanim2;
	
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");

		character_reversed = getImage(base, "data/character2-reversed.png");
		character2_reversed = getImage(base, "data/character2-reversed.png");
		character3_reversed = getImage(base, "data/character3-reversed.png");
		
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		
		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		heliboy_reversed = getImage(base, "data/heliboy_reversed.png");
		heliboy2_reversed = getImage(base, "data/heliboy2_reversed.png");
		heliboy3_reversed = getImage(base, "data/heliboy3_reversed.png");
		heliboy4_reversed = getImage(base, "data/heliboy4_reversed.png");
		heliboy5_reversed = getImage(base, "data/heliboy5_reversed.png");

		background = getImage(base, "data/background.png");
		
		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");
	 

		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);
		
		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		currentSprite = anim.getImage();

		anim2 = new Animation();
		anim2.addFrame(character_reversed, 1250);
		anim2.addFrame(character2_reversed, 50);
		anim2.addFrame(character3_reversed, 50);
		anim2.addFrame(character2_reversed, 50);
		
		hanim2 = new Animation();
		hanim2.addFrame(heliboy_reversed, 100);
		hanim2.addFrame(heliboy2_reversed, 100);
		hanim2.addFrame(heliboy3_reversed, 100);
		hanim2.addFrame(heliboy4_reversed, 100);
		hanim2.addFrame(heliboy5_reversed, 100);
		hanim2.addFrame(heliboy4_reversed, 100);
		hanim2.addFrame(heliboy3_reversed, 100);
		hanim2.addFrame(heliboy2_reversed, 100);		
		
		revcurrentSprite = anim2.getImage();		

		
		
	}

	public void animate() {
		   anim.update(10);
		   hanim.update(50);
		   anim2.update(10);
		   hanim2.update(50);
	}	
	
	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();		
		// Initialize Tiles
        try {
            loadMap("data/map1.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
			
		Thread thread = new Thread(this);
		thread.start();
	}


	private void loadMap(String filename) throws IOException {
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {
				System.out.println(i + "is i ");

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {

		if (state == GameState.Running) {
			while (true) {
				robot.update();
				if (robot.isJumped()) {
					currentSprite = characterJumped;
				} else if (robot.isJumped() == false
						&& robot.isDucked() == false) {
					currentSprite = anim.getImage();
				}

				ArrayList projectiles = robot.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
					}
				}

				updateTiles();
				hb.update();
				hb2.update();
				bg1.update();
				bg2.update();
				animate();
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (robot.getCenterY() > 500) {
					state = GameState.Dead;
				}
			}
		}

	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {

		if (state == GameState.Running) {
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintTiles(g);
			
			ArrayList projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);


				if (reversed == true){
					p.setSpeedX(-7);
					g.setColor(Color.YELLOW);
					g.fillRect(p.getX() - 60, p.getY(), 10, 5);					
					
				} else {
					
					p.setSpeedX(7);
					g.setColor(Color.YELLOW);
					g.fillRect(p.getX(), p.getY(), 10, 5);						
					
				}

			}			
			
			//g.drawRect((int)robot.rect.getX(), (int)robot.rect.getY(), (int)robot.rect.getWidth(), (int)robot.rect.getHeight());
			//g.drawRect((int)robot.rect2.getX(), (int)robot.rect2.getY(), (int)robot.rect2.getWidth(), (int)robot.rect2.getHeight());	
			//g.drawRect((int)hb.r.getX(), (int)hb.r.getY(), (int)hb.r.getWidth(), (int)hb.r.getHeight());
			if (hb.getCenterX()>robot.getCenterX() || hb2.getCenterX()>robot.getCenterX() || (hb.health==0 && hb2.health==0 )){
				
			  g.drawImage(currentSprite, robot.getCenterX() + 10, robot.getCenterY() + 28, this);
			  g.drawImage(hanim.getImage(), hb.getCenterX() + 10, hb.getCenterY() + 45, this);
			  g.drawImage(hanim.getImage(), hb2.getCenterX() + 10, hb2.getCenterY() + 45, this);	
			  
			} else if (hb.getCenterX()<robot.getCenterX() || hb2.getCenterX()<robot.getCenterX()){
			  reversed = true;	
		      g.drawImage(revcurrentSprite, robot.getCenterX() + 10, robot.getCenterY() + 28, this);	
			  g.drawImage(hanim2.getImage(), hb.getCenterX() + 10, hb.getCenterY() + 45, this);
			  g.drawImage(hanim2.getImage(), hb2.getCenterX() + 10, hb2.getCenterY() + 45, this);		      
			}
			
	
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		}else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);


		}

	}

 	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}


	}


	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}	
	
	
    @Override
 public void keyPressed(KeyEvent e) {

     switch (e.getKeyCode()) {
     case KeyEvent.VK_UP:
         System.out.println("Move up");
         break;

     case KeyEvent.VK_DOWN:
         currentSprite = characterDown;
         if (robot.isJumped() == false){
             robot.setDucked(true);
             robot.setSpeedX(0);
         }
         break;

     case KeyEvent.VK_LEFT:
         robot.moveLeft();
         robot.setMovingLeft(true);
         break;

     case KeyEvent.VK_RIGHT:
         robot.moveRight();
         robot.setMovingRight(true);
         break;

     case KeyEvent.VK_SPACE:
         robot.jump();
         break;
         
     case KeyEvent.VK_CONTROL:
    	 if (robot.isDucked() == false && robot.isJumped() == false) {
    		 robot.shoot();
    		 robot.setReadyToFire(false);
		 }
		 break;         
         

     }

 }

 @Override
 public void keyReleased(KeyEvent e) {
     switch (e.getKeyCode()) {
     case KeyEvent.VK_UP:
         System.out.println("Stop moving up");
         break;

     case KeyEvent.VK_DOWN:
    	 currentSprite = anim.getImage();
         robot.setDucked(false);
         break;

     case KeyEvent.VK_LEFT:
         robot.stopLeft();
         break;

     case KeyEvent.VK_RIGHT:
         robot.stopRight();
         break;

     case KeyEvent.VK_SPACE:
         break;
         
     case KeyEvent.VK_CONTROL:
    	 robot.setReadyToFire(true);
    	 break;    

     }

 }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }	
	
 	public static Robot getRobot(){
		return robot;
	}   

}