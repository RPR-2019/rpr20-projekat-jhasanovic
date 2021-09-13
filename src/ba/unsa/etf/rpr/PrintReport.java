package ba.unsa.etf.rpr;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintReport extends JFrame {
    public void showReport(Connection conn, String reportType, HashMap<String, Object> p) throws JRException {
        String reportSrcFile = switch (reportType) {
            case "Generalni izvještaj" -> getClass().getResource("/reports/Report1_bs.jrxml").getFile();
            case "Dnevni izvještaj" -> getClass().getResource("/reports/Report2_bs.jrxml").getFile();
            case "General report" -> getClass().getResource("/reports/Report1_en.jrxml").getFile();
            case "Daily report" -> getClass().getResource("/reports/Report2_en.jrxml").getFile();
            case "Receipt_bs" -> getClass().getResource("/reports/Receipt_bs.jrxml").getFile();
            case "Receipt_en" -> getClass().getResource("/reports/Receipt_en.jrxml").getFile();
            case "Izvještaj o stanju" -> getClass().getResource("/reports/Report3_bs.jrxml").getFile();
            case "Products in stock" -> getClass().getResource("/reports/Report3_en.jrxml").getFile();
            default -> "";
        };


        String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        p.put("reportsDirPath", reportsDir);
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(p);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, p, conn);
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }
}
