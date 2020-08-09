package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ViewPage extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PersonalInfo_Adapter personalInfo_adapter;
    ListView m_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        try {
            json_string = getUserData(); //getJSON
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            jsonObject = new JSONObject(json_string);  // here is the data source
            jsonArray = jsonObject.getJSONArray("server_response"); //key:server_response

            personalInfo_adapter = new PersonalInfo_Adapter(this, jsonArray);  // instantiate the custom list adapter
            m_listView = (ListView) findViewById(R.id.listview);   // get the ListView and attach the adapter
            m_listView.setAdapter(personalInfo_adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
    private String getUserData() throws ExecutionException, InterruptedException {
        String get_data_url = "http://10.0.2.2/android_use/bmr_calculator/get_user_data1.php"; //<-注意!這邊ip要打10.0.2.2

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

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                    StringBuilder stringBuilder = new StringBuilder();
                    String data;
                    while((data = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(data+"\n");
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

        return result;

    }
}
