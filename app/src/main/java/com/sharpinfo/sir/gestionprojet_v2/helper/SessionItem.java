/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharpinfo.sir.gestionprojet_v2.helper;

/**
 *
 * @author moulaYounes
 */
public class SessionItem {
    private String key;
    private Object object;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "SessionItem{" + "key=" + key + ", object=" + object + '}';
    }
    
    
}
