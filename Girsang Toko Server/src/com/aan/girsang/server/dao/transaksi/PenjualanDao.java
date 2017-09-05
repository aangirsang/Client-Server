/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.transaksi;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.api.model.transaksi.Penjualan;
import com.aan.girsang.api.model.transaksi.PenjualanDetail;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class PenjualanDao extends BaseDaoHibernate<Penjualan>{
    public Penjualan cariId(String id){
        Penjualan p = (Penjualan) sessionFactory.getCurrentSession().get(Penjualan.class, id);
        if(p!=null){
            Hibernate.initialize(p.getPenjualanDetails());
            for(PenjualanDetail detail : p.getPenjualanDetails()){
                Hibernate.initialize(detail.getBarang());
            }
        }
        return p;
    }
    
    @Override
    public List<Penjualan> semua(){
        Boolean isPending = false;
        return sessionFactory.getCurrentSession().createQuery(
                "from Penjualan p where p.isPending=:isPending Order By p.tanggal Desc")
                .setParameter("isPending", isPending)
                .list();
    }
    public List<PenjualanDetail> cariBarang(Barang b){
        return sessionFactory.getCurrentSession().createQuery(
                "from PenjualanDetail p where p.barang=:b")
                .setParameter("b", b)
                .list();
    }
    public List<Penjualan> cariPelanggan(Pelanggan pelanggan){
        return sessionFactory.getCurrentSession().createQuery(
                "from Penjualan p where p.pelanggan=:pelanggan")
                .setParameter("pelanggan", pelanggan)
                .list();
    }
    public List<Penjualan> piutangPelanggan(Pelanggan pelanggan){
        BigDecimal piutang = new BigDecimal(0);
        return sessionFactory.getCurrentSession().createQuery(
                "from Penjualan p where p.pelanggan=:pelanggan and p.saldoPiutang>:piutang")
                .setParameter("pelanggan", pelanggan)
                .setParameter("piutang", piutang)
                .list();
    }
    public List<Penjualan> cariKasir(Pengguna pengguna){
        return sessionFactory.getCurrentSession().createQuery(
                "from Penjualan p where p.kasir=:pengguna")
                .setParameter("pengguna", pengguna)
                .list();
    }
    public List<Penjualan> pending(Boolean pending){
        return sessionFactory.getCurrentSession().createQuery(
                "from Penjualan p where p.isPending=:isPending")
                .setParameter("isPending", pending)
                .list();
    }
    public List<PenjualanDetail> cariDetail(Penjualan p){
        return sessionFactory.getCurrentSession().createQuery(
                "from PenjualanDetail p where p.penjualan=:penjualan")
                .setParameter("penjualan", p)
                .list();
    }
    public List<Penjualan> filterBulanTahun(int bulan, int tahun){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, bulan);
        calendar.set(Calendar.YEAR, tahun);
        Date date = calendar.getTime();
        System.out.println("bulan calendar " +calendar.getTime().getMonth());
        System.out.println("tahun calendar " +calendar.getTime().getYear());
        System.out.println("tahun dikirim "+tahun);
        System.out.println(date);
        
        String tgl = new SimpleDateFormat("MM yyyy").format(date);
        return sessionFactory.getCurrentSession().createQuery(
                "from Penjualan p where TO_CHAR(p.tanggal, 'MM yyyy') LIKE :bulan")
                .setParameter("bulan","%"+tgl+"%")
                .list();
    }
}
