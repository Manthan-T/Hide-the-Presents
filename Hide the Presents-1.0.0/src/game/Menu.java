package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
	
	Screen_Size screenSize = new Screen_Size();
	
	int wait = 0;

	Music decktheHalls;
	Sound click;
	
	Image menuScreen;
	Image playButton;
	Image quitButton;
	Image play_unpressed;
	Image play_pressed;
	Image quit_unpressed;
	Image quit_pressed;
	Image howtoplay;

	public Menu(int state) {	
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {	
		menuScreen = new Image("res/levels/menu.png");
		
		playButton = new Image("res/buttons/play_unpressed.png");
		play_unpressed = new Image("res/buttons/play_unpressed.png");
		play_pressed = new Image("res/buttons/play_pressed.png");
		
		quitButton = new Image("res/buttons/quit_unpressed.png");
		quit_unpressed = new Image("res/buttons/quit_unpressed.png");
		quit_pressed = new Image("res/buttons/quit_pressed.png");
		
		howtoplay = new Image("res/buttons/howtoplay.png");
		
		click = new Sound("res/soundfx/click.wav");
		decktheHalls = new Music("res/music/deckthehallsB.ogg", true);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {		
		g.drawImage(menuScreen, 0, 0);
		g.drawImage(playButton, screenSize.getWidth()/3, screenSize.getHeight()/2 - 100);
		g.drawImage(quitButton, screenSize.getWidth()/3, screenSize.getHeight()/2 + 100);
		g.drawImage(howtoplay, 25, 25);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {			
		if (!decktheHalls.playing()) {
			decktheHalls.loop();
		}
		
		if (wait < 1000 * delta) {
			wait++;
		}
		
		int mousePosX = Mouse.getX();
		int mousePosY = Mouse.getY();
		
		if ((mousePosX >= screenSize.getWidth()/3 && mousePosX <= (2 * screenSize.getWidth())/3) && (mousePosY >= screenSize.getHeight()/2 + 100 - play_unpressed.getHeight() && mousePosY <= screenSize.getHeight()/2 + 100)) {
			playButton = play_pressed; 
			
			if (Mouse.isButtonDown(0)) {
				click.play();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				game.enterState(1);
			}
			
		} else {
			playButton = play_unpressed;
		}
		
		if ((mousePosX >= screenSize.getWidth()/3 && mousePosX <= (2 * screenSize.getWidth())/3) && (mousePosY >= screenSize.getHeight()/2 - 100 - quit_unpressed.getHeight() && mousePosY <= screenSize.getHeight()/2 - 100)) {
			quitButton = quit_pressed; 
			
			if (Mouse.isButtonDown(0)) {
				click.play();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				container.exit();
			}
		
		} else {
			quitButton = quit_unpressed;
		}
	
		if (mousePosX >= 25 && mousePosX <= 74 && mousePosY <= screenSize.getHeight() - 25 && mousePosY >= screenSize.getHeight() - 74 && wait >= 1000 * delta) {
			if (Mouse.isButtonDown(0)) {
				click.play();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				wait = 0;
				game.enterState(6);
			}
		}
		
	}
	
	public int getID() {
		return 0;
	}

}