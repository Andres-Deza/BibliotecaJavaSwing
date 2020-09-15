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
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * La Clase BDLibro es la que conecta con la tabla Libro de la base de datos permitiendo realizar los CRUD en esta
 * @author JSC
 * Fecha creación 05-06-18
 */
public class BDLibro {
    private static PreparedStatement pst;
    private static Statement st;
    private static Connection conn;
    private static ResultSet rs;
    public static DefaultTableModel tm;
    public static List<Libro> listadoLibros = new ArrayList();
   
//    public static void generarListadoLibros() {
//        listadoLibros.add(new Libro(1, "5622335176764", "ISAAC NEWTON GRANDE ENTRE LOS GRANDES", 370, 2012, Arrays.asList(1,2), Arrays.asList(1), Arrays.asList(1,2)));
//        listadoLibros.add(new Libro(1, "9789562479332", "LA TREGUA", 208, 2015, Arrays.asList(5), Arrays.asList(5), Arrays.asList(1)));
//        listadoLibros.add(new Libro(1, "9788401352898", "LA CASA DE LOS ESPIRITUS", 512, 2011, Arrays.asList(6), Arrays.asList(6), Arrays.asList(1)));
//        listadoLibros.add(new Libro(1, "8433920634", "EL CARTERO", 192, 2013, Arrays.asList(6), Arrays.asList(6), Arrays.asList(4)));
//    }
    
    public static void obtenerListadoLibros(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        cbm.addElement("");
        for(int i = 0; i < listadoLibros.size(); i++){
            if(listadoLibros.get(i)!=null){
                cbm.addElement(listadoLibros.get(i));
            }            
        }
        cb.setModel(cbm);            
    }
   
    	 /**
	 * 
	 * Método que obtiene de la base de datos el listado de libros cuyo estado es disponible
	 *  
	 */
    
    
    public static void obtenerListaDisponibles(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_libro, id_editorial, isbn, titulo, numero_paginas, precio_referencia, anio FROM libro WHERE estado_libro = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                cbm.addElement(new Libro(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getFloat(6), rs.getInt(7)));
            }
            cb.setModel(cbm);
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }        
    }
    
        /**
	 * 
	 * Método que obtiene de la base de datos el listado completo de libros
	 *  
	 */
    
    public static void obtenerListaCompleta(JComboBox cb){
        cbm = new DefaultComboBoxModel();
        String sql = "SELECT id_libro, id_editorial, isbn, titulo, numero_paginas, precio_referencia, anio FROM libro";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al listado
            cbm.addElement("");
            while(rs.next()){
                cbm.addElement(new Libro(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getFloat(6), rs.getInt(7)));
            }
            cb.setModel(cbm);
            
        }catch(Exception e){
            System.err.println("Excepción al obtener lista: " + e);
        }        
    }
    
 /**
     * 
     * Metódo que obtiene los campos desde la base de datos, instancia una tabla,
     * almacena los datos en la tabla instanciada y la devuelve con los datos completados.
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
//        tm.addColumn("IdEstado");
        tm.addColumn("IdEditorial");
//        tm.addColumn("N° Serie");
        tm.addColumn("ISBN");
        tm.addColumn("Título");
        tm.addColumn("Páginas");
        tm.addColumn("Precio (REF)");
        tm.addColumn("Año");
        
        //Se genera consulta SQL para seleccionar todos los datos de la tabla
        String sql = "SELECT * FROM libro WHERE estado_libro = 1";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                int idLibro = rs.getInt(1);
                Object[] arr = new Object[7];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getInt(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);
                arr[4] = rs.getInt(5);
                arr[5] = rs.getFloat(6);
                arr[6] = rs.getInt(7);
                tm.addRow(arr);                
            }
            
            //Se carga modelo de tabla a la tabla principal
            tabla.setSelectionMode(0); //NO se pueden escoger multiples registros
            tabla.setModel(tm);            
        }catch(Exception e){
            System.err.println("Excepción al listar: " + e);
        }
    }
    
    public static void listarLibrosBiblio(JTable tabla){
        tm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("COD");
        tm.addColumn("IdEstado");
        tm.addColumn("IdEditorial");
        tm.addColumn("N° Serie");
        tm.addColumn("ISBN");
        tm.addColumn("Título");
        tm.addColumn("Páginas");
        tm.addColumn("Precio");
        tm.addColumn("Año");
        
        //Se genera consulta SQL para seleccionar todos los datos de la tabla
        String sql = "SELECT id_libro_b, B.id_libro, id_estado, id_editorial, numero_serie, isbn, titulo_b, numero_paginas, precio, anio "
                + "FROM libro L, libro_biblioteca B "
                + "WHERE L.id_libro=B.id_libro "
                + "AND estado_libro_b = 1";        
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                int idLibro = rs.getInt(1);
                Object[] arr = new Object[10];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getInt(2);
                arr[2] = rs.getInt(3);
                arr[3] = rs.getInt(4);
                arr[4] = rs.getString(5);
                arr[5] = rs.getString(6);
                arr[6] = rs.getString(7);
                arr[7] = rs.getInt(8);
                arr[8] = rs.getFloat(9);
                arr[9] = rs.getInt(10);
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
     * @param texto
     * @param criterio
     * 
     */
    public static void filtrar(JTable tabla, String criterio, String texto){
        tm = new DefaultTableModel();
        tm.addColumn("ID");
//        tm.addColumn("IdEstado");
        tm.addColumn("IdEditorial");
        tm.addColumn("N° Serie");
        tm.addColumn("ISBN");
        tm.addColumn("Título");
        tm.addColumn("Páginas");
        tm.addColumn("Precio (REF)");
        tm.addColumn("Año");
        
        //Se genera consulta SQL para seleccionar datos de la tabla segun campo y criterio
        String sql = "SELECT * FROM libro WHERE " + criterio + " LIKE '%" + texto + "%' ";
        try{
            conn = BDConexion.getConexion();
            st = conn.prepareStatement(sql);          
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agraga al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[7];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getInt(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);
                arr[4] = rs.getInt(5);
                arr[5] = rs.getFloat(6);
                arr[6] = rs.getInt(7);
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
     * Metódo para insertar un Libro en la base de datos, se insertan registros en las tablas Libro, categoria_Libro, autor_libro e idioma_Libro
     * @param lib
     *
     */   

    
    public static void insertar(Libro lib){
        String sqlLibro = "INSERT INTO libro (id_editorial, isbn, titulo, numero_paginas, precio_referencia, anio, estado_libro)"
                                                + "VALUES (?,?,?,?,?,?,1)";
        String sqlCategoriaLibro = "INSERT INTO categoria_libro (id_libro, id_categoria) VALUES (?,?)";
        String sqlAutorLibro = "INSERT INTO autor_libro (id_libro, id_autor) VALUES (?,?)";
        String sqlIdiomaLibro = "INSERT INTO idioma_libro (id_libro, id_idioma) VALUES (?,?)";
        int idGenerado = 0;
        try{
            //Se inserta nuevo libro
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sqlLibro, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, lib.getIdEditorial());
            pst.setString(2, lib.getISBN());
            pst.setString(3, lib.getTitulo());
            pst.setInt(4, lib.getPaginas());
            pst.setFloat(5, lib.getPrecio());
            pst.setInt(6, lib.getAnio());
            pst.execute();
            rs = pst.getGeneratedKeys();
            if(rs.next()){
                idGenerado = rs.getInt(1);
            }
            
            //Se insertan registros en tabla relacional categoria_libro           
            for(int i = 0; i < lib.getIdCategoria().size(); i++){
                pst = conn.prepareStatement(sqlCategoriaLibro);
                pst.setInt(1, idGenerado);
                pst.setInt(2, lib.getIdCategoria().get(i));       
                pst.execute();                
            }
            
            //Se insertan registros en tabla relacional autor_libro
            for(int i = 0; i < lib.getIdAutor().size(); i++){
                pst = conn.prepareStatement(sqlAutorLibro);
                pst.setInt(1, idGenerado);
                pst.setInt(2, lib.getIdAutor().get(i));
                pst.execute();                
            }
            
            //Se insertan registros en tabla relacional idioma_libro
            for(int i = 0; i < lib.getIdIdioma().size(); i++){
                pst = conn.prepareStatement(sqlIdiomaLibro);
                pst.setInt(1, idGenerado);
                pst.setInt(2, lib.getIdIdioma().get(i));
                pst.execute();               
            }            
            
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al agregar: " + e);
        }       
    }
      
        /**
     * 
     * Metódo para modificar la tabla Libro en la base de datos, se actualizan registros en las tablas Libro, categoria_Libro, autor_libro e idioma_Libro
     * @param lib
     * @param idLib
     * 
     */
    
    public static void modificar(Libro lib, int idLib){
        String sqlLibro = "UPDATE libro SET id_editorial = ?,"
                + " isbn = ?,"
                + " titulo = ?,"
                + " numero_paginas = ?,"
                + " precio_referencia = ?,"
                + " anio = ? WHERE id_libro = " + idLib;
        String sqlCategoriaLibro = "INSERT INTO categoria_libro (id_libro, id_categoria) VALUES (" + idLib + ",?)";
        String sqlAutorLibro = "INSERT INTO autor_libro (id_libro, id_autor) VALUES (" + idLib + ",?)";
        String sqlIdiomaLibro = "INSERT INTO idioma_libro (id_libro, id_idioma) VALUES (" + idLib + ",?)";
        try{
            //Se modifican registros tabla libro
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sqlLibro);
            pst.setInt(1, lib.getIdEditorial());
            pst.setString(2, lib.getISBN());
            pst.setString(3, lib.getTitulo());
            pst.setInt(4, lib.getPaginas());
            pst.setFloat(5, lib.getPrecio());
            pst.setInt(6, lib.getAnio());
            pst.execute();
            
            //Se eliminan registros antiguos de tabla relacional categoria_libro
            String sql = "DELETE FROM categoria_libro WHERE id_libro = " + idLib; 
            try{
                conn = BDConexion.getConexion();
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch(Exception e){
                System.err.println("Excepción al eliminar: " + e);
            }       
            //Se insertan nuevos registros en tabla relacional categoria_libro           
            for(int i = 0; i < lib.getIdCategoria().size(); i++){
                pst = conn.prepareStatement(sqlCategoriaLibro);
                pst.setInt(1, lib.getIdCategoria().get(i));
                System.out.println("i: " + i + " - " + "idCat: " + lib.getIdCategoria().get(i) );               
                pst.execute();                
            }
            
            //Se eliminan registros antiguos de tabla relacional autor_libro
            sql = "DELETE FROM autor_libro WHERE id_libro = " + idLib; 
            try{
                conn = BDConexion.getConexion();
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch(Exception e){
                System.err.println("Excepción al eliminar: " + e);
            }
            //Se insertan nuevos registros en tabla relacional autor_libro
            for(int i = 0; i < lib.getIdAutor().size(); i++){
                pst = conn.prepareStatement(sqlAutorLibro);
                pst.setInt(1, lib.getIdAutor().get(i));
                System.out.println("i: " + i + " - " + "idAut: " + lib.getIdCategoria().get(i) ); 
                pst.execute();                
            }
            
            //Se eliminan registros antiguos de tabla relacional idioma_libro
            sql = "DELETE FROM idioma_libro WHERE id_libro = " + idLib; 
            try{
                conn = BDConexion.getConexion();
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch(Exception e){
                System.err.println("Excepción al eliminar: " + e);
            }
            //Se insertan nuevos registros en tabla relacional autor_libro
            for(int i = 0; i < lib.getIdIdioma().size(); i++){
                pst = conn.prepareStatement(sqlIdiomaLibro);
                pst.setInt(1, lib.getIdIdioma().get(i));
                System.out.println("i: " + i + " - " + "idIdi: " + lib.getIdCategoria().get(i) ); 
                pst.execute();               
            }            
            
            JOptionPane.showMessageDialog(null,"Registro modificado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al modificar: " + e);
        }
    }
    
    public static void modificar(int idB, int idL, int idE, String serie){
        String sqlLibro = "UPDATE libro_biblioteca SET id_estado = ?, numero_serie = ? WHERE id_libro_b = " + idB;        
        try{
            //Se modifican registros tabla libro
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sqlLibro);
            pst.setInt(1, idE);
            pst.setString(2, serie);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro modificado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al modificar: " + e);
        }              
    }
    
     /**
     * 
     * Metódo que actualiza el estado de un libro en la tabla Libro de la base de datos, dejandolo inhabilitado.
     * @param id
     * 
     * 
     */
    
    public static void eliminar(int id){
        String sql = "UPDATE LIBRO SET estado_libro = 0 WHERE id_libro = ?"; 
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
     /**
     * 
     * Metódo que trae de la base de datos, todos los datos de un libro almacenado en la tabla
     * @param id
     * 
     * 
     */
    
    
    public static Libro buscarLibro(int id){
        //Se genera consulta SQL para seleccionar todos los datos de la tabla
        String sqlLibro = "SELECT * FROM libro WHERE id_libro = " + id;
        String sqlCategoria = "SELECT id_categoria FROM categoria_libro WHERE id_libro = " + id;
        String sqlAutor = "SELECT id_autor FROM autor_libro WHERE id_libro = " + id;
        String sqlIdioma = "SELECT id_idioma FROM idioma_libro WHERE id_libro = " + id;
                
        Libro lib = new Libro();
        List<Integer> lstCat = new ArrayList<>();
        List<Integer> lstAut = new ArrayList<>();
        List<Integer> lstIdm = new ArrayList<>();
        
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sqlLibro);
            while(rs.next()){
                lib.setId(rs.getInt(1));
                lib.setIdEditorial(rs.getInt(2));
                lib.setISBN(rs.getString(3));
                lib.setTitulo(rs.getString(4));
                lib.setPaginas(rs.getInt(5));
                lib.setPrecio(rs.getFloat(6));
                lib.setAnio(rs.getInt(7));
                lib.setEstado_libro(rs.getShort(8));
            }
            
            int cont=0;
            st = conn.createStatement();
            rs = st.executeQuery(sqlCategoria);
            while(rs.next()){
                lstCat.add(rs.getInt(1));
                cont++;
            }            
            lib.setIdCategoria(lstCat); 
            
            cont=0;
            st = conn.createStatement();
            rs = st.executeQuery(sqlAutor);
            while(rs.next()){
                lstAut.add(rs.getInt(1));
                cont++;
            }            
            lib.setIdAutor(lstAut);  
            
            cont=0;
            st = conn.createStatement();
            rs = st.executeQuery(sqlIdioma);
            while(rs.next()){
                lstIdm.add(rs.getInt(1));
                cont++;
            }
            lib.setIdIdioma(lstIdm);
            
            return lib;            
        }catch(Exception e){
            System.err.println("Error al buscar registro: " + e);
        }
        return null;
    }
    
    public static Libro buscarLibroBiblioteca(int id, int idL){
        String sqlLibro = "SELECT id_libro_b, id_estado, id_editorial, numero_serie, isbn, titulo_b, numero_paginas, precio, anio, B.id_libro "
                + "FROM libro L, libro_biblioteca B "
                + "WHERE L.id_libro=B.id_libro "
                + "AND estado_libro_b = 1 "
                + "AND id_libro_b = " + id;   
        
        String sqlCategoria = "SELECT id_categoria FROM categoria_libro WHERE id_libro = " + idL;
        String sqlAutor = "SELECT id_autor FROM autor_libro WHERE id_libro = " + idL;
        String sqlIdioma = "SELECT id_idioma FROM idioma_libro WHERE id_libro = " + idL;
                
        Libro lib = new Libro();
        List<Integer> lstCat = new ArrayList<>();
        List<Integer> lstAut = new ArrayList<>();
        List<Integer> lstIdm = new ArrayList<>();
        
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sqlLibro);
            while(rs.next()){
                lib.setId(rs.getInt(1));
                lib.setIdEstado(rs.getInt(2));
                lib.setIdEditorial(rs.getInt(3));
                lib.setSerie(rs.getString(4));
                lib.setISBN(rs.getString(5));
                lib.setTitulo(rs.getString(6));
                lib.setPaginas(rs.getInt(7));
                lib.setPrecio(rs.getFloat(8));
                lib.setAnio(rs.getInt(9));
                lib.setIdL(10);
            }
            
            int cont=0;
            st = conn.createStatement();
            rs = st.executeQuery(sqlCategoria);
            while(rs.next()){
                lstCat.add(rs.getInt(1));
                cont++;
            }            
            lib.setIdCategoria(lstCat); 
            
            cont=0;
            st = conn.createStatement();
            rs = st.executeQuery(sqlAutor);
            while(rs.next()){
                lstAut.add(rs.getInt(1));
                cont++;
            }            
            lib.setIdAutor(lstAut);  
            
            cont=0;
            st = conn.createStatement();
            rs = st.executeQuery(sqlIdioma);
            while(rs.next()){
                lstIdm.add(rs.getInt(1));
                cont++;
            }
            lib.setIdIdioma(lstIdm);
            
            return lib;            
        }catch(Exception e){
            System.err.println("Error al buscar registro: " + e);
        }
        return null;
    }
}
