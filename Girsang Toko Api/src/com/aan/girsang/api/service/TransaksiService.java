/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.service;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.PelunasanHutang;
import com.aan.girsang.api.model.transaksi.PelunasanHutangDetail;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.api.model.transaksi.ReturPembelian;
import com.aan.girsang.api.model.transaksi.ReturPembelianDetail;
import java.util.List;

/**
 *
 * @author ITSUSAHBRO
 */
public interface TransaksiService {
//<editor-fold defaultstate="collapsed" desc="Pembelian">
    public void simpan(Pembelian pembelian);
    public Pembelian cariPembelian(String id);
    public List<Pembelian> semuaPembelian();
    public List<Pembelian> descPembelian();
    public List<Pembelian> hutangPembelian(Supplier s);
    public List<Pembelian> cariSupplierPembelian (Supplier s);
    public List<PembelianDetail> cariPembelianDetail(Pembelian p);
    public List<PembelianDetail> cariBarang(Barang barang);
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="ReturPembelian">
    public void simpan(ReturPembelian returPembelian);
    public ReturPembelian cariReturPembelian(String id);
    public List<ReturPembelian> semuaReturPembelian();
    public List<ReturPembelian> descReturPembelian();
    public List<ReturPembelianDetail> cariReturPembelianDetail(ReturPembelian p);
    public List<ReturPembelianDetail> cariBarangReturPembelian(Barang barang);
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Pelunasan Hutang">
    public void simpan(PelunasanHutang p);
    public void hapus(PelunasanHutang p);
    public PelunasanHutang cariId(String id);
    public List<PelunasanHutang> semua();
    public List<PelunasanHutang> cariSupplier(Supplier s);
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang p);
    public List<PelunasanHutangDetail> cariPembelian(Pembelian pembelian);
//</editor-fold>
    
}
