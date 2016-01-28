package ec.edu.espol.integradora.dadtime.collage;
//reference https://github.com/thuytrinh/android-collage-views/blob/master/demo/src/main/java/com/thuytrinh/android/collageviewsdemo/CollageView.java


/**
 * Created by ces_m
 * on 1/23/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import ec.edu.espol.integradora.dadtime.collage2.QuiltView;

public class CollageView extends ImageView {

    private static final int PADDING = 8;
    private static final float STROKE_WIDTH = 8.0f;

    private Paint mBorderPaint;

    public CollageView(Context context) {
        this(context, null);
    }

    public CollageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    public CollageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initBorderPaint();
    }

    private void initBorderPaint() {
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(PADDING, PADDING, getWidth() - PADDING, getHeight() - PADDING, mBorderPaint);
    }

}