package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/*
 *
 * @author MrFirekiller
 * @category Christmas Games
 * @version 1.0.0
 * @about A Christmas game where you need to hide presents without getting caught
 * 
 */

public class HidethePresents extends StateBasedGame {
	
	public static final String gamename = "Hide the Presents!";
	public static final int menu = 0;
	public static final int level_1 = 1;
	public static final int level_2 = 2;
	public static final int level_3 = 3;
	public static final int level_4 = 4;
	public static final int level_5 = 5;
	public static final int how_to_play = 6;
	
   	public HidethePresents(String gamename) {	            
   		super(gamename);
   		this.addState(new Menu(menu));
   		this.addState(new How_To_Play(how_to_play));
   		this.addState(new Level_1(level_1));
   		this.addState(new Level_2(level_2));
   		this.addState(new Level_3(level_3));
   		this.addState(new Level_4(level_4));
   		this.addState(new Level_5(level_5));
   	}
	
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(menu).init(container, this);
		this.getState(level_1).init(container, this);
		this.getState(level_2).init(container, this);
		this.getState(level_3).init(container, this);
		this.getState(level_4).init(container, this);
		this.getState(level_5).init(container, this);
		this.enterState(menu);
	}
    
	public static void main(String args[]) {
		try {
			AppGameContainer appContainer = new AppGameContainer(new HidethePresents(gamename));
			appContainer.setIcon("res/icon.png");
			appContainer.setDisplayMode(1536, 864, false);
			appContainer.setShowFPS(false);
			appContainer.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	    
}