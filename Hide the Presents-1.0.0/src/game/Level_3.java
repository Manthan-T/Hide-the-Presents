package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level_3 extends BasicGameState {

	Screen_Size screenSize = new Screen_Size();
	
	float acceleration = 0.0005f;
	float x_velocity = 0f;
	float y_velocity = 0f;

	Music music;
	
	Sound level1Announce;
	
	Image level3;
	Image icylayer;
	Image victorytree;
	Image santa;
	Image santaUp;
	Image santaDown;
	Image santaLeft;
	Image santaRight;
	Image[] arrowheadSprite = new Image[this.getID()];
	Object[][] childSpriteInfo = {
		{15f, (float) (screenSize.getHeight()/2 - 195), true, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 - 175)}, //child's x, child's y, is child going right, arrowhead's x, arrowhead's y
		{(float) (screenSize.getWidth()/2), (float) (screenSize.getHeight()/2 - 80), true, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 - 65)},
		{(float) (screenSize.getWidth() - 139), (float) (screenSize.getHeight()/2 + 30), false, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 + 55)}
	};
	Object[][] sparksInfo = {
		{(float) (screenSize.getWidth()/2), 100f, true},
		{(float) (screenSize.getWidth()/2), (float) (screenSize.getHeight() - 300), false}
	};

	SpriteSheet arrowheadSheet;
	SpriteSheet childSheetLeft;
	SpriteSheet childSheetRight;
	SpriteSheet electricSparks;
	Animation childLeft;
	Animation childRight;
	Animation[] childSprite = new Animation[this.getID()];
	Animation[] sparks = new Animation[2];

	int[] Duration = {100, 100};
	float SantaX = (float) ((screenSize.getWidth() - 140)/2);
	float SantaY = (float) (screenSize.getHeight() - 175);

	public Level_3(int state) {
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		level3 = new Image("res/levels/Level_3.png");
		icylayer = new Image("res/levels/icylayer.png");		
		victorytree = new Image("res/sprites/victorytree.png");
		
		santaUp = new Image("res/sprites/santa/santa_back.png");
		santaDown = new Image("res/sprites/santa/santa_down.png");
		santaLeft = new Image("res/sprites/santa/santa_left.png");
		santaRight = new Image("res/sprites/santa/santa_right.png");
		santa = santaUp;
		
		electricSparks = new SpriteSheet("res/sprites/electric_sparks.png", 100, 100);
		childSheetLeft = new SpriteSheet("res/sprites/child/child_left.png", 112, 130);
		childSheetRight = new SpriteSheet("res/sprites/child/child_right.png", 112, 130);
		childRight = new Animation(childSheetRight, 200);
		childLeft = new Animation(childSheetLeft, 200);

		arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
		childSprite[0] = childLeft;
		arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
		childSprite[1] = childRight;
		arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
		childSprite[2] = childRight;

		for (int x = 0; x < sparks.length; x++) {
			sparks[x] = new Animation(electricSparks, 50);
		}

		music = new Music("res/music/Level_3_music.ogg", true);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(level3, 0, 0);
		g.drawImage(icylayer, 0, 0);
		
		g.drawImage(victorytree, (float) ((screenSize.getWidth() - 140)/2), 25);
		g.setColor(Color.black);
		g.setLineWidth(3);

		for (int x = 0; x < childSprite.length; x++) {
			g.drawImage(arrowheadSprite[x], (float) (childSpriteInfo[x][3]), (float) (childSpriteInfo[x][4]));
			g.drawLine((float) (childSpriteInfo[x][0]) + 56, (float) (childSpriteInfo[x][4]) + 7, (float) (childSpriteInfo[x][3]) + 5, (float) (childSpriteInfo[x][4]) + 7);
			g.drawAnimation(childSprite[x], (float) (childSpriteInfo[x][0]), (float) (childSpriteInfo[x][1]));
		}

		for (int x = 0; x < sparks.length; x++) {
			g.drawAnimation(sparks[x], (float) (sparksInfo[x][0]), (float) (sparksInfo[x][1]));
		}

		g.drawImage(santa, SantaX, SantaY);
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (!music.playing()) {
			music.loop();
		}
		
		for (int x = 0; x < childSprite.length; x++) {
			if ((float) (childSpriteInfo[x][0]) <= 15) {
				childSpriteInfo[x][2] = true;
				childSprite[x] = childRight;
				childSpriteInfo[x][3] = (float) (screenSize.getWidth() - 27);
				arrowheadSprite[x].rotate(180);
			}

			if ((float) (childSpriteInfo[x][0]) >= 1397) {
				childSpriteInfo[x][2] = false;
				childSprite[x] = childLeft;
				childSpriteInfo[x][3] = 15f;
				arrowheadSprite[x].rotate(180);
			}

			if ((boolean) (childSpriteInfo[x][2]) == true) {
				childSpriteInfo[x][0] = (float) (childSpriteInfo[x][0]) + delta * .6f;

			} else {
				childSpriteInfo[x][0] = (float) (childSpriteInfo[x][0]) - delta * .6f;
			}

		}

		for (int x = 0; x < sparks.length; x++) {
			if ((float) (sparksInfo[x][0]) <= 15) {
				sparksInfo[x][2] = true;
			}

			if ((float) (sparksInfo[x][0]) >= 1436) {
				sparksInfo[x][2] = false;
			}

			if ((boolean) (sparksInfo[x][2]) == true) {
				sparksInfo[x][0] = (float) (sparksInfo[x][0]) + delta * 3f;

			} else {
				sparksInfo[x][0] = (float) (sparksInfo[x][0]) - delta * 3f;
			}

		}

		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_W)) {
			santa = santaUp;
			y_velocity = -.45f;
			SantaY += delta * y_velocity;

			if (SantaY <= 0) {
				SantaY = 0;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(4);
			}

		}

		if (input.isKeyDown(Input.KEY_A)) {
			santa = santaLeft;
			x_velocity = -.45f;			
			SantaX += delta * x_velocity;

			if (SantaX <= 0) {
				SantaX = 0;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
			
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
			
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(4);
			}

		}


		if (input.isKeyDown(Input.KEY_S)) {
			santa = santaDown;
			y_velocity = .45f;
			SantaY += delta * y_velocity;

			if (SantaY >= screenSize.getHeight() - 141) {
				SantaY = (float) (screenSize.getHeight() - 141);
			}

		}

		if (input.isKeyDown(Input.KEY_D)) {
			santa = santaRight;
			x_velocity = .45f;
			SantaX += delta * x_velocity;

			if (SantaX >= screenSize.getWidth() - 163) {
				SantaX = (float) (screenSize.getWidth() - 163);
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
			
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
			
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(4);
			}

		}
		
		if ((!input.isKeyDown(Input.KEY_W)) && (!input.isKeyDown(Input.KEY_S))) {
			if (y_velocity > 0f) {
				y_velocity -= delta * acceleration;
				
			} else if (y_velocity < 0f) {
				y_velocity += delta * acceleration;
			}
			
			SantaY += delta * y_velocity;
			
			if (SantaY <= 0) {
				SantaY = 0;
				
			} else if (SantaY >= screenSize.getHeight() - 141) {
				SantaY = (float) (screenSize.getHeight() - 141);
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
			
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
			
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				
				game.enterState(4);
			}
		
		}
		
		if ((!input.isKeyDown(Input.KEY_A)) && (!input.isKeyDown(Input.KEY_D))) {
			if (x_velocity > 0f) {
				x_velocity -= delta * acceleration;
				
			} else if (x_velocity < 0f) {
				x_velocity += delta * acceleration;
			}
			
			SantaX += delta * x_velocity;
			
			if (SantaX <= 0) {
				SantaX = 0;
				
			} else if (SantaX >= screenSize.getWidth() - 163) {
				SantaX = (float) (screenSize.getWidth() - 163);
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
			
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
			
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(4);
			}
		
		}

		for (int x = 0; x < childSprite.length; x++) {
			if ((((boolean) (childSpriteInfo[x][2]) == true && SantaX + 163 >= (float) (childSpriteInfo[x][0]) + 56 && SantaX + 163 <= (float) (childSpriteInfo[x][3])) || (((boolean) (childSpriteInfo[x][2]) == false && SantaX >= (float) (childSpriteInfo[x][3]) && SantaX <= (float) (childSpriteInfo[x][0]) + 56))) && (SantaY <= (float) (childSpriteInfo[x][1]) + 25 && SantaY + 141 >= (float) (childSpriteInfo[x][1]) + 25)) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
			
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
			
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
			}
		}

		for (int x = 0; x < sparks.length; x++) {
			if (((float) (sparksInfo[x][0]) + 15 >= SantaX && (float) (sparksInfo[x][0]) + 85 <= SantaX + 163) && ((float) (sparksInfo[x][1]) + 15 >= SantaY && (float) (sparksInfo[x][1]) + 85 <= SantaY + 141)) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 30);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = 15f;
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 50);
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
			
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 300);
				sparksInfo[1][2] = false;
			
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
			}
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			game.enterState(0);
		}

	}

	public int getID() {
		return 3;
	}

}
