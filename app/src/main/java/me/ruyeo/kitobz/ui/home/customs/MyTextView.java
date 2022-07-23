package me.ruyeo.kitobz.ui.home.customs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.text.LineBreaker;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Layout layout = getLayout();
        if (layout != null) {
            int width = (int) Math.ceil(getMaxLineWidth(layout))
                    + getCompoundPaddingLeft() + getCompoundPaddingRight();
            int width1 = getMeasuredWidth() / 2 + getMeasuredWidth() / 5;
            int height = getMeasuredHeight();
            String text = getText().toString();
            if (text.contains(" ")){
                setMeasuredDimension(width1, height);
                setBreakStrategy(LineBreaker.BREAK_STRATEGY_BALANCED);
            }

        }
    }

    private float getMaxLineWidth(Layout layout) {
        float max_width = 0.0f;
        int lines = layout.getLineCount();
        for (int i = 0; i < lines; i++) {
            if (layout.getLineWidth(i) > max_width) {
                max_width = layout.getLineWidth(i);
            }
        }
        return max_width;
    }
}