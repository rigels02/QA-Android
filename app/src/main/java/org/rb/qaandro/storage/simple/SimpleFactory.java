package org.rb.qaandro.storage.simple;

import org.rb.mm.interfaceimpl.SimpleXmlParser;

import org.rb.qa.storage.AbstractStorageFactory;

import java.util.Date;
import java.util.List;

public class SimpleFactory extends AbstractStorageFactory<KNBase> {

    public SimpleFactory() {
        super(new SimpleXmlParser<>(KNBase.class));
    }

    @Override
    protected KNBase knBaseToTypeT(org.rb.qa.model.KNBase knBase) {
        KNBase knBaseS = new KNBase();
        List<QA> tlst = knBaseS.getQaList();
        for (org.rb.qa.model.QA qa : knBase.getQaList()) {
            QA nqa = new QA(qa.getQuestion(), qa.getAnswer());
            tlst.add(nqa);
        }
        knBaseS.setModifyTime(new Date());
        return knBaseS;
    }

    @Override
    protected org.rb.qa.model.KNBase typeTtoKnBase(KNBase knBaseS) {
        org.rb.qa.model.KNBase knb = new org.rb.qa.model.KNBase();
        this.dataModifyDate = knBaseS.getModifyTime();
        List<org.rb.qa.model.QA>  tlst = knb.getQaList();
        for (QA qa : knBaseS.getQaList()) {
            org.rb.qa.model.QA nqa= new org.rb.qa.model.QA(qa.getQuestion(), qa.getAnswer());
            tlst.add(nqa);
        }
        return knb;
    }
}
