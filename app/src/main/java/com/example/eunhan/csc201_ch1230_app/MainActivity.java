package com.example.eunhan.csc201_ch1230_app;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText input;

    private ArrayList<String> resultlist = new ArrayList<String>();
    ListView resultlistview;
    ArrayAdapter<String> Adapter;

    Button resultbtn;

    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath() + "/createdfiles";
    String filename = "Lincoln.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultlistview = (ListView) findViewById(R.id.result);
        input = (EditText) findViewById(R.id.input);
        resultbtn = (Button) findViewById(R.id.resultbtn);
        resultbtn.setOnClickListener(this);



    }

    private void intoresultview(String s) //print lists into chatroom tab
    {
        resultlist.add(s);
        Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultlist);
        resultlistview.setAdapter(Adapter);
    }


    public static Map<Character, Counter> count(String input) throws FileNotFoundException {
        Map<Character, Counter> map = new HashMap<>();

        for (int i = 65; i < 91; i++)
            map.put((char) i, new Counter());



                String str="";
                str = input.toUpperCase();
                char[] arr = str.toCharArray();
                for (char ch : arr) {
                    Counter cnt = map.get(ch);
                    if (cnt == null)
                        continue;
                    cnt.increase();
                }

        return map;

    }

    @Override
    public void onClick(View v) {
        filename = input.getText().toString();
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/createdfiles/" + filename;


        WriteTextFile(foldername, filename, "(Occurrences of each letter) Write a program that prompts the user to enter a file name and displays the occurrences of each letter in the file. Letters are case-insensitive. Here is a sample run:");

        String read = ReadTextFile(filepath);
                Map temp = null;
        try {
            temp = count(read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 65; i < 91; i++)
            intoresultview("Numbers of " + (char) i + "\'s: " + temp.get((char) i));


        File file = new File(filepath);
        file.delete();
    }


    public void WriteTextFile(String foldername, String filename, String contents) {
        try {
            File dir = new File(foldername);
            //디렉토리 폴더가 없으면 생성함
            if (!dir.exists()) {
                dir.mkdir();
            }
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername + "/" + filename, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents);
            writer.flush();

            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ReadTextFile(String path) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                strBuffer.append(line + "\n");
            }

            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
    }





}


class Counter {
    int count;

    public Counter() {
        this.count = 0;
    }

    public void increase() {
        count++;
    }

    public String toString() {
        return "" + count;
    }

    //numbers of A is counted 1
}

