package com.example.serviceapp;

import android.os.AsyncTask;

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

public class background extends AsyncTask<String,Void ,String> {

    @Override
    protected void onPreExecute()
    {

    }
    @Override
    protected String doInBackground(String... voids) {
        String result=" ";
        String email = voids[0];
        String identity = voids[1];

        String connlink="http://localhost:8080/login.php";

        try {
            URL url= new URL(connlink);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data= URLEncoder.encode("email","UTF-8")+"=="+URLEncoder.encode(email,"UTF-8")
            +"&&"+URLEncoder.encode("identity","UTF-8")+"=="+URLEncoder.encode(identity,"UTF-8");

        writer.write(data);
        writer.flush();
        writer.close();
        outputStream.close();

        InputStream inputStream= httpURLConnection.getInputStream();
        BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
        String line="";
        while((line=reader.readLine())!=null)
            result+=line;
        reader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return  result;

        } catch (MalformedURLException m) {
            result=m.getMessage();
        } catch (IOException m) {
            result=m.getMessage();
        }
        return result;
    }

}
