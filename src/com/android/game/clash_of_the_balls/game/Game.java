package com.android.game.clash_of_the_balls.game;


import com.android.game.clash_of_the_balls.GameSettings;
import com.android.game.clash_of_the_balls.UIBase;
import com.android.game.clash_of_the_balls.UIHandler;


public class Game extends GameBase implements UIBase {

	private GameView m_game_view;
	
	public Game(GameSettings s) {
		super(s);
		// TODO Auto-generated constructor stub
		
		//TODO: use gravity sensor
	}

	@Override
	public void onTouchEvent(float x, float y, int event) {
		// that's not used in the game
	}

	@Override
	public void move(float dsec) {
		// TODO Auto-generated method stub
		
		//get sensor values & send to server
		
	}

	@Override
	public void draw(RenderHelper renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UIHandler.UIChange UIChange() {
		// TODO Auto-generated method stub
		
		return UIHandler.UIChange.NO_CHANGE;
	}

	@Override
	public void onActivate() {
		// ignore
	}

	@Override
	public void onDeactivate() {
		// ignore
	}

}
