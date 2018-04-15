package org.rb.qaandro;

import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.qa.model.KNBase;
import org.rb.qaandro.resfulclient.KNBaseService;

/**
 * Restful server must be running before test
 */
public class KNBaseServiceIT {


    @Test
    public void test_getRemoteData(){
        System.out.println("************* test_getRemoteData");
        KNBaseService service = new KNBaseService("http://localhost:9998/knbase/", null);
        KNBase knb=null;
        try {
            knb = service.getRemoteData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
        assertTrue(knb!= null);
        assertTrue(knb.getQaList().size()>0);
    }
}
