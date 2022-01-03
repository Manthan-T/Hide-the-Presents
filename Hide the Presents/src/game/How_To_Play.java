package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class How_To_Play extends BasicGameState {
	
	Screen_Size screenSize = new Screen_Size();
	
	int wait = 0;
	
	Sound click;

	Image rules;
	Image back;

	public How_To_Play(int state) {
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		click = new Sound("res/soundfx/click.wav");
		rules = new Image("res/levels/rules.png");
		back = new Image("res/buttons/back.png");
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(rules, 0, 0);
		g.drawImage(back, 25, 25);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (wait < 1000 * delta) {
			wait++;
		}
		
		int mousePosX = Mouse.getX();
		int mousePosY = Mouse.getY();
		
		if (mousePosX >= 25 && mousePosX <= 74 && mousePosY <= screenSize.getHeight() - 25 && mousePosY >= screenSize.getHeight() - 74 && wait >= 1000 * delta) {
			if (Mouse.isButtonDown(0)) {
				click.play();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				wait = 0;
				game.enterState(0);
			}
		}
	}

	public int getID() {
		return 6;
	}
	
}
