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

public class Level_2 extends BasicGameState {

	Screen_Size screenSize = new Screen_Size();

	Music music;
	
	Sound level2Announce;
	
	Image level2;
	Image victorytree;
	Image santa;
	Image santaUp;
	Image santaDown;
	Image santaLeft;
	Image santaRight;
	Image[] arrowheadSprite = new Image[this.getID()];
	Object[][] childSpriteInfo = {
		{15f, (float) (screenSize.getHeight()/2 - 210), true, (float) (screenSize.getWidth() - 27), (float) (screenSize.getHeight()/2 - 190)}, //child's x, child's y, is child going right, arrowhead's x, arrowhead's y
		{(float) (screenSize.getWidth() - 139), (float) (screenSize.getHeight()/2 + 10), false, 15f, (float) (screenSize.getHeight()/2 + 35)}
	};
	Object[] sparksInfo = {
		(float) (screenSize.getWidth()/2), (float) (screenSize.getHeight()/2 - 80), true
	};

	SpriteSheet arrowheadSheet;
	SpriteSheet childSheetLeft;
	SpriteSheet childSheetRight;
	SpriteSheet electricSparks;
	Animation childLeft;
	Animation childRight;
	Animation[] childSprite = new Animation[this.getID()];
	Animation sparks;

	int[] Duration = {100, 100};
	float SantaX = (float) ((screenSize.getWidth() - 140)/2);
	float SantaY = (float) (screenSize.getHeight() - 175);

	public Level_2(int state) {
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		level2 = new Image("res/levels/Level_2.png");
		victorytree = new Image("res/sprites/victorytree.png");
		
		santaUp = new Image("res/sprites/santa/santa_back.png");
		santaDown = new Image("res/sprites/santa/santa_down.png");
		santaLeft = new Image("res/sprites/santa/santa_left.png");
		santaRight = new Image("res/sprites/santa/santa_right.png");
		santa = santaUp;
		
		electricSparks = new SpriteSheet("res/sprites/electric_sparks.png", 100, 100);
		sparks = new Animation(electricSparks, 50);
		childSheetLeft = new SpriteSheet("res/sprites/child/child_left.png", 112, 130);
		childSheetRight = new SpriteSheet("res/sprites/child/child_right.png", 112, 130);
		childRight = new Animation(childSheetRight, 200);
		childLeft = new Animation(childSheetLeft, 200);

		for (int x = 0; x < childSprite.length; x++) {
			if (x % 2 == 0) {
				arrowheadSprite[x] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				childSprite[x] = childLeft;
			} else {
				arrowheadSprite[x] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				childSprite[x] = childRight;
			}

		}
		
		music = new Music("res/music/Level_2_music.ogg", true);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(level2, 0, 0);
		g.drawImage(victorytree, (float) ((screenSize.getWidth() - 140)/2), 25);
		g.drawAnimation(sparks, (float) (sparksInfo[0]), (float) (sparksInfo[1]));
		g.setColor(Color.black);
		g.setLineWidth(3);

		for (int x = 0; x < childSprite.length; x++) {
			g.drawImage(arrowheadSprite[x], (float) (childSpriteInfo[x][3]), (float) (childSpriteInfo[x][4]));
			g.drawLine((float) (childSpriteInfo[x][0]) + 56, (float) (childSpriteInfo[x][4]) + 7, (float) (childSpriteInfo[x][3]) + 5, (float) (childSpriteInfo[x][4]) + 7);
			g.drawAnimation(childSprite[x], (float) (childSpriteInfo[x][0]), (float) (childSpriteInfo[x][1]));
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
				childSpriteInfo[x][0] = (float) (childSpriteInfo[x][0]) + delta * .2f;

			} else {
				childSpriteInfo[x][0] = (float) (childSpriteInfo[x][0]) - delta * .2f;
			}

		}

		if ((float) (sparksInfo[0]) <= 15) {
			sparksInfo[2] = true;
		}

		if ((float) (sparksInfo[0]) >= 1436) {
			sparksInfo[2] = false;
		}

		if ((boolean) (sparksInfo[2]) == true) {
			sparksInfo[0] = (float) (sparksInfo[0]) + delta * 1f;

		} else {
			sparksInfo[0] = (float) (sparksInfo[0]) - delta * 1f;
		}

		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_W)) {
			santa = santaUp;
			SantaY -= delta * .15f;

			if (SantaY <= 0) {
				SantaY += delta * .15f;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 + 35);
				childSprite[0] = childRight;
				childSprite[1] = childLeft;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				
				sparksInfo[0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1] = (float) (screenSize.getHeight()/2 - 80);
				sparksInfo[2] = true;
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				
				game.enterState(3);
			}

		}

		if (input.isKeyDown(Input.KEY_A)) {
			santa = santaLeft;
			SantaX -= delta * .15f;

			if (SantaX <= 0) {
				SantaX += delta * .15f;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 + 35);
				childSprite[0] = childRight;
				childSprite[1] = childLeft;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				
				sparksInfo[0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1] = (float) (screenSize.getHeight()/2 - 80);
				sparksInfo[2] = true;
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				
				game.enterState(3);
			}

		}

		if (input.isKeyDown(Input.KEY_S)) {
			santa = santaDown;
			SantaY += delta * .15f;

			if (SantaY >= screenSize.getHeight() - 141) {
				SantaY -= delta * .15f;
			}

		}

		if (input.isKeyDown(Input.KEY_D)) {
			santa = santaRight;
			SantaX += delta * .15f;

			if (SantaX >= screenSize.getWidth() - 163) {
				SantaX -= delta * .15f;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 + 35);
				childSprite[0] = childRight;
				childSprite[1] = childLeft;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				
				sparksInfo[0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1] = (float) (screenSize.getHeight()/2 - 80);
				sparksInfo[2] = true;
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				
				game.enterState(3);
			}

		}

		for (int x = 0; x < childSprite.length; x++) {
			if ((((boolean) (childSpriteInfo[x][2]) == true && SantaX + 163 >= (float) (childSpriteInfo[x][0]) + 56 && SantaX + 163 <= (float) (childSpriteInfo[x][3])) || (((boolean) (childSpriteInfo[x][2]) == false && SantaX >= (float) (childSpriteInfo[x][3]) && SantaX <= (float) (childSpriteInfo[x][0]) + 56))) && (SantaY <= (float) (childSpriteInfo[x][1]) + 25 && SantaY + 141 >= (float) (childSpriteInfo[x][1]) + 25)) {
				childSpriteInfo[0][0] = 15f;
				childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
				childSpriteInfo[0][2] = true;
				childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
				childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
				childSpriteInfo[1][0] = (float) (screenSize.getWidth() - 139);
				childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 + 10);
				childSpriteInfo[1][2] = false;
				childSpriteInfo[1][3] = 15f;
				childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 + 35);
				childSprite[0] = childRight;
				childSprite[1] = childLeft;
				arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
				arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
				
				sparksInfo[0] = (float) (screenSize.getWidth()/2);
				sparksInfo[1] = (float) (screenSize.getHeight()/2 - 80);
				sparksInfo[2] = true;
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				
				game.enterState(0);
			}
		}

		if (((float) (sparksInfo[0]) + 15 >= SantaX && (float) (sparksInfo[0]) + 85 <= SantaX + 163) && ((float) (sparksInfo[1]) + 15 >= SantaY && (float) (sparksInfo[1]) + 85 <= SantaY + 141)) {
			childSpriteInfo[0][0] = 15f;
			childSpriteInfo[0][1] = (float) (screenSize.getHeight()/2 - 210);
			childSpriteInfo[0][2] = true;
			childSpriteInfo[0][3] = (float) (screenSize.getWidth() - 27);
			childSpriteInfo[0][4] = (float) (screenSize.getHeight()/2 - 190);
			childSpriteInfo[1][0] = (float) (screenSize.getWidth() - 139);
			childSpriteInfo[1][1] = (float) (screenSize.getHeight()/2 + 10);
			childSpriteInfo[1][2] = false;
			childSpriteInfo[1][3] = 15f;
			childSpriteInfo[1][4] = (float) (screenSize.getHeight()/2 + 35);
			childSprite[0] = childRight;
			childSprite[1] = childLeft;
			arrowheadSprite[0] = new Image("res/sprites/arrowhead/arrowhead_left.png");
			arrowheadSprite[1] = new Image("res/sprites/arrowhead/arrowhead_right.png");
			
			sparksInfo[0] = (float) (screenSize.getWidth()/2);
			sparksInfo[1] = (float) (screenSize.getHeight()/2 - 80);
			sparksInfo[2] = true;
			
			SantaX = (float) ((screenSize.getWidth() - 140)/2);
			SantaY = (float) (screenSize.getHeight() - 175);
			
			game.enterState(0);
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			game.enterState(0);
		}

	}

	public int getID() {
		return 2;
	}

}
