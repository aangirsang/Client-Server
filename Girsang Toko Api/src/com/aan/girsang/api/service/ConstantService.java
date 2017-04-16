/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.service;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.model.constant.RunningNumber;
import java.util.Date;
import java.util.List;

/**
 *
 * @author GIRSANG PC
 */
public interface ConstantService {
    public void simpan(RunningNumber r);
    public List<RunningNumber> semuaRunningNumber();
    public String ambilBerikutnya(MasterRunningNumberEnum id);
    public String ambilBerikutnya(TransaksiRunningNumberEnum id);
    public Date tanggalService();
    public String clientOnline(String client);
}
