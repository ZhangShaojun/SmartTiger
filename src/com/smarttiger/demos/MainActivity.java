
package com.smarttiger.demos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class MainActivity extends Activity implements OnItemClickListener {

    private ListView mListView;
    List<Demo> demos = new ArrayList<Demo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView = (ListView) findViewById(R.id.list);
        demos.add(DensityActivity.DEMO);
        demos.add(DrawableSizeActivity.DEMO);
        demos.add(DefaultOpenActivity.DEMO);
        demos.add(FocusWeixinActivity.DEMO);
        demos.add(TestSurfaceViewActivity.DEMO);
        demos.add(FragmentTestActivity.DEMO);
        demos.add(SquenceInfoBarActivity.DEMO);
        demos.add(TypeListViewActivity.DEMO);
        demos.add(SpannableActivity.DEMO);
        demos.add(ClickAppActivity.DEMO);
        demos.add(VolleyActivity.DEMO);
        demos.add(MaskDrawableActivity.DEMO);
        MyAdapter myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(this);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return demos.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return demos.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = new TextView(MainActivity.this);
            }
            TextView textView = (TextView) convertView;
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, 20, 0, 20);
            Demo demo = demos.get(position);
            textView.setText(demo.getTitle());
            textView.setTag(demo);
            return convertView;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	
        Demo demo = demos.get(position);
        Intent intent = new Intent(MainActivity.this, demo.getClassName());
        startActivity(intent);
    }
}
