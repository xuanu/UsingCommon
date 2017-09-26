package zeffect.cn.usingcommon;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
=======
        findViewById(R.id.di1_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

>>>>>>> origin/master
    }
}
