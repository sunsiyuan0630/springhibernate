/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefun.f3d;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jsun
 */
public interface F3DService {
    public List<String> getAllConbination();
    
    public Map<Integer,Integer> findFreqOfEveryNumber();
    
    public Map<String,Integer> findFreqOfEveryStage();
    
    public Map<String,Integer> findFreqOfEveryStageIgnorOrder();
    
    public List<String> findConbinationHasNotAppeared(List<String> all,Map<String,Integer> appear);
    
    public List<String> findConbinationHasNotAppearedIgnorOrder(List<String> conbinationHasNotAppeared);
}
