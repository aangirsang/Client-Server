/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.launcher;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author GIRSANG PC
 */
public class ServerLauncher {
    public static Logger log = Logger.getLogger(ServerLauncher.class.getName());
    public static void main(String[] args) {
        try{
            AbstractApplicationContext ctx =
                    new ClassPathXmlApplicationContext(
                            new String[]{"applicationContext.xml","serverContext.xml"});
            ctx.registerShutdownHook();
            log.info("SERVER ONLINE");    
        }catch(BeanCreationException bce){
            JOptionPane.showMessageDialog(null, "Server Sudah Aktif");
            bce.printStackTrace();
        }
        
    }
}
