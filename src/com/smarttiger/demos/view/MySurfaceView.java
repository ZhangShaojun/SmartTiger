package com.smarttiger.demos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder holder;
	private MyThread myThread;

	public MySurfaceView(Context context) {
		super(context);

		// 通过SurfaceView获得SurfaceHolder对象
		holder = getHolder();

		// 为holder添加回调结构SurfaceHolder.Callback
		holder.addCallback(this);

		// 创建一个绘制线程，将holder对象作为参数传入，这样在绘制线程中就可以获得holder
		// 对象，进而在绘制线程中可以通过holder对象获得Canvas对象，并在Canvas上进行绘制
		myThread = new MyThread(holder);
	}

	// 实现SurfaceHolder.Callback接口中的三个方法，都是在主线程中调用，而不是在绘制线程中调用的
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 启动线程。当这个方法调用时，说明Surface已经有效了
		myThread.setRun(true);
		myThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 结束线程。当这个方法调用时，说明Surface即将要被销毁了
		myThread.setRun(false);
	}

	class MyThread extends Thread {
		private SurfaceHolder holder;
		private boolean run;

		public MyThread(SurfaceHolder holder) {
			this.holder = holder;
			run = true;
		}

		@Override
		public void run() {
			int counter = 0;
			Canvas canvas = null;
			while (run) {
				// 具体绘制工作
				try {
					// 获取Canvas对象，并锁定之
					canvas = holder.lockCanvas();

					// 设定Canvas对象的背景颜色
					canvas.drawColor(Color.WHITE);

					// 创建画笔
					Paint p = new Paint();
					// 设置画笔颜色
					p.setColor(Color.BLACK);
					// 设置文字大小
					p.setTextSize(30);

					// 创建一个Rect对象rect
					Rect rect = new Rect(100, 50, 380, 330);
					// 在canvas上绘制rect
					canvas.drawRect(rect, p);
					// 在canvas上显示时间
					canvas.drawText("Interval = " + (counter++) + " seconds.",
							100, 410, p);
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						// 解除锁定，并提交修改内容
						holder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}

		public boolean isRun() {
			return run;
		}

		public void setRun(boolean run) {
			this.run = run;
		}
	}
}
