package com.sdust.im.util;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class SystemMethod {
	private static final int ww=480;
	private static final int hh=800;
	public static int dip2px(Context context,int value){
		float scaleing=context.getResources().getDisplayMetrics().density;
		return (int) (value*scaleing+0.5f);
	}
	
	public static int px2dip(Context context,int value){
		float scaling=context.getResources().getDisplayMetrics().density;
		return (int) (value/scaling+0.5f);
	}
	
	/**
	 * 图片变圆角
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) { 

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
		Canvas canvas = new Canvas(output); 

		final int color = 0xff424242; 
		final Paint paint = new Paint(); 
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
		final RectF rectF = new RectF(rect); 
		final float roundPx = pixels; 

		paint.setAntiAlias(true); 
		canvas.drawARGB(0, 0, 0, 0); 
		paint.setColor(color); 
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		canvas.drawBitmap(bitmap, rect, rect, paint); 
		return output; 
	}
	
	/**
	 * save
	 * @param path
	 * @param buffer
	 * @return
	 */
	public static int saveBitmap(String path,byte[] buffer){
		int result=-1;
		try {
			FileOutputStream out=new FileOutputStream(new File(path));
			out.write(buffer);
			out.flush();
			out.close();
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getdecodeBitmap(String filePath){
		if(filePath==null){
			return null;
		}
		BitmapFactory.Options options=new Options();
		options.inJustDecodeBounds=true;
		Bitmap bitmap=BitmapFactory.decodeFile(filePath, options);
		
		int width=options.outWidth;
		int height=options.outHeight;
		float scale=1f;
		if(width>ww&&width>height){
			scale=width/ww;
		}else if(height>hh&&height>width){
			scale=height/hh;
		}else{
			scale=1;
		}
		
		options.inSampleSize=(int) scale;
		options.inJustDecodeBounds=false;
		bitmap=BitmapFactory.decodeFile(filePath, options);
		return bitmap;
	}
	
	public static int saveBitmap(String path,Bitmap bitmap){
		int result=-1;
		try {
			FileOutputStream fos=new FileOutputStream(new File(path));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
}
