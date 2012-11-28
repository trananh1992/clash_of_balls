package com.android.game.clash_of_the_balls.menu;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.widget.Toast;

import com.android.game.clash_of_the_balls.R;
import com.android.game.clash_of_the_balls.Font2D;
import com.android.game.clash_of_the_balls.Texture;
import com.android.game.clash_of_the_balls.TextureManager;
import com.android.game.clash_of_the_balls.VertexBufferFloat;
import com.android.game.clash_of_the_balls.game.RenderHelper;
import com.android.game.clash_of_the_balls.game.Vector;

public class MenuItemPresiArrow extends MenuItem{

	private String LOG_TAG = "debug";
		
	private Texture m_texture_unpressed;
	private Texture m_texture_pressed;
	private boolean m_pressed=false;
	private PresiArrow m_type;

	public enum PresiArrow{
		RIGHT,
		LEFT
	}
	
	
	
	
	public MenuItemPresiArrow(Vector position, Vector size
			,TextureManager m_tex_manager,PresiArrow type) {
		super(position, size);

		m_position_data = new VertexBufferFloat
				(VertexBufferFloat.sprite_position_data, 3);
		m_color_data = new VertexBufferFloat
				(VertexBufferFloat.sprite_color_data_white, 4);
		m_type=type;
		
		switch(type){
		case RIGHT:
			m_texture_pressed=m_tex_manager
			.get(R.raw.texture_presiright_pressed_button);
	m_texture_unpressed=m_tex_manager
			.get(R.raw.texture_presiright_unpressed_button);
		break;
		case LEFT:
			m_texture_pressed=m_tex_manager
			.get(R.raw.texture_presileft_pressed_button);
	m_texture_unpressed=m_tex_manager
			.get(R.raw.texture_presileft_unpressed_button);
		break;
		}

	}

	public void move(float dsec) {
		// nothing to do
	}
	public void select() {
		m_pressed =true;
	}
	
	public void deselect(){
		m_pressed=false;
	}
	
	
	
	public void draw(RenderHelper renderer) {		
		Texture m_texture;
		if(m_pressed){
			m_texture=m_texture_pressed;
		}else{
			m_texture=m_texture_unpressed;
		}
		
		renderer.shaderManager().activateTexture(0);
		m_texture.useTexture(renderer);
		int model_mat_pos = renderer.pushModelMat();
		float model_mat[] = renderer.modelMat();
		Matrix.setIdentityM(model_mat, model_mat_pos);
		Matrix.translateM(model_mat, model_mat_pos, m_position.x, m_position.y, 0.f);
		Matrix.scaleM(model_mat, model_mat_pos, this.size().x, this.size().y, 0.f);
		
		// position
		int position_handle = renderer.shaderManager().a_Position_handle;
		if(position_handle != -1)
			m_position_data.apply(position_handle);
		
        // color
		int color_handle = renderer.shaderManager().a_Color_handle;
		if(color_handle != -1)
			m_color_data.apply(color_handle);      

		renderer.apply();
		
        // Draw
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);                               
        
        renderer.popModelMat();
	}

}
