package zeffect.cn.usingcommon;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.qimon.commonlibrary.gesture.ZGesture;

import zeffect.cn.common.gesture.OnGesture;

public class MainActivity extends Activity implements View.OnClickListener {
    private ZGesture mZGesture;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.root_layout);
        //
        findViewById(R.id.di1_layout).setOnClickListener(this);
        mZGesture = new ZGesture(this, mOnGesture);
        findViewById(R.id.di2_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                return mZGesture.onTouchEvent(pEvent);
            }
        });
    }

    @Override
    public void onClick(View view) {
    }


    private OnGesture mOnGesture = new OnGesture() {

        @Override
        public void onLeftUp() {
            super.onLeftUp();
            toast("单指左抬起");
        }

        @Override
        public void on2LeftUp(Float pFloat) {
            super.on2LeftUp(pFloat);
            toast("双指向左抬起");
        }

        @Override
        public void on2RightUp(Float pFloat) {
            super.on2RightUp(pFloat);
            toast("双指向右抬起");
        }
    };


    private void toast(String message) {
        if (message == null) message = "传入参数为Null";
        Snackbar.make(mView, message, Snackbar.LENGTH_SHORT).show();
    }

}