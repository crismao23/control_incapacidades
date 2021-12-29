package basico.bdconexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * @author crism
 */

public class Bd {
    //variables de la tabla INCAPACIDAD/PRESCRIPCION
    int idInc = 0;
    String dateInc = "";
    String diasInc = "";
    String ccInc = "";
    String codDiag1 = "";
    String codDiag2 = "-";
    String codDiag3 = "-";
    
    //variables de la tabla PERSONA
    
    String ccPer = "";
    String nomPer = "";
    String apePer = "";
    String depPer = "";
    
    //variables de la tabla DIAGNOSTICO
    
    String codDiag = "";
    String nomDiag = "";
    
    //variables de la tabla DEPENDENCIA
    
    String codDep = "";
    String nomDep = "";
    
    //variables de la licencia
    String ccLic = "";
    String obsLic = "";
    String fechaLicIni = "";
    String fechaLicFin = "";  
    

    public void viewTable(Connection con) throws SQLException {
        String query = "select cedula, nombre "
                    + "from PERSONA "
                    + "where CEDULA LIKE '91%'";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
              String cedula = rs.getString("cedula");
              String nombre = rs.getString("nombre");
              //int supplierID = rs.getInt("SUP_ID");
              //float price = rs.getFloat("PRICE");
              System.out.println(cedula + ", " + nombre );
            }
        } catch (SQLException e) {
          e.printStackTrace();
        }
    } 
    public void updateData(Connection con) throws SQLException {
        String query = "update licencia set observaciones='actualizacion desde java' where idInc=2";
        try (Statement stmt = con.createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            int count = stmt.executeUpdate(query);
            System.out.println("Number of rows updated in database =  " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //CONSULTAS
    //Se crea clase que genera el nro_incapacidad siempre aumentando en 1
    public void consecutivo() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();         
        String query = "select MAX(NRO_INCAPACIDAD) from INCAPACIDAD ";
        try (Statement stmt = cn.getCon().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
              idInc = rs.getInt("MAX(NRO_INCAPACIDAD)");
            }
        cn.desconectar();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
    
    public void consultarDependencias(JComboBox cbox_dependencias){
        Conexion cn = new Conexion();
        cn.conectar();
        String query = "select CODDEPENDENCIA || ' - ' || NOMBRE_DEPENDENCIA AS DP from DEPENDENCIA";
        try (Statement stmt = cn.getCon().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            cbox_dependencias.addItem("Seleccione una dependencia");
            while (rs.next()) {
              cbox_dependencias.addItem(rs.getString("DP"));
            }
        cn.desconectar();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
    public void consultarDiagnostico(JComboBox cbox_personas){
        Conexion cn = new Conexion();
        cn.conectar();
        String query = "select CODDIAGNOSTICO || ' - ' || NOMBRE AS DI from DIAGNOSTICO ORDER BY CODDIAGNOSTICO ASC";
        try (Statement stmt = cn.getCon().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            cbox_personas.addItem("-");
            while (rs.next()) {
              cbox_personas.addItem(rs.getString("DI"));
            }
        cn.desconectar();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }    
    
    //INSERTAR
     
    public void insertDataIncap() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();  
        String queryInc = "insert into INCAPACIDAD (fecha_inicio, cantidad_dias, cedula)"
                        + "values ('"+ dateInc +"', '"+ diasInc +"','"+ ccInc +"')";
  
        try (Statement stmt = cn.getCon().createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            int count = stmt.executeUpdate(queryInc);

            JOptionPane.showMessageDialog(null, "Se ha insertado adecuadamente " + count + " incapacidad");
            cn.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void insertDataLic() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();        
        String queryLic = "insert into LICENCIA (FECHAINICIO, FECHAFIN, CEDULA, OBSERVACIONES)"
                        + "values ('"+ fechaLicIni +"', '"+ fechaLicFin +"','"+ ccLic +"','"+ obsLic +"')";
  
        try (Statement stmt = cn.getCon().createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            int count = stmt.executeUpdate(queryLic);
            JOptionPane.showMessageDialog(null, "Se ha insertado adecuadamente " + count + " licencia");
            cn.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    public void insertDataPresc() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();
        String queryPre1 = "insert into PRESCRIPCION (nro_incapacidad,coddiagnostico)"
                        + "values ("+ idInc +", '"+ codDiag1 +"')";
        
        try (Statement stmt = cn.getCon().createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            stmt.executeUpdate(queryPre1);
            
            if(!"-".equals(codDiag2)){
                String queryPre2 = "insert into PRESCRIPCION (nro_incapacidad,coddiagnostico)"
                + "values ("+ idInc +", '"+ codDiag2 +"')";
                stmt.executeUpdate(queryPre2);
            }
            //!codDiag3.isEmpty()
            if(!"-".equals(codDiag3)){
                String queryPre3 = "insert into PRESCRIPCION (nro_incapacidad,coddiagnostico)"
                + "values ("+ idInc +", '"+ codDiag3 +"')";
                stmt.executeUpdate(queryPre3);
            }
            cn.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void insertDataPersona() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();
        String queryPer = "insert into PERSONA (CEDULA, NOMBRE, CODDEPENDENCIA)"
                        + "values ('"+ ccPer +"', '"+ apePer + " "+nomPer +"','"+ depPer +"')";
  
        try (Statement stmt = cn.getCon().createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            int count = stmt.executeUpdate(queryPer);
            JOptionPane.showMessageDialog(null, "Se ha insertado adecuadamente " + count + " persona");
            cn.desconectar();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void insertDataDiag() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();
        String queryDiag = "insert into DIAGNOSTICO (CODDIAGNOSTICO, NOMBRE)"
                        + "values ('"+ codDiag +"', '"+ nomDiag +"')";
  
        try (Statement stmt = cn.getCon().createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            int count = stmt.executeUpdate(queryDiag);

            JOptionPane.showMessageDialog(null, "Se ha insertado adecuadamente " + count + " diagnóstico");
            cn.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void insertDataDep() throws SQLException {
        Conexion cn = new Conexion();
        cn.conectar();
        String queryDep = "insert into DEPENDENCIA (CODDEPENDENCIA, NOMBRE_DEPENDENCIA)"
                        + "values ('"+ codDep +"', '"+ nomDep +"')";
  
        try (Statement stmt = cn.getCon().createStatement()) {
            //Executing a SQL INSERT query using executeUpdate()  method of Statement object.
            int count = stmt.executeUpdate(queryDep);

            JOptionPane.showMessageDialog(null, "Se ha insertado adecuadamente " + count + " dependencia");
            cn.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    

    public String getDateInc() {
        return dateInc;
    }

    public void setDateInc(String dateInc) {
        this.dateInc = dateInc;
    }

    public String getDiasInc() {
        return diasInc;
    }

    public void setDiasInc(String diasInc) {
        this.diasInc = diasInc;
    }

    public String getCcInc() {
        return ccInc;
    }

    public void setCcInc(String ccInc) {
        this.ccInc = ccInc;
    }

    public String getCodDiag1() {
        return codDiag1;
    }

    public void setCodDiag1(String codDiag1) {
        this.codDiag1 = codDiag1;
    }

    public String getCodDiag2() {
        return codDiag2;
    }

    public void setCodDiag2(String codDiag2) {
        this.codDiag2 = codDiag2;
    }

    public String getCodDiag3() {
        return codDiag3;
    }

    public void setCodDiag3(String codDiag3) {
        this.codDiag3 = codDiag3;
    }

    public int getIdInc() {
        return idInc;
    }

    public void setIdInc(int idInc) {
        this.idInc = idInc;
    }

    public String getCcPer() {
        return ccPer;
    }

    public void setCcPer(String ccPer) {
        this.ccPer = ccPer;
    }

    public String getNomPer() {
        return nomPer;
    }

    public void setNomPer(String nomPer) {
        this.nomPer = nomPer;
    }

    public String getApePer() {
        return apePer;
    }

    public void setApePer(String apePer) {
        this.apePer = apePer;
    }

    public String getDepPer() {
        return depPer;
    }

    public void setDepPer(String depPer) {
        this.depPer = depPer;
    }

    public String getCodDiag() {
        return codDiag;
    }

    public void setCodDiag(String codDiag) {
        this.codDiag = codDiag;
    }

    public String getNomDiag() {
        return nomDiag;
    }

    public void setNomDiag(String nomDiag) {
        this.nomDiag = nomDiag;
    }

    public String getCodDep() {
        return codDep;
    }

    public void setCodDep(String codDep) {
        this.codDep = codDep;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getCcLic() {
        return ccLic;
    }

    public void setCcLic(String ccLic) {
        this.ccLic = ccLic;
    }

    public String getObsLic() {
        return obsLic;
    }

    public void setObsLic(String obsLic) {
        this.obsLic = obsLic;
    }

    public String getFechaLicIni() {
        return fechaLicIni;
    }

    public void setFechaLicIni(String fechaLicIni) {
        this.fechaLicIni = fechaLicIni;
    }

    public String getFechaLicFin() {
        return fechaLicFin;
    }

    public void setFechaLicFin(String fechaLicFin) {
        this.fechaLicFin = fechaLicFin;
    }
    
    
}
