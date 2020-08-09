package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class ModifyPage extends AppCompatActivity {

    String name="",gender="", height="", weight="", age="", id="";


    private EditText m_editText_id, m_editText_name, m_editText_height, m_editText_weight, m_editText_age;
    private Button m_btnModify, m_btn_Home;
    private RadioButton m_btnMale;
    private RadioButton m_btnFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_page);

        m_editText_id = (EditText) findViewById(R.id.editText_id);
        m_editText_name = (EditText) findViewById(R.id.editText_name);
        m_btnMale = (RadioButton) findViewById(R.id.radioButton_male);
        m_btnFemale = (RadioButton) findViewById(R.id.radioButton_female);
        m_editText_height = (EditText) findViewById(R.id.editText_height);
        m_editText_weight = (EditText) findViewById(R.id.editText_weight);
        m_editText_age = (EditText) findViewById(R.id.editText_age);
        m_btnModify = (Button) findViewById(R.id.btnModify);
        m_btn_Home = (Button) findViewById(R.id.btn_Home);

        m_btnModify.setOnClickListener(btnOnClick_modify);
        m_btn_Home.setOnClickListener(btnOnClick_home);
    }

    Button.OnClickListener btnOnClick_modify = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(m_editText_id.getText().toString())) {
                showToast("ID number is necessary.");
            } else {
                id = m_editText_id.getText().toString();
                if (!TextUtils.isEmpty(m_editText_name.getText().toString())) {
                    name = m_editText_name.getText().toString();
                }
                if (!TextUtils.isEmpty(m_editText_height.getText().toString())) {
                    height = m_editText_height.getText().toString();
                }
                if (!TextUtils.isEmpty(m_editText_weight.getText().toString())) {
                    weight = m_editText_weight.getText().toString();
                }
                if (!TextUtils.isEmpty(m_editText_age.getText().toString())) {
                    age = m_editText_age.getText().toString();
                }
                if ((m_btnMale.isChecked()) || (m_btnFemale.isChecked())) {
                    if (m_btnMale.isChecked())
                        gender = "M";
                    else
                        gender = "F";
                }
                modifyData();
            }

        };
    };

    private void modifyData() {

        Runnable myThread = new Runnable() {
            String str_response;

            @Override
            public void run() {
                String modify_url = "http://10.0.2.2/android_use/bmr_calculator/modify.php"; //<-注意!這邊ip要打10.0.2.2

                try {
                    URL url = new URL(modify_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");  //Using "POST" means we're going to encode first and then we send the message
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("id","UTF-8") +"="+URLEncoder.encode(id,"UTF-8") +"&"+
                            URLEncoder.encode("name","UTF-8") +"="+URLEncoder.encode(name,"UTF-8") +"&"+
                            URLEncoder.encode("gender","UTF-8") +"="+URLEncoder.encode(gender,"UTF-8") +"&"+
                            URLEncoder.encode("height","UTF-8") +"="+URLEncoder.encode(height,"UTF-8") +"&"+
                            URLEncoder.encode("weight","UTF-8") +"="+URLEncoder.encode(weight,"UTF-8") +"&"+
                            URLEncoder.encode("age","UTF-8") +"="+URLEncoder.encode(age,"UTF-8");
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
                        Toast.makeText(ModifyPage.this, str_response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread t = new Thread(myThread) ;
        t.start() ;


    }

    Button.OnClickListener btnOnClick_home = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            openActivity();
        };
    };
    private void openActivity()
    {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void showToast(String text)
    {
        Toast.makeText(ModifyPage.this,text,Toast.LENGTH_SHORT).show();
    }


}
