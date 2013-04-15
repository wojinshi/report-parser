package com.me.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

/**
 * @author julia.lin
 * @summary
 */
public class PostJSON {
    public static void main(String[] args) throws ParseException, JSONException {
//        useHttpClient();
        useURLConnection();
    }

    private static void useHttpClient() throws JSONException {
        HttpClient httpClient = new HttpClient();
        String url = "http://localhost:8080/jss-core-5.12.0-DL-SNAPSHOT/action?FIE=" + (new Date()).getTime();
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] data = {
                new NameValuePair("module", "system"),
                new NameValuePair("method", "listConfig"),
        };
        //   将表单的值放入postMethod中
        postMethod.setRequestBody(data);
        //   执行postMethod
        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod(postMethod);
            String str = postMethod.getResponseBodyAsString();
            JSONObject config = new JSONObject(str);
            System.out.println(config.toString());
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void useURLConnection(){
        try {
            URL url = new URL(
                    "http://localhost:8080/jss-core-5.12.0-DL-SNAPSHOT/action?FIE=" + (new Date()).getTime());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setRequestProperty("Connection", "keep-alive");
            OutputStream out = con.getOutputStream();
            // requestStr is difference with useHTTPClient.
            String request = "json=%7B%22module%22%3A%22system%22%2C%22method%22%3A%22listConfig%22%7D";
            out.write(request.getBytes());
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
