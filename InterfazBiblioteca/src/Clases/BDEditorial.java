/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JSC
 */
public class BDEditorial {
    private static PreparedStatement pst;
    private static Statement st;
    private static Connection conn;
    private static ResultSet rs;
    public static DefaultTableModel tm;
    public static DefaultComboBoxModel cbm;
    
    public static Editorial buscar( int ed ){
        String sql = "Select id_editorial, nom_editorial FROM editorial WHERE id_editorial = " + ed;
        Editorial edit = new Editorial();
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){                
                edit.setId(rs.getInt(1));
                edit.setNombre(rs.getString(2));                
            }
            return edit;
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }   
        return null;
    }
    
     public static void obtenerListaCompleta(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_editorial, nom_editorial from editorial";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){                
                cbm.addElement(new Editorial(rs.getInt(1), rs.getString(2)));
            }
            cb.setModel(cbm);
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }        
    }
    
    public static void obtenerListaDisponibles(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_editorial, nom_editorial FROM editorial WHERE estado_editorial = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){                
                cbm.addElement(new Editorial(rs.getInt(1), rs.getString(2)));
            }
            cb.setModel(cbm);
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }        
    }
    
    /**
     * 
     * Metódo para listar datos desde la base de datos a la tabla instanciada
     * @param tabla 
     * 
     */
    public static void listar(JTable tabla){
        tm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("Rut");
        tm.addColumn("Nombre");
        tm.addColumn("Dirección");
        tm.addColumn("Teléfono");
        tm.addColumn("Correo");
        
        //Se genera consulta SQL para seleccionar todos los datos de la tabla
        String sql = "SELECT * FROM editorial WHERE estado_editorial = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[6];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);
                arr[4] = rs.getInt(5);
                arr[5] = rs.getString(6);
                tm.addRow(arr);                
            }
            
            //Se carga modelo de tabla a la tabla principal
            tabla.setSelectionMode(0); //NO se pueden escoger multiples registros
            tabla.setModel(tm);            
        }catch(Exception e){
            System.err.println("Excepción al listar: " + e);
        }
    }
    
    /**
     * 
     * Metódo para filtrar datos desde la base de datos a la tabla instanciada según criterio de búsqueda
     * @param tabla
     * @param campo
     * @param criterio
     * 
     */
    public static void filtrar(JTable tabla, String criterio, String texto){
        tm = new DefaultTableModel();
        tm.addColumn("ID");
        tm.addColumn("Rut");
        tm.addColumn("Nombre");
        tm.addColumn("Dirección");
        tm.addColumn("Teléfono");
        tm.addColumn("Correo");
        
        //Se genera consulta SQL para seleccionar datos de la tabla segun campo y criterio
        String sql = "SELECT * FROM editorial WHERE " + criterio + " LIKE '%" + texto + "%' ";
        try{
            conn = BDConexion.getConexion();
            st = conn.prepareStatement(sql);          
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[6];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);
                arr[4] = rs.getInt(5);
                arr[5] = rs.getString(6);
                tm.addRow(arr);                
            }
            
            //Se carga modelo de tabla a la tabla principal
            tabla.setModel(tm);
        }catch(Exception e){
            System.err.println("Excepción al filtrar: " + e);
        }
    }
    
    /**
     * 
     * Método para agregar un nuevo registro a la base de datos
     * @param rut
     * @param nom
     * @param dir
     * @param tel
     * @param cor 
     * 
     */
    public static void insertar(String rut, String nom, String dir, int tel, String cor){
        String sql = "INSERT INTO editorial (rut_editorial, nom_editorial, direccion_editorial, telefono_editorial, correo_editorial, estado_editorial)"
                                                + "VALUES (?,?,?,?,?,1)";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rut);
            pst.setString(2, nom);
            pst.setString(3, dir);
            pst.setInt(4, tel);
            pst.setString(5, cor);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al agregar: " + e);
        }
    }
    
    /**
     * 
     * Método para modificar un registro de la base de datos
     * @param id
     * @param rut
     * @param nom
     * @param dir
     * @param tel
     * @param cor 
     * 
     */
    public static void modificar(int id, String rut, String nom, String dir, int tel, String cor){
        String sql = "UPDATE editorial SET rut_editorial = ?, nom_editorial = ?, direccion_editorial = ?, telefono_editorial = ?, correo_editorial = ? " 
                        + "WHERE " +id+ "= id_editorial";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rut);
            pst.setString(2, nom);
            pst.setString(3, dir);
            pst.setInt(4, tel);
            pst.setString(5, cor);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro modificado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al modificar: " + e);
        }
    }
    
    /**
     * 
     * Método para eliminar un registro de la base de datos
     * @param id 
     * 
     */
    public static void eliminar(int id){
//        String sql = "DELETE FROM editorial WHERE id_editorial = ?"; 
        String sql = "UPDATE editorial SET estado_editorial = 0 WHERE id_editorial = ?"; 
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro eliminado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al eliminar: " + e);
        }
    }
    
}
