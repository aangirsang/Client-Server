/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.master;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.HPPBarang;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class HPPDao extends BaseDaoHibernate<HPPBarang>{
//<editor-fold defaultstate="collapsed" desc="Cari ID">
    public HPPBarang cariId(String id){
        return (HPPBarang) sessionFactory.getCurrentSession().get(HPPBarang.class, id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    public List<HPPBarang> semua(Barang barang){
        return sessionFactory.getCurrentSession().createQuery("from HPPBarang h where h.barang =:barang order by h.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
//</editor-fold>
}
