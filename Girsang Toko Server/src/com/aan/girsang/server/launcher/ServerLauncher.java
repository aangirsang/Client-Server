/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.launcher;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author GIRSANG PC
 */
public class ServerLauncher {
    public static void main(String[] args) {
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext(
                        new String[]{"applicationContext.xml","serverContext.xml"});
        
        ctx.registerShutdownHook();
        
        System.out.println("Server Berjalan");
    }
}
