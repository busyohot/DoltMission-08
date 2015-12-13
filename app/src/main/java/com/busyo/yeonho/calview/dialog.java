package com.busyo.yeonho.calview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class dialog extends ActionBarActivity {

    public static String dlsc;
    public static String dlhh;
    public static String dlmm;
    String year ;
    String month;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        receiveintent();
         final EditText edsc = (EditText)findViewById(R.id.editsc);
         final EditText edhh = (EditText)findViewById(R.id.edithh);
         final EditText edmm = (EditText)findViewById(R.id.editmm);
        final TextView toptv = (TextView)findViewById(R.id.toptv);
         Button btsave = (Button)findViewById(R.id.btsave);
         Button btclose = (Button)findViewById(R.id.btclose);
         toptv.setText(year+"년 "+month+"월 " +date+"일");
        final RadioGroup rg = (RadioGroup)findViewById(R.id.rg);
        final RadioButton rbam = (RadioButton)findViewById(R.id.rbam);
        final RadioButton rbpm = (RadioButton)findViewById(R.id.rbpm);


        dlsc= String.valueOf(edsc.getText());
        dlhh= String.valueOf(edhh.getText());
        dlmm= String.valueOf(edmm.getText());
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                Intent dialintent = new Intent();//(getBaseContext(),menu.class);
                dialintent.putExtra("inputsc", String.valueOf(edsc.getText()));
                dialintent.putExtra("inputhh", String.valueOf(edhh.getText()));
                dialintent.putExtra("inputmm", String.valueOf(edmm.getText()));
                dialintent.putExtra("inputrb", rb.getText().toString());
                if(String.valueOf(edsc.getText()).length()==0)
                {
                    Toast.makeText(getApplicationContext(),"일정을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else if(String.valueOf(edhh.getText()).length()==0)
                {
                    Toast.makeText(getApplicationContext(),"'시'를 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(edhh.getText().toString())>12)
                {
                    Toast.makeText(getApplicationContext(),"입력한 '시'를 확인하세요.",Toast.LENGTH_SHORT).show();
                }

                else if(String.valueOf(edmm.getText()).length()==0)
                {
                    Toast.makeText(getApplicationContext(),"'분'을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(edmm.getText().toString())>60)
                {
                    Toast.makeText(getApplicationContext(),"입력한 '분'을 확인하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    setResult(RESULT_OK, dialintent);

                    finish();
                }


            }
        });
        btclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //넘겨받은 인텐트 값
    public void receiveintent()
    {
        Intent receiveintent = getIntent();
        year  = receiveintent.getStringExtra("year");
        month = receiveintent.getStringExtra("month");
        date  = receiveintent.getStringExtra("date");



    }
}
