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

public class Level_1 extends BasicGameState {

	Screen_Size screenSize = new Screen_Size();

	Music music;
	
	Sound level1Announce;
	Sound footsteps;
	
	Image level1;
	Image victorytree;
	Image santa;
	Image santaUp;
	Image santaDown;
	Image santaLeft;
	Image santaRight;
	Image arrowhead;

	SpriteSheet childSheetRight;
	SpriteSheet childSheetLeft;
	Animation childRight;
	Animation childLeft;
	Animation child;

	boolean ChildRight = true;
	float ChildX = 15;
	float ChildY = (float) (screenSize.getHeight()/2 - 100);
	float ArrowheadX = (float) (screenSize.getWidth() - 27);
	float SantaX = (float) ((screenSize.getWidth() - 140)/2);
	float SantaY = (float) (screenSize.getHeight() - 175);

	public Level_1(int state) {
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		level1 = new Image("res/levels/Level_1.png");
		victorytree = new Image("res/sprites/victorytree.png");
		
		santaUp = new Image("res/sprites/santa/santa_back.png");
		santaDown = new Image("res/sprites/santa/santa_down.png");
		santaLeft = new Image("res/sprites/santa/santa_left.png");
		santaRight = new Image("res/sprites/santa/santa_right.png");
		santa = santaUp;
		
		childSheetRight = new SpriteSheet("res/sprites/child/child_right.png", 112, 130);
		childSheetLeft = new SpriteSheet("res/sprites/child/child_left.png", 112, 130);
		childRight = new Animation(childSheetRight, 200);
		childLeft = new Animation(childSheetLeft, 200);
		child = childRight;
		arrowhead = new Image("res/sprites/arrowhead/arrowhead_left.png");
		
		music = new Music("res/music/Level_1_music.ogg", true);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(level1, 0, 0);
		g.drawImage(victorytree, (float) ((screenSize.getWidth() - 140)/2), 25);
		g.setColor(Color.black);
		g.setLineWidth(3);
		g.drawImage(arrowhead, ArrowheadX, ChildY + 25);
		g.drawLine(ArrowheadX + 5, ChildY + 31, ChildX + 56, ChildY + 31);
		g.drawAnimation(child, ChildX, ChildY);
		g.drawImage(santa, SantaX, SantaY);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (!music.playing()) {
			music.loop();
		}
		
		if (ChildX <= 15) {
			ChildRight = true;
			child = childRight;
			ArrowheadX = (float) (screenSize.getWidth() - 27);
			arrowhead.rotate(180);
		}

		if (ChildX >= 1397) {
			ChildRight = false;
			child = childLeft;
			ArrowheadX = 15;
			arrowhead.rotate(180);
		}

		if (ChildRight == true) {
			ChildX += delta * .2f;

		} else if (ChildRight == false) {
			ChildX -= delta * .2f;
		}

		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_W)) {
			santa = santaUp;
			SantaY -= delta * .15f;

			if (SantaY <= 0) {
				SantaY += delta * .15f;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				ChildRight = true;
				ChildX = 15;
				ChildY = (float) (screenSize.getHeight()/2 - 100);
				child = childRight;
				arrowhead = new Image("res/sprites/arrowhead/arrowhead_left.png");
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				game.enterState(2);
			}

		}

		if (input.isKeyDown(Input.KEY_A)) {
			santa = santaLeft;
			SantaX -= delta * .15f;

			if (SantaX <= 0) {
				SantaX += delta * .15f;
			}

			if (SantaY <= 100 && SantaX >= 560 && SantaX <= 790) {
				ChildRight = true;
				ChildX = 15;
				ChildY = (float) (screenSize.getHeight()/2 - 100);
				child = childRight;
				arrowhead = new Image("res/sprites/arrowhead/arrowhead_left.png");
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				game.enterState(2);
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
				ChildRight = true;
				ChildX = 15;
				ChildY = (float) (screenSize.getHeight()/2 - 100);
				child = childRight;
				arrowhead = new Image("res/sprites/arrowhead/arrowhead_left.png");
				
				SantaX = (float) ((screenSize.getWidth() - 140)/2);
				SantaY = (float) (screenSize.getHeight() - 175);
				game.enterState(2);
			}

		}

		if (((ChildRight == true && SantaX + 163 >= ChildX + 56 && SantaX + 163 <= ArrowheadX) || (ChildRight == false && SantaX >= ArrowheadX && SantaX <= ChildX + 56)) && (SantaY <= ChildY + 25 && SantaY + 141 >= ChildY + 25)) {
			ChildRight = true;
			ChildX = 15;
			ChildY = (float) (screenSize.getHeight()/2 - 100);
			child = childRight;
			arrowhead = new Image("res/sprites/arrowhead/arrowhead_left.png");
			
			SantaX = (float) ((screenSize.getWidth() - 140)/2);
			SantaY = (float) (screenSize.getHeight() - 175);
			game.enterState(0);
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			game.enterState(0);
		}

	}

	public int getID() {
		return 1;
	}

}
