package Reports;

import RelcCon.RELC;
import java.awt.Container;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;



public class ReportView extends JFrame
{
    public ReportView(String fileName)
    {
        this(fileName, null);
    }
    public ReportView(String fileName, HashMap para){
        super("RELC");
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/logo64.png")));
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo64.png")).getImage());

        Connection con = RELC.mycon();

        try
        {
            JasperPrint print = JasperFillManager.fillReport(fileName, para, con);
            JRViewer viewer = new JRViewer(print);
            Container c = getContentPane();
            c.add(viewer);            
        } 
        catch (JRException jRException)
        {
            System.out.println(jRException);
        }
        setBounds(2, 2, 900, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
