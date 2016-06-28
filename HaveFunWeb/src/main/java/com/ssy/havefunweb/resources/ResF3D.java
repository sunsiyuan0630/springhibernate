/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefunweb.resources;

import com.ssy.havefun.f3d.F3DEntity;
import com.ssy.havefun.f3d.F3DService;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * @author jsun
 */
@Controller
@RequestMapping("/f3d")
public class ResF3D {
    private static final Logger LOGGER = Logger.getLogger(ResF3D.class);
    
    @Autowired
    private F3DService f3DService;
    
    @RequestMapping("/findFreqForNumbers")
    public @ResponseBody String findFreqOfEveryNumber(ModelMap modelMap){
        LOGGER.info("findFreqOfEveryNumber start");
//        List<F3DEntity> list = f3DDao.findAllInRange("20160101", "20161231");
        Map<Integer,Integer> map = f3DService.findFreqOfEveryNumber();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());    
        }
        return map.toString();
    }
}
