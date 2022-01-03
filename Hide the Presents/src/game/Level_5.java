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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level_5 extends BasicGameState {

	Screen_Size screenSize = new Screen_Size();

	float SantaX = (float) ((screenSize.getWidth() - 140)/2);
	float SantaY = (float) (screenSize.getHeight() - 175);
	float acceleration = 0.0005f;
	float x_velocity = 0f;
	float y_velocity = 0f;

	int[] Duration = {100, 100};

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
	Image buggyLeft;
	Image buggyRight;
	Image slapLeft;
	Image slapRight;

	Image[] buggy = new Image[4];
	Image[] arrowheadSprite = new Image[this.getID()];

	Object[][] childSpriteInfo = {
		{15f, (float) (screenSize.getHeight()/2 - 170), true, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 - 150)}, //child's x, child's y, is child going right, arrowhead's x, arrowhead's y
		{(float) (screenSize.getWidth()/2) - 56, (float) (screenSize.getHeight()/2 - 80), true, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 - 60)},
		{(float) (screenSize.getWidth() - 139), (float) (screenSize.getHeight()/2 + 80), false, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 + 100)},
		{(float) (screenSize.getWidth()/2) + 66, (float) (screenSize.getHeight()/2 - 10), false, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 + 10)},
		{(float) (screenSize.getWidth()/2), 111f, true, (float) (screenSize.getWidth() - 27), 131f}
	};
	Object[][] sparksInfo = {
		{(float) (screenSize.getWidth()/2), 100f, true},
		{0f, (float) (screenSize.getHeight() - 100), false},
		{(float) (screenSize.getWidth()/2), (float) (screenSize.getHeight()/2), true},
		{(float) (screenSize.getWidth()), (float) (screenSize.getHeight()/2 + 200), false}
	};
	Object[][] buggyInfo = {
		{0f, 0f, true, true},
		{(float) screenSize.getWidth(), (float) screenSize.getHeight() - 260, false, false},
		{0f, (float) screenSize.getHeight() - 260, true, false},
		{(float) screenSize.getWidth(), 0f, false, true}
	};
	float[] slapInfo = {screenSize.getWidth(), 200, -191, 200};
	
	SpriteSheet arrowheadSheet;
	SpriteSheet childSheetLeft;
	SpriteSheet childSheetRight;
	SpriteSheet electricSparks;

	Animation childLeft;
	Animation childRight;
	Animation[] childSprite = new Animation[this.getID()];
	Animation[] sparks = new Animation[4];
	
	Rectangle hitboxes[] = new Rectangle[7];

	public Level_5(int state) {
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		level3 = new Image("res/levels/Level_5.png");
		icylayer = new Image("res/levels/icylayer.png");
		victorytree = new Image("res/sprites/victorytree.png");
		
		santaUp = new Image("res/sprites/santa/santa_back.png");
		santaDown = new Image("res/sprites/santa/santa_down.png");
		santaLeft = new Image("res/sprites/santa/santa_left.png");
		santaRight = new Image("res/sprites/santa/santa_right.png");
		santa = santaUp;
		hitboxes[0] = new Rectangle(SantaX, SantaY, 163f, 141f);

		electricSparks = new SpriteSheet("res/sprites/electric_sparks.png", 100, 100);

		buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
		buggyRight = new Image("res/sprites/buggy/buggy_right.png");
		buggy[0] = buggyRight;
		buggy[1] = buggyLeft;
		buggy[2] = buggyRight;
		buggy[3] = buggyLeft;
		hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
		hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
		hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
		hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

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
		arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
		childSprite[3] = childRight;
		arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
		childSprite[4] = childRight;

		for (int x = 0; x < sparks.length; x++) {
			sparks[x] = new Animation(electricSparks, 50);		
		}

		slapLeft = new Image("res/sprites/parent_slap/parent_slap_left.png");
		slapRight = new Image("res/sprites/parent_slap/parent_slap_right.png");
		hitboxes[5] = new Rectangle(slapInfo[0], slapInfo[1], 191, 190);
		hitboxes[6] = new Rectangle(slapInfo[2], slapInfo[3], 191, 190);

		music = new Music("res/music/Level_5_music.ogg", true);
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

		g.drawImage(buggy[0], (float) buggyInfo[0][0], (float) buggyInfo[0][1]);
		g.drawImage(buggy[1], (float) buggyInfo[1][0], (float) buggyInfo[1][1]);
		g.drawImage(buggy[2], (float) buggyInfo[2][0], (float) buggyInfo[2][1]);
		g.drawImage(buggy[3], (float) buggyInfo[3][0], (float) buggyInfo[3][1]);

		g.drawImage(slapLeft, slapInfo[0], slapInfo[1]);
		g.drawImage(slapRight, slapInfo[2], slapInfo[3]);
		
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
		
		for (int x = 0; x < buggy.length; x++) {
			if ((float) (buggyInfo[x][0]) <= 0) {
				buggyInfo[x][2] = true;
				buggy[x] = buggyRight;
			}

			if ((float) (buggyInfo[x][0]) >= screenSize.getWidth() - 100) {
				buggyInfo[x][2] = false;
				buggy[x] = buggyLeft;
			}
			if ((float) (buggyInfo[x][1]) <= 0) {
				buggyInfo[x][3] = true;
			}

			if ((float) (buggyInfo[x][1]) >= screenSize.getHeight() - 260) {
				buggyInfo[x][3] = false;
			}

			if ((boolean) (buggyInfo[x][2]) == true) {
				buggyInfo[x][0] = (float) (buggyInfo[x][0]) + delta * 1.5f;

			} else {
				buggyInfo[x][0] = (float) (buggyInfo[x][0]) - delta * 1.5f;
			}
			
			if ((boolean) (buggyInfo[x][3]) == true) {
				buggyInfo[x][1] = (float) (buggyInfo[x][1]) + delta * .15f;

			} else {
				buggyInfo[x][1] = (float) (buggyInfo[x][1]) - delta * .15f;
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
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
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
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
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
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
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
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
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
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);
				
				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
			}

		}

		hitboxes[0].setLocation(SantaX, SantaY);
		hitboxes[1].setLocation((float) buggyInfo[0][0], (float) buggyInfo[0][1]);
		hitboxes[2].setLocation((float) buggyInfo[1][0], (float) buggyInfo[1][1]);
		hitboxes[3].setLocation((float) buggyInfo[2][0], (float) buggyInfo[2][1]);
		hitboxes[4].setLocation((float) buggyInfo[3][0], (float) buggyInfo[3][1]);
		
		if (SantaY <= 390 && !(slapInfo[0] <= screenSize.getWidth()/2 + 191 && slapInfo[2] >= screenSize.getWidth()/2 - 191)) {
			slapInfo[0] -= delta * 4;
			slapInfo[2] += delta * 4;
		}
		
		hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
		hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);

		for (int x = 0; x < childSprite.length; x++) {
			if ((((boolean) (childSpriteInfo[x][2]) == true && SantaX + 163 >= (float) (childSpriteInfo[x][0]) + 56 && SantaX + 163 <= (float) (childSpriteInfo[x][3])) || (((boolean) (childSpriteInfo[x][2]) == false && SantaX >= (float) (childSpriteInfo[x][3]) && SantaX <= (float) (childSpriteInfo[x][0]) + 56))) && (SantaY <= (float) (childSpriteInfo[x][1]) + 25 && SantaY + 141 >= (float) (childSpriteInfo[x][1]) + 25)) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
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
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
				childSpriteInfo[1][2] = true;
				childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
				childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
				childSpriteInfo[2][2] = false;
				childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
				childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
				childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
				childSpriteInfo[3][2] = false;
				childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
				childSpriteInfo[4][1] = 111f;
				childSpriteInfo[4][2] = true;
				childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[4][4] = 131f;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[0] = childLeft;
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[1] = childRight;
				arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[2] = childRight;
				arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[3] = childRight;
				arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[4] = childRight;
				
				sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[0][1] = 100f;
				sparksInfo[0][2] = true;
				sparksInfo[1][0] = 0f;
				sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
				sparksInfo[1][2] = false;
				sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
				sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
				sparksInfo[2][2] = true;
				sparksInfo[3][0] = (float) (screenSize.getWidth());
				sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
				sparksInfo[3][2] = false;

				buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
				buggyRight = new Image("res/sprites/buggy/buggy_right.png");
				buggy[0] = buggyRight;
				buggy[1] = buggyLeft;
				buggy[2] = buggyRight;
				buggy[3] = buggyLeft;
				buggyInfo[0][0] = 0f;
				buggyInfo[0][1] = 0f;
				buggyInfo[0][2] = true;
				buggyInfo[0][3] = true;
				buggyInfo[1][0] = (float) screenSize.getWidth();
				buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[1][2] = false;
				buggyInfo[1][3] = false;
				buggyInfo[2][0] = 0f;
				buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
				buggyInfo[2][2] = true;
				buggyInfo[2][3] = false;
				buggyInfo[3][0] = (float) screenSize.getWidth();
				buggyInfo[3][1] = 0f;
				buggyInfo[3][2] = false;
				buggyInfo[3][3] = true;
				hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
				hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
				hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
				hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

				slapInfo[0] = screenSize.getWidth();
				slapInfo[1] = 200;
				slapInfo[2] = -191;
				slapInfo[3] = 200;				
				hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
				hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);

				y_velocity = 0f;
				x_velocity = 0f;
				
				game.enterState(0);
			}
		}
		
		if (hitboxes[0].intersects(hitboxes[1]) || hitboxes[0].intersects(hitboxes[2])) {
			childSpriteInfo[0][0] = 15f;
			childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
			childSpriteInfo[0][2] = true;
			childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
			childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
			childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
			childSpriteInfo[1][2] = true;
			childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
			childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
			childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
			childSpriteInfo[2][2] = false;
			childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
			childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
			childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
			childSpriteInfo[3][2] = false;
			childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
			childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
			childSpriteInfo[4][1] = 111f;
			childSpriteInfo[4][2] = true;
			childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[4][4] = 131f;
			arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
			childSprite[0] = childLeft;
			arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			childSprite[1] = childRight;
			arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			childSprite[2] = childRight;
			arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
			childSprite[3] = childRight;
			arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			childSprite[4] = childRight;
			
			sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
			sparksInfo[0][1] = 100f;
			sparksInfo[0][2] = true;
			sparksInfo[1][0] = 0f;
			sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
			sparksInfo[1][2] = false;
			sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
			sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
			sparksInfo[2][2] = true;
			sparksInfo[3][0] = (float) (screenSize.getWidth());
			sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
			sparksInfo[3][2] = false;

			buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
			buggyRight = new Image("res/sprites/buggy/buggy_right.png");
			buggy[0] = buggyRight;
			buggy[1] = buggyLeft;
			buggy[2] = buggyRight;
			buggy[3] = buggyLeft;
			buggyInfo[0][0] = 0f;
			buggyInfo[0][1] = 0f;
			buggyInfo[0][2] = true;
			buggyInfo[0][3] = true;
			buggyInfo[1][0] = (float) screenSize.getWidth();
			buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
			buggyInfo[1][2] = false;
			buggyInfo[1][3] = false;
			buggyInfo[2][0] = 0f;
			buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
			buggyInfo[2][2] = true;
			buggyInfo[2][3] = false;
			buggyInfo[3][0] = (float) screenSize.getWidth();
			buggyInfo[3][1] = 0f;
			buggyInfo[3][2] = false;
			buggyInfo[3][3] = true;
			hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
			hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
			hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
			hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

			slapInfo[0] = screenSize.getWidth();
			slapInfo[1] = 200;
			slapInfo[2] = -191;
			slapInfo[3] = 200;				
			hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
			hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
			
			SantaX = (float) ((screenSize.getWidth() - 140)/2);
			SantaY = (float) (screenSize.getHeight() - 175);

			y_velocity = 0f;
			x_velocity = 0f;
			
			game.enterState(0);
		}
		
		if (hitboxes[0].intersects(hitboxes[5]) || hitboxes[0].intersects(hitboxes[6])) {
			childSpriteInfo[0][0] = 15f;
			childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 170);
			childSpriteInfo[0][2] = true;
			childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 150);
			childSpriteInfo[1][0] = (float) (screenSize.getWidth()/2 - 56);
			childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 - 80);
			childSpriteInfo[1][2] = true;
			childSpriteInfo[1][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 - 60);
			childSpriteInfo[2][0] = (float) (screenSize.getWidth() - 139);
			childSpriteInfo[2][1] = (float) (screenSize.getHeight()/2 + 80);
			childSpriteInfo[2][2] = false;
			childSpriteInfo[2][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[2][4] = (float) (screenSize.getHeight()/2 + 100);
			childSpriteInfo[3][0] = (float) (screenSize.getWidth()/2) + 66;
			childSpriteInfo[3][1] = (float) (screenSize.getHeight()/2 - 10);
			childSpriteInfo[3][2] = false;
			childSpriteInfo[3][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[3][4] = (float) (screenSize.getHeight()/2 + 10);
			childSpriteInfo[4][0] = (float) (screenSize.getWidth()/2);
			childSpriteInfo[4][1] = 111f;
			childSpriteInfo[4][2] = true;
			childSpriteInfo[4][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[4][4] = 131f;
			arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
			childSprite[0] = childLeft;
			arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			childSprite[1] = childRight;
			arrowheadSprite[2] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			childSprite[2] = childRight;
			arrowheadSprite[3] = new Image("res/sprites/arrowhead/arrowhead_left.png");
			childSprite[3] = childRight;
			arrowheadSprite[4] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			childSprite[4] = childRight;
			
			sparksInfo[0][0] = (float) (screenSize.getWidth()/2);
			sparksInfo[0][1] = 100f;
			sparksInfo[0][2] = true;
			sparksInfo[1][0] = 0f;
			sparksInfo[1][1] = (float) (screenSize.getHeight() - 100);
			sparksInfo[1][2] = false;
			sparksInfo[2][0] = (float) (screenSize.getWidth()/2);
			sparksInfo[2][1] = (float) (screenSize.getHeight()/2);
			sparksInfo[2][2] = true;
			sparksInfo[3][0] = (float) (screenSize.getWidth());
			sparksInfo[3][1] = (float) (screenSize.getHeight()/2 + 200);
			sparksInfo[3][2] = false;

			buggyLeft = new Image("res/sprites/buggy/buggy_left.png");
			buggyRight = new Image("res/sprites/buggy/buggy_right.png");
			buggy[0] = buggyRight;
			buggy[1] = buggyLeft;
			buggy[2] = buggyRight;
			buggy[3] = buggyLeft;
			buggyInfo[0][0] = 0f;
			buggyInfo[0][1] = 0f;
			buggyInfo[0][2] = true;
			buggyInfo[0][3] = true;
			buggyInfo[1][0] = (float) screenSize.getWidth();
			buggyInfo[1][1] = (float) screenSize.getHeight() - 260;
			buggyInfo[1][2] = false;
			buggyInfo[1][3] = false;
			buggyInfo[2][0] = 0f;
			buggyInfo[2][1] = (float) screenSize.getHeight() - 260;
			buggyInfo[2][2] = true;
			buggyInfo[2][3] = false;
			buggyInfo[3][0] = (float) screenSize.getWidth();
			buggyInfo[3][1] = 0f;
			buggyInfo[3][2] = false;
			buggyInfo[3][3] = true;
			hitboxes[1] = new Rectangle((float) buggyInfo[0][0], (float) buggyInfo[0][1], 100f, 72f);
			hitboxes[2] = new Rectangle((float) buggyInfo[1][0], (float) buggyInfo[1][1], 100f, 72f);
			hitboxes[3] = new Rectangle((float) buggyInfo[2][0], (float) buggyInfo[2][1], 100f, 72f);
			hitboxes[4] = new Rectangle((float) buggyInfo[3][0], (float) buggyInfo[3][1], 100f, 72f);

			slapInfo[0] = screenSize.getWidth();
			slapInfo[1] = 200;
			slapInfo[2] = -191;
			slapInfo[3] = 200;				
			hitboxes[5].setLocation(slapInfo[0], slapInfo[1]);
			hitboxes[6].setLocation(slapInfo[2], slapInfo[3]);
			
			SantaX = (float) ((screenSize.getWidth() - 140)/2);
			SantaY = (float) (screenSize.getHeight() - 175);

			y_velocity = 0f;
			x_velocity = 0f;
			
			game.enterState(0);
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			game.enterState(0);
		}

	}

	public int getID() {
		return 5;
	}

}
