package com.example.alfred.ui;


import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alfred.R;

import java.util.ArrayList;

public class Gastos_adapter extends BaseAdapter {

    private ArrayList<item_gasto> mainList;
    public Gastos_adapter(@NonNull Context context, int resource, @NonNull ArrayList<item_gasto> questionForSliderMenu) {
        super();
        this.mainList = questionForSliderMenu;
    }


    @Override
    public int getCount() {

        return mainList.size();
    }

    @Override
    public Object getItem(int position) {

        return mainList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(convertView.getContext());
            convertView = inflater.inflate(R.layout.item_gasto, null);
        }


        TextView tv1 = (TextView) convertView
                .findViewById(R.id.row_textView1);
        TextView tv2 = (TextView) convertView
                .findViewById(R.id.row_install_textView1);
        ImageView imageIcon = (ImageView) convertView
                .findViewById(R.id.row_imageView1);
        ImageView imageClick = (ImageView) convertView
                .findViewById(R.id.row_click_imageView1);

        try {

            tv1.setText(" List Item "+ " : " + position);
            View finalConvertView = convertView;
            View finalConvertView1 = convertView;
            imageClick.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {



                    switch (v.getId()) {
                        case R.id.row_click_imageView1:

                            PopupMenu popup = new PopupMenu(finalConvertView1.getContext(), v);
                            popup.getMenuInflater().inflate(R.menu.menu_pop_up,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.install:

                                            //Or Some other code you want to put here.. This is just an example.
                                            Toast.makeText(finalConvertView.getContext(), " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();

                                            break;
                                        case R.id.addtowishlist:

                                            Toast.makeText(finalConvertView.getContext(), "Add to Wish List Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();

                                            break;

                                        default:
                                            break;
                                    }

                                    return true;
                                }
                            });

                            break;

                        default:
                            break;
                    }



                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        return convertView;
    }

}
