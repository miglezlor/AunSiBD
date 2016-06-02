/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgl.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author miglezlor
 */
public class Database {
    
    Connection conexion = null;
    /** Metodo para establecer la conexion con la base
     * @return
     * @throws ClassNotFoundException 
     */
    public Connection iniciarConexion(String urlDB, String usuario, String pass){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(urlDB,usuario,pass);
            JOptionPane.showMessageDialog(null, "Se ha establecido conexion");
        } catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, "No se ha establecido conexion");
    }
        return conexion;
    }
    /** Metodo para insertar valores en una tabla
     * en la base de datos
     * @param nombreTabla
     * @param nombreColumna
     * @param valores
     * @return
     * @throws SQLException 
     */
    public void insertar(String nombreTabla, String valores){
        
        try {
            PreparedStatement prepstat= conexion.prepareStatement("insert into "+nombreTabla+" VALUES("+valores+")");
            prepstat.executeUpdate();
       
        } catch (SQLException ex) {
            System.out.println("Error en la insercion");
        }
    }
    /** Metodo para eliminar
      * @param nombreTabla
      * @param ID
      * @return
      * @throws SQLException 
      */
    public void borrar(String nombreTabla, int ID){
        
        try {
            PreparedStatement prepstat=conexion.prepareStatement("delete from " +nombreTabla+" where id="+ID);
            prepstat.executeUpdate();
       
        
        } catch (SQLException ex) {
            System.out.println("Error en el delete");
        }
    }
    /** Metodo para visualizar
      * @param nombreTabla
      * @param numcolumnas
      * @param datosVer
      * @return
      * @throws SQLException 
      */
     public String consultaDatos(String nombreTabla,int numcolumnas, String datosVer){
        
        String visualizar="";
        try {
            Statement prepstat= conexion.createStatement();
            ResultSet resset= prepstat.executeQuery("select "+datosVer+" from "+nombreTabla);
            String [] datos= new String[numcolumnas-1];
            while(resset.next()){
          
                for (int i = 0; i < datos.length; i++) {
                    datos[i]=resset.getString(i+1);
                    visualizar= visualizar +" "+datos[i];
                
                }
            }
                   
        } catch (SQLException ex) {
            System.out.println("Error en la visualizacion");
        }
        return visualizar;
    }
    
     public void cerrarConexion() throws SQLException{
         conexion.close();
     }
}
