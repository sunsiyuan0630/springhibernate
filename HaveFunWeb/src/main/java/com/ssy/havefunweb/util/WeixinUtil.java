/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefunweb.util;

import com.ssy.havefunweb.model.AccessToken;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author jsun
 */
public class WeixinUtil {
    private final static String APPID = "wx51668046e8eab504";
    private final static String APPSECRET = "5bc657f7315a58f678e90da3ecf827aa";
    private final static String TOKENPROPERTIES = "token.properties";
    private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public static JSONObject doGetStr(String url) throws IOException{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(entity!=null){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        return jsonObject;
    }
    
    public static JSONObject doPostStr(String url,String outStr) throws UnsupportedEncodingException, IOException{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }
    
    public  AccessToken getAccessToken() throws IOException{
        AccessToken token = readProperties();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        if(token.getExpires_in() == -1){
            token = regetAccessToken(url);
            wirteProperties(token);
        }else if((new Date().getTime()/1000)-token.getUpdateTime()/1000>token.getExpires_in()){
            token = regetAccessToken(url);
            wirteProperties(token);
        }
//        JSONObject jsonObject = doGetStr(url);
//        if(jsonObject!=null){
//            token.setAccess_token(jsonObject.getString("access_token"));
//            token.setExpires_in(jsonObject.getInt("expires_in"));
//        }
        return token;
    }
    public  void wirteProperties(AccessToken token) throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
//        String filePath = getClass().getResource("/").getPath() + TOKENPROPERTIES;
        String path = this.getClass().getResource("/").getPath();
        path = path.substring(1, path.indexOf("classes"));//从路径字符串中取出工程路径
        path = path + TOKENPROPERTIES;
        OutputStream oFile = new FileOutputStream(path);//true表示追加打开
//        OutputStream out = getClass().getResourceAsStream(TOKENPROPERTIES_PATH);
        prop.setProperty("access_token", token.getAccess_token());
        prop.setProperty("expires_in", String.valueOf(token.getExpires_in()));
        prop.setProperty("updateTime", String.valueOf(token.getUpdateTime()));
        prop.store(oFile, "Update time"+myFmt.format(new Date()));
        oFile.close();
    }
    public static AccessToken regetAccessToken(String url) throws IOException{
        JSONObject jsonObject = doGetStr(url);
        AccessToken token = new AccessToken();
        if(jsonObject!=null){
            token.setAccess_token(jsonObject.getString("access_token"));
            token.setExpires_in(jsonObject.getInt("expires_in"));
            token.setUpdateTime(new Date().getTime());
        }
        return token;
    }
    public  AccessToken readProperties() throws IOException{
        //读取属性文件a.properties
        Properties prop = new Properties();
        AccessToken token = new AccessToken();
        InputStream in = null;
        try  {
            String path = this.getClass().getResource("/").getPath();
            path = path.substring(1, path.indexOf("classes"));//从路径字符串中取出工程路径
            path = path + TOKENPROPERTIES;
//            in = getClass().getResourceAsStream("/WEB-INF/"+TOKENPROPERTIES);
            in = new BufferedInputStream (new FileInputStream(path));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                if(key.equals("access_token")){
                    token.setAccess_token(prop.getProperty(key));
                }
                else if(key.equals("expires_in")){
                    String value = prop.getProperty("expires_in");
                    if(null!=value && !value.equals("")){
                        token.setExpires_in(Integer.parseInt(value));
                    }else{
                        token.setExpires_in(-1);
                    }
                }
                else if(key.equals("updateTime")){
                    String value = prop.getProperty("updateTime");
                    if(null!=value && !value.equals("")){
                        token.setUpdateTime(Long.parseLong(value));
                    }else{
                        token.setUpdateTime(-1L);
                    }
                }
                
                System.out.println(key+":"+prop.getProperty(key));
            }
        }finally{
            in.close();
        }
        return token;
    }
}
