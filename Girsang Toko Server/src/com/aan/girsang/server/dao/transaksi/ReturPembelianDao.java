/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.transaksi;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.ReturPembelian;
import com.aan.girsang.api.model.transaksi.ReturPembelianDetail;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ITSUSAHBRO
 */
@Repository
public class ReturPembelianDao extends BaseDaoHibernate<ReturPembelian>{
//<editor-fold defaultstate="collapsed" desc="Cari Id">
    public ReturPembelian cariId(String id) {
        ReturPembelian returPembelian = (ReturPembelian) sessionFactory.getCurrentSession().get(ReturPembelian.class, id);
        if(returPembelian!=null){
            Hibernate.initialize(returPembelian.getReturPembelianDetails());
            for(ReturPembelianDetail d : returPembelian.getReturPembelianDetails()){
                Hibernate.initialize(d.getBarang());
            }
        }
        return returPembelian;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Retur Beli Detail">
    public List<ReturPembelianDetail> cariReturBeliDetail(ReturPembelian returPembelian) {
        return sessionFactory.getCurrentSession().createQuery("from ReturPembelianDetail p where p.returPembelian=:returPembelian order by p.id asc")
                .setParameter("returPembelian", returPembelian)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari ID Retur Beli Detail">
    public ReturPembelianDetail cariIdReturBeliDetail(String id) {
        return (ReturPembelianDetail) sessionFactory.getCurrentSession().createQuery("from ReturPembelianDetail p where p.id=:id order by p.id asc")
                .setParameter("id", id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<ReturPembelian> semua() {
        return sessionFactory.getCurrentSession().createQuery("from ReturPembelian p order by p.tanggal desc").list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Filter Bulan">
    public List<ReturPembelian> filterBulanRP(int bulan, int tahun) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, bulan);
        calendar.set(Calendar.YEAR, tahun);
        Date date = calendar.getTime();
        
        String tgl = new SimpleDateFormat("MM yyyy").format(date);
        return sessionFactory.getCurrentSession().createQuery(
                "from ReturPembelian p where TO_CHAR(p.tanggal, 'MM yyyy') LIKE :bulan "
                        + "Order By p.tanggal Desc")
                .setParameter("bulan","%"+tgl+"%")
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Supplier">
    public List<ReturPembelian> cariSupplier(Supplier s){
        return (List<ReturPembelian>) sessionFactory.getCurrentSession().createQuery(
                "from ReturPembelian p where p.supplier=:supplier")
                .setParameter("supplier", s)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Barang">
    public List<ReturPembelianDetail> cariBarang(Barang barang) {
        return sessionFactory.getCurrentSession().createQuery("from ReturPembelianDetail p where p.barang=:barang")
                .setParameter("barang", barang)
                .list();
    }
//</editor-fold>
}
