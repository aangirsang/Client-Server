/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.model.transaksi;

import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.model.security.Pengguna;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="PENJUALAN")
public class Penjualan implements Serializable{
    @Id
    @Column(name="NOREF",length=15)
    private String noRef;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TANGGAL")
    private Date tanggal;
    
    @ManyToOne
    @JoinColumn(name="KASIR")
    private Pengguna kasir;
    
    @ManyToOne
    @JoinColumn(name="PELANGGAN")
    private Pelanggan pelanggan;
    
    @Column(name="ISKREDIT")
    private Boolean isKredit = Boolean.FALSE;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "TANGGAL_TEMPO")
    private Date tanggalTempo;
    
    @Column(name="BAYAR")
    private BigDecimal bayar=BigDecimal.ZERO;
    
    @Column(name="PIUTANG_AWAL")
    private BigDecimal piutangAwal=BigDecimal.ZERO;
    
    @Column(name="SALDO_PIUTANG")
    private BigDecimal saldoPiutang=BigDecimal.ZERO;
    
    @Column(name="ISLUNAS")
    private Boolean isLunas = Boolean.FALSE;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "TANGGAL_LUNAS")
    private Date tanggalLunas;
    
    @Column(name="JUMLAH_PENJUALAN")
    private BigDecimal jumlahPenjualan=BigDecimal.ZERO;
    
    @Column(name="DISC_TOTAL")
    private Integer discTotal;
    
    @Column(name="PEMBULATAN")
    private BigDecimal pembulatan=BigDecimal.ZERO;
    
    @Column(name="TOTAL")
    private BigDecimal total=BigDecimal.ZERO;
    
    @Column(name="SUBTOTAL")
    private BigDecimal subTotal=BigDecimal.ZERO;
    
    @Column(name="LOKASI",length=15)
    private String lokasi;
    
    @Column(name="ISPENDING")
    private Boolean isPending = Boolean.FALSE;
    
    @OneToMany(mappedBy = "penjualan", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
    
    public List<PenjualanDetail> getPenjualanDetails(){
        return penjualanDetails;
    }
    
    public void setPenjualanDetails(List<PenjualanDetail> penjualanDetails) {
        this.penjualanDetails = penjualanDetails;
        if(penjualanDetails !=null && !penjualanDetails.isEmpty()){
            for(PenjualanDetail detail : penjualanDetails){
                detail.setPenjualan(this);
            }
        }
    }
    
    public void addPenjualanDetails(PenjualanDetail detail){
        if(penjualanDetails==null){
            penjualanDetails = new ArrayList<PenjualanDetail>();
        }
        penjualanDetails.add(detail);
        detail.setPenjualan(this);
    }

    public void removePenjualanDetails(PenjualanDetail detail){
        if(penjualanDetails==null){
            penjualanDetails = new ArrayList<PenjualanDetail>();
        }
        penjualanDetails.remove(detail);
        detail.setPenjualan(null);
    }

    public String getNoRef() {
        return noRef;
    }

    public void setNoRef(String noRef) {
        this.noRef = noRef;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Pengguna getKasir() {
        return kasir;
    }

    public void setKasir(Pengguna kasir) {
        this.kasir = kasir;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Boolean getIsKredit() {
        return isKredit;
    }

    public void setIsKredit(Boolean isKredit) {
        this.isKredit = isKredit;
    }

    public Date getTanggalTempo() {
        return tanggalTempo;
    }

    public void setTanggalTempo(Date tanggalTempo) {
        this.tanggalTempo = tanggalTempo;
    }

    public BigDecimal getBayar() {
        return bayar;
    }

    public void setBayar(BigDecimal bayar) {
        this.bayar = bayar;
    }

    public BigDecimal getPiutangAwal() {
        return piutangAwal;
    }

    public void setPiutangAwal(BigDecimal piutangAwal) {
        this.piutangAwal = piutangAwal;
    }

    public BigDecimal getSaldoPiutang() {
        return saldoPiutang;
    }

    public void setSaldoPiutang(BigDecimal saldoPiutang) {
        this.saldoPiutang = saldoPiutang;
    }

    public Boolean getIsLunas() {
        return isLunas;
    }

    public void setIsLunas(Boolean isLunas) {
        this.isLunas = isLunas;
    }

    public Date getTanggalLunas() {
        return tanggalLunas;
    }

    public void setTanggalLunas(Date tanggalLunas) {
        this.tanggalLunas = tanggalLunas;
    }

    public BigDecimal getJumlahPenjualan() {
        return jumlahPenjualan;
    }

    public void setJumlahPenjualan(BigDecimal jumlahPenjualan) {
        this.jumlahPenjualan = jumlahPenjualan;
    }

    public Integer getDiscTotal() {
        return discTotal;
    }

    public void setDiscTotal(Integer discTotal) {
        this.discTotal = discTotal;
    }

    public BigDecimal getPembulatan() {
        return pembulatan;
    }

    public void setPembulatan(BigDecimal pembulatan) {
        this.pembulatan = pembulatan;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(Boolean isPending) {
        this.isPending = isPending;
    }
    
}
