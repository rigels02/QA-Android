package org.rb.qaandro;

import org.junit.AfterClass;
import org.junit.Test;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;
import org.rb.qaandro.storage.simple.SimpleFactory;


import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SimpleFactoryUT {
    private static final String FILEPATH = "testXml.xml";


    @AfterClass
    public static void downClass(){
        File fi;
        if((fi =new File(FILEPATH)).exists()){
            fi.delete();
        }

    }
    @Test
    public void testWriteRead() throws Exception {
        System.out.println("testWriteRead");
        KNBase knBase = new KNBase();
        for(int i=1;i<= 5; i++){
            QA nqa = new QA("Question 1Ā_"+i, "Answer 1ņ"+i);
            knBase.getQaList().add(nqa);
        }
        SimpleFactory factory = new SimpleFactory();
        //WHEN
        factory.serialize(FILEPATH,knBase);
        //-----------Read -----------//
        KNBase knBase1 = factory.deSerialize(FILEPATH);
        for (QA qa: knBase1.getQaList() ) {
            System.out.println("QA-> "+qa.toString());
        }
        //THEN
        assertEquals(knBase1.toString(),knBase.toString());
    }
}