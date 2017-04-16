/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.master;

import com.aan.girsang.api.model.master.GolonganBarang;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class GolonganBarangDao extends BaseDaoHibernate<GolonganBarang>{
//<editor-fold defaultstate="collapsed" desc="Cari ID">
    public GolonganBarang cariId (String id){
        return (GolonganBarang) sessionFactory.getCurrentSession().get(GolonganBarang.class, id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Nama">
    public List<GolonganBarang> cariNama(String GolonganBarang) {
        return sessionFactory.getCurrentSession().createQuery("from GolonganBarang k where k.golonganBarang LIKE :GolonganBarang")
                .setParameter("GolonganBarang",  "%" + GolonganBarang.toUpperCase() + "%")
                .list();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<GolonganBarang> semua(){
        return sessionFactory.getCurrentSession().createQuery("from GolonganBarang g order by g.golonganBarang Asc")
                .list();
    }
//</editor-fold>
}
