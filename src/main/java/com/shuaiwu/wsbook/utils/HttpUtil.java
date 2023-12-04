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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

@Slf4j
public class HttpUtil {

    private final static int CONN_TIME_OUT = 5000;
    private final static int READ_TIME_OUT = 1000 * 30;

    public static String get(String baseUrl, String method, Map<String, ?> params, String charset) {
        return get(baseUrl, method, params, charset, CONN_TIME_OUT, READ_TIME_OUT);
    }

    public static String get(String baseUrl, String method, Map<String, ?> params, String charset, int connTimeout,
        int readTimeout) {
        try {
            String u = baseUrl.concat(method);
            if (params != null && !params.isEmpty()){
                String pp = URLUtil.buildQuery(params, Charset.forName(charset));
                u = u.concat("?").concat(pp);
            }
            URL url = new URL(u);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.35.0");
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName(charset)));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                bufferedReader.close();
                return stringBuffer.toString();
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
        return null;
    }

    public static String post(String baseUrl, String method, Map<String, ?>  params, String charset) {
        return post(baseUrl, method, params, charset, CONN_TIME_OUT, READ_TIME_OUT);
    }

    public static String post(String baseUrl, String method, Map<String, ?> params, String charset, int connTimeout,
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
                    byte[] bytes = JSONUtil.toJsonStr(params).getBytes(charset);
                    os.write(bytes, 0, bytes.length);
                }
            }
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName(charset)));
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
//        Map<String, Object> pp = new HashMap<>();
//        pp.put("a", "aval");
//        pp.put("b", "中文");


        String res = get("https://www.xbiquge.bz", "/", null, "GBK");
//        String result = post("http://127.0.0.1:8080", "/test", pp);

        log.info(res);
//        log.info(result);
    }
}
