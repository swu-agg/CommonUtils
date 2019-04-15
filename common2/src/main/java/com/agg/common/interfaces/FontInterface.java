package com.agg.common.interfaces;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

public interface FontInterface<This> {
    This init(Context var1);

    This loadWithStrongRef(String var1);

    This loadWithSoftRef(String var1);

    Typeface get(String var1);

    void setActivityFont(Activity var1, String var2);

    <V extends View> void setViewFont(V var1, String var2);
}
