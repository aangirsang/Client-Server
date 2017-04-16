/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.launcher;

import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.api.service.ConstantService;
import com.aan.girsang.api.service.MasterService;
import com.aan.girsang.api.service.SecurityService;
import com.aan.girsang.api.service.TransaksiService;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ITSUSAHBRO
 */
public class ClientLauncher {
    public static Logger log = Logger.getLogger(ClientLauncher.class.getName());
    private static ConstantService constantService;
    private static MasterService masterService;
    private static TransaksiService transaksiService;
    private static SecurityService securityService;
    private static Pengguna penggunaAktif;
    private static Date tanggalServer;

    public static ConstantService getConstantService() {
        return constantService;
    }

    public static MasterService getMasterService() {
        return masterService;
    }

    public static TransaksiService getTransaksiService() {
        return transaksiService;
    }

    public static SecurityService getSecurityService() {
        return securityService;
    }

    public static void setPenggunaAktif(Pengguna penggunaAktif) {
        ClientLauncher.penggunaAktif = penggunaAktif;
    }

    public static Pengguna getPenggunaAktif() {
        return penggunaAktif;
    }
    
    public static void main(String[] args) throws Exception {
        
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", Boolean.FALSE);
            
        AbstractApplicationContext ctx = 
                new ClassPathXmlApplicationContext("clientContext.xml");
        ctx.registerShutdownHook();

        constantService = (ConstantService) ctx.getBean("constantServiceRemote");
        securityService = (SecurityService) ctx.getBean("securityServiceRemote");
        masterService = (MasterService) ctx.getBean("masterServiceRemote");
        transaksiService = (TransaksiService) ctx.getBean("transaksiServiceRemote");
        
        log.info("Client Online");
        constantService.clientOnline("CLIENT");
        java.awt.EventQueue.invokeLater(() -> {
            FrameUtama fu = new FrameUtama();
            fu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fu.setVisible(true);
            fu.jam();
        });
    }
    
    
}
