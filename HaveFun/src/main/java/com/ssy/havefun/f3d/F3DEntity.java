/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefun.f3d;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author jsun
 */
@Entity
@Table(name = "f_3d")
public class F3DEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "first", unique = false, nullable = false)
    private Integer first;
    @Column(name = "second", unique = false, nullable = false)
    private Integer second;
    @Column(name = "third", unique = false, nullable = false)
    private Integer third;
    @Column(name = "createdate", unique = false, nullable = false)
    private String date;
    @Column(name = "sid", unique = false, nullable = false)
    private Integer sId;
    @Column(name = "sale", unique = false, nullable = true)
    private Integer sale;
    @Column(name = "direct", unique = false, nullable = true)
    private Integer direct;
    @Column(name = "thirdcombination", unique = false, nullable = true)
    private Integer thirdCombination;
    @Column(name = "sixcombination", unique = false, nullable = true)
    private Integer sixCombination;

    public Integer getFirst() {
        return first;
    }

    public Integer getSecond() {
        return second;
    }

    public Integer getThird() {
        return third;
    }

    public String getDate() {
        return date;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getThirdCombination() {
        return thirdCombination;
    }

    public void setThirdCombination(Integer thirdCombination) {
        this.thirdCombination = thirdCombination;
    }

    public Integer getSixCombination() {
        return sixCombination;
    }

    public void setSixCombination(Integer sixCombination) {
        this.sixCombination = sixCombination;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    

    public Integer getsId() {
        return sId;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public void setThird(Integer third) {
        this.third = third;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setsId(Integer sId) {
        this.sId = sId;
    }
    
    @Override
    public String toString(){
        String s = this.sId+" "+this.first+","+this.second+","+this.third+" "+this.date;
        return s;
    }
}
