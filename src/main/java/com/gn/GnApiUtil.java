package com.gn;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GnApiUtil {


    // Performs a HTTP POST request & returns the response as a string.
    public static String _httpPostRequest(String url, String data) throws IOException {
        URL URL = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) URL.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept", "application/xml");
        httpURLConnection.setRequestProperty("Content-Type", "application/xml");

        OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
        writer.write(data);
        writer.close();

    /* return response */
        String text = "";
        try {
            text = _readOutput(httpURLConnection.getInputStream());
        } catch (Exception ignored) {
        }
        return text;
    }

    private static String _readOutput(InputStream in) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        reader.close();
        return output.toString();
    }


}
