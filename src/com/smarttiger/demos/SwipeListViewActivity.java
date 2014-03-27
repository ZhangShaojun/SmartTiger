
package com.smarttiger.demos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.view.ExpandAniLinearLayout;
import com.smarttiger.demos.view.ExpandAniLinearLayout.OnLayoutAnimatListener;
import com.smarttiger.demos.view.swipe.AnimateDismissAdapter;
import com.smarttiger.demos.view.swipe.OnDismissCallback;
import com.smarttiger.demos.view.swipe.RotateYView;

public class SwipeListViewActivity extends BaseActivity implements OnClickListener,
        OnLayoutAnimatListener {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(SwipeListViewActivity.class);
        DEMO.setTitle("各种listview动画");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }
    private ListView listView;
    private LayoutInflater mInflater;
    private List<Integer> mSelectedPositions;
    private Button remove, removeAll;
    private ToggleButton edit;
    private MyListAdapter listAdapter;
    private List<RotateYView> mRotatteYViews;
    private Handler handler;
    private int ANIMATE_DELAY = 3;
    private ArrayList<String> data;
    private ExpandAniLinearLayout swipeListAni;
    private int screenWidth = 0;
    private int screenHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        mRotatteYViews = new ArrayList<RotateYView>();
        setContentView(R.layout.swipe_listview_layout);
        swipeListAni = (ExpandAniLinearLayout) findViewById(R.id.swipe_list_ani);
        handler = new Handler();
        mSelectedPositions = new ArrayList<Integer>();
        listView = (ListView) findViewById(R.id.swipe_list);
        removeAll = (Button) findViewById(R.id.remove_all);
        remove = (Button) findViewById(R.id.remove);
        edit = (ToggleButton) findViewById(R.id.edit);
        mInflater = LayoutInflater.from(this);
        data = getItems();
        int totalHeight = 0;
        int position = 0;
        View testView = newConvertView();
        for (int i = 0; i < data.size(); i++) {
            int height =
                    measureHeight(i, data.get(i), testView);
            totalHeight = totalHeight + height;
            position = i;
            if (totalHeight > screenHeight) {
                break;
            }
            System.out.println("measureHeight" + height);
        }
        for (int i = 0; i < position; i++) {
            View child = newConvertView();
            ViewHolder viewHolder = (ViewHolder) child.getTag();
            generateUi(i, data.get(i), viewHolder);
            // swipeListAni.addView();
            swipeListAni.addView(child, R.id.rotate_y_view, R.id.message_contents,
                    -1, -1, false);
        }
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        listView.setDivider(colorDrawable);
        swipeListAni.setDividerDrawable(colorDrawable);
        swipeListAni.setOnLayoutAnimatListener(this);

        listAdapter = new MyListAdapter(this, R.layout.activity_animateremoval_row,
                data);
        final AnimateDismissAdapter animateDismissAdapter = new AnimateDismissAdapter(listAdapter,
                new MyOnDismissCallback());
        listView.setAdapter(animateDismissAdapter);
        listView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                System.out.println("view onScroll" + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                System.out.println("view onScroll" + firstVisibleItem + "visibleItemCount"
                        + visibleItemCount);
            }
        });
        animateDismissAdapter.setAbsListView(listView);
        remove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                animateDismissAdapter.animateDismiss(mSelectedPositions);
                mSelectedPositions.clear();

            }
        });
        edit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                if (!isChecked) {
                    mSelectedPositions.clear();
                }
                Collections.sort(mRotatteYViews);
                for (int i = 0; i < mRotatteYViews.size(); i++) {
                    final int j = i;
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mRotatteYViews.get(j).animateState(isChecked);
                        }
                    }, i * (RotateYView.ANIMATE_TIME / ANIMATE_DELAY));
                }

            }
        });

        removeAll.setOnClickListener(this);
        swipeListAni.startExpand();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void removeAll() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(listView, "alpha", 1, 0);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                data.clear();
                listAdapter.notifyDataSetChanged();
            }
        });

    }

    public static ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j <= Math.random() * 7; j++) {
                sb.append("xxxxxxxxxxx");
            }
            items.add(sb.toString() + i);
        }
        return items;
    }

    private class MyListAdapter extends ArrayAdapter<String> {

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        public MyListAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);
        }

        @Override
        public String getItem(int position) {
            // TODO Auto-generated method stub
            return super.getItem(position);
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder holder = null;
            System.out.println("getView" + position);
            if (convertView == null) {
                convertView = newConvertView();
                holder = (ViewHolder) convertView.getTag();
                mRotatteYViews.add(holder.mRotateYView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mRotateYView.setIndex(position);
            // holder.mCheckBox.setText(String.valueOf(getItem(position)));
            generateUi(position, getItem(position), holder);
            return convertView;
        }

    }

    private View newConvertView() {
        View convertView = mInflater.inflate(R.layout.message_item_layout, null);
        ViewHolder holder = new ViewHolder();
        holder.mMessageIcon = (ImageView) convertView.findViewById(R.id.message_icon);
        holder.mMessageTitle = (TextView) convertView.findViewById(R.id.message_title);
        holder.mMessageDescription = (TextView) convertView
                .findViewById(R.id.message_desciption);
        holder.mMessageTime = (TextView) convertView.findViewById(R.id.message_time);
        holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.check_box);
        holder.mCheckBox.setOnClickListener(SwipeListViewActivity.this);
        holder.mRotateYView = (RotateYView) convertView.findViewById(R.id.rotate_y_view);
        convertView.setTag(holder);

        return convertView;
    }

    private void generateUi(int position, String str, ViewHolder viewHolder) {
        viewHolder.mCheckBox.setChecked(mSelectedPositions.contains(str));
        viewHolder.mCheckBox.setTag(position);
        int imgres = R.drawable.message_type_news;
        switch (position) {
            case 0:

                break;
            case 1:
                imgres = R.drawable.message_type_series;
                break;
            case 2:
                imgres = R.drawable.message_type_series;
                break;
            case 3:
                imgres = R.drawable.message_type_topic;
                break;
            case 4:
                imgres = R.drawable.message_type_update;
                break;

            default:
                break;
        }
        viewHolder.mMessageIcon.setImageResource(imgres);
        viewHolder.mMessageTitle.setText("xxxxxxx");
        viewHolder.mMessageDescription.setText(str);
        viewHolder.mMessageTime.setText("2012/23/34");
        viewHolder.mRotateYView.setState(edit.isChecked());
    }

    private static final class ViewHolder {
        public TextView mMessageTitle;
        public TextView mMessageDescription;
        public ImageView mMessageIcon;
        public TextView mMessageTime;
        public CheckBox mCheckBox;
        public RotateYView mRotateYView;
    }

    private class MyOnDismissCallback implements OnDismissCallback {

        @Override
        public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                data.remove(position);
            }
            listAdapter.notifyDataSetChanged();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    edit.setChecked(false);
                }
            }, 10);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_box:
                Object object = v.getTag();
                CheckBox checkBox = (CheckBox) v;
                if (object != null && object instanceof Integer) {
                    Integer position = (Integer) object;
                    if (checkBox.isChecked()) {

                        mSelectedPositions.add(position);
                    }
                    else {
                        mSelectedPositions.remove(position);
                    }
                }

                break;
            case R.id.remove_all:
                removeAll();
                break;

            default:
                break;
        }
    }

    private int measureHeight(int positon, String text, View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        int measureSpecHeight = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.AT_MOST);
        int measureSpecWidth = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        generateUi(positon, text, viewHolder);
        convertView.measure(measureSpecWidth, measureSpecHeight);
        return convertView.getMeasuredHeight();
    }

    @Override
    public void onAnimatEnd() {

        listView.setVisibility(View.VISIBLE);
        View parent = (View) swipeListAni.getParent();
        parent.setVisibility(View.GONE);
        isAnimat = false;

    }

    @Override
    public void onBackPressed() {
        if (isAnimat) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isAnimat) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAnimat) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    boolean isAnimat = false;

    @Override
    public void onAnimatStart() {
        isAnimat = true;
        listView.setVisibility(View.GONE);

    }
}
