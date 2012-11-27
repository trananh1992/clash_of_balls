package com.android.game.clash_of_the_balls.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class TextureHelper
{
		
	public static int loadTexture(final Context context, final int resourceId)
	{
		final int textureHandle;
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;	// No pre-scaling

		// Read in the resource
		final Bitmap loaded_bitmap = BitmapFactory.decodeResource(
				context.getResources(), resourceId, options);

		textureHandle = loadTextureFromBitmap(loaded_bitmap);

		// Recycle the bitmap, since its data has been loaded into OpenGL.
		loaded_bitmap.recycle();
		
		return textureHandle;
	}
	
	public static int loadTextureFromBitmap(Bitmap bitmap) {

		final int[] textureHandle = new int[1];
		
		GLES20.glGenTextures(1, textureHandle, 0);
		
		if (textureHandle[0] != 0)
		{
			
			// Flip bitmap upside down
			Matrix flip = new Matrix();
			flip.postScale(1f, -1f);
			final Bitmap bitmap_final = Bitmap.createBitmap(bitmap, 0, 0
					, bitmap.getWidth(), bitmap.getHeight(), flip, true);
			
			// Bind to the texture in OpenGL
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
			
			// Set filtering
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			
			// Load the bitmap into the bound texture.
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap_final, 0);
						
			// Recycle the bitmap, since its data has been loaded into OpenGL.
			bitmap_final.recycle();
		}

		if (textureHandle[0] == 0)
		{
			throw new RuntimeException("Error loading texture.");
		}
		
		return textureHandle[0];
	}
}
