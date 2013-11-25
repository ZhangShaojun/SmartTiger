
package com.smarttiger.demos;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.smarttiger.demos.bean.Demo;

public class VolleyActivity extends BaseActivity {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(VolleyActivity.class);
        DEMO.setTitle("volley 的用法");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());
    }
    NetworkImageView networkImageView;
    ImageView img;
    RequestQueue requestQueue;
    ImageCache imageCache;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_activity);
        networkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        img = (ImageView) findViewById(R.id.img);
        requestQueue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);

        imageCache = new ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);

            }

            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }

        };
        imageLoader = new ImageLoader(requestQueue, imageCache);
        loadImageByVolley();
        showImageByNetworkImageView();
        // getJSONByVolley();
    }

    private void loadImageByVolley() {
        String imageUrl = "http://183.61.244.2:10001/201309/041159338pn9.png";
        ImageListener listener = ImageLoader.getImageListener(img,
                R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(imageUrl, listener);
    }

    private void showImageByNetworkImageView() {
        String imageUrl = "http://183.61.244.2:10001/201309/041159338pn9.png";
        networkImageView.setTag("url");
        networkImageView.setImageUrl(imageUrl, imageLoader);

    }

    private void getJSONByVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
        final ProgressDialog progressDialog = ProgressDialog.show(this, "This is title",
                "...Loading...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                JSONDataUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response=" + response);
                        if (progressDialog.isShowing() && progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        System.out.println("sorry,Error");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

}
