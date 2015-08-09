package com.example.luong.asynctaskterminatedapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText et;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsync();
            }
        });
        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv.setText(s.toString());
            }
        });

        Log.e("", "Activity has been created " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("", "Activity has been destroyed");
    }

    private void runAsync() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Log.e("", "Begin do in background " + Thread.interrupted() + ", " + Thread.currentThread().isInterrupted());
                for (int i = 0; i < 3; i++) {
                    Log.e("", "Interrupted: " +  Thread.currentThread().isInterrupted());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("", "End do in background " + Thread.interrupted() + ", " + Thread.currentThread().isInterrupted());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.e("", "post execute");
                btn.setText("Text is set onPostExecute");
                Toast.makeText(MainActivity.this, "Text is set onPostExecute", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.e("", "on cancel");
                btn.setText("Text is set onCancelled");
                Log.e("", "Showing toast...");
                Toast.makeText(MainActivity.this, "Toast shown", Toast.LENGTH_SHORT).show();
                Log.e("", "Showing dialog...");
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("please wait.....");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        };
        task.execute();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.cancel(true);
    }


}
