package com.example.yuzelli.bluetoolsvehiclemonitoring.bean;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/8/1.
 */

public class ToothInfoBean implements Serializable {
    private double jaquan;
    private double ben;
    private double co2;
    private double co;
    private double so2;
    private double no;

    public ToothInfoBean() {
    }

    public ToothInfoBean(double jaquan, double ben, double co2, double co, double so2, double no) {
        this.jaquan = jaquan;
        this.ben = ben;
        this.co2 = co2;
        this.co = co;
        this.so2 = so2;
        this.no = no;
    }

    public double getJaquan() {
        return jaquan;
    }

    public void setJaquan(double jaquan) {
        this.jaquan = jaquan;
    }

    public double getBen() {
        return ben;
    }

    public void setBen(double ben) {
        this.ben = ben;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }
}
