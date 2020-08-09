package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button m_btnInsert,m_btnViewAll, m_btnDelete, m_btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        m_btnInsert = (Button)findViewById(R.id.btnInsert);
        m_btnInsert.setOnClickListener(btnOnClick_btnInsert);

        m_btnViewAll = (Button)findViewById(R.id.btnViewAll);
        m_btnViewAll.setOnClickListener(btnOnClick_btnViewAll);

        m_btnDelete = (Button)findViewById(R.id.btnDelete);
        m_btnDelete.setOnClickListener(btnOnClick_btnDelete);

        m_btnModify = (Button)findViewById(R.id.btnModify);
        m_btnModify.setOnClickListener(btnOnClick_btnModify);
    }

    Button.OnClickListener btnOnClick_btnModify = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            openActivity_modify();
        };

    };
    private void openActivity_modify()
    {
        Intent intent = new Intent(this, ModifyPage.class);
        startActivity(intent);
    }

    Button.OnClickListener btnOnClick_btnDelete = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            openActivity_delete();
        };

    };
    private void openActivity_delete()
    {
        Intent intent = new Intent(this, DeletePage.class);
        startActivity(intent);
    }

    Button.OnClickListener btnOnClick_btnInsert = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            openActivity_insert();
        };

    };
    private void openActivity_insert()
    {
        Intent intent = new Intent(this, InsertPage.class);
        startActivity(intent);
    }


    Button.OnClickListener btnOnClick_btnViewAll = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            openActivity_ViewAll();
        };

    };
    private void openActivity_ViewAll()
    {
        Intent intent = new Intent(this, ViewPage.class);
        startActivity(intent);
    }


}
