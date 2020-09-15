/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import static Clases.BDEditorial.cbm;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JSC
 */
public class BDDistribuidor {
    private static PreparedStatement pst;
    private static Statement st;
    private static Connection conn;
    private static ResultSet rs;
    public static DefaultTableModel tm;
    
    public static void obtenerListaDisponibles(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_distribuidor, nombre_distribuidor FROM distribuidor WHERE estado_distribuidor = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                cbm.addElement(new Distribuidor(rs.getInt(1), rs.getString(2)));
            }
            cb.setModel(cbm);
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }        
    }
    
    public static void obtenerListaCompleta(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_distribuidor, nombre_distribuidor FROM distribuidor";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                cbm.addElement(new Distribuidor(rs.getInt(1), rs.getString(2)));
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
        tm.addColumn("Año inicio");
        
        //Se genera consulta SQL para seleccionar todos los datos de la tabla
        String sql = "SELECT * FROM distribuidor WHERE estado_distribuidor = 1";
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
                arr[5] = rs.getInt(6);
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
        tm.addColumn("Año inicio");
        
        //Se genera consulta SQL para seleccionar datos de la tabla segun campo y criterio
        String sql = "SELECT * FROM distribuidor WHERE " + criterio + " LIKE '%" + texto + "%' AND estado_distribuidor = 1";
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
                arr[5] = rs.getInt(6);
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
     * @param anio 
     * 
     */
    public static void insertar(String rut, String nom, String dir, int tel, int anio){
        String sql = "INSERT INTO distribuidor (rut_distribuidor, nombre_distribuidor, direccion_distribuidor, telefono_distribuidor, anio_inicio, estado_distribuidor)"
                                                + "VALUES (?,?,?,?,?,1)";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rut);
            pst.setString(2, nom);
            pst.setString(3, dir);
            pst.setInt(4, tel);
            pst.setInt(5, anio);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch (MySQLIntegrityConstraintViolationException e) {
            System.err.println("RUT ya existe - " + e);
            JOptionPane.showMessageDialog(null,"El rut ya existe","ERROR",0);
        }catch (SQLException e2){
            System.err.println("Error al agregar: " + e2);
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
     * @param anio 
     * 
     */
    public static void modificar(int id, String rut, String nom, String dir, int tel, int anio){
        String sql = "UPDATE distribuidor SET rut_distribuidor = ?, nombre_distribuidor = ?, direccion_distribuidor = ?, telefono_distribuidor = ?, anio_inicio = ? " 
                        + "WHERE " +id+ "= id_distribuidor";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rut);
            pst.setString(2, nom);
            pst.setString(3, dir);
            pst.setInt(4, tel);
            pst.setInt(5, anio);
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
//        String sql = "DELETE FROM distribuidor WHERE id_distribuidor = ?"; 
        String sql = "UPDATE distribuidor set estado_distribuidor = 0 WHERE id_distribuidor = ?"; 
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
    
    public static Distribuidor buscar(int folio){
        String sql = "SELECT * FROM distribuidor D, factura F "
                + "WHERE D.id_distribuidor = F.id_distribuidor "
                + "AND folio_factura = " + folio;
        int cont = 0;        
        Distribuidor dis = new Distribuidor();
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                dis.setId(rs.getInt(1));
                dis.setRut(rs.getString(2));
                dis.setNombre(rs.getString(3));
                dis.setDireccion(rs.getString(4));
                dis.setTelefono(rs.getInt(5));
                dis.setAnio(rs.getShort(6));
            }
            return dis;            
        }catch(Exception e){
            System.err.println("Excepción al validar Folio: " + e);
            return null;
        }
    }    
}
