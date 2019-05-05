package com.agg.common.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agg.common.interfaces.FontInterface;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class XFontHelper implements FontInterface<XFontHelper> {

    private Map<String, SoftReference<Typeface>> xSoftRefMap;
    private Map<String, Typeface> typefaceMap;
    private Context applicationContext;
    @SuppressLint("StaticFieldLeak")
    private static volatile XFontHelper defaultInstance;

    private XFontHelper() {
    }

    public static XFontHelper getInstance() {
        XFontHelper XFontHelper = defaultInstance;
        if (defaultInstance == null) {
            Class var1 = XFontHelper.class;
            synchronized(XFontHelper.class) {
                XFontHelper = defaultInstance;
                if (defaultInstance == null) {
                    XFontHelper = new XFontHelper();
                    defaultInstance = XFontHelper;
                }
            }
        }
        return XFontHelper;
    }

    public XFontHelper init(Context context) {
        this.xSoftRefMap = new HashMap();
        this.typefaceMap = new HashMap();
        this.applicationContext = context.getApplicationContext();
        return this;
    }

    public XFontHelper loadWithStrongRef(String fontPathInAssets) {
        if (TextUtils.isEmpty(fontPathInAssets)) {
            throw new RuntimeException("XFontHelper: the font path is null !");
        } else {
            if (!this.typefaceMap.containsKey(fontPathInAssets)) {
                this.typefaceMap.put(fontPathInAssets, this._load(fontPathInAssets));
            }
            return this;
        }
    }

    public XFontHelper loadWithSoftRef(String fontPathInAssets) {
        if (TextUtils.isEmpty(fontPathInAssets)) {
            throw new RuntimeException("XFontHelper: the font path is null !");
        } else {
            SoftReference typefaceSoftReference;
            if (!this.xSoftRefMap.containsKey(fontPathInAssets)) {
                typefaceSoftReference = new SoftReference(this._load(fontPathInAssets));
                this.xSoftRefMap.put(fontPathInAssets, typefaceSoftReference);
            } else {
                typefaceSoftReference = (SoftReference)this.xSoftRefMap.get(fontPathInAssets);
                if (typefaceSoftReference == null || typefaceSoftReference.get() == null) {
                    this.xSoftRefMap.remove(fontPathInAssets);
                    SoftReference<Typeface> xSoftRef = new SoftReference(this._load(fontPathInAssets));
                    this.xSoftRefMap.put(fontPathInAssets, xSoftRef);
                }
            }
            return this;
        }
    }

    public Typeface get(String fontPathInAssets) {
        if (TextUtils.isEmpty(fontPathInAssets)) {
            throw new RuntimeException("XFontHelper: the font path is null !");
        } else {
            Typeface typeface;
            if (this.typefaceMap.containsKey(fontPathInAssets)) {
                typeface = (Typeface)this.typefaceMap.get(fontPathInAssets);
            } else {
                SoftReference typefaceSoftReference;
                if (!this.xSoftRefMap.containsKey(fontPathInAssets)) {
                    typeface = this._load(fontPathInAssets);
                    typefaceSoftReference = new SoftReference(typeface);
                    this.xSoftRefMap.put(fontPathInAssets, typefaceSoftReference);
                } else {
                    typefaceSoftReference = (SoftReference)this.xSoftRefMap.get(fontPathInAssets);
                    if (typefaceSoftReference != null && typefaceSoftReference.get() != null) {
                        typeface = (Typeface)typefaceSoftReference.get();
                    } else {
                        this.xSoftRefMap.remove(fontPathInAssets);
                        typeface = this._load(fontPathInAssets);
                        SoftReference<Typeface> xSoftRef = new SoftReference(typeface);
                        this.xSoftRefMap.put(fontPathInAssets, xSoftRef);
                    }
                }
            }
            return typeface;
        }
    }

    public void setActivityFont(Activity activity, String fontPathInAssets) {
        if (activity != null && fontPathInAssets != null && fontPathInAssets.length() != 0) {
            ViewGroup mContainer = (ViewGroup)activity.findViewById(android.R.id.content).getRootView();
            Typeface m = this.get(fontPathInAssets);
            this._setViewGroupFont(mContainer, m);
        }
    }

    public <V extends View> void setViewFont(V view, String fontPathInAssets) {
        if (view instanceof TextView) {
            ((TextView)view).setTypeface(this.get(fontPathInAssets));
        } else if (view instanceof ViewGroup) {
            this._setViewGroupFont((ViewGroup)view, this.get(fontPathInAssets));
        }
    }

    private void _setViewGroupFont(ViewGroup mContainer, Typeface mFont) {
        if (mContainer != null && mFont != null) {
            for(int i = 0; i < mContainer.getChildCount(); ++i) {
                View mChild = mContainer.getChildAt(i);
                if (mChild instanceof TextView) {
                    ((TextView)mChild).setTypeface(mFont);
                } else if (mChild instanceof ViewGroup) {
                    this._setViewGroupFont((ViewGroup)mChild, mFont);
                }
            }
        }
    }

    private Typeface _load(String fontPathInAssets) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(this.applicationContext.getAssets(), fontPathInAssets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeface;
    }

}
