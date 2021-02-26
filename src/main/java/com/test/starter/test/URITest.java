package com.test.starter.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @author: xq
 * @Date: 2021/01/23 14:13
 */
public class URITest {
    public static void main(String[] args) throws Exception {
        String six = "https://wenku.baidu.com/view/f5112c755a0216fc700abb68a98271fe910eafb1.html";

        URI uri = new URI(six);
        System.out.println(uri);
        // 获取url
        URL url = uri.toURL();
        // 获取连接
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        // 获取io
        InputStream in;
        BufferedReader br = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));

        // 获取数据
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line=br.readLine())!=null) {
            sb.append(line);
        }

        System.out.println(sb);
    }
}
