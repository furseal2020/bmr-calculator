package com.example.bmrcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

public class PersonalInfo_Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater li;
    private JSONArray array;

    public PersonalInfo_Adapter(Context context, JSONArray array) {  //'context' refers to the activity where the adapter is created. It is helps you with accessing system services and resources in case you need them.
        this.context = context;
        this.array = array;
        this.li = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {

        return array.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public String getItem2(int position, String key) throws JSONException {
        JSONObject jsonObject = array.getJSONObject(position);
        String value = jsonObject.optString(key);
        return value;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public static class ViewHolder
    {
        ImageView image;
        TextView id;
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //'final' means that once you have declared a variable, or method, or a class ,its value would remain the same throughout and one can not change its value again.
        if (convertView == null) {  //To optimize the listview's performance, a new row layout should be inflated only when convertView == null
            convertView = li.inflate(R.layout.row_layout, parent, false);  // inflate the layout for each list row
            holder = new ViewHolder();

            holder.image = (ImageView)convertView.findViewById(R.id.image);
            holder.id = (TextView) convertView.findViewById(R.id.txt_id_img);               // get the TextView
            holder.name = (TextView) convertView.findViewById(R.id.txt_name_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        PersonalInfo personalInfo = null;
        try {
            personalInfo = new PersonalInfo(getItem2(position,"id"),getItem2(position,"name"),getItem2(position,"img"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.id.setText(personalInfo.getId());                     //set the text from the current item object
        holder.name.setText(personalInfo.getName());
        final String url = personalInfo.getImg();

        Picasso.with(context)
                .load(url)
                .resize(75,75)
                .into(holder.image) ;

        final String finalId = personalInfo.getId();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonalPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", finalId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;  // returns the view for the current row
    }

}
