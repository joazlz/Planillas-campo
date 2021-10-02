package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class ConexionBD {
    
    private static Connection conn;
    //private static final String url="jdbc:oracle:thin:@192.168.1.3:1521:xe";
    private static final String url="jdbc:oracle:thin:@localhost:1521:xe";
    public final String user_admin="db_admin";

    
    public ConexionBD(){
        conn=null;
        try {
           conn = DriverManager.getConnection(url,"db_mruizr","1234");
        }catch (java.sql.SQLRecoverableException e){
            JOptionPane.showMessageDialog(null, "Fallo la Conexion <SQL Conexion> \nServicios oracle apagados: Tu Direccion puede haber cambiado\n"+ e );
            System.exit(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo la Conexion <SQL Conexion>\n ERROR:"+ ex);
            System.exit(0);
        }
    }
    public ConexionBD(String user, String password){
        conn=null;
        try {
           conn = DriverManager.getConnection(url,user,password);
           conn.setClientInfo("user_admin", "db_admin");
        }catch (java.sql.SQLRecoverableException e){
            JOptionPane.showMessageDialog(null, "Fallo la Conexion <SQL Conexion> \nServicios oracle apagados: Tu Direccion puede haber cambiado\n"+ e );
            System.exit(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo la Conexion <SQL Conexion>\n ERROR:"+ ex);
            System.exit(0);
        }
    }
    
    public Connection getConnection(){
        return conn;
    }
    
    public void desconectar(){
        conn=null;
    }

    
}

