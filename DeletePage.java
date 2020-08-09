package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class DeletePage extends AppCompatActivity {

    private EditText m_editText_name, m_editText_age;
    private Button m_btnDelete;
    private Button m_btnHOME;

    private String name,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_page);

        m_editText_name = (EditText)findViewById(R.id.editText_name);
        m_editText_age = (EditText)findViewById(R.id.editText_age);
        m_btnDelete = (Button)findViewById(R.id.btnDelete);
        m_btnHOME = (Button)findViewById(R.id.btnHOME);

        m_btnDelete.setOnClickListener(btnOnClick_delete);
        m_btnHOME.setOnClickListener(btnOnClick_home);
    }
    Button.OnClickListener btnOnClick_delete = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(TextUtils.isEmpty(m_editText_name.getText().toString() )  || TextUtils.isEmpty(m_editText_age.getText().toString()))
            {
                showToast("Please fill in all of the blanks above.");
            }
            else
            {
                name = m_editText_name.getText().toString();
                age =m_editText_age.getText().toString();
                deleteData(name, age);
            }
        };

    };

    private void deleteData(String name, String age)   //If there is more than a row of data that matches the request then all of them will be deleted.
    {
        final String str_name = name;
        final String str_age = age;

        Runnable myThread = new Runnable() {
            String str_response;

            @Override
            public void run() {
                String delete_url = "http://10.0.2.2/android_use/bmr_calculator/delete.php"; //<-注意!這邊ip要打10.0.2.2

                try {
                    URL url = new URL(delete_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");  //Using "POST" means we're going to encode first and then we send the message
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("name","UTF-8") +"="+URLEncoder.encode(str_name,"UTF-8") +"&"+
                            URLEncoder.encode("age","UTF-8") +"="+URLEncoder.encode(str_age,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                    StringBuilder stringBuilder = new StringBuilder();
                    String response;
                    while((response = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(response+"\n");

                    }
                    str_response = stringBuilder.toString().trim();
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DeletePage.this, str_response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread t = new Thread(myThread) ;
        t.start() ;
    }

    Button.OnClickListener btnOnClick_home = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            openActivity_home();
        };
    };

    private void openActivity_home()
    {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void showToast(String text)
    {
        Toast.makeText(DeletePage.this,text,Toast.LENGTH_SHORT).show();
    }
}
