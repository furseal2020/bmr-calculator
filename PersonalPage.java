package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class PersonalPage extends AppCompatActivity {

    private TextView m_txt_id, m_txt_name, m_txt_gender, m_txt_age, m_txt_height, m_txt_weight, m_txt_bmr;
    private ImageView m_img_avatar;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        m_txt_id = (TextView)findViewById(R.id.txt_id);
        m_txt_name = (TextView)findViewById(R.id.txt_name);
        m_txt_gender = (TextView)findViewById(R.id.txt_gender);
        m_txt_age = (TextView)findViewById(R.id.txt_age);
        m_txt_height = (TextView)findViewById(R.id.txt_height);
        m_txt_weight = (TextView)findViewById(R.id.txt_weight);
        m_txt_bmr = (TextView)findViewById(R.id.txt_bmr);
        m_img_avatar = (ImageView)findViewById(R.id.img_avatar);

        Bundle bundle = getIntent().getExtras();          //Get the bundle
        String id = bundle.getString("id");

        try {
            json_string = getUserData(id); //getJSON

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
        jsonObject = new JSONObject(json_string);  // here is the data source
        jsonArray = jsonObject.getJSONArray("server_response"); //key:server_response

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject0 = null;
        try {
            jsonObject0 = jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = jsonObject0.optString("name");
        String gender = jsonObject0.optString("gender");
        Log.e("gender",gender);
        String age = jsonObject0.optString("age");
        String height = jsonObject0.optString("height");
        String weight = jsonObject0.optString("weight");
        String bmr = jsonObject0.optString("bmr");
        String img_url = jsonObject0.optString("img");
        Picasso.with(this)
                .load(img_url)
                .resize(130,130)
                .into(m_img_avatar) ;
        m_txt_id.setText("ID : "+id);
        m_txt_name.setText("Name : "+name);
        m_txt_gender.setText("Gender : "+gender);
        m_txt_age.setText("Age : "+age);
        m_txt_height.setText("Height : "+height);
        m_txt_weight.setText("Weight : "+weight);
        m_txt_bmr.setText("BMR : "+bmr);

    }


    private String getUserData(String id) throws ExecutionException, InterruptedException {
        String get_data_url = "http://10.0.2.2/android_use/bmr_calculator/get_user_data2.php"; //<-注意!這邊ip要打10.0.2.2
        final String finalId = id;

        String result = new AsyncTask<String, Void, String>()
        {
            @Override
            protected String doInBackground(String... params)
            {
                String result = null;
                String get_data_url = params[0];

                try {
                    URL url = new URL(get_data_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");  //Using "POST" means we're going to encode first and then we send the message
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("id","UTF-8") +"="+URLEncoder.encode(finalId,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    String data_str;
                    while((data_str = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(data_str+"\n");
                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }.execute(get_data_url).get();
        Log.e("bug",result);
        return result;
    }

    public void homeOnClick(View view)
    {
        openActivity_home();
    }
    private void openActivity_home()
    {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
