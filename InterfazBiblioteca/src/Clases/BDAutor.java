/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import static Clases.BDEditorial.cbm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

public class BDAutor {
    private static PreparedStatement pst;
    private static Statement st;
    private static Connection conn;
    private static ResultSet rs;
    public static DefaultTableModel tm;
    
    public static void obtenerListaDisponibles(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_autor, nom_autor, ape_pat_autor, ape_mat_autor, estado_autor FROM autor WHERE estado_autor = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                cbm.addElement(new Autor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            cb.setModel(cbm);
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }        
    }
    
    public static void obtenerListaCompleta(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_autor, nom_autor, ape_pat_autor, ape_mat_autor, estado_autor FROM autor";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                cbm.addElement(new Autor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
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
        tm =  new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("Nombre");
        tm.addColumn("Apellido Paterno");
        tm.addColumn("Apellido Materno");        
        
        //Se genera consulta SQL para seleccionar todos los datos de la tabla:
        String sql = "SELECT * FROM autor WHERE estado_autor = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[4];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);                
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
        tm.addColumn("Nombre");
        tm.addColumn("Apellido Paterno");
        tm.addColumn("Apellido Materno");
        
        //Se genera consulta SQL para seleccionar datos de la tabla segun campo y criterio
        String sql = "SELECT * FROM autor WHERE " + criterio + " LIKE '%" + texto + "%' ";
        try{
            conn = BDConexion.getConexion();
            st = conn.prepareStatement(sql);          
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[4];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);
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
     * @param nom
     * @param apeP
     * @param apeM
     * 
     */
    public static void insertar(String nom, String apeP, String apeM){
        String sql = "INSERT INTO autor (nom_autor, ape_pat_autor, ape_mat_autor, estado_autor)"
                                                + "VALUES (?,?,?,1)";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, apeP);
            pst.setString(3, apeM);
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
     * @param nom
     * @param apeP
     * @param apeM
     * 
     */
    public static void modificar(int id, String nom, String apeP, String apeM){
        String sql = "UPDATE autor SET nom_autor = ?, ape_pat_autor = ?, ape_mat_autor = ? " + "WHERE " +id+ "= id_autor";                        
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, apeP);
            pst.setString(3, apeM);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
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
        String sql = "UPDATE AUTOR SET estado_autor = 0 WHERE id_autor = ?"; 
//        String sql = "DELETE FROM autor WHERE id_autor = ?"; 
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
    

