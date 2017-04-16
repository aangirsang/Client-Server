/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.service.impl;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.constant.RunningNumber;
import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.service.ConstantService;
import com.aan.girsang.server.dao.constant.RunningNumberDao;
import java.util.List;
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
}
