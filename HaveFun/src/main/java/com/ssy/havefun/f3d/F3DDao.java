/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefun.f3d;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author jsun
 */
public interface F3DDao {
    public List<F3DEntity> doImport()throws IOException;
    
    public List<F3DEntity> findAll();
    
    public List<F3DEntity> findAllInRange(String start,String end);
    
    public List<F3DEntity> findAllInRange(String start);
}
