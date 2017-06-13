package com.example.dam.gestordejuegos.cliente;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Vir on 06/06/2017.
 */

public class Conexion {


   /* ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      //  ...
    }*/

    //https://developer.android.com/training/basics/network-ops/connecting.html
    public String downloadUrl(String strUrl, String json) {
        System.out.println("xxx...");
        InputStream is = null;
        String r = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.connect();
            BufferedWriter out =  new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            out.write(json);
            out.flush();
            out.close();
            is = conn.getInputStream();
            r = readInputStream(is);
        } catch (UnsupportedEncodingException e) {
        } catch (ProtocolException e) {
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        if(r==null){r="patata";}

        return r;
    }

    public String readInputStream(InputStream stream) throws IOException {
        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        Reader reader = new InputStreamReader(stream, "UTF-8");
        final StringBuilder sb = new StringBuilder();
        for (;;) {
            int bytes = reader.read(buffer, 0, buffer.length);
            if (bytes < 0) {
                break;
            }
            sb.append(buffer, 0, bytes);
        }
        System.out.println("xxx..." + sb.toString());
        return sb.toString();
    }


}
