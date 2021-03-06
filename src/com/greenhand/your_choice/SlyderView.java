package com.greenhand.your_choice;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.RotateAnimation;

public class SlyderView	extends View 
{
	public SlyderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public SlyderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SlyderView(Context context) {
		super(context);
		init(context);
//		showAnimation(this); 
	}

	private int nowAngle;//当前角度
	/**
	 * 屏幕宽度
	 */
	private int screenW;

	/**
	 * 屏幕的高度
	 */
	private int screenH;
	/**
	 * 分割的度数
	 */
	private int[] drgrees = { 60, 60, 60, 60, 60, 60 };
	/***
	 * 分割的文字
	 */
	private String[] strs = { "", "", "", "", "",""};
	/**
	 * 分割的颜色
	 */
	private int[] colos = new int[] {0xfed9c960,0xfe57c8c8,0xfe9fe558,0xfef6b000,0xfef46212,0xfecf2911,0xfe9d3011 };
	/**
	 * 画笔
	 */
	private Paint paint;
	/**
	 * 文字的大小
	 */
	private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
	/**
	 * 文字的颜色
	 */
	private int textcolor = Color.WHITE;
	/**
	 * 园的半径
	 */
	private float radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics());
	/**
	 * 画文字的距离
	 */
	private float textdis = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
	/**
	 * 圆心
	 */
	private float centerX;
	/**
	 * 圆心
	 */
	private float centerY;

	@SuppressWarnings("deprecation")
	private void init(Context context) {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		centerX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
		centerY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
		nowAngle = 0;
	}
	/**
	 * 绘制矩形框
	 */
	private RectF oval;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		oval = new RectF(centerX - radius, centerY - radius, centerX + radius,centerY + radius);
		float start = 0;
		paint.setColor(Color.rgb(0xdd, 0xdd, 0xdd));
		paint.setAlpha(127);
		canvas.drawCircle(centerX, centerY, radius, paint);
		paint.setAlpha(255);
		// 画扇形
		paint.setAntiAlias(true);
		for (int i = 0; i < drgrees.length; i++) {
			float sweepAngle = drgrees[i];
			float startAngle = start;
			paint.setColor(colos[i % colos.length]);
			canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
			start += drgrees[i];
		}
		// 画文字
		paint.setColor(textcolor);
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.RIGHT);
		start = 0;
		for (int i = 0; i < drgrees.length; i++) {
			canvas.save();
			canvas.rotate(start + drgrees[i] / 2+7, centerX, centerY);
			canvas.drawText(strs[i], centerX + textdis, centerY, paint);
			canvas.restore();
			start += drgrees[i];
		}
		int saveCount = canvas.save();
	}
	public void showAnimation(View mView) 
	 {
		float x = centerX;
		float y = centerY;
	  //这个是设置需要旋转的角度，我设置的是1080度
		int rand = (int) (Math.random()*360);
		rand = 1080 + rand;
		RotateAnimation rotateAnimation = new RotateAnimation(nowAngle,rand,x,y);
	  //这个是设置通话时间的
		rotateAnimation.setDuration(1000*4);
		rotateAnimation.setFillAfter(true);
		mView.startAnimation(rotateAnimation);
		nowAngle = rand % 360;
	 }
	
	//设置放入扇形的文字
	public void setdrgrees(ArrayList<String> list)
	{
		int num = list.size();
		drgrees = new int[num];
		strs = new String[num];
		for(int i=0;i<num;i++)
		{
			drgrees[i] = 360/num;
			strs[i] = list.get(i);
			if(strs[i].length() > 4)
			{
				strs[i] = strs[i].substring(0, 3) + "…";
			}
		}
		int last = 360-drgrees[0]*num;
		drgrees[0] = drgrees[0]+last;
	}
}
