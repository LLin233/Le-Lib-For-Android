package androidpath.ll.animationgogogo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import static android.support.v4.view.ViewCompat.animate;


//public class MainActivity extends Activity {
//    private TextView textWidget;
//    private Animation anim;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
//
//        // 监听动画的状态（开始，结束）
//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        textWidget = (TextView) findViewById(R.id.tv);
//        textWidget.setText("画面旋转动画效果");
//        textWidget.startAnimation(anim);
//    }
//
//    public void spin(View v) {
//        v.startAnimation(anim);
//    }
//
//}

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mMenuButton;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mItemButton1;
    private Button mItemButton2;
    private Button mItemButton3;
    private Button mItemButton4;
    private Button mItemButton5;

    private boolean mIsMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initView() {
        mButton1 = (Button) findViewById(R.id.myButton1);
        mButton1.setOnClickListener(this);

        mButton2 = (Button) findViewById(R.id.myButton2);
        mButton2.setOnClickListener(this);

        mButton3 = (Button) findViewById(R.id.myButton3);
        mButton3.setOnClickListener(this);

        mMenuButton = (Button) findViewById(R.id.menu);
        mMenuButton.setOnClickListener(this);

        mItemButton1 = (Button) findViewById(R.id.item1);
        mItemButton1.setOnClickListener(this);

        mItemButton2 = (Button) findViewById(R.id.item2);
        mItemButton2.setOnClickListener(this);

        mItemButton3 = (Button) findViewById(R.id.item3);
        mItemButton3.setOnClickListener(this);

        mItemButton4 = (Button) findViewById(R.id.item4);
        mItemButton4.setOnClickListener(this);

        mItemButton5 = (Button) findViewById(R.id.item5);
        mItemButton5.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenuButton.performClick();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

            case R.id.myButton1:
//                ObjectAnimator anim = ObjectAnimator.ofFloat(mButton1, "translationY", -mButton1.getHeight() * 5);
//                anim.start();
                ObjectAnimator anim = ObjectAnimator.ofInt(v, "width", mButton1.getWidth() * 2).setDuration(3000);
                anim.addListener((new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        v.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator.ofInt(v, "width", v.getWidth() / 2).start();
                        v.setClickable(true);
                    }
                }));
                anim.start();

                break;
            case R.id.myButton2:
                ObjectAnimator positionAnim = ObjectAnimator.ofFloat(v, "translationY", v.getY() * (- 1) + v.getHeight()/2 ).setDuration(2000);
                ValueAnimator colorAnim = ObjectAnimator.ofInt(v, "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
                colorAnim.setDuration(2000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);


                AnimatorSet animSet = new AnimatorSet();
                animSet.play(positionAnim).with(colorAnim);
                animSet.setDuration(5000);
                animSet.start();
                break;
            case R.id.myButton3:
//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(
//                        ObjectAnimator.ofFloat(v, "rotationX", 0, 360),
//                        ObjectAnimator.ofFloat(v, "rotationY", 0, 180),
//                        ObjectAnimator.ofFloat(v, "rotation", 0, -90),
//                        ObjectAnimator.ofFloat(v, "translationX", 0, 90),
//                        ObjectAnimator.ofFloat(v, "translationY", 0, 90),
//                        ObjectAnimator.ofFloat(v, "scaleX", 1, 1.5f),
//                        ObjectAnimator.ofFloat(v, "scaleY", 1, 0.5f),
//                        ObjectAnimator.ofFloat(v, "alpha", 1, 0.25f, 1)
//                );
//                set.setDuration(5 * 1000).start();
                final float re_y = v.getY();
                animate(v).setDuration(2000).rotationYBy(3600).y(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().y(re_y);
                    }
                });
                break;

        }

        if (v == mMenuButton) {
            if (!mIsMenuOpen) {
                mIsMenuOpen = true;
                doAnimateOpen(mItemButton1, 0, 5, 500);
                doAnimateOpen(mItemButton2, 1, 5, 500);
                doAnimateOpen(mItemButton3, 2, 5, 500);
                doAnimateOpen(mItemButton4, 3, 5, 500);
                doAnimateOpen(mItemButton5, 4, 5, 500);
            } else {
                mIsMenuOpen = false;
                doAnimateClose(mItemButton1, 0, 5, 500);
                doAnimateClose(mItemButton2, 1, 5, 500);
                doAnimateClose(mItemButton3, 2, 5, 500);
                doAnimateClose(mItemButton4, 3, 5, 500);
                doAnimateClose(mItemButton5, 4, 5, 500);
            }
        }

    }

    /**
     * 打开菜单的动画
     *
     * @param view   执行动画的 view
     * @param index  view 在动画序列中的顺序
     * @param total  动画序列的个数
     * @param radius 动画半径
     */
    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = (int) (radius * Math.cos(degree));
        int translationY = (int) (radius * Math.sin(degree));
        Log.d(TAG, String.format("degree=%f, translationX=%d, translationY=%d",
                degree, translationX, translationY));
        AnimatorSet set = new AnimatorSet();
        // 包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));

        // 动画周期为 500ms
        set.setDuration(1 * 300).start();
    }

    /**
     * 关闭菜单的动画
     *
     * @param view   执行动画的 view
     * @param index  view 在动画序列中的顺序
     * @param total  动画序列的个数
     * @param radius 动画半径
     */
    private void doAnimateClose(final View view, int index, int total,
                                int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = (int) (radius * Math.cos(degree));
        int translationY = (int) (radius * Math.sin(degree));
        Log.d(TAG, String.format("degree=%f, translationX=%d, translationY=%d",
                degree, translationX, translationY));
        AnimatorSet set = new AnimatorSet();
        // 包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
        // 为动画加上事件监听，当动画结束的时候，我们把当前 view 隐藏
        set.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }
        });

        set.setDuration(1 * 300).start();
    }
}
