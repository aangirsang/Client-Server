/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.server.service.impl;

import com.aan.girsang.api.model.report.ReportBarang;
import com.aan.girsang.api.model.report.ReportPenjualanBarang;
import com.aan.girsang.api.service.ReportService;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ITSUSAHBRO
 */
@Service("reportService")
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
    private static final Logger log = Logger.getLogger(ReportServiceImpl.class);
    @Autowired SessionFactory sessionFactory;

    @Override
    public JasperPrint testReport() {
        try {
            List<ReportBarang> reportBarang
                    = sessionFactory.getCurrentSession()
                            .createQuery("select b.plu as plu,"
                                    + " b.namaBarang as namaBarang, "
                                    + " b.hargaBeli as hargaBeli from Barang b order by b.namaBarang")
                            .setResultTransformer(
                                    Transformers.aliasToBean(ReportBarang.class))
                            .list();
            InputStream is = ReportServiceImpl.class
                    .getResourceAsStream("/com/aan/girsang/server/report/testBarangReport.jasper");
            Map<String, Object> parameters = new HashMap<String, Object>();
            System.out.println("Report Tampil");
            return JasperFillManager.fillReport(is, parameters,
                    new JRBeanCollectionDataSource(reportBarang));
        } catch (JRException ex) {
            log.error("error generate DailySalesReport", ex);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public JasperPrint penjualanBarang(Date date) {
        try {
            List<ReportPenjualanBarang> reportPenjualanBarang = 
                    sessionFactory.getCurrentSession().createQuery(
                            "select p.barang.namaBarang as namaBarang, "
                                    + "sum(p.kuantitas) as jumlah, "
                                    + "sum(p.subTotal) as subTotal "
                                    + "from PenjualanDetail p "
                                    + "where day(p.penjualan.tanggal) = day(:date) "
                                    + "group by p.barang.namaBarang order by p.barang.namaBarang")
                            .setParameter("date", date)
                            .setResultTransformer(Transformers.aliasToBean(ReportPenjualanBarang.class))
                            .list();
            
            InputStream is = ReportServiceImpl.class.getResourceAsStream(
                    "/com/aan/girsang/server/report/PenjualanBarang.jasper");
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("date", date);
            
            return JasperFillManager.fillReport(is,parameters,
                    new JRBeanCollectionDataSource(reportPenjualanBarang));
        } catch (JRException ex) {
            log.error("error generate DailySalesReport", ex);
            ex.printStackTrace();
        }
        return null;
    }
}
