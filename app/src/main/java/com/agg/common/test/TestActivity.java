package com.agg.common.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import com.agg.common.utils.StorageUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;

public class TestActivity extends RxAppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.tv_study)
    TextView tvStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        Log.i(TAG, "onCreate: "+ Formatter.formatFileSize(this,StorageUtils.getTotalMemorySize())+"..."+Formatter.formatFileSize(this,StorageUtils.getAvailableMemorySize(this)));
//        XFontHelper.getInstance().init(this);
//        XFontHelper.getInstance().setActivityFont(this, "fonts/HuaKangWaWaW5.ttf");
//        ButterKnife.bind(this);
//        tvStudy.setText(R.string.study_not);
//
//        if (Build.VERSION.SDK_INT > 23)
//            PermissionHelper.rxAsk(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
//                    .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(aBoolean -> {
//                        Log.i(TAG, "" + aBoolean);
//                    });
    }

}
