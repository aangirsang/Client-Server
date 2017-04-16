/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.service.impl;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.GolonganBarang;
import com.aan.girsang.api.model.master.HPPBarang;
import com.aan.girsang.api.model.master.SatuanBarang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.api.service.MasterService;
import com.aan.girsang.server.dao.master.BarangDao;
import com.aan.girsang.server.dao.master.GolonganBarangDao;
import com.aan.girsang.server.dao.master.HPPDao;
import com.aan.girsang.server.dao.constant.RunningNumberDao;
import com.aan.girsang.server.dao.master.SatuanBarangDao;
import com.aan.girsang.server.dao.master.SupplierDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIRSANG PC
 */
@Service("masterService")
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService{

    @Autowired GolonganBarangDao golonganBarangDao;
    @Autowired RunningNumberDao runningNumberDao;
    @Autowired SatuanBarangDao satuanBarangDao;
    @Autowired SupplierDao supplierDao;
    @Autowired BarangDao barangDao;
    @Autowired HPPDao hPPDao;

    //<editor-fold defaultstate="collapsed" desc="Golongan Barang">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(GolonganBarang golonganBarang) {
        if (golonganBarang.getId()==null){
            golonganBarang.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.GOLONGAN));
        }
        golonganBarangDao.simpan(golonganBarang);
    }
    @Override
    @Transactional
    public void hapus(GolonganBarang golonganBarang) {
        golonganBarangDao.hapus(golonganBarang);
    }
    @Override
    public List<GolonganBarang> semua() {
        return golonganBarangDao.semua();
    }
    @Override
    public List<GolonganBarang> cariNama(String GolonganBarang) {
        return golonganBarangDao.cariNama(GolonganBarang);
    }
    @Override
    public GolonganBarang cariID(String id) {
        return golonganBarangDao.cariId(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Satuan Barang">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(SatuanBarang satuanBarang) {
        if (satuanBarang.getId()==null)satuanBarang.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.SATUAN));
        satuanBarangDao.simpan(satuanBarang);
    }
    
    @Override
    @Transactional
    public void hapus(SatuanBarang satuanBarang) {
        satuanBarangDao.hapus(satuanBarang);
    }
    
    @Override
    public List<SatuanBarang> daftarSatuanBarang() {
        return satuanBarangDao.semua();
    }
    
    @Override
    public List<SatuanBarang> cariNamaSatuanBarang(String SatuanBarang) {
        return satuanBarangDao.cariNama(SatuanBarang);
    }
    
    @Override
    public SatuanBarang cariIdSatuanBarang(String id) {
        return satuanBarangDao.cariId(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Supplier">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(Supplier supplier) {
        if("".equals(supplier.getId()))supplier.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.SUPPLIER));
        supplierDao.simpan(supplier);
    }
    
    @Override
    @Transactional
    public void hapus(Supplier supplier) {
        supplierDao.hapus(supplier);
    }
    
    @Override
    public List<Supplier> daftarSupplier() {
        return supplierDao.semua();
    }
    
    @Override
    public List<Supplier> cariNamaSupplier(String Supplier) {
        return supplierDao.cariNama(Supplier);
    }
    
    @Override
    public Supplier cariIdSupplier(String id) {
        return supplierDao.cariId(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Barang">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(Barang barang) {
        if("".equals(barang.getPlu()))barang.setPlu(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.BARANG));
        barangDao.simpan(barang);
    }
    
    @Override
    @Transactional
    public void hapus(Barang barang) {
        barangDao.hapus(barang);
    }
    
    @Override
    public List<Barang> semuaBarang() {
        return barangDao.semua();
    }
    
    @Override
    public List<Barang> cariNamaBarang(String Barang) {
        return barangDao.cariNama(Barang);
    }
    
    @Override
    public Barang cariIdBarang(String id) {
        return barangDao.cariId(id);
    }
    @Override
    public List<PembelianDetail> historyPembelian(Barang barang) {
        return barangDao.historyPembelian(barang);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HPP Barang">
    
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(HPPBarang hPPBarang) {
        if("".equals(hPPBarang.getIdHpp()))hPPBarang.setIdHpp(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.HPP));
        
        Barang b = barangDao.cariId(hPPBarang.getBarang().getPlu());
        b.setHargaBeli(hPPBarang.getHppSatuan());
        b.setIsiPembelian(hPPBarang.getIsi());
        
        barangDao.simpan(b);
        hPPDao.simpan(hPPBarang);
        
    }
    
    @Override
    @Transactional
    public void hapus(HPPBarang hPPBarang) {
        hPPDao.hapus(hPPBarang);
    }
    
    @Override
    public List<HPPBarang> hPPBarangs(Barang barang) {
        return (List<HPPBarang>) hPPDao.semua(barang);
    }
    
    @Override
    public HPPBarang hPPBarangBerdasarkanId(String id) {
        return hPPDao.cariId(id);
    }
    
//</editor-fold>
    
}
