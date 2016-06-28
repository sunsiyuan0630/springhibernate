/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefun.f3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jsun
 */
@Service("f3DService")
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class F3DServiceImpl implements F3DService{
    @Autowired
    private F3DDao f3DDao;
    @Override
    public Map<Integer, Integer> findFreqOfEveryNumber() {
        List<F3DEntity> all = f3DDao.findAll();
        Map<Integer,Integer> map = new HashMap<>();
        for(F3DEntity f3d:all){
            if(map.containsKey(f3d.getFirst())){
                Integer v = map.get(f3d.getFirst());
                v++;
                map.replace(f3d.getFirst(), v);
            }
            else{
                map.put(f3d.getFirst(), 0);
            }
            if(map.containsKey(f3d.getSecond())){
                Integer v = map.get(f3d.getSecond());
                v++;
                map.replace(f3d.getSecond(), v);
            }
            else{
                map.put(f3d.getSecond(), 0);
            }
            if(map.containsKey(f3d.getThird())){
                Integer v = map.get(f3d.getThird());
                v++;
                map.replace(f3d.getThird(), v);
            }
            else{
                map.put(f3d.getThird(), 0);
            }
        }
        return map;
    }

    @Override
    public Map<String, Integer> findFreqOfEveryStage() {
        List<F3DEntity> all = f3DDao.findAll();
        Map<String,Integer> map = new HashMap<>();
        for (F3DEntity f3d:all){
            String key = f3d.getFirst()+","+f3d.getSecond()+","+f3d.getThird();
            if(map.containsKey(key)){
                Integer v = map.get(f3d.getThird());
                v++;
                map.replace(key, v);
            }else{
                map.put(key, 0);
            }
        }
        return map;
    }

    @Override
    public Map<String, Integer> findFreqOfEveryStageIgnorOrder() {
        List<F3DEntity> all = f3DDao.findAll();
        Map<String,Integer> map = new HashMap<>();
        for (F3DEntity f3d:all){
            String [] arr = {String.valueOf(f3d.getFirst()),String.valueOf(f3d.getSecond()),String.valueOf(f3d.getThird())};
            List<String> list = getThreeNumberCombination(arr);
            boolean contains = false;
            for (String str:list){
                if(map.containsKey(str)){
                    Integer v = map.get(str);
                    v++;
                    map.replace(str, v);
                    contains = true;
                    break;
                }
            }
            if (!contains){
                String key = f3d.getFirst()+","+f3d.getSecond()+","+f3d.getThird();
                map.put(key, 1);
            }
        }
        return map;
    }
    public List<String> getThreeNumberCombination(String[] arr){
        List<String> list = new ArrayList<>();
        list.add(arr[0]+","+arr[1]+","+arr[2]);
        list.add(arr[0]+","+arr[2]+","+arr[1]);
        list.add(arr[1]+","+arr[2]+","+arr[0]);
        list.add(arr[1]+","+arr[0]+","+arr[2]);
        list.add(arr[2]+","+arr[1]+","+arr[0]);
        list.add(arr[2]+","+arr[0]+","+arr[1]);
        Map<String,Integer> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            if (map.containsKey(list.get(i))){
                list.remove(i);
            }else{
                map.put(list.get(i), 1);
            }
        }
        return list;
    }
    
    public List<String> getAllConbination(){
        String[] arr = {"0","1","2","3","4","5","6","7","8","9"};
        List<String> list = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            for(int j=i+1;j<10;j++){
//                for(int k=j+1;k<10;k++){
//                    list.add(arr[i]+","+arr[j]+","+arr[k]);
//                }
//            }
//        }
//        for(int i=0;i<10;i++)
//            list.add(arr[i]+","+arr[i]+","+arr[i]);
//        
//        for(int i=0;i<10;i++){
//            for(int j=i+1;j<10;j++){
//                list.add(arr[i]+","+arr[j]+","+arr[j]);
//            }
//        }
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                for(int k=0;k<10;k++){
                    list.add(arr[i]+","+arr[j]+","+arr[k]);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> findConbinationHasNotAppeared(List<String> all,Map<String,Integer> appear) {
        List<String> list = findConbinationHasNotAppearedIgnorOrder(all);
        List<String> returnList = new ArrayList<>();
        int size = list.size();
        for(int i=0;i<size;i++){
            List<String> allConbination = getThreeNumberCombination(list.get(i).split(","));
            boolean contains = false;
            for (String a:allConbination){
                if (appear.containsKey(a)){
                    contains = true;
                    break;
                }
            }
            if (contains){
                list.set(i, null);
            }
        }
        for(String s:list){
            if(s!=null){
                returnList.add(s);
            }
        }
        return returnList;
    }
    @Override
    public List<String> findConbinationHasNotAppearedIgnorOrder(List<String> conbinationHasNotAppeared){
        List<String> cache = conbinationHasNotAppeared;
//        Set<String> set = new HashSet<>(conbinationHasNotAppeared);
//        Iterator<String> iterator = set.iterator();
//        while(iterator.hasNext()){
//            String s = iterator.next();
//            List<String> threeNumberCombination = getThreeNumberCombination(s.split(","));
//            for(int i=0;i<threeNumberCombination.size();i++){
//                iterator.remove();
//            }
//        }
//        for(String s:set){
//            List<String> threeNumberCombination = getThreeNumberCombination(s.split(","));
//        }
        for(int i=0;i<conbinationHasNotAppeared.size();i++){
            List<String> threeNumberCombination = getThreeNumberCombination(conbinationHasNotAppeared.get(i).split(","));
            for(int j=0;j<threeNumberCombination.size();j++){
                for(int k=i+1;k<conbinationHasNotAppeared.size();k++){
                    if(threeNumberCombination.get(j).equals(conbinationHasNotAppeared.get(k))){
                        cache.remove(k);
                    }
                }
            }
        }
        return cache;
    }
}
