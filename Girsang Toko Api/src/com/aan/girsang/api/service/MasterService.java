/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.service;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.GolonganBarang;
import com.aan.girsang.api.model.master.HPPBarang;
import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.model.master.SatuanBarang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import java.util.List;

/**
 *
 * @author GIRSANG PC
 */
public interface MasterService {
    //<editor-fold defaultstate="collapsed" desc="Golongan Barang">
    public void simpan (GolonganBarang golonganBarang);
    public void hapus (GolonganBarang golonganBarang);
    public List<GolonganBarang> semua();
    public List<GolonganBarang> cariNama(String GolonganBarang);
    public GolonganBarang cariID(String id);
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Satuan Barang">
    public void simpan (SatuanBarang satuanBarang);
    public void hapus (SatuanBarang satuanBarang);
    public List<SatuanBarang> daftarSatuanBarang();
    public List<SatuanBarang> cariNamaSatuanBarang(String SatuanBarang);
    public SatuanBarang cariIdSatuanBarang(String id);
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Supplier">
    public void simpan (Supplier supplier);
    public void hapus (Supplier supplier);
    public List<Supplier> daftarSupplier();
    public List<Supplier> cariNamaSupplier(String Supplier);
    public Supplier cariIdSupplier(String id);
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Barang">
    public void simpan (Barang barang);
    public void hapus (Barang barang);
    public List<Barang> semuaBarang();
    public List<Barang> cariNamaBarang(String Barang);
    public List<Barang> isJual(Boolean isJual);
    public Barang cariIdBarang(String id);
    public List<PembelianDetail> historyPembelian(Barang barang);
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HPP Barang">
    public void simpan (HPPBarang hPPBarang);
    public void hapus (HPPBarang hPPBarang);
    public List<HPPBarang> hPPBarangs(Barang barang);
    public HPPBarang hPPBarangBerdasarkanId(String id);
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Pelanggan">
    public void simpan (Pelanggan pelanggan);
    public void hapus (Pelanggan pelanggan);
    public List<Pelanggan> semuaPelanggan();
    public List<Pelanggan> cariNamaPelanggan(String nama);
    public Pelanggan cariIdPelanggan(String id);
//</editor-fold>
}
