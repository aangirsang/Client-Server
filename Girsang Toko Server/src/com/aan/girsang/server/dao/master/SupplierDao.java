/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.master;

import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class SupplierDao extends BaseDaoHibernate<Supplier>{
//<editor-fold defaultstate="collapsed" desc="Cari ID">
    public Supplier cariId (String id){
        return  (Supplier) sessionFactory.getCurrentSession().get(Supplier.class, id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Cari Nama">
    public List<Supplier> cariNama(String supplier) {
        return sessionFactory.getCurrentSession().createQuery("from Supplier s where s.namaSupplier LIKE :namaSupplier")
                .setParameter("namaSupplier",  "%" + supplier.toUpperCase() + "%")
                .list();
    }
//</editor-fold>
}
