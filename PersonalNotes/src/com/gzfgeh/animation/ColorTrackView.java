package com.gzfgeh.animation;

import com.gzfgeh.personalnote.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ColorTrackView extends View {
	private float progress;
	private String text = "Android";
	private int textSize;
	private int startX;
	private Paint paint;
	private Rect rect;
	private int textOriginColor = 0xFF000000;
	private int textChangeColor = 0xFFFF0000;
	private int textWidth;
	private int realWidth;
	private int direction = DIRECTION_LEFT;
	private static int DIRECTION_LEFT = 0;
	public enum Direction{
		LEFT , RIGHT ;
	}
	
	public ColorTrackView(Context context) {
		this(context, null);
	}
	
	public ColorTrackView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ColorTrackView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackView);
		textSize = array.getDimensionPixelSize(R.styleable.ColorTrackView_text_size, textSize);
		text = array.getString(R.styleable.ColorTrackView_text);
		textOriginColor = array.getColor(R.styleable.ColorTrackView_text_origin_color,
						textOriginColor);
		textChangeColor = array.getColor(R.styleable.ColorTrackView_text_change_color, 
						textChangeColor);
		progress = array.getFloat(R.styleable.ColorTrackView_progress, progress);
		direction = array.getInt(R.styleable.ColorTrackView_direction, direction);
		array.recycle();
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(textSize);
		textWidth = (int) paint.measureText(text);
		rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);  
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
		
		realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		startX = (realWidth - textWidth) / 2;
	}

	private int measureHeight(int heightMeasureSpec) {
		int mode = MeasureSpec.getMode(heightMeasureSpec);
		int size = MeasureSpec.getSize(heightMeasureSpec);
		int result = 0;
		switch (mode) {
		case MeasureSpec.EXACTLY:
			result = size;
			break;
			
		case MeasureSpec.AT_MOST:
			result = Math.min(rect.height(), size);
			break;
			
		case MeasureSpec.UNSPECIFIED:
			result = rect.height();
			break;
			
		default:
			break;
		}
		
		return result + getPaddingBottom() + getPaddingTop();
	}

	private int measureWidth(int widthMeasureSpec) {
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int result = 0;
		switch (mode) {
		case MeasureSpec.EXACTLY:
			result = size;
			break;
			
		case MeasureSpec.AT_MOST:
			result = Math.min(textWidth, size);
			break;
			
		case MeasureSpec.UNSPECIFIED:
			result = textWidth;
			break;
			
		default:
			break;
		}
		return result + getPaddingLeft() + getPaddingRight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int passX = (int) (progress*textWidth + startX);
		if (direction == DIRECTION_LEFT){
			drawChangeLeft(canvas, passX);  
            drawOriginLeft(canvas, passX);
		}else {
			drawOriginRight(canvas, passX);  
            drawChangeRight(canvas, passX);
		}
	}

	private void drawChangeRight(Canvas canvas, int passX) {
		drawText(canvas, textChangeColor, (int) (startX +(1-progress)*textWidth), startX+textWidth ); 
	}

	private void drawText(Canvas canvas, int color, int i, int j) {
		paint.setColor(color);
		canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.clipRect(i, 0, j, getMeasuredHeight());
		canvas.drawText(text, startX, getMeasuredHeight()/2 + rect.height()/2, paint);
		canvas.restore();
	}

	private void drawOriginRight(Canvas canvas, int passX) {
		drawText(canvas, textOriginColor, startX, (int) (startX +(1-progress)*textWidth) );
	}

	private void drawOriginLeft(Canvas canvas, int passX) {
		drawText(canvas, textOriginColor, passX, startX+textWidth );
	}

	private void drawChangeLeft(Canvas canvas, int passX) {
		drawText(canvas, textChangeColor, startX, passX );
	}
	

	
	public int getTextSize() {
		return textSize;
	}


	public void setTextSize(int textSize) {
		this.textSize = textSize;
		requestLayout();
		invalidate();
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}
	

	public float getProgress() {
		return progress;
	}


	public void setProgress(float progress) {
		this.progress = progress;
		invalidate();
	}

	public int getTextOriginColor() {
		return textOriginColor;
	}

	public void setTextOriginColor(int textOriginColor) {
		this.textOriginColor = textOriginColor;
		invalidate();
	}

	public int getTextChangeColor() {
		return textChangeColor;
	}

	public void setTextChangeColor(int textChangeColor) {
		this.textChangeColor = textChangeColor;
		invalidate();
	}
	
	
}
