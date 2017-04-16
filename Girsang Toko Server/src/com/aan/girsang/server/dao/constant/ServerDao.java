/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.constant;

import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ITSUSAHBRO
 */
@Repository
public class ServerDao {
    public Date tanggalServer(){
        Date date = new Date();
        Thread t = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
        return date;
    }
}
