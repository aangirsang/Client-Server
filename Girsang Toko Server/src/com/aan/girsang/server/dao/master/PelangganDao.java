/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.master;

import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class PelangganDao extends BaseDaoHibernate<Pelanggan>{
    public Pelanggan cariId(String id){
        return (Pelanggan) sessionFactory.getCurrentSession().get(Pelanggan.class, id);
    }
    public List<Pelanggan> cariNama(String nama){
        return sessionFactory.getCurrentSession().createQuery("from Pelanggan s where s.nama LIKE :nama")
                .setParameter("nama",  "%" + nama.toUpperCase() + "%")
                .list();
    }
    @Override
    public List<Pelanggan> semua(){
        return sessionFactory.getCurrentSession().createQuery(
                "from Pelanggan p order By p.nama asc")
                .list();
    }
}
