/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.service.impl;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.HPPBarang;
import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.api.model.transaksi.PelunasanHutang;
import com.aan.girsang.api.model.transaksi.PelunasanHutangDetail;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.api.model.transaksi.Penjualan;
import com.aan.girsang.api.model.transaksi.PenjualanDetail;
import com.aan.girsang.api.model.transaksi.ReturPembelian;
import com.aan.girsang.api.model.transaksi.ReturPembelianDetail;
import com.aan.girsang.api.service.TransaksiService;
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.server.dao.master.BarangDao;
import com.aan.girsang.server.dao.master.HPPDao;
import com.aan.girsang.server.dao.constant.RunningNumberDao;
import com.aan.girsang.server.dao.master.PelangganDao;
import com.aan.girsang.server.dao.master.SupplierDao;
import com.aan.girsang.server.dao.transaksi.PelunasanHutangDao;
import com.aan.girsang.server.dao.transaksi.PembelianDao;
import com.aan.girsang.server.dao.transaksi.PenjualanDao;
import com.aan.girsang.server.dao.transaksi.ReturPembelianDao;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Month;
import java.time.Year;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ITSUSAHBRO
 */
@Service("transaksiService")
@Transactional(readOnly = true)
public class TransaksiServiceImpl implements TransaksiService {

    @Autowired PembelianDao pembelianDao;
    @Autowired BarangDao barangDao;
    @Autowired RunningNumberDao runningNumberTransaksiDao;
    @Autowired RunningNumberDao runningNumberDao;
    @Autowired HPPDao hPPDao;
    @Autowired SupplierDao supplierDao;
    @Autowired PelangganDao pelangganDao;
    @Autowired PelunasanHutangDao pelunasanHutangDao;
    @Autowired ReturPembelianDao returPembelianDao;
    @Autowired PenjualanDao penjualanDao;

    //<editor-fold defaultstate="collapsed" desc="Pembelian">
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void simpan(Pembelian p) {
        Pembelian pembelian = pembelianDao.cariId(p.getNoRef());
        if (pembelian == null) {
            p.setNoRef(runningNumberTransaksiDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.PEMBELIAN));
            int i = 1;
            for (PembelianDetail detail : p.getPembelianDetails()) {
                detail.setId(p.getNoRef() + i++);
            }
        } else {
            int i = 1;
            for (PembelianDetail detail : p.getPembelianDetails()) {
                try {
                    i++;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                } catch (Exception e) {
                    i = i + 1;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                }
                if (detail.getId() == null) {
                    detail.setId(p.getNoRef() + i++);
                }
            }
        }
        //update barang
        //<editor-fold defaultstate="collapsed" desc="Update Barang">
        for (PembelianDetail detail : p.getPembelianDetails()) {
            
            Barang b = barangDao.cariId(detail.getBarang().getPlu());
            if (detail.getUpdate() == true) {
                Double hargaBeli = Double.valueOf(TextComponentUtils.getValueFromTextNumber(
                        TextComponentUtils.formatNumber(detail.getHargaBarang())));
                Double isi = Double.valueOf(detail.getIsiPembelian());//harga beli di bagi isi
                Double hargaSatuan = hargaBeli / isi;
                BigDecimal hS = new BigDecimal(hargaSatuan, MathContext.DECIMAL64);
                b.setSatuanPembelian(detail.getSatuanPembelian());
                b.setHargaBeli(BigDecimal.valueOf(hargaSatuan));
                b.setIsiPembelian(detail.getIsiPembelian());
                
                //Simpan HPP Barang
                HPPBarang hPPBarang = new HPPBarang();
                hPPBarang.setIdHpp(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.HPP));
                hPPBarang.setTanggal(detail.getPembelian().getTanggal());
                hPPBarang.setBarang(b);
                hPPBarang.setHpp(detail.getHargaBarang());
                hPPBarang.setIsi(detail.getIsiPembelian());
                hPPBarang.setHppSatuan(BigDecimal.valueOf(hargaSatuan));
                
                barangDao.simpan(b);
                hPPDao.simpan(hPPBarang);
            }
            
        }
        //</editor-fold>
        pembelianDao.merge(p);
        simpanStokPembelian(p);
        simpanHutang();
    }
    
    @Transactional
    @Override
    public void hapus(Pembelian p){
        pembelianDao.hapus(p);
        simpanStokPembelian(p);
        simpanHutang();
    } 
    
    
    @Override
    public Pembelian cariPembelian(String id) {
        return pembelianDao.cariId(id);
    }
    
    @Override
    public List<Pembelian> semuaPembelian() {
        return pembelianDao.semua();
    }
    
    @Override
    public List<Pembelian> descPembelian() {
        return pembelianDao.semua();
    }
    
    @Override
    public List<Pembelian> hutangPembelian(Supplier s){
        return pembelianDao.hutangPembelian(s);
    }
    
    @Override
    public List<Pembelian> cariSupplierPembelian(Supplier s){
        return pembelianDao.cariSupplier(s);
    }
    
    @Override
    public List<PembelianDetail> cariPembelianDetail(Pembelian p) {
        return pembelianDao.cariDetail(p);
    }
    
    @Override
    public List<PembelianDetail> cariBarang(Barang barang) {
        return pembelianDao.cariBarang(barang);
    }
    
    @Override
    public PembelianDetail cariDetailBeli(String id) {
        return pembelianDao.cariIDDetail(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Penjualan">
    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(Penjualan p) {
        Penjualan penjualan = penjualanDao.cariId(p.getNoRef());
        if(penjualan == null){
            p.setNoRef(runningNumberDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.PENJUALAN));
            int i = 1;
            for(PenjualanDetail detail : p.getPenjualanDetails()){
                detail.setId(p.getNoRef() + i++);
            }
        }else{
            int i = 1;
            for(PenjualanDetail detail : p.getPenjualanDetails()){
                try {
                    i++;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                } catch (Exception e) {
                    i = i + 1;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                }
                if (detail.getId() == null) {
                    detail.setId(p.getNoRef() + i++);
                }
            }
        }
        penjualanDao.merge(p);
        simpanStokPenjualan(p);
        simpanPiutang();
    }
    
    @Override
    @Transactional
    public void hapus(Penjualan p) {
        penjualanDao.hapus(p);
    }
    
    @Override
    public Penjualan cariIDPenjualan(String id) {
        return penjualanDao.cariId(id);
    }
    
    @Override
    public List<Penjualan> semuaPenjualan() {
        return penjualanDao.semua();
    }
    
    @Override
    public List<Penjualan> cariPelanggan(Pelanggan p) {
        return penjualanDao.cariPelanggan(p);
    }
    
    @Override
    public List<Penjualan> cariPiutang(Pelanggan p) {
        return penjualanDao.piutangPelanggan(p);
    }
    
    @Override
    public List<Penjualan> cariKasir(Pengguna p) {
        return penjualanDao.cariKasir(p);
    }
    
    @Override
    public List<PenjualanDetail> cariBarangJual(Barang b) {
        return penjualanDao.cariBarang(b);
    }
    
    @Override
    public List<Penjualan> pending(Boolean pending) {
        return penjualanDao.pending(pending);
    }
    @Override
    public List<PenjualanDetail> cariDetail(Penjualan p){
        return penjualanDao.cariDetail(p);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Retur Pembelian">
            @Override
            @Transactional(isolation=Isolation.SERIALIZABLE)
            public void simpan(ReturPembelian returPembelian) {
                ReturPembelian rP = returPembelianDao.cariId(returPembelian.getNoRef());
                if(rP==null){
                    returPembelian.setNoRef(runningNumberTransaksiDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.RETURPEMBELIAN));
                    int i = 1;
                    for (ReturPembelianDetail detail : returPembelian.getReturPembelianDetails()) {
                        detail.setId(returPembelian.getNoRef() + i++);
                    }
                }else{
                    int i = 1;
                    for (ReturPembelianDetail detail : returPembelian.getReturPembelianDetails()) {
                        try {
                            i++;
                            if (detail.getId() == null) {
                                detail.setId(returPembelian.getNoRef() + i++);
                            }
                        } catch (Exception e) {
                            i = i + 1;
                            if (detail.getId() == null) {
                                detail.setId(returPembelian.getNoRef() + i++);
                            }
                        }
                        if (detail.getId() == null) {
                            detail.setId(returPembelian.getNoRef() + i++);
                        }
                    }
                }
                Pembelian p = returPembelian.getPembelian();
                p.setIsRetur(Boolean.TRUE);
                returPembelianDao.merge(returPembelian);
                pembelianDao.merge(p);
                simpanStokReturPembelian(returPembelian);
            }

            @Override
            public ReturPembelian cariReturPembelian(String id) {
                return returPembelianDao.cariId(id);
            }

            @Override
            public List<ReturPembelian> semuaReturPembelian() {
                return returPembelianDao.semua();
            }

            @Override
            public List<ReturPembelian> descReturPembelian() {
                return returPembelianDao.semua();
            }

            @Override
            public List<ReturPembelianDetail> cariReturPembelianDetail(ReturPembelian p) {
                return returPembelianDao.cariReturBeliDetail(p);
            }

            @Override
            public List<ReturPembelianDetail> cariBarangReturPembelian(Barang barang) {
                return returPembelianDao.cariBarang(barang);
            }
        //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Pelunasan Hutang">
    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(PelunasanHutang p) {
        PelunasanHutang pelunasanHutang = pelunasanHutangDao.cariId(p.getNoRef());
        if(pelunasanHutang==null){
            p.setNoRef(runningNumberTransaksiDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.HUTANG));
            int i = 1;
            for(PelunasanHutangDetail detail : p.getPelunasanHutangDetails()){
                detail.setId(p.getNoRef() + i++);
            }
        } else {
            int i = 1;
            for (PelunasanHutangDetail detail : p.getPelunasanHutangDetails()) {
                try {
                    i++;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                } catch (Exception e) {
                    i = i + 1;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                }
                if (detail.getId() == null) {
                    detail.setId(p.getNoRef() + i++);
                }
            }
        }
        pelunasanHutangDao.merge(p);
        simpanHutang();
    }
    @Override
    @Transactional
    public void hapus(PelunasanHutang p) {
        pelunasanHutangDao.hapus(p);
        simpanHutang();
    }
    
    @Override
    public PelunasanHutang cariId(String id) {
        return pelunasanHutangDao.cariId(id);
    }
    
    @Override
    public List<PelunasanHutang> semua() {
        return pelunasanHutangDao.semua();
    }
    @Override
    public List<PelunasanHutang> cariSupplier(Supplier s) {
        return pelunasanHutangDao.cariSupplier(s);
    }
    
    @Override
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang p) {
        return pelunasanHutangDao.cariDetail(p);
    }
    
    @Override
    public List<PelunasanHutangDetail> cariPembelian(Pembelian pembelian) {
        return pelunasanHutangDao.cariPembelian(pembelian);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Simpan Hutang">
    private void simpanHutang(){
        List<Supplier> suppliers = supplierDao.semua();
        for(Supplier s : suppliers){
            List<Pembelian> pembelians = pembelianDao.cariSupplier(s);
            List<PelunasanHutang> hutangs = pelunasanHutangDao.cariSupplier(s);
            
            BigDecimal hutangPembelian = new BigDecimal(0);
            BigDecimal pembayaranHutang = new BigDecimal(0);
            
            for(int i=0;i<pembelians.size();i++){
                Pembelian p = pembelians.get(i);
                hutangPembelian = hutangPembelian.add(p.getDaftarKredit().getSisaKredit());
            }
            
            for(int i=0;i<hutangs.size();i++){
                PelunasanHutang pH = hutangs.get(i);
                pembayaranHutang = pembayaranHutang.add(pH.getJlhBayar());
            }
            s.setSaldoHutang(hutangPembelian.subtract(pembayaranHutang));
            
            supplierDao.simpan(s);
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Simpan Piutang">
    private void simpanPiutang(){
        List<Pelanggan> daftarPelanggan = pelangganDao.semua();
        for(Pelanggan p : daftarPelanggan){
            List<Penjualan> daftarPenjualan = penjualanDao.cariPelanggan(p);
            //List<PelunasanHutang> hutangs = pelunasanHutangDao.cariSupplier(p);
            
            BigDecimal piutang = new BigDecimal(0);
//            BigDecimal pembayaranHutang = new BigDecimal(0);
            
            for(int i=0;i<daftarPenjualan.size();i++){
                Penjualan jual = daftarPenjualan.get(i);
                piutang = piutang.add(jual.getPiutangAwal());
            }
            
//            for(int i=0;i<hutangs.size();i++){
//                PelunasanHutang pH = hutangs.get(i);
//                pembayaranHutang = pembayaranHutang.add(pH.getJlhBayar());
//            }
            p.setSaldoPiutang(piutang);
            
            pelangganDao.simpan(p);
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Simpan Stok Pembelian">
    private void simpanStokPembelian(Pembelian p) {
        Integer stokToko = 0;
        Integer stokGudang = 0;
        for (PembelianDetail detail : p.getPembelianDetails()) {
                    stokToko = 0;
                    stokGudang = 0;
            Barang b = barangDao.cariId(detail.getBarang().getPlu());
            List<PembelianDetail> PD = pembelianDao.cariBarang(detail.getBarang());
            for (PembelianDetail PD1 : PD) {
                if("Toko".equals(PD1.getPembelian().getLokasi())){
                    stokToko = stokToko + (PD1.getKuantitas() * PD1.getIsiPembelian());
                }else if("Gudang".equals(PD1.getPembelian().getLokasi())){
                    stokGudang = stokGudang + (PD1.getKuantitas() * PD1.getIsiPembelian());
                }
            }
            b.setStokPembelianToko(stokToko);
            b.setStokPembelianGudang(stokGudang);
            barangDao.simpan(b);
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Simpan Stok Penjualan">
    private void simpanStokPenjualan(Penjualan p) {
        Integer stokToko = 0;
        Integer stokGudang = 0;
        for (PenjualanDetail detail : p.getPenjualanDetails()) {
                    stokToko = 0;
                    stokGudang = 0;
            Barang b = barangDao.cariId(detail.getBarang().getPlu());
            List<PembelianDetail> PD = pembelianDao.cariBarang(detail.getBarang());
            for (PembelianDetail PD1 : PD) {
                if("Toko".equals(PD1.getPembelian().getLokasi())){
                    stokToko = stokToko + (PD1.getKuantitas() * PD1.getIsiPembelian());
                }else if("Gudang".equals(PD1.getPembelian().getLokasi())){
                    stokGudang = stokGudang + (PD1.getKuantitas() * PD1.getIsiPembelian());
                }
            }
            b.setStokPenjualanToko(stokToko);
            b.setStokPenjualanGudang(stokGudang);
            barangDao.simpan(b);
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Simpan Stok Retur Pembelian">
    private void simpanStokReturPembelian(ReturPembelian rP) {
        Integer stokToko = 0;
        Integer stokGudang = 0;
        for (ReturPembelianDetail detail : rP.getReturPembelianDetails()) {
            Barang b = barangDao.cariId(detail.getBarang().getPlu());
            List<ReturPembelianDetail> PD = returPembelianDao.cariBarang(detail.getBarang());
            for (ReturPembelianDetail PD1 : PD) {
                System.out.println(PD1.getBarang().getNamaBarang());
                if("Toko".equals(PD1.getReturPembelian().getPembelian().getLokasi())){
                    stokToko = 0;
                    stokToko = stokToko + (PD1.getKuantitas() * PD1.getIsiReturPembelian());
                }else if("Gudang".equals(PD1.getReturPembelian().getPembelian().getLokasi())){
                    stokGudang = 0;
                    stokGudang = stokGudang + (PD1.getKuantitas() * PD1.getIsiReturPembelian());
                }
            }
            b.setStokReturBeliToko(stokToko);
            b.setStokReturBeliGudang(stokGudang);
            
            barangDao.simpan(b);
        }
    }
//</editor-fold>

    @Override
    public List<Penjualan> filterBulanTahun(int bulan, int tahun) {
        return penjualanDao.filterBulanTahun(bulan, tahun);
    }
}
