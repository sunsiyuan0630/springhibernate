/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefunweb.resources;

import com.ssy.havefun.f3d.F3DService;
import com.ssy.havefunweb.model.AccessToken;
import com.ssy.havefunweb.model.AttentionMessage;
import com.ssy.havefunweb.model.Button;
import com.ssy.havefunweb.model.ClickButton;
import com.ssy.havefunweb.model.Menu;
import com.ssy.havefunweb.model.TextMessage;
import com.ssy.havefunweb.model.ViewButton;
import com.ssy.havefunweb.util.CheckUtil;
import com.ssy.havefunweb.util.MessageUtil;
import com.ssy.havefunweb.util.WeixinUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author jsun
 */
@Controller
@RequestMapping("/weixinverify")
public class ResWeixinVerify {
    
    private final static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    
    
    @Autowired
    private F3DService f3DService;
    private final static Set<String> specialText = new HashSet<>();
    static{
        specialText.add("1");
    }
    
    @RequestMapping(value="/verify", method= RequestMethod.GET)
    public void verify(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echost = request.getParameter("echostr");
        
        PrintWriter out = response.getWriter();
        if(CheckUtil.checkSignature(signature, timestamp, nonce)){
            out.print(echost);
        }
    }
    
    @RequestMapping(value="/verify", method= RequestMethod.POST)
    public void handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        try {
            
            Map<String,String> map = MessageUtil.xmlToMap(request);
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");
            
            String message = null;
            if("text".equals(MsgType)){
                message = handleText(map);                
                System.out.println(message);
            }
            else if ("event".equals(MsgType)){
                message = handleEvent(map);
            }
            out.print(message);
        } catch (DocumentException ex) {
            Logger.getLogger(ResWeixinVerify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            out.close();
        }
    }
    
    public String handleText(Map<String,String> map) throws IOException{
        String content = map.get("Content");
        TextMessage text = new TextMessage();
        text.setFromUserName(map.get("ToUserName"));
        text.setToUserName(map.get("FromUserName"));
        text.setMsgType("text");
        text.setCreateTime(String.valueOf(new Date().getTime()));
        if (specialText.contains(content)){
            if(content.equals("1")){
                Map<Integer,Integer> freqMap = f3DService.findFreqOfEveryNumber();
                StringBuilder str = new StringBuilder();
                for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {   
                    str.append("号码："+entry.getKey()+",出现次数:"+entry.getValue()+"\n");
                }
                text.setContent(str.toString());
            }
        }else if(content.equals("2")){
            WeixinUtil w = new WeixinUtil();
            w.getAccessToken();
//            WeixinUtil.getAccessToken();
        }else{
            text.setContent("你说："+content+".\n但是我认为佟鼎是小屌丝！");
        }
        return MessageUtil.textMessageToXml(text);
    }
    
    public String handleEvent(Map<String,String> map){
        String Event = map.get("Event");
        TextMessage text = new TextMessage();
        text.setFromUserName(map.get("ToUserName"));
        text.setToUserName(map.get("FromUserName"));
        text.setMsgType("text");
        text.setCreateTime(String.valueOf(new Date().getTime()));
        if (Event.equals("subscribe")){                      
            text.setContent("感谢您的关注!\n公众号还在开发中,敬请期待!");
        }else if(Event.equals("unsubscribe")){
            text.setContent("我们还会更加努力!");
        }
        return MessageUtil.textMessageToXml(text);
    }
    
    public String handleMenu(){
        Menu menu = new Menu();
        ClickButton button1 = new ClickButton();
        button1.setName("click菜单");
        button1.setType("click");
        button1.setKey("111");
        
        ViewButton button2 = new ViewButton();
        button2.setName("view菜单");
        button2.setType("view");
        button2.setUrl("http://www.baidu.com");
        
        ClickButton button3 = new ClickButton();
        button1.setName("扫码事件");
        button1.setType("scancode_push");
        button1.setKey("112");
        
        ClickButton button4 = new ClickButton();
        button1.setName("地理位置");
        button1.setType("location_select");
        button1.setKey("113");
        
        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button3,button4});
        
        menu.setButton(new Button[]{button1,button2,button});
        return null;
    }
    private int createMenu(String token,String menu){
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        return 0;
    }
}
