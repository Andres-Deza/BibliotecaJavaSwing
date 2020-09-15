/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

	/**
	 * La clase BDConexion es la encargada de realizar la conexión a la base de datos de la biblioteca
	 * @author jsoto
	 * Fecha creación 05/06/18
	 */
public class BDConexion {
    private static String NOMBRE_BD = "biblioteca";
    private static String USUARIO_BD = "root";
//    private static String USUARIO_BD = "jsoto";
    private static String CLAVE_BD = "";
//    private static String CLAVE_BD = "js123";
    private static String DRIVER_BD = "com.mysql.jdbc.Driver";
    private static Connection conn;
    
    public static Connection getConexion() {
        try {
            Class.forName(DRIVER_BD);
            if(conn == null)conn = DriverManager.getConnection("jdbc:mysql://localhost/" + NOMBRE_BD, USUARIO_BD, CLAVE_BD);
//            if(conn == null)conn = DriverManager.getConnection("jdbc:mysql://192.168.1.2:3306/" + NOMBRE_BD, USUARIO_BD, CLAVE_BD);
            return conn;            
        }catch(Exception e){            
            System.err.println("Excepción en ConexionBD.getConexion: " + e);
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos","ERROR",0);
            return null;
        }
    }

    	 /**
	 * Método que realiza la validación de los usuarios al conectarse a la base de datos
	 * @param nom
	 * @param rut 
	 */
    
    public static boolean validaUsuario(String nom, String rut){
        String v_nom = "";
        String v_rut = "";
        try{
            String sql = "select nom_trabajador, rut_trabajador "
                    + "from trabajador "
                    + "where nom_trabajador = '"+nom+"' "
                    + "and rut_trabajador='"+rut+"'";
            Statement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                v_nom = rs.getString("nom_trabajador").trim();
                v_rut = rs.getString("rut_trabajador").trim();
            }
            if (nom.equals(v_nom) && rut.equals(v_rut)){
                return true;
            }
        }catch(Exception e){}
        return false;
    }
}
