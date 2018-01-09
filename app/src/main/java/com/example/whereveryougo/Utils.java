package com.example.whereveryougo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    public static String convertStreamToString(InputStream is) {
        BufferedReader br= new BufferedReader(new InputStreamReader(is),10*1024);
        StringBuilder sb=new StringBuilder();
        String line=null;
        try {
            while((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
