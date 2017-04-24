/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.model.transaksi;

import com.aan.girsang.api.model.master.Barang;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="DETAILPENJUALAN")
public class PenjualanDetail implements Serializable{
    @Id
    @Column(name="ID",length=20)
    private String id;
    
    @ManyToOne
    @JoinColumn(name="PENJUALAN")
    private Penjualan penjualan;
    
    @ManyToOne
    @JoinColumn(name = "BARANG", nullable = false)
    private Barang barang;
    
    @Column(name = "KUANTITAS", nullable = false)
    private Integer kuantitas = 0;
    
    @Column(name="HARGAJUAL")
    private BigDecimal hargaJual=BigDecimal.ZERO;
    
    @Column(name="HPP")
    private BigDecimal hpp=BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public BigDecimal getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
    }

    public BigDecimal getHpp() {
        return hpp;
    }

    public void setHpp(BigDecimal hpp) {
        this.hpp = hpp;
    }
    
    
}
