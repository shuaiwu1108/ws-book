package com.shuaiwu.wsbook.utils;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import sun.net.www.protocol.http.HttpURLConnection;

@Slf4j
public class HttpUtil {

    private final static int CONN_TIME_OUT = 5000;
    private final static int READ_TIME_OUT = 10000;

    public static String get(String baseUrl, String method, Map<String, ?> params) {
        return get(baseUrl, method, params, CONN_TIME_OUT, READ_TIME_OUT);
    }

    public static String get(String baseUrl, String method, Map<String, ?> params, int connTimeout,
        int readTimeout) {
        try {
            String pp = URLUtil.buildQuery(params, Charset.forName("utf-8"));
            URL url = new URL(baseUrl.concat(method).concat("?").concat(pp));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName("utf-8")));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                bufferedReader.close();
                return stringBuffer.toString();
            }
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String post(String baseUrl, String method, Map<String, ?>  params) {
        return post(baseUrl, method, params, CONN_TIME_OUT, READ_TIME_OUT);
    }

    public static String post(String baseUrl, String method, Map<String, ?> params, int connTimeout,
        int readTimeout) {
        try {
            URL url = new URL(baseUrl.concat(method));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            if (!params.isEmpty()) {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);
                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] bytes = JSONUtil.toJsonStr(params).getBytes("utf-8");
                    os.write(bytes, 0, bytes.length);
                }
            }
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName("utf-8")));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                bufferedReader.close();
                return stringBuffer.toString();
            }
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> pp = new HashMap<>();
        pp.put("a", "aval");
        pp.put("b", "中文");


        String res = get("http://baidu.com", "/", null);
//        String result = post("http://127.0.0.1:8080", "/test", pp);

        log.info(res);
//        log.info(result);
    }
}
