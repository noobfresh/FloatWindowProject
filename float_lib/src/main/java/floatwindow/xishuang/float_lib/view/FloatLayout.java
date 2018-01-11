package floatwindow.xishuang.float_lib.view;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import floatwindow.xishuang.float_lib.FloatActionController;
import floatwindow.xishuang.float_lib.R;

/**
 * Author:xishuang
 * Date:2017.08.01
 * Des:悬浮窗的布局
 */
public class FloatLayout extends FrameLayout implements View.OnClickListener{
    private final WindowManager mWindowManager;
    private final ImageView okImage;
    private final ImageView upImage;
    private final ImageView downImage;
    private final ImageView leftImage;
    private final ImageView rightImage;
    //    private final DraggableFlagView mDraggableFlagView;
    private long startTime;
    private float mTouchStartX;
    private float mTouchStartY;
    private boolean isclick;
    private WindowManager.LayoutParams mWmParams;
    private Context mContext;
    private long endTime;
    private View relative;

    private int lastX, lastY, moveDownX;
    private int screenWidth, screenHeight;

    public FloatLayout(Context context) {
        this(context, null);
        mContext = context;
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        relative = LayoutInflater.from(context).inflate(R.layout.float_littlemonk_layout, this);
        //浮动窗口按钮
        okImage = (ImageView) findViewById(R.id.float_id);
        upImage = (ImageView) findViewById(R.id.float_id2);
        downImage = (ImageView) findViewById(R.id.float_id5);
        leftImage =(ImageView) findViewById(R.id.float_id4);
        rightImage = (ImageView) findViewById(R.id.float_id3);
        okImage.setOnClickListener(this);
        upImage.setOnClickListener(this);
        downImage.setOnClickListener(this);
        leftImage.setOnClickListener(this);
        rightImage.setOnClickListener(this);

        Display dis = mWindowManager.getDefaultDisplay();
        screenHeight = dis.getHeight();
        screenWidth = dis.getWidth();
//        relative.setOnTouchListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        //下面的这些事件，跟图标的移动无关，为了区分开拖动和点击事件
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //图标移动的逻辑在这里
                float mMoveStartX = event.getX();
                float mMoveStartY = event.getY();
                // 如果移动量大于3才移动
                if (Math.abs(mTouchStartX - mMoveStartX) > 3
                        && Math.abs(mTouchStartY - mMoveStartY) > 3) {
                    // 更新浮动窗口位置参数
                    mWmParams.x = (int) (x - mTouchStartX);
                    mWmParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(this, mWmParams);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                endTime = System.currentTimeMillis();
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                if ((endTime - startTime) > 0.1 * 1000L) {
                    isclick = false;
                } else {
                    isclick = true;
                }
                break;
        }
        //响应点击事件
//        if (isclick) {
//            Toast.makeText(mContext, "你点击了这个东西", Toast.LENGTH_SHORT).show();
//        }
        return true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

    /**
     * 设置小红点显示
     */
    public void setDragFlagViewVisibility(int visibility) {
//        mDraggableFlagView.setVisibility(visibility);
    }

    /**
     * 设置小红点数量
     */
    public void setDragFlagViewText(int number) {
//        mDraggableFlagView.setText(number + "");
    }

    @Override
    public void onClick(View view) {
        if(view == okImage){
            Toast.makeText(mContext, "你点击了OK", Toast.LENGTH_SHORT).show();
        }else if(view == upImage){
            Toast.makeText(mContext, "你点击了shang", Toast.LENGTH_SHORT).show();
        }else if(view == downImage){
            Toast.makeText(mContext, "你点击了xia", Toast.LENGTH_SHORT).show();
        }else if(view == leftImage){
            Toast.makeText(mContext, "你点击了zuo", Toast.LENGTH_SHORT).show();
        }else if(view == rightImage){
            Toast.makeText(mContext, "你点击了you", Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//            lastX = (int) motionEvent.getRawX();
//            lastY = (int) motionEvent.getRawY();
//            moveDownX = (int) motionEvent.getRawX();
//        }
//
//        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//            int dx = (int) motionEvent.getRawX() - lastY;
//            int dy = (int) motionEvent.getRawY() - lastX;
//
//            int top = view.getTop() + dy;
//            int left = view.getLeft() + dx;
//
//            if (top <= 0) {
//                top = 0;
//            }
//            if (top >= screenHeight - relative.getHeight()) {
//                top = screenHeight - relative.getHeight();
//            }
//            if (left >= screenWidth - relative.getWidth()) {
//                left = screenWidth - relative.getWidth();
//            }
//            if (left <= 0) {
//                left = 0;
//            }
//            view.layout(left, top, left + relative.getWidth(), top + relative.getHeight());
//            lastX = (int) motionEvent.getRawX();
//            lastY = (int) motionEvent.getRawY();
//        }
//
//        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//            int tempDX = (int) motionEvent.getRawX() - moveDownX;
//            if (Math.abs(tempDX) < 6) {
//                // do your things
//                return false;// 距离较小，当作click事件来处理
//            }
//        }
//
//        return false;
//    }
}
