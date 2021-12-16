package com.rhodonite.dashboard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

public class DashBoard extends View {


    private Paint paint, tmpPaint, textPaint, strokePain;
    private RectF rect;
    private int backGroundColor;    //被景色
    private float pointLength;      //指針長度
    private float per;
    private float perPoint;        //變化中的指針
    private float perOld;          //變化前的指針百分比
    private float length;          //圖形半徑
    private float r;

    public DashBoard(Context context) {
        super(context);
        init();
    }


    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heitht = width / 2 / 4 * 5;
        initIndex(width / 2);

        setMeasuredDimension(width, heitht);
    }


    private void initIndex(int specSize) {
        backGroundColor = Color.WHITE;
        r = specSize;
        length = r / 4 * 3;
        pointLength = -(float) (r * 0.6);
        per = 0;
        perOld = 0;
    }


    private void init() {
        paint = new Paint();
        rect = new RectF();
        textPaint = new Paint();
        tmpPaint = new Paint();
        strokePain = new Paint();
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setR(float r) {
        this.r = r;
        this.length = r / 4 * 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        //環的顏色
        initRing(canvas);
        //指针
        initPointer(canvas);
        //提示内容
        initText(canvas);
    }

    private void initText(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, r);

        float rIndex = length / 3;//length ;

        paint.setColor(Color.parseColor("#eeeeee"));
        paint.setShader(null);
        paint.setShadowLayer(5, 0, 0, 0x54000000);
        rect = new RectF(-(rIndex / 3), -(rIndex / 3), rIndex / 3, rIndex / 3);
        canvas.drawArc(rect, 0, 360, true, paint);

        paint.clearShadowLayer();

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2f, r);


        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.parseColor("#fc6555"));
        textPaint.setTextAlign(Paint.Align.RIGHT);

        int _per = (int) (per * 180);
        if (_per < 60) {
            textPaint.setColor(Color.parseColor("#ff6450"));
        } else if (_per < 120) {
            textPaint.setColor(Color.parseColor("#f5a623"));
        } else {
            textPaint.setColor(Color.parseColor("#79d062"));
        }
        float swidth = textPaint.measureText(String.valueOf(_per));
        swidth = (swidth - swidth / 2);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float sheight = fontMetrics.descent - fontMetrics.ascent;
        sheight = (sheight - (sheight+20)/2);
        canvas.translate(swidth, sheight);
        canvas.drawText("" + _per, 0, 0, textPaint);

        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.parseColor("#999999"));

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, r + length / 3 / 2);


    }

    public void setBackGroundColor(int color) {
        this.backGroundColor = color;
    }

    public void setPointLength1(float pointLength1) {
        this.pointLength = -length * pointLength1;
    }

    private void initPointer(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, r);
        float change;

        if (perPoint < 1) {
            change = perPoint * 180;

        } else {
            change = perPoint * 180;
        }
        //旋轉角度
        canvas.rotate(-90 + change, 0f, 0f);

        //描繪指針
        Path path = new Path();
        path.moveTo(0, pointLength);
        path.lineTo(-10, pointLength + 10);
        path.lineTo(10, pointLength + 10);

        path.close();

        canvas.drawPath(path, paint);
        Path pathRect = new Path();
        pathRect.moveTo(-10, pointLength + 10);
        pathRect.lineTo(-10, 10 + (length / 6));
        pathRect.lineTo(10, 10 + (length / 6));
        pathRect.lineTo(10, pointLength + 10);
        pathRect.close();
        canvas.drawPath(pathRect, paint);
    }

    private void initRing(Canvas canvas) {
        paint.setAntiAlias(true);

        canvas.save();
        canvas.translate(canvas.getWidth() / 2, r);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#F95A37"));
        rect = new RectF(-length, -length, length, length);
        canvas.drawArc(rect, -180, 60, true, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#f9cf45"));
        rect = new RectF(-length, -length, length, length);
        canvas.drawArc(rect, -120/*180f + 180f * (140f / 180f)*/, 60, true, paint);


        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#3FBF55"));
        rect = new RectF(-length, -length, length, length);
        canvas.drawArc(rect, -60, 60, true, paint);

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, r);


        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, r);


        paint.setShader(null);
        paint.setColor(backGroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(-length, (float) (Math.sin(Math.toRadians(10)) * length / 3f * 2f) - 47f, length, (float) (Math.sin(Math.toRadians(10)) * length + 100), paint);


        //填滿內部顏色
        paint.setColor(backGroundColor);
        paint.setShader(null);
        rect = new RectF(-(length - length / 3f - 2), -(length / 3f * 2f - 2), length - length / 3f - 2, length / 3f * 2f - 2);
        canvas.drawArc(rect, 0, 360, true, paint);


    }

    public void cgangePer(float per) {
        this.perOld = this.per;
        this.per = per;
        ValueAnimator va = ValueAnimator.ofFloat(perOld, per);
        va.setDuration(1000);
        va.setInterpolator(new OvershootInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                perPoint = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();

    }
}