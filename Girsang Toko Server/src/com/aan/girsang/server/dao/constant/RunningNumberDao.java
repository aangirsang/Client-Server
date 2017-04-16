/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.constant;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.constant.RunningNumber;
import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class RunningNumberDao {

    @Autowired
    private SessionFactory sessionFactory;

//<editor-fold defaultstate="collapsed" desc="Simpan">
    public void simpan(RunningNumber r) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(r);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Hapus">
    public void hapus(RunningNumber p) {
        sessionFactory.getCurrentSession().delete(p);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Ambil Master">
    public String ambilBerikutnya(MasterRunningNumberEnum id) {
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId());
        if (r == null) {
            r = new RunningNumber();
            r.setId(id.getId());
            r.setNumber(0);
            sessionFactory.getCurrentSession().save(r);
        }
        return id.getId() + StringUtils.padWithZero(r.getNumber() + 1, id.getDigit());
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Ambil dan Simpan Master">
    public String ambilBerikutnyaDanSimpan(MasterRunningNumberEnum id) {
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId());
        if (r == null) {
            r = new RunningNumber();
            r.setId(id.getId());
            r.setNumber(1);
        } else {
            r.setNumber(r.getNumber() + 1);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(r);
        return id.getId() + StringUtils.padWithZero(r.getNumber(), id.getDigit());
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Ambil Transaksi">
    public String ambilBerikutnya(TransaksiRunningNumberEnum id) {
        Date tanggal = new Date();
        String strTanggal = new SimpleDateFormat("yyMMdd").format(tanggal);
        String tahun = new SimpleDateFormat("yyyy").format(tanggal);
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId() + tahun);
        if (r == null) {
            r = new RunningNumber();
            r.setId(id.getId() + tahun);
            r.setNumber(0);
            simpan(r);
        }
        return id.getId() + strTanggal + StringUtils.padWithZero(r.getNumber() + 1, id.getDigit());
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Ambil dan Simpan Transaksi">
    public String ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum id) {
        Date tanggal = new Date();
        String strTanggal = new SimpleDateFormat("yyMMdd").format(tanggal);
        String tahun = new SimpleDateFormat("yyyy").format(tanggal);
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId() + tahun);
        if (r == null) {
            r = new RunningNumber();
            r.setId(id.getId() + tahun);
            r.setNumber(1);
        } else {
            r.setNumber(r.getNumber() + 1);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(r);
        return id.getId() + strTanggal + StringUtils.padWithZero(r.getNumber(), id.getDigit());
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    public List<RunningNumber> semua() {
        return sessionFactory.getCurrentSession()
                .createCriteria(RunningNumber.class)
                .list();
    }
//</editor-fold>

}
