package com.gzfgeh.animation;

import com.gzfgeh.personalnote.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

public class RoundImageView extends ImageView {
	/**
	 * picture type circle or round
	 */
	private int type;
	private static final int TYPE_CIRCLE = 0;
	private static final int TYPE_ROUND = 1;
	
	private static final int BODER_RADIUS_DEFAULT = 10;	//round picture default circle
	private int borderRadius;	//round picture round value
	private Paint bitmapPaint;	
	private int radius;			//circle picture tadius
	private Matrix matrix;
	private BitmapShader bitmapShader;
	private int viewWidth;
	private RectF rectf;
	
	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		matrix = new Matrix();  
		bitmapPaint = new Paint();  
		bitmapPaint.setAntiAlias(true);  /* 去锯齿 */
  
        TypedArray a = context.obtainStyledAttributes(attrs,  
                R.styleable.RoundImageView);  
  
        borderRadius = a.getDimensionPixelSize(  
                R.styleable.RoundImageView_borderRadius, (int) TypedValue  
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,  
                                BODER_RADIUS_DEFAULT, getResources()  
                                        .getDisplayMetrics()));// 默认为10dp  
        type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 默认为Circle  
  
        a.recycle();  
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (type == TYPE_CIRCLE)  
        {  
			viewWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());  
			radius = viewWidth / 2;  
            setMeasuredDimension(viewWidth, viewWidth);  
        } 
	}
	
	private void setUpShader(){  
        Drawable drawable = getDrawable();  
        if (drawable == null){  
            return;  
        }  
  
        Bitmap bmp = drawableToBitamp(drawable);  
        // 将bmp作为着色器，就是在指定区域内绘制bmp  
        bitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);  
        float scale = 1.0f;  
        if (type == TYPE_CIRCLE){  
            // 拿到bitmap宽或高的小值  
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());  
            scale = viewWidth * 1.0f / bSize;  
  
        } else if (type == TYPE_ROUND){  
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；  
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight()  
                    * 1.0f / bmp.getHeight());  
        }  
        // shader的变换矩阵，我们这里主要用于放大或者缩小  
        matrix.setScale(scale, scale);  
        // 设置变换矩阵  
        bitmapShader.setLocalMatrix(matrix);  
        // 设置shader  
        bitmapPaint.setShader(bitmapShader);  
    }  
	
	 private Bitmap drawableToBitamp(Drawable drawable){  
        if (drawable instanceof BitmapDrawable){  
            BitmapDrawable bd = (BitmapDrawable) drawable;  
            return bd.getBitmap();  
        }  
        int w = drawable.getIntrinsicWidth();  
        int h = drawable.getIntrinsicHeight();  
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);  
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, w, h);  
        drawable.draw(canvas);  
        return bitmap;  
	 }

	@Override
	protected void onDraw(Canvas canvas){  
        if (getDrawable() == null)
            return; 
        
        setUpShader();
        if (type == TYPE_ROUND)  
            canvas.drawRoundRect(rectf, borderRadius, borderRadius,  
                    bitmapPaint);  
        else   
            canvas.drawCircle(radius, radius, radius, bitmapPaint);
    }  
      
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh){  
        super.onSizeChanged(w, h, oldw, oldh);  
        // 圆角图片的范围  
        if (type == TYPE_ROUND)  
        	rectf = new RectF(0, 0, getWidth(), getHeight());  
    }  
	 
	 
}
