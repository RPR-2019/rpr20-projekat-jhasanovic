package sample;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintReport extends JFrame {
    public void showReport(Connection conn,String reportType,HashMap<String,Object> p) throws JRException {
        String reportSrcFile="";

        if(reportType.equals("Generalni izvještaj"))
        reportSrcFile = getClass().getResource("/reports/Report1_bs.jrxml").getFile();
        else if(reportType.equals("Dnevni izvještaj"))
            reportSrcFile = getClass().getResource("/reports/Report2_bs.jrxml").getFile();
        else if(reportType.equals("General report"))
            reportSrcFile = getClass().getResource("/reports/Report1_en.jrxml").getFile();
        else if(reportType.equals("Daily report"))
            reportSrcFile = getClass().getResource("/reports/Report2_en.jrxml").getFile();
        else if(reportType.equals("Receipt_bs")){
            reportSrcFile = getClass().getResource("/reports/Receipt_bs.jrxml").getFile();
        }
        else if(reportType.equals("Receipt_en")){
            reportSrcFile = getClass().getResource("/reports/Receipt_en.jrxml").getFile();
        }


        String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = p;
        parameters.put("reportsDirPath", reportsDir);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }
}
