/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.transaksi;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.Kredit;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ITSUSAHBRO
 */
@Repository
public class PembelianDao extends BaseDaoHibernate<Pembelian>{
//<editor-fold defaultstate="collapsed" desc="Cari ID">
    public Pembelian cariId(String id) {
        Pembelian pembelian = (Pembelian) sessionFactory.getCurrentSession().get(Pembelian.class, id);
        if(pembelian!=null){
            Hibernate.initialize(pembelian.getPembelianDetails());
            for(PembelianDetail d : pembelian.getPembelianDetails()){
                Hibernate.initialize(d.getBarang());
            }
        }
        return pembelian;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<Pembelian> semua() {
        return sessionFactory.getCurrentSession().createQuery(
                "from Pembelian p order by p.noRef desc").list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Detail">
    public List<PembelianDetail> cariDetail(Pembelian pembelian) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.pembelian=:pembelian order by p.id asc")
                .setParameter("pembelian", pembelian)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari ID Detail">
    public PembelianDetail cariIDDetail(String id) {
        return (PembelianDetail) sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.id=:id order by p.id asc")
                .setParameter("id", id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Kredit">
    public List<Kredit> kredit(BigDecimal sisaKredit) {
        return sessionFactory.getCurrentSession().createQuery("from kredit k where p.sisaKredit=:sisaKredit")
                .setParameter("sisaKredit", sisaKredit)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Barang">
    public List<PembelianDetail> cariBarang(Barang barang) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.barang=:barang order by p.pembelian.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Hutang Pembelian">
    public List<Pembelian> hutangPembelian(Supplier supplier){
        BigDecimal hutang = new BigDecimal(0);
        return (List<Pembelian>) sessionFactory.getCurrentSession().createQuery(
                "from Pembelian p where p.supplier=:supplier and p.daftarKredit.sisaKredit>:hutang")
                .setParameter("supplier", supplier)
                .setParameter("hutang", hutang)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Supplier">
    public List<Pembelian> cariSupplier(Supplier s){
        return (List<Pembelian>) sessionFactory.getCurrentSession().createQuery(
                "from Pembelian p where p.supplier=:supplier")
                .setParameter("supplier", s)
                .list();
    }
//</editor-fold>
}