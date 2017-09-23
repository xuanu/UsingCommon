package zeffect.cn.usingcommon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import zeffect.cn.common.activity.ActivityUtils;
import zeffect.cn.common.gesture.OnClickFastListener;
import zeffect.cn.common.log.L;
import zeffect.cn.common.view.ViewUtils;
import zeffect.cn.common.weak.WeakAsyncTask;
import zeffect.cn.common.weak.WeakHandler;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.di1_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
