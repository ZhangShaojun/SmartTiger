
package com.smarttiger.demos;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class TypeListViewActivity extends BaseActivity {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(TypeListViewActivity.class);
        DEMO.setTitle("ListView不同的item类型");
        StringBuilder sb = new StringBuilder();
        sb.append("ListView里如果有多种显示风格的子View该怎么办呢?");
        DEMO.setDescription(sb.toString());

    }

    ListView list;
    List<Item> strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_list_view_activity);
        strs = new ArrayList<Item>();
        for (int i = 0; i < 100; i++)
        {
            Item item=new Item();
            item.type=(int) (Math.random()*5);
            item.value=String.valueOf(i);
            strs.add(item);
        }
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(new MyAdapter());

    }

    int TypeCount = 5;

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return strs.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return strs.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Item item = strs.get(position);
            if (convertView == null) {
                switch (getItemViewType(position)) {
                    case 0:
                        TextView t = new TextView(parent.getContext());
                        t.setText(item.value);
                        convertView = t;
                        break;
                    case 1:
                        Button b = new Button(parent.getContext());
                        b.setText(item.value);
                        convertView = b;
                        break;
                    case 2:
                        ImageView img = new ImageView(parent.getContext());
                        img.setImageResource(R.drawable.ic_launcher);
                        convertView = img;
                        break;
                    case 3:
                        TextView t1 = new TextView(parent.getContext());
                        t1.setText(item.value);
                        t1.setTextColor(Color.RED);
                        convertView = t1;
                        break;
                    case 4:
                        Button b1 = new Button(parent.getContext());
                        b1.setText(item.value);
                        b1.setBackgroundColor(Color.YELLOW);
                        convertView = b1;
                        break;
                    default:
                        break;
                }
            } else {

                switch (getItemViewType(position)) {
                    case 0:
                        TextView t = (TextView) convertView;
                        t.setText(item.value);
                        break;
                    case 1:
                        Button b = (Button) convertView;
                        b.setText(item.value);
                        break;
                    case 2:
                        ImageView img = (ImageView) convertView;
                        img.setImageResource(R.drawable.ic_launcher);
                        break;
                    case 3:
                        TextView t1 = (TextView) convertView;
                        t1.setText(item.value);
                        t1.setTextColor(Color.RED);
                        break;
                    case 4:
                        Button b1 = (Button) convertView;
                        b1.setText(item.value);
                        b1.setBackgroundColor(Color.YELLOW);
                        break;
                    default:
                        break;
                }

            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            Item item = strs.get(position);
            int value = item.type;
            return value;

        }

        @Override
        public int getViewTypeCount() {
            return TypeCount;
        }

    }

    private class Item {
        String value;
        int type;
    }
}
