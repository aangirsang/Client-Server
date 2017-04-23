/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.model.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="PELANGGAN")
public class Pelanggan implements Serializable{
    @Id
    @Column(name="IDPELANGGAN",length=6)
    private String idPelanggan;
    
    @Column(name="NAMAPELANGGAN",length=30)
    private String nama;
    
    @Column(name="JENISKELAMIN",length=15)
    private String jenisKelamin;
    
    @Column(name="TEMPATLAHIR",length=20)
    private String tempatLahir;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "TANGGALLAHIR")
    private Date tanggalLahir;
    
    @Column(name="AGAMA",length=10)
    private String agama;
    
    @Column(name="ALAMAT",length=50)
    private String alamat;
    
    @Column(name="KOTA",length=20)
    private String kota;
    
    @Column(name="PROVINSI",length=50)
    private String provinsi;
    
    @Column(name="KODEPOS",length=7)
    private String kodePos;
    
    @Column(name="CP1",length=20)
    private String cp1;
    
    @Column(name="CP2",length=20)
    private String cp2;
    
    @Column(name="TELEPON",length=20)
    private String telepon;
    
    @Column(name="DISC_DLMPERSEN")
    private Integer disc;
    
    @Column(name="SALDOPIUTANG")
    private BigDecimal saldoPiutang = BigDecimal.ZERO;
    
    @Column(name="POINT")
    private Integer point;

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getCp1() {
        return cp1;
    }

    public void setCp1(String cp1) {
        this.cp1 = cp1;
    }

    public String getCp2() {
        return cp2;
    }

    public void setCp2(String cp2) {
        this.cp2 = cp2;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public Integer getDisc() {
        return disc;
    }

    public void setDisc(Integer disc) {
        this.disc = disc;
    }

    public BigDecimal getSaldoPiutang() {
        return saldoPiutang;
    }

    public void setSaldoPiutang(BigDecimal saldoPiutang) {
        this.saldoPiutang = saldoPiutang;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    
    
}
