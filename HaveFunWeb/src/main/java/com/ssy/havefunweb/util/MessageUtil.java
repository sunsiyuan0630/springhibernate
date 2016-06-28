/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefunweb.util;

import com.ssy.havefunweb.model.TextMessage;
import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author jsun
 */
public class MessageUtil {
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();
        
        List<Element> list = root.elements();
        
        list.stream().forEach((e) -> {
            map.put(e.getName(), e.getText());
        });
        
        ins.close();
        return map;
    }
    
    public static String textMessageToXml(TextMessage text){
        XStream xstream = new XStream();
        xstream.alias("xml", text.getClass());
        return xstream.toXML(text);
    }
}
