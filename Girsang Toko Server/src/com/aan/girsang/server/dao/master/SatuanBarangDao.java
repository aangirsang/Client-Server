/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.master;

import com.aan.girsang.api.model.master.SatuanBarang;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class SatuanBarangDao extends BaseDaoHibernate<SatuanBarang>{
//<editor-fold defaultstate="collapsed" desc="Cari Id">
    public SatuanBarang cariId (String id){
        return (SatuanBarang) sessionFactory.getCurrentSession().get(SatuanBarang.class, id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Nama">
    public List<SatuanBarang> cariNama(String SatuanBarang) {
        return sessionFactory.getCurrentSession().createQuery("from SatuanBarang k where k.satuanBarang LIKE :SatuanBarang")
                .setParameter("SatuanBarang",  "%" + SatuanBarang.toUpperCase() + "%")
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<SatuanBarang> semua(){
        return sessionFactory.getCurrentSession().createQuery("from SatuanBarang s order by s.satuanBarang Asc")
                .list();
    }
//</editor-fold>
}
