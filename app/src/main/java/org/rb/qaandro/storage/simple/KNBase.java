package org.rb.qaandro.storage.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple Xml specific Data model.
 * Used by SimpleXml library
 * @author raitis
 */
public class KNBase {

    private Date modifyTime;

    private List<QA> qaList;

    public KNBase() {
        qaList= new ArrayList<>();
    }

    
    public List<QA> getQaList() {
        return qaList;
    }
    

    public void setQaList(List<QA> qaList) {
        this.qaList = qaList;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }



    @Override
    public String toString() {
        return "KNBase{" + "qaList=" + qaList + '}';
    }

    
    
}
