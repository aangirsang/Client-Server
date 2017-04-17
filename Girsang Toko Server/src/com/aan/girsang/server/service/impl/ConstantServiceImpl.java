/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.service.impl;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.constant.RunningNumber;
import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.api.service.ConstantService;
import com.aan.girsang.server.dao.constant.RunningNumberDao;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIRSANG PC
 */
@Service("constantService")
@Transactional(readOnly = true)
public class ConstantServiceImpl implements ConstantService{
    Logger log = Logger.getLogger(ConstantServiceImpl.class.getName());
    
    @Autowired RunningNumberDao runningNumberDao;

    @Override
    @Transactional
    public void simpan(RunningNumber r) {
        runningNumberDao.simpan(r);
    }

    @Override
    public List<RunningNumber> semuaRunningNumber() {
        return runningNumberDao.semua();
    }

    @Override
    @Transactional
    public String ambilBerikutnya(MasterRunningNumberEnum id) {
        return runningNumberDao.ambilBerikutnya(id);
    }
    
    @Override
    @Transactional
    public String ambilBerikutnya(TransaksiRunningNumberEnum id) {
        return runningNumberDao.ambilBerikutnya(id);
    }

    @Override
    public Date tanggalService() {
        return new Date();
    }

    @Override
    public String clientOnline(String client) {
        log.info(client+" ONLINE");
        return "Halo, "+client;
    }

    @Override
    public Pengguna penggunaOnline(Pengguna p) {
        log.info(p.getNamaLengkap()+" LOGIN");
        return p;
    }
    @Override
    public Pengguna penggunaOffline(Pengguna p) {
        if(p!=null){
            log.info(p.getNamaLengkap()+" LOGOUT");
        }
        return p;
    }
}