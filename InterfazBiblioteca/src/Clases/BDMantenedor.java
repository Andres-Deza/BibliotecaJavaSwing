/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import static Clases.BDEditorial.cbm;
import Pantallas.PantallaMantenedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jsoto
 */
public class BDMantenedor {
    private static PreparedStatement pst;
    private static Statement st;
    private static Connection conn;
    private static ResultSet rs;
    public static DefaultTableModel tm;
    
    /**
     * 
     * @param cb
     * @param id
     * @param tabla
     * @param atrib 
     */
    public static void obtenerLista(JComboBox cb, String id, String tabla, String atrib){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT " + id + ", " + atrib + " FROM " + tabla;
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                if(tabla.equals("estado")){
                    cbm.addElement(new Estado(rs.getInt(1), rs.getString(2)));
                }else if(tabla.equals("categoria")){
                    cbm.addElement(new Categoria(rs.getInt(1), rs.getString(2)));
                }else if(tabla.equals("idioma")){
                    cbm.addElement(new Idioma(rs.getInt(1), rs.getString(2)));
                }else if(tabla.equals("metodopago")){
                 //   cbm.addElement(new MetodoPago(rs.getInt(1), rs.getString(2)));
                }
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
    public static void listar(JTable tabla, String nomTabla){
        tm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("Nombre");
        
        //Se genera consulta SQL para seleccionar todos los datos de la tabla
        String sql = "SELECT * FROM " + nomTabla;
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[2];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                tm.addRow(arr);                
            }
            
            //Se carga modelo de tabla a la tabla principal
            tabla.setSelectionMode(0); //NO se pueden escoger multiples registros
//            tabla.setAutoResizeMode(0);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
            tabla.setModel(tm);            
        }catch(Exception e){
            System.err.println("Excepción al listar: " + e);
        }
    }
    
    public static void ventanaAgregar(String titulo, String tabla, String atrib){
        PantallaMantenedor pm = new PantallaMantenedor();
        pm.lblTitulo.setText(titulo);
        pm.lblTabla.setText(tabla);
        pm.lblAtrib.setText(atrib);
        pm.lblID.setText("");      
        pm.txtNombre.setText("");      
        pm.setLocationRelativeTo(null);
        pm.setVisible(true);
    }
    
    public static void ventanaModificar(String titulo, String tabla, String atrib, String idNom, String id, String nom){
        PantallaMantenedor pm = new PantallaMantenedor();
        pm.lblTitulo.setText(titulo);
        pm.lblTabla.setText(tabla);
        pm.lblAtrib.setText(atrib);
        pm.lblAtribId.setText(idNom);
        pm.lblID.setText(id);      
        pm.txtNombre.setText(nom); 
        pm.setLocationRelativeTo(null);
        pm.setVisible(true); 
    }
    
    public static void insertar(String tabla, String atrib, String nom){
        String sql = "INSERT INTO " + tabla + " (" + atrib + ") VALUES (?)";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, nom);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);            
        }catch(Exception e){
            System.err.println("Excepción al agregar: " + e);
        }
        
    }
    
    public static void modificar(String tabla, String atrib, String idNom, int id, String nom){
        String sql = "UPDATE " + tabla + " SET " + atrib + " = ? WHERE " + idNom + " = ?";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setInt(2, id);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al agregar: " + e);
        }        
    }
    
  
//    En prueba
    public static String buscar(String tabla, String atrib, String atribId, int id){
        String sql = "SELECT " + atrib + " FROM " + tabla + " WHERE " + atribId + "=" + id;
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            return rs.getString(1);
        }catch(Exception e){
            System.err.println("Excepción al listar: " + e);
        }
        return null;
    }
    
    
    
}
