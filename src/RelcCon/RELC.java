package RelcCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RELC {
    public static Connection mycon(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/relc","root", "");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
