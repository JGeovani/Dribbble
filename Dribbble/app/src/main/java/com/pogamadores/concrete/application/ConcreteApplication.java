package com.pogamadores.concrete.application;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pogamadores.concrete.util.OkHttpStack;

/**
 * Created by igorcferreira on 1/25/15.
 */
public class ConcreteApplication extends Application {
    private RequestQueue mQueue;
    private static ConcreteApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3)
                .diskCacheExtraOptions(480, 320, null)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static ConcreteApplication get() {
        return mApplication;
    }

    public RequestQueue getQueue() {
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(mApplication, new OkHttpStack());
        }
        return mQueue;
    }

    public void addRequestToQueue(Request<?> request, String tag) {
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        getQueue().add(request);
    }

    public void cancelRequest(final String tag) {
        getQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return  (request.getTag().equals(tag));
            }
        });
    }
}
