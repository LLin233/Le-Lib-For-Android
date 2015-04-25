package androidpath.ll.swipemenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by Le on 2015/4/25.
 */
public class MainUI extends RelativeLayout {
    public static final int LEFT_ID = 0xaabbcc;
    public static final int MID_ID = 0xaaccbb;
    public static final int RIGHT_ID = 0xccbbaa;
    private Context context;
    private FrameLayout leftMenu;
    private FrameLayout rightMenu;
    private FrameLayout midMenu;
    private FrameLayout midMask;
    private Scroller mScroller;

    public MainUI(Context context) {
        super(context);
        initView(context);
    }

    public MainUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressWarnings("ResourceType")
    private void initView(Context context) {
        this.context = context;
        mScroller = new Scroller(context, new DecelerateInterpolator());
        leftMenu = new FrameLayout(context);
        leftMenu.setId(LEFT_ID);
        rightMenu = new FrameLayout(context);
        rightMenu.setId(RIGHT_ID);
        midMenu = new FrameLayout(context);
        midMask = new FrameLayout(context);
        midMenu.setId(MID_ID);
        midMenu.setBackgroundColor(getResources().getColor(R.color.mid));
        leftMenu.setBackgroundColor(getResources().getColor(R.color.left));
        rightMenu.setBackgroundColor(getResources().getColor(R.color.right));
        midMask.setBackgroundColor(Color.LTGRAY);
        midMask.setAlpha(0); // Initially transparent
        addView(leftMenu);
        addView(midMenu);
        addView(rightMenu);
        addView(midMask);
    }


    //listen to event to make midMask visible
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        int curX = Math.abs(getScrollX());
        float scale = curX / (float) leftMenu.getMeasuredWidth();
        midMask.setAlpha(scale);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        midMenu.measure(widthMeasureSpec, heightMeasureSpec);
        midMask.measure(widthMeasureSpec, heightMeasureSpec);
        int fullWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tempWidth = MeasureSpec.makeMeasureSpec(
                (int) (fullWidth * 0.6f),
                MeasureSpec.EXACTLY);
        leftMenu.measure(tempWidth, heightMeasureSpec);
        rightMenu.measure(tempWidth, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        midMenu.layout(l, t, r, b);
        midMask.layout(l, t, r, b);
        leftMenu.layout(l - leftMenu.getMeasuredWidth(), t, r, b);
        rightMenu.layout(l + midMenu.getMeasuredWidth(), t, l + midMenu.getMeasuredWidth() + rightMenu.getMeasuredWidth(), b);
    }

    private boolean isTouch = false;
    private static final int DES = 20;
    private boolean isSwipeRightLelt = false;
    private boolean isSwipeTopBottom = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isTouch) {
            getEventType(ev);
            return true;
        }

        if (isSwipeRightLelt) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_MOVE:
                    int curScrollX = getScrollX();
                    int dis_x = (int) (ev.getX() - point.x);
                    int expectX = dis_x * -1 + curScrollX;
                    int final_X = 0;
                    if (expectX < 0) {
                        //To left
                        final_X = Math.max(expectX, -leftMenu.getMeasuredWidth());
                    } else {
                        //To right
                        final_X = Math.min(expectX, rightMenu.getMeasuredWidth());
                    }
                    scrollTo(final_X, 0);
                    getPoint(ev);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    curScrollX = getScrollX();
                    if (Math.abs(curScrollX) > leftMenu.getMeasuredWidth() >> 1) {
                        if (curScrollX < 0) {
                            //right menu shows
                            mScroller.startScroll(curScrollX, 0, -leftMenu.getMeasuredWidth() - curScrollX, 0, 200);
                        } else {
                            //left menu shows
                            mScroller.startScroll(curScrollX, 0, leftMenu.getMeasuredWidth() - curScrollX, 0, 200);
                        }
                    } else {
                        //back to midFrame
                        mScroller.startScroll(curScrollX, 0, -curScrollX, 0);
                    }
                    //refresh UI
                    invalidate();

                    //reset motion
                    isTouch = false;
                    isSwipeRightLelt = false;
                    break;

            }
        } else {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                    isSwipeRightLelt = false;
                    isTouch = false;
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.computeScrollOffset()) {
            return;
        }
        int tempX = mScroller.getCurrX();
        scrollTo(tempX, 0);
    }

    private Point point = new Point();

    private void getPoint(MotionEvent ev) {
        point.x = (int) ev.getX();
        point.y = (int) ev.getY();
    }

    private void getEventType(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getPoint(ev);
                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = Math.abs((int) ev.getX() - point.x);
                int dY = Math.abs((int) ev.getY() - point.y);
                // swipe motion: left <-> right
                if (dX >= DES && dX > dY) {
                    isSwipeRightLelt = true;
                    getPoint(ev);
                    isTouch = true;

                } else if (dY >= DES && dY > dX) { //swipe motion: top <-> bottom
                    isSwipeRightLelt = false;
                    getPoint(ev);
                    isTouch = true;

                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                super.dispatchTouchEvent(ev);
                isSwipeRightLelt = false;
                isTouch = false;
                break;

        }
    }
}
