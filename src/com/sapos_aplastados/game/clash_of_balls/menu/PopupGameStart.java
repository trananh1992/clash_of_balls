/*
 * Copyright (C) 2012-2013 Hans Hardmeier <hanshardmeier@gmail.com>
 * Copyright (C) 2012-2013 Andrin Jenal
 * Copyright (C) 2012-2013 Beat Küng <beat-kueng@gmx.net>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */

package com.sapos_aplastados.game.clash_of_balls.menu;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Typeface;

import com.sapos_aplastados.game.clash_of_balls.R;
import com.sapos_aplastados.game.clash_of_balls.Font2D;
import com.sapos_aplastados.game.clash_of_balls.Texture;
import com.sapos_aplastados.game.clash_of_balls.TextureManager;
import com.sapos_aplastados.game.clash_of_balls.Font2D.Font2DSettings;
import com.sapos_aplastados.game.clash_of_balls.Font2D.TextAlign;
import com.sapos_aplastados.game.clash_of_balls.game.GamePlayer;
import com.sapos_aplastados.game.clash_of_balls.game.RenderHelper;
import com.sapos_aplastados.game.clash_of_balls.game.Vector;

/**
 * this popup is used right before a game starts: shows a countdown & the player
 */

public class PopupGameStart extends PopupBase {
	
	private float m_timeout;
	
	private Font2D m_timeout_font;
	private Vector m_timeout_font_pos;
	private Vector m_timeout_font_size;
	
	private MenuItemStringMultiline m_device_position_hint;
	
	private Font2D m_game_start_font;
	private Vector m_game_start_font_pos;
	private Vector m_game_start_font_size;
	
	private GamePlayer m_player; //only used for drawing
	private Vector m_player_pos;
	private Vector m_player_size;
	
	
	public PopupGameStart(Context context, TextureManager tex_manager
			, float screen_width, float screen_height
			, float timeout, int player_color, Typeface font_typeface, World world) {
		super(context, tex_manager, screen_width, screen_height);
		
		final float border_offset = 0.03f;
		
		final int font_color = 0xff888888;
		
		//player preview
		m_player = new GamePlayer(null, (short)0
				, new Vector(), player_color, tex_manager, null
				, world, new BodyDef());
		m_player_size = new Vector(m_size.x * 0.3f, m_size.x * 0.3f);
		m_player_pos = new Vector(m_position.x + m_size.x*(0.1f+border_offset)
				, m_position.y + m_size.y*(1.f - 0.1f - border_offset) - m_player_size.y);
		
		//timeout font
		m_timeout_font_size = new Vector(m_player_size);
		m_timeout_font_size.mul(0.9f);
		m_timeout_font_pos = new Vector(m_position.x + m_size.x*(1.f-border_offset)
				-m_timeout_font_size.x
				, m_position.y + m_size.y*border_offset);
		m_timeout = timeout;
		Font2DSettings font_settings = new Font2DSettings(font_typeface
				, TextAlign.CENTER, font_color);
		m_timeout_font = new Font2D(tex_manager, m_timeout_font_size, font_settings
				, (int)(m_timeout_font_size.y*0.7));
		timeoutChanged((int)(m_timeout + 1.f));
		
		//game start font
		m_game_start_font_size = new Vector(m_timeout_font_pos.x - m_position.x
				, m_timeout_font_size.y);
		m_game_start_font_pos = new Vector(m_position.x
				, m_timeout_font_pos.y);
		font_settings.m_align = TextAlign.RIGHT;
		m_game_start_font = new Font2D(tex_manager, m_game_start_font_size, font_settings
				, (int)(m_game_start_font_size.y*0.26));
		m_game_start_font.setString("Game starts in");
		
		font_settings.m_align = TextAlign.CENTER;
		Vector device_position_pos = new Vector(m_player_pos.x + m_player_size.x, 
				m_position.y + m_size.y/2.f);
		m_device_position_hint = new MenuItemStringMultiline(
				device_position_pos,
				new Vector(m_position.x + m_size.x*(1.f-border_offset) - device_position_pos.x
						, m_player_size.y*0.8f),
				font_settings, 
				"hold your device\nin desired\nneutral position", 
				tex_manager);
	}
	
	private void timeoutChanged(int new_timeout) {
		
		m_timeout_font.setString(""+new_timeout);
		
	}
	
	
	public void draw(RenderHelper renderer) {
		
		super.draw(renderer);
		
		//the player
		renderer.pushModelMat();
		renderer.modelMatTranslate(m_player_pos.x+m_player_size.x/2.f
				, m_player_pos.y+m_player_size.y/2.f, 0.f);
		renderer.modelMatScale(m_player_size.x, m_player_size.y, 0.f);
		
		m_player.draw(renderer);
		
        renderer.popModelMat();
        
        //countdown font
		renderer.pushModelMat();
		renderer.modelMatTranslate(m_timeout_font_pos.x
				, m_timeout_font_pos.y, 0.f);
		renderer.modelMatScale(m_timeout_font_size.x
				, m_timeout_font_size.y, 0.f);
		
		m_timeout_font.draw(renderer);
		
        renderer.popModelMat();
        
        //game start font
		renderer.pushModelMat();
		renderer.modelMatTranslate(m_game_start_font_pos.x
				, m_game_start_font_pos.y, 0.f);
		renderer.modelMatScale(m_game_start_font_size.x
				, m_game_start_font_size.y, 0.f);
		
		m_game_start_font.draw(renderer);
		
        renderer.popModelMat();
        
        m_device_position_hint.draw(renderer);
		
	}
	
	public void move(float dsec) {
		super.move(dsec);
		
		int timeout = (int)(m_timeout + 1.f);
		m_timeout -= dsec;
		int new_timeout = (int)(m_timeout + 1.f);
		if(new_timeout < 0) new_timeout = 0;
		if(timeout != new_timeout) {
			timeoutChanged(new_timeout);
		}
	}
	
}
