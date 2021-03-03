package com.example.severtest;

import android.app.Activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {

    private Integer store_code = 1111111111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 클릭 대기 : 시작
        Button btn = (Button) findViewById(R.id.button_call);
        btn.setOnClickListener(this);
        //버튼 클릭 대기 : 끝

    }

    @Override
    public void onClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String host_URL = "http://15.164.142.204:3000/users";

        EditText et_webpage_src = (EditText)findViewById(R.id.webpage_src);


        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(host_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

//                int resCode = conn.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                String page = "";

                while (true) {
                    line = reader.readLine();
                    page+=line;
                    if (line == null) {
                        break;
                    }
//                    Log.d("line",line);

                    output.append(line);
                }
//                JSONObject json = new JSONObject(page);

                JSONArray jArr = new JSONArray(page);


                Log.d("line2",jArr.toString());
                for (int i=0; i<jArr.length(); i++){

                    //i번째 배열 할당
                    JSONObject json = jArr.getJSONObject(i);

                    //ksNo,korName의 값을 추출함
                    String id = json.getString("id");
                    Integer pass = json.getInt("pass");
                    String name = json.getString("name");
                    //ksNo,korName의 값을 출력함
                    et_webpage_src.append("id :"+ id+"\n");
                    et_webpage_src.append("pass :"+pass+"\n");
                    et_webpage_src.append("id :"+ name+"\n");

                }

//
//                String id = json.getString("id");
//                Integer pass =json.getInt("pass");
//                String name = json.getString("name");
//
//                Log.d("json",id);
//                Log.d("json",name);
//                et_webpage_src.append("id :"+ id+"\n");
//                et_webpage_src.append("pass :"+pass+"\n");
//                et_webpage_src.append("id :"+ name+"\n");

                reader.close();

                conn.disconnect();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
