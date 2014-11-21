package com.greenhand.your_choice;

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

public class SlyderView	extends View {
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
		showAnimation(this); 
	}

	/**
	 * ��Ļ���
	 */
	private int screenW;

	/**
	 * ��Ļ�ĸ߶�
	 */
	private int screenH;
	/**
	 * �ָ�Ķ���
	 */
	private int[] drgrees = { 60, 60, 60, 60, 60, 60 };
	/***
	 * �ָ������
	 */
	private String[] strs = { "level1", "level2", "level3", "level4", "level5",
			"level6"};
	/**
	 * �ָ����ɫ
	 */
	private int[] colos = new int[] { 0xfed9c960, 0xfe57c8c8, 0xfe9fe558,
			0xfef6b000, 0xfef46212, 0xfecf2911, 0xfe9d3011 };
	/**
	 * ����
	 */
	private Paint paint;
	/**
	 * ���ֵĴ�С
	 */
	private float textSize = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics());
	/**
	 * ���ֵ���ɫ
	 */
	private int textcolor = Color.WHITE;
	/**
	 * ԰�İ뾶
	 */
	private float radius = TypedValue
			.applyDimension(TypedValue.COMPLEX_UNIT_SP, 140, getResources()
					.getDisplayMetrics());
	/**
	 * �����ֵľ���
	 */
	private float textdis = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 130, getResources().getDisplayMetrics());
	/**
	 * Բ��
	 */
	private float centerX;
	/**
	 * Բ��
	 */
	private float centerY;

	@SuppressWarnings("deprecation")
	private void init(Context context) {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		screenW = (((Activity) context).getWindowManager().getDefaultDisplay()
				.getWidth()/320)*150;
		screenH = (((Activity) context).getWindowManager().getDefaultDisplay()
				.getHeight()/320)*150;
	}
	/**
	 * ���ƾ��ο�
	 */
	private RectF oval;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		centerX = screenW;
		centerY = screenH / 2;
		oval = new RectF(centerX - radius, centerY - radius, centerX + radius,
				centerY + radius);
		float start = 0;
		paint.setColor(Color.rgb(0xdd, 0xdd, 0xdd));
		paint.setAlpha(127);
		canvas.drawCircle(centerX, centerY, radius, paint);
		paint.setAlpha(255);
		// ������
		paint.setAntiAlias(true);
		for (int i = 0; i < drgrees.length; i++) {
			float sweepAngle = drgrees[i];
			float startAngle = start;
			paint.setColor(colos[i % colos.length]);
			canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
			start += drgrees[i];
		}
		// ������
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
		float x = screenW;
		float y = screenH/2;
	  //�����������Ҫ��ת�ĽǶȣ������õ���1080��
	  RotateAnimation rotateAnimation = new RotateAnimation(0, 1080,x,y);
	  //���������ͨ��ʱ���
	  rotateAnimation.setDuration(1000*4);
	  rotateAnimation.setFillAfter(true);
	  mView.startAnimation(rotateAnimation);
	 }
}
