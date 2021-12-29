/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basico.bdconexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author crism
 */
public class Conexion {
private Connection con;

    public boolean conectar() {
        try {   
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","INCAP","CIRILA");
            return true;
        }catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Error en la conexión de la base de datos");
            ex.printStackTrace();
            return false;
        }
    }
    public Connection getCon() {
        return con;
    }

    public void desconectar() {
        try {
            this.con.close();
        }catch(Exception ex) {      
        }
    }
}
