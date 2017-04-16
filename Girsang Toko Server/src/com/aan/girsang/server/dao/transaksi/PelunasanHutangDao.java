/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.transaksi;

import com.aan.girsang.api.model.transaksi.PelunasanHutang;
import com.aan.girsang.api.model.transaksi.PelunasanHutangDetail;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import java.util.function.Supplier;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ITSUSAHBRO
 */
@Repository
public class PelunasanHutangDao extends BaseDaoHibernate<PelunasanHutang>{
//<editor-fold defaultstate="collapsed" desc="Cari Id">
    public PelunasanHutang cariId(String id) {
        PelunasanHutang pelunasanHutang = (PelunasanHutang) sessionFactory.
                getCurrentSession().get(PelunasanHutang.class, id);
        if(pelunasanHutang!=null){
            Hibernate.initialize(pelunasanHutang.getPelunasanHutangDetails());
            for(PelunasanHutangDetail d : pelunasanHutang.getPelunasanHutangDetails()){
                Hibernate.initialize(d.getPembelian());
            }
        }
        return pelunasanHutang;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<PelunasanHutang> semua() {
        return sessionFactory.getCurrentSession().createQuery(
                "from PelunasanHutang p order by p.noRef desc").list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Supplier">
    public List<PelunasanHutang> cariSupplier(Supplier s) {
        return sessionFactory.getCurrentSession().createQuery(
                "from PelunasanHutang p where p.supplier=:s order by p.id desc")
                .setParameter("s", s)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Detail">
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang pelunasanHutang) {
        return sessionFactory.getCurrentSession().createQuery(
                "from PelunasanHutangDetail p where p.pelunasanHutang=:pelunasanHutang order by p.id asc")
                .setParameter("pelunasanHutang", pelunasanHutang)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari ID Detail">
    public PelunasanHutangDetail cariIDDetail(String id) {
        return (PelunasanHutangDetail) sessionFactory.getCurrentSession().createQuery(
                "from PelunasanHutangDetail p where p.id=:id order by p.id asc")
                .setParameter("id", id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Pembelian">
    public List<PelunasanHutangDetail> cariPembelian(Pembelian pembelian){
        return sessionFactory.getCurrentSession().createQuery(
                "from PelunasanHutangDetail p where p.pembelian=:pembelian order by p.id asc")
                .setParameter("pembelian", pembelian)
                .list();
    }
//</editor-fold>
}
