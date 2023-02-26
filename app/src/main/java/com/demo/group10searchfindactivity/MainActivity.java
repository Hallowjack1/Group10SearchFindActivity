package com.demo.group10searchfindactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText Input;
    Button Save,Read, Clear;

    private String filename = "SampleFile.txt";
    File myExternalFile;
    String myData = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Input = findViewById(R.id.etInput);
        Save = findViewById(R.id.btnSave);
        Read = findViewById(R.id.btnRead);
        Clear = findViewById(R.id.btnClear);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(Input.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Input.setText("");
            }
        });

        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = Input.getText().toString();
                try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                        if (strLine.contains(search)) {
                            Toast.makeText(getApplicationContext(), "Search word is found", Toast.LENGTH_LONG).show();
                            int position = strLine.indexOf(search);
                            Input.setText("Search word '" + search + "' found at position " + position);
                            return;
                        }
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Search word not found", Toast.LENGTH_LONG).show();
                Input.setText("");
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input.setText("");
            }
        });

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Save.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filename), filename);
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}