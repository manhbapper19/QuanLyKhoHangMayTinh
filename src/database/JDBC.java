package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ASUS
 */
public class JDBC {
    
    public static Connection getJDBCConnection(){
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;      
    }
    public static void main(String[] args) {
        Connection conn = getJDBCConnection();
        System.out.println(conn != null ? "ket noi thanh cong!" : "ket noi that bai!");
    }
}
