package com.smarttiger.demos.bean;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;
	
	public BitmapCache() {
		int maxSize = 10 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getWidth() * value.getHeight();
			}
			
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
	    
	    Bitmap bitmap=mCache.get(url);
	    
		return bitmap;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}

}
