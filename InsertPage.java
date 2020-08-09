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

public class InsertPage extends AppCompatActivity {

    String name,gender;
    double height, weight;
    int age;

    private EditText m_editText_name;
    private EditText m_editText_height;
    private EditText m_editText_weight;
    private EditText m_editText_age;
    private Button m_btnSubmit;
    private RadioButton m_btnMale;
    private RadioButton m_btnFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_page);

        m_editText_name = (EditText)findViewById(R.id.editText_name);
        m_btnMale = (RadioButton)findViewById(R.id.btnMale);
        m_btnFemale = (RadioButton)findViewById(R.id.btnFemale);
        m_editText_height = (EditText)findViewById(R.id.editText_height);
        m_editText_weight = (EditText)findViewById(R.id.editText_weight);
        m_editText_age = (EditText)findViewById(R.id.editText_age);
        m_btnSubmit = (Button)findViewById(R.id.btnSubmit);

        m_btnSubmit.setOnClickListener(btnOnClick_submit);
    }

    Button.OnClickListener btnOnClick_submit = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (TextUtils.isEmpty( m_editText_name.getText().toString())||TextUtils.isEmpty( m_editText_height.getText().toString())||TextUtils.isEmpty( m_editText_weight.getText().toString())||TextUtils.isEmpty( m_editText_age.getText().toString()))
            {
                showToast("Please fill in all of the blanks above.");
            }
            else if ((!m_btnMale.isChecked())&&(!m_btnFemale.isChecked()))
            {
                showToast("Please choose your gender.");
            }
            else
            {
                name = m_editText_name.getText().toString();
                if (m_btnMale.isChecked())
                    gender = "M";
                else
                    gender = "F";
                height = Double.valueOf(m_editText_height.getText().toString());
                weight = Double.valueOf(m_editText_weight.getText().toString());
                age = Integer.valueOf(m_editText_age.getText().toString());
                //showToast("Submitted");
                openActivity();
            }

        };
    };

    public void showToast(String text)
    {
        Toast.makeText(InsertPage.this,text,Toast.LENGTH_SHORT).show();
    }
    private void openActivity()
    {
        Intent intent = new Intent(this, OutputPage.class);
        Bundle bundle = new Bundle();        //Create the bundle
        bundle.putString("name",name);       //Add your data to bundle
        bundle.putString("gender",gender);
        bundle.putDouble("height",height);
        bundle.putDouble("weight",weight);
        bundle.putInt("age",age);
        intent.putExtras(bundle);            //Add the bundle to the intent
        startActivity(intent);               //Fire the second activity
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
