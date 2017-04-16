/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.security;

import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.api.model.security.TingkatAkses;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class PenggunaDao extends BaseDaoHibernate<Pengguna>{
//<editor-fold defaultstate="collapsed" desc="Cari ID">
    public Pengguna cariID(String Id){
        return (Pengguna) sessionFactory.getCurrentSession().get(Pengguna.class, Id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Username">
    public Pengguna cariUsername(String userName){
        return (Pengguna) sessionFactory.getCurrentSession().createQuery(
                "from Pengguna p where p.userName=:userName")
                .setString("userName", userName)
                .uniqueResult();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Login">
    public Pengguna login(String userName, String password){
        return (Pengguna) sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p where p.userName=:userName and"
                        + " p.password=:password")
                .setString("userName", userName)
                .setString("password", password)
                .uniqueResult();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<Pengguna> semua(){
        return sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p Order By p.namaLengkap asc")
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Nama Lengkap">
    public List<Pengguna> cariNamaLengkap(String namaLengkap){
        return sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p where p.namaLengkap LIKE :namaLengkap "
                        + "Order By p.tingkatAkses asc And Order By p.namaLengkap asc")
                .setParameter("namaLengkap", namaLengkap)
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari TIngkat Akses">
    public List<Pengguna> cariTingkatAkses(TingkatAkses tingkatAkses){
        return sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p where p.tingkatAkses=:tingkatAkses "
                        + "order By p.namaLengkap asc")
                .setParameter("tingkatAkses", tingkatAkses)
                .list();
    }
//</editor-fold>
}
