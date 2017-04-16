/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.dao.security;

import com.aan.girsang.api.model.security.TingkatAkses;
import com.aan.girsang.server.dao.BaseDaoHibernate;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class TingkatAksesDao extends BaseDaoHibernate<TingkatAkses>{
//<editor-fold defaultstate="collapsed" desc="Cari Id">
    public TingkatAkses cariId(String id){
        return (TingkatAkses) sessionFactory.getCurrentSession().get(TingkatAkses.class, id);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Semua">
    @Override
    public List<TingkatAkses> semua(){
        return sessionFactory.getCurrentSession().createQuery(
                "From TingkatAkses t order By t.namaTingkatAkses asc")
                .list();
    }
//</editor-fold>
}
