package com.example.sistemas.taihengnavdrawer.Adaptador;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sistemas.taihengnavdrawer.R;

import java.util.List;

public class CustomAdapter {

    public static class CustomListAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private int id;
        private List<String> items ;

        public CustomListAdapter(Context context, int textViewResourceId , List<String> list)
        {
            super(context, textViewResourceId, list);
            mContext = context;
            id = textViewResourceId;
            items = list ;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent)
        {
            View mView = v ;
            if(mView == null){
                LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mView = vi.inflate(id, null);
            }

            TextView text = (TextView) mView.findViewById(R.id.tvlistado);

            if(items.get(position) != null )
            {
                text.setTextColor(Color.BLACK);
                text.setText(items.get(position));
                text.setTextSize(15);
                int color = Color.argb(10, 0, 20, 200);
                text.setBackgroundColor(color);
            }
            return mView;
        }
    }
}
