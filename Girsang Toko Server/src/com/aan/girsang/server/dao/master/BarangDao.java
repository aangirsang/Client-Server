/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.master;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class BarangDao extends BaseDaoHibernate<Barang> {
//<editor-fold defaultstate="collapsed" desc="Cari ID">
    public Barang cariId(String id) {
        Barang b = (Barang) sessionFactory.getCurrentSession().get(Barang.class, id);
        return b;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Nama">
    public List<Barang> cariNama(String brg) {
        List<Barang> b = sessionFactory.getCurrentSession().createQuery(
                "from Barang b where b.namaBarang LIKE :namaBarang order by b.namaBarang asc")
                .setParameter("namaBarang", "%" + brg.toUpperCase() + "%")
                .list();
        return b;
    }
    
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<Barang> semua() {
        List<Barang> b = sessionFactory.getCurrentSession().createQuery("from Barang b order by b.namaBarang asc")
                .list();
        return b;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Histori Pembelian">
    public List<PembelianDetail> historyPembelian(Barang barang) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.barang =:barang order by p.pembelian.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
//</editor-fold>
    
    public List<Barang> isJual(Boolean isJual){
        List<Barang> b = (List<Barang>) sessionFactory.getCurrentSession().createQuery(
                "from Barang b where b.jual=:isJual order by b.namaBarang asc")
                .setParameter("isJual", isJual)
                .list();
        return b;
    }
    public Barang cariBarcode1(String barcode1){
        return (Barang) sessionFactory.getCurrentSession().createQuery(
                "from Barang b where b.barcode1=:barcode1")
                .setString("barcode1", barcode1)
                .uniqueResult();
    }
    public Barang cariBarcode2(String barcode2){
        return (Barang) sessionFactory.getCurrentSession().createQuery(
                "from Barang b where b.barcode2=:barcode2")
                .setString("barcode2", barcode2)
                .uniqueResult();
    }
}
