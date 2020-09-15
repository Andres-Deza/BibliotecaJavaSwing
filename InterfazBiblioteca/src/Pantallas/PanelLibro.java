/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import Clases.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jsoto
 */
public class PanelLibro extends javax.swing.JPanel {

    /**
     * Creates new form AgregarIdioma
     */
    public PanelLibro() {
        initComponents();
        
        tableTablaDatos.getTableHeader().setReorderingAllowed(false);
        BDLibro.listar(tableTablaDatos);
        comprobarCampos();
        panelCampos.setVisible(false);
        panelInferiorBotones.setVisible(false);
    }

    /**
     * Limpiar para actualizar la tabla cada vez que se registren cambios
     */
    public void refrescarTabla(){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Id");
//        modeloTabla.addColumn("Estado");	
        modeloTabla.addColumn("Editorial");	
//        modeloTabla.addColumn("Serie");
        modeloTabla.addColumn("ISBN");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Páginas");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Año");
        tableTablaDatos.getTableHeader().setReorderingAllowed(false);
        tableTablaDatos.setModel( modeloTabla );
        panelTabla.setVisible(true);
    }
    
    public void limpiarListas(){
        DefaultListModel lm = new DefaultListModel();
        lm.removeAllElements();
        
        jListAutor.setModel(lm);
        jListCategoria.setModel(lm);
        jListIdioma.setModel(lm);
    }
    
    public void llenarListas(){
        BDMantenedor.obtenerLista(cbCategoria, "id_categoria", "categoria", "categoria");
        BDMantenedor.obtenerLista(cbIdioma, "id_idioma", "idioma", "idioma");
    }
    
    /**
     * Método para comprobar los campos, habilitar y deshabilitar botones de acción
     */
    public void comprobarCampos(){
        //Se compruebas campos vacíos
        String cc = txtISBN.getText() + txtTitulo.getText() + txtPaginas.getText() + txtPrecio.getText();
        if( cc.equals("")){
            btnGuardar.setEnabled(false);
            btnLimpiar.setEnabled(false);            
        }else if (txtISBN.getText().isEmpty() ||
                txtTitulo.getText().isEmpty() || txtPaginas.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            btnGuardar.setEnabled(false);
            btnLimpiar.setEnabled(true);
        }else{
            btnGuardar.setEnabled(true);
            btnLimpiar.setEnabled(true);
        }
        
        //Se desactivan botones para agregar items a la lista si no hay ninguno elegido
        if(cbCategoria.getSelectedIndex() == 0){
            btnAgregarCategoria.setEnabled(false);
        }else{
            btnAgregarCategoria.setEnabled(true);
        }
        if(cbAutor.getSelectedIndex() == 0){
            btnAgregarAutor.setEnabled(false);
        }else{
            btnAgregarAutor.setEnabled(true);
        }
        if(cbIdioma.getSelectedIndex() == 0){
            btnAgregarIdioma.setEnabled(false);
        }else{
            btnAgregarIdioma.setEnabled(true);
        }
        
        //Se desactivan botones para eliminar items de la lista si no hay ninguno elegido
        if(jListCategoria.getSelectedIndex() == -1){
           btnEliminarCategoria.setEnabled(false);
        }else{
            btnEliminarCategoria.setEnabled(true);
        }
        if(jListAutor.getSelectedIndex() == -1){
           btnEliminarAutor.setEnabled(false);
        }else{
            btnEliminarAutor.setEnabled(true);
        }
        if(jListIdioma.getSelectedIndex() == -1){
           btnEliminarIdioma.setEnabled(false);
        }else{
            btnEliminarIdioma.setEnabled(true);
        }
                
        //Se activan o desactivan botones para modificar y eliminar si no hay registro seleccionado
        if (tableTablaDatos.getSelectedRow() == -1){
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }else{
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }        
    }
    
    public void obtenerCampos(){
        //Se obtienen datos de fila seleccionada en la tabla y se cargan los campos correspondientes
        
        llenarListas();
        BDAutor.obtenerListaCompleta(cbAutor);
        BDEditorial.obtenerListaCompleta(cbEditorial);
        int rowSel = tableTablaDatos.getSelectedRow();
        int idLib = (Integer)tableTablaDatos.getValueAt(rowSel, 0);
        Libro lib = BDLibro.buscarLibro(idLib); 
        
        lblID.setText(String.valueOf(lib.getId()));
        txtTitulo.setText(lib.getTitulo());
        txtISBN.setText(lib.getISBN());
        txtPaginas.setText(String.valueOf(lib.getPaginas()));
        jYearAnio.setValue(lib.getAnio());
        txtPrecio.setText(String.valueOf(lib.getPrecio()));
        
        ComboBoxModel cbm = cbEditorial.getModel();
        for(int i = 1; i < cbm.getSize(); i++){
            if( ((Editorial)cbm.getElementAt(i)).getId()==lib.getIdEditorial() ){
                cbEditorial.setSelectedIndex(i);
            }
        }        
        
        //LlenarJList y eliminar items del jComboBox
        DefaultListModel dlm = new DefaultListModel();
        for(int i=0; i< lib.getIdCategoria().size(); i++){
            boolean encontrado = false;
            cbm = cbCategoria.getModel();
            int index = 1;
            while(!encontrado){
                if( ((Categoria)cbm.getElementAt(index)).getId()==lib.getIdCategoria().get(i) ){
                    dlm.addElement(cbm.getElementAt(index));
                    cbCategoria.removeItemAt(index);
                    encontrado = true;
                }
                index++;
            }            
        }
        jListCategoria.setModel(dlm);
        
        dlm = new DefaultListModel();
        for(int i=0; i< lib.getIdAutor().size(); i++){
            boolean encontrado = false;
            cbm = cbAutor.getModel();
            int index = 1;
            while(!encontrado){
                if( ((Autor)cbm.getElementAt(index)).getId()==lib.getIdAutor().get(i) ){
                    dlm.addElement(cbm.getElementAt(index));
                    cbAutor.removeItemAt(index);
                    encontrado = true;
                }
                index++;
            }            
        }
        jListAutor.setModel(dlm);
        
        dlm = new DefaultListModel();
        for(int i=0; i< lib.getIdIdioma().size(); i++){
            boolean encontrado = false;
            cbm = cbIdioma.getModel();
            int index = 1;
            while(!encontrado){
                if( ((Idioma)cbm.getElementAt(index)).getId()==lib.getIdIdioma().get(i) ){
                    dlm.addElement(cbm.getElementAt(index));
                    cbIdioma.removeItemAt(index);
                    encontrado = true;
                }
                index++;
            }            
        }
        jListIdioma.setModel(dlm);
    }
         
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jCheckBox1 = new javax.swing.JCheckBox();
        lblTitulo = new javax.swing.JLabel();
        panelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTablaDatos = new javax.swing.JTable();
        panelCampos = new javax.swing.JPanel();
        lblEtiquetaID = new javax.swing.JLabel();
        lblISBN = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        lblTituloLibro = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblPaginas = new javax.swing.JLabel();
        txtPaginas = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblAnio = new javax.swing.JLabel();
        jYearAnio = new com.toedter.calendar.JYearChooser();
        lblAutor = new javax.swing.JLabel();
        cbAutor = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCategoria = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListIdioma = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListAutor = new javax.swing.JList<>();
        btnAgregarAutor = new javax.swing.JButton();
        btnEliminarIdioma = new javax.swing.JButton();
        lblIdioma = new javax.swing.JLabel();
        cbIdioma = new javax.swing.JComboBox<>();
        btnAgregarCategoria = new javax.swing.JButton();
        btnEliminarCategoria = new javax.swing.JButton();
        lblCategoria = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        btnAgregarIdioma = new javax.swing.JButton();
        btnEliminarAutor = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();
        btnNuevaCategoria = new javax.swing.JButton();
        btnNuevoAutor = new javax.swing.JButton();
        btnNuevoIdioma = new javax.swing.JButton();
        lblEditorial = new javax.swing.JLabel();
        cbEditorial = new javax.swing.JComboBox<>();
        lblEtiquetaID1 = new javax.swing.JLabel();
        panelSuperiorBotones = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelFiltrar = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnQuitarFiltro = new javax.swing.JButton();
        panelInferiorBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        setMaximumSize(new java.awt.Dimension(800, 570));
        setMinimumSize(new java.awt.Dimension(800, 570));
        setPreferredSize(new java.awt.Dimension(800, 570));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("LIBROS");

        panelTabla.setMaximumSize(new java.awt.Dimension(780, 140));
        panelTabla.setMinimumSize(new java.awt.Dimension(780, 140));
        panelTabla.setPreferredSize(new java.awt.Dimension(780, 140));

        tableTablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableTablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTablaDatosMouseClicked(evt);
            }
        });
        tableTablaDatos.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                tableTablaDatosCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tableTablaDatos);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        panelCampos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEtiquetaID.setText("ID");

        lblISBN.setText("ISBN");

        txtISBN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtISBNFocusLost(evt);
            }
        });
        txtISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtISBNActionPerformed(evt);
            }
        });
        txtISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtISBNKeyTyped(evt);
            }
        });

        lblTituloLibro.setText("Título");

        txtTitulo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTituloFocusLost(evt);
            }
        });
        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });
        txtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTituloKeyTyped(evt);
            }
        });

        lblPaginas.setText("Páginas");

        txtPaginas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaginasFocusLost(evt);
            }
        });
        txtPaginas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaginasActionPerformed(evt);
            }
        });
        txtPaginas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaginasKeyTyped(evt);
            }
        });

        lblPrecio.setText("Precio");

        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioFocusLost(evt);
            }
        });
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        lblAnio.setText("Año");

        jYearAnio.setEndYear(anioActual);

        lblAutor.setText("Autor");

        cbAutor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbAutor.setMaximumSize(new java.awt.Dimension(220, 23));
        cbAutor.setMinimumSize(new java.awt.Dimension(220, 23));
        cbAutor.setPreferredSize(new java.awt.Dimension(220, 23));
        cbAutor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbAutorMouseEntered(evt);
            }
        });
        cbAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutorActionPerformed(evt);
            }
        });

        jListCategoria.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListCategoriaValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListCategoria);

        jListIdioma.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListIdiomaValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListIdioma);

        jListAutor.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAutorValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jListAutor);

        btnAgregarAutor.setText(">");
        btnAgregarAutor.setMaximumSize(new java.awt.Dimension(40, 23));
        btnAgregarAutor.setMinimumSize(new java.awt.Dimension(40, 23));
        btnAgregarAutor.setPreferredSize(new java.awt.Dimension(40, 23));
        btnAgregarAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAutorActionPerformed(evt);
            }
        });

        btnEliminarIdioma.setText("x");
        btnEliminarIdioma.setMaximumSize(new java.awt.Dimension(40, 23));
        btnEliminarIdioma.setMinimumSize(new java.awt.Dimension(40, 23));
        btnEliminarIdioma.setPreferredSize(new java.awt.Dimension(40, 23));
        btnEliminarIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarIdiomaActionPerformed(evt);
            }
        });

        lblIdioma.setText("Idioma");

        cbIdioma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbIdioma.setMaximumSize(new java.awt.Dimension(150, 23));
        cbIdioma.setMinimumSize(new java.awt.Dimension(150, 23));
        cbIdioma.setPreferredSize(new java.awt.Dimension(150, 23));
        cbIdioma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbIdiomaMouseEntered(evt);
            }
        });
        cbIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbIdiomaActionPerformed(evt);
            }
        });

        btnAgregarCategoria.setText(">");
        btnAgregarCategoria.setMaximumSize(new java.awt.Dimension(40, 23));
        btnAgregarCategoria.setMinimumSize(new java.awt.Dimension(40, 23));
        btnAgregarCategoria.setPreferredSize(new java.awt.Dimension(40, 23));
        btnAgregarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCategoriaActionPerformed(evt);
            }
        });

        btnEliminarCategoria.setText("x");
        btnEliminarCategoria.setMaximumSize(new java.awt.Dimension(40, 23));
        btnEliminarCategoria.setMinimumSize(new java.awt.Dimension(40, 23));
        btnEliminarCategoria.setPreferredSize(new java.awt.Dimension(40, 23));
        btnEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCategoriaActionPerformed(evt);
            }
        });

        lblCategoria.setText("Categoría");

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategoria.setMaximumSize(new java.awt.Dimension(150, 23));
        cbCategoria.setMinimumSize(new java.awt.Dimension(150, 23));
        cbCategoria.setPreferredSize(new java.awt.Dimension(150, 23));
        cbCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbCategoriaFocusLost(evt);
            }
        });
        cbCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbCategoriaMouseEntered(evt);
            }
        });
        cbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriaActionPerformed(evt);
            }
        });

        btnAgregarIdioma.setText(">");
        btnAgregarIdioma.setMaximumSize(new java.awt.Dimension(40, 23));
        btnAgregarIdioma.setMinimumSize(new java.awt.Dimension(40, 23));
        btnAgregarIdioma.setPreferredSize(new java.awt.Dimension(40, 23));
        btnAgregarIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarIdiomaActionPerformed(evt);
            }
        });

        btnEliminarAutor.setText("x");
        btnEliminarAutor.setMaximumSize(new java.awt.Dimension(40, 23));
        btnEliminarAutor.setMinimumSize(new java.awt.Dimension(40, 23));
        btnEliminarAutor.setPreferredSize(new java.awt.Dimension(40, 23));
        btnEliminarAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAutorActionPerformed(evt);
            }
        });

        lblID.setText("ID");

        btnNuevaCategoria.setText("+");
        btnNuevaCategoria.setMaximumSize(new java.awt.Dimension(40, 23));
        btnNuevaCategoria.setMinimumSize(new java.awt.Dimension(40, 23));
        btnNuevaCategoria.setPreferredSize(new java.awt.Dimension(40, 23));
        btnNuevaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCategoriaActionPerformed(evt);
            }
        });

        btnNuevoAutor.setText("+");
        btnNuevoAutor.setMaximumSize(new java.awt.Dimension(40, 23));
        btnNuevoAutor.setMinimumSize(new java.awt.Dimension(40, 23));
        btnNuevoAutor.setPreferredSize(new java.awt.Dimension(40, 23));
        btnNuevoAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAutorActionPerformed(evt);
            }
        });

        btnNuevoIdioma.setText("+");
        btnNuevoIdioma.setMaximumSize(new java.awt.Dimension(40, 23));
        btnNuevoIdioma.setMinimumSize(new java.awt.Dimension(40, 23));
        btnNuevoIdioma.setPreferredSize(new java.awt.Dimension(40, 23));
        btnNuevoIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoIdiomaActionPerformed(evt);
            }
        });

        lblEditorial.setText("Editorial");

        cbEditorial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEditorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbEditorialMouseEntered(evt);
            }
        });

        lblEtiquetaID1.setText("$");

        javax.swing.GroupLayout panelCamposLayout = new javax.swing.GroupLayout(panelCampos);
        panelCampos.setLayout(panelCamposLayout);
        panelCamposLayout.setHorizontalGroup(
            panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCamposLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCamposLayout.createSequentialGroup()
                        .addComponent(lblEtiquetaID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarCategoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategoria, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEliminarCategoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAgregarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEliminarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblAutor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addComponent(cbAutor, 0, 1, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addComponent(lblAnio)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAgregarIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEliminarIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIdioma)
                                    .addComponent(lblPrecio))
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelCamposLayout.createSequentialGroup()
                                                .addComponent(cbIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnNuevoIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelCamposLayout.createSequentialGroup()
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(lblEtiquetaID1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jYearAnio, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                            .addComponent(txtPrecio))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(14, 14, 14))
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(lblEditorial))
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblISBN)
                                    .addComponent(lblTituloLibro))))
                        .addGap(30, 30, 30)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCamposLayout.createSequentialGroup()
                                .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPaginas)
                                .addGap(18, 18, 18)
                                .addComponent(txtPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelCamposLayout.setVerticalGroup(
            panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCamposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEtiquetaID)
                            .addComponent(lblTituloLibro)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAnio))
                        .addGap(14, 14, 14)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblISBN)
                            .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPaginas)
                            .addComponent(txtPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecio)
                            .addComponent(lblEtiquetaID1)))
                    .addComponent(jYearAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEditorial)
                    .addComponent(cbEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnAgregarIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCamposLayout.createSequentialGroup()
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNuevoIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAutor)
                                        .addComponent(btnNuevoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblIdioma))
                                    .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addComponent(btnAgregarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEliminarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addComponent(btnAgregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEliminarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAgregar.setText("Agregar");
        btnAgregar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnAgregar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnAgregar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        panelSuperiorBotones.add(btnAgregar);

        btnModificar.setText("Modificar");
        btnModificar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnModificar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnModificar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        panelSuperiorBotones.add(btnModificar);

        btnEliminar.setText("Eliminar");
        btnEliminar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnEliminar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnEliminar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        panelSuperiorBotones.add(btnEliminar);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Rut" }));
        jComboBox1.setMaximumSize(new java.awt.Dimension(200, 23));
        jComboBox1.setMinimumSize(new java.awt.Dimension(200, 23));
        jComboBox1.setPreferredSize(new java.awt.Dimension(200, 23));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        panelFiltrar.add(jComboBox1);

        txtBusqueda.setMaximumSize(new java.awt.Dimension(200, 23));
        txtBusqueda.setMinimumSize(new java.awt.Dimension(200, 23));
        txtBusqueda.setPreferredSize(new java.awt.Dimension(200, 23));
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyTyped(evt);
            }
        });
        panelFiltrar.add(txtBusqueda);

        btnFiltrar.setText("Filtrar");
        btnFiltrar.setMaximumSize(new java.awt.Dimension(100, 23));
        btnFiltrar.setMinimumSize(new java.awt.Dimension(100, 23));
        btnFiltrar.setPreferredSize(new java.awt.Dimension(100, 23));
        panelFiltrar.add(btnFiltrar);

        btnQuitarFiltro.setText("Quitar filtro");
        btnQuitarFiltro.setMaximumSize(new java.awt.Dimension(100, 23));
        btnQuitarFiltro.setMinimumSize(new java.awt.Dimension(100, 23));
        btnQuitarFiltro.setPreferredSize(new java.awt.Dimension(100, 23));
        btnQuitarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarFiltroActionPerformed(evt);
            }
        });
        panelFiltrar.add(btnQuitarFiltro);

        btnGuardar.setText("Guardar");
        btnGuardar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnGuardar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnGuardar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelInferiorBotones.add(btnGuardar);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnLimpiar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnLimpiar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        panelInferiorBotones.add(btnLimpiar);

        btnCancelar.setText("Cancelar");
        btnCancelar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnCancelar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnCancelar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        panelInferiorBotones.add(btnCancelar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSuperiorBotones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInferiorBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSuperiorBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(panelInferiorBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        //Se validan que los campos estén correctos
        boolean datosOK = true;
        //Si todo está correcto se guardan nuevos datos, de lo contrario se muestra mensaje de error
        if(datosOK){
            //Se obtienen datos del panel y se almacenan en un objeto para ser luego enviados a la clase BD            
            int idEditorial = ((Editorial)cbEditorial.getSelectedItem()).getId();
            String isbn = txtISBN.getText();
            String titulo = txtTitulo.getText();
            int pag = Integer.parseInt(txtPaginas.getText());
            float precio = Float.parseFloat(txtPrecio.getText());
            int anio = jYearAnio.getValue();
            List<Integer> lstIdCategoria = new ArrayList<Integer>(); 
            List<Integer> lstIdAutor = new ArrayList<Integer>();  
            List<Integer> lstIdIdioma = new ArrayList<Integer>();
            
            ListModel lm = jListCategoria.getModel();
            for(int i = 0; i <= jListCategoria.getLastVisibleIndex(); i++){
                int id = ((Categoria)lm.getElementAt(i)).getId();
                lstIdCategoria.add(id);
            }
            lm = jListAutor.getModel();
            for(int i = 0; i <= jListAutor.getLastVisibleIndex(); i++){
                int id = ((Autor)lm.getElementAt(i)).getId();
                lstIdAutor.add(id);
            }
            lm = jListIdioma.getModel();
            for(int i = 0; i <= jListIdioma.getLastVisibleIndex(); i++){
                int id = ((Idioma)lm.getElementAt(i)).getId();
                lstIdIdioma.add(id);
            }

            Libro lib = new Libro (idEditorial, isbn, titulo, pag, precio, anio, lstIdCategoria, lstIdAutor, lstIdIdioma);
            
            if(btnAgregar.isSelected()){
                BDLibro.insertar(lib);
                Limpiar.contenidoPanel(panelCampos);
                comprobarCampos();
            }else if(btnModificar.isSelected()){
                int id = Integer.parseInt(lblID.getText());
                BDLibro.modificar(lib, id);
                BDLibro.listar(tableTablaDatos);
                Limpiar.contenidoPanel(panelCampos);
                comprobarCampos();
            }
            
            JOptionPane.showMessageDialog(this,"Registro almacenado correctamente","INGRESO",1);
            BDLibro.listar(tableTablaDatos);
        }else{
            JOptionPane.showMessageDialog(this,"Debe ingresar todos los datos","ERROR",0);            
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        btnAgregar.setSelected(true);
        btnAgregar.setBackground(Color.GRAY);
        btnModificar.setSelected(false);
        btnModificar.setBackground(null);
        Limpiar.contenidoPanel(panelCampos);
        limpiarListas();        
        lblID.setText("");
        jYearAnio.setValue(-1);
        llenarListas();
        BDAutor.obtenerListaDisponibles(cbAutor);
        BDEditorial.obtenerListaDisponibles(cbEditorial);
        comprobarCampos();
        panelCampos.setVisible(true);
        panelInferiorBotones.setVisible(true);
        txtTitulo.requestFocus();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtISBNActionPerformed
        txtTitulo.requestFocus();
    }//GEN-LAST:event_txtISBNActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        txtPaginas.requestFocus();
    }//GEN-LAST:event_txtTituloActionPerformed

    private void txtPaginasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaginasActionPerformed
        txtPrecio.requestFocus();
    }//GEN-LAST:event_txtPaginasActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        Limpiar.contenidoPanel(panelCampos);
        limpiarListas();
        comprobarCampos();        
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Se limpian campos y se oculta panel
        Limpiar.contenidoPanel(panelCampos);
        comprobarCampos();
        panelCampos.setVisible(false);
        panelInferiorBotones.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        //Se activa selección de botón Modificar y de desactiva botón Agregar
        btnModificar.setSelected(true);
        btnModificar.setBackground(Color.GRAY);
        btnAgregar.setSelected(false);
        btnAgregar.setBackground(null);
        //Se limpian campos
        Limpiar.contenidoPanel(panelCampos);
        limpiarListas();
        obtenerCampos();
        comprobarCampos();
        panelCampos.setVisible(true);
        panelInferiorBotones.setVisible(true);
        txtTitulo.requestFocus();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if ( respuesta == 0){
            //Se obtiene ID para eliminar registro
            int rowSel = tableTablaDatos.getSelectedRow();
            int reg = (Integer) tableTablaDatos.getValueAt(rowSel, 0);
            BDLibro.eliminar(reg);
            BDLibro.listar(tableTablaDatos);
            Limpiar.contenidoPanel(panelCampos);
            lblID.setText("");            
        }        
        comprobarCampos();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtISBNFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtISBNFocusLost
        txtISBN.setText(txtISBN.getText().toUpperCase());
        comprobarCampos();
    }//GEN-LAST:event_txtISBNFocusLost

    private void txtTituloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTituloFocusLost
        txtTitulo.setText(txtTitulo.getText().toUpperCase());
        comprobarCampos();
    }//GEN-LAST:event_txtTituloFocusLost

    private void txtPaginasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaginasFocusLost
        comprobarCampos();
    }//GEN-LAST:event_txtPaginasFocusLost

    private void btnAgregarAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAutorActionPerformed
        DefaultListModel dlm = new DefaultListModel();
        ListModel lm = jListAutor.getModel();
        for(int i = 0; i<lm.getSize(); i++ ){
            dlm.addElement(lm.getElementAt(i));
        }
        try{
            int selItem = cbAutor.getSelectedIndex();
            dlm.addElement((Autor)cbAutor.getSelectedItem());
            jListAutor.setModel(dlm);
            cbAutor.removeItemAt(selItem);
            cbAutor.setSelectedIndex(0);
        }catch(Exception e){
            System.err.println("Error :" + e);
        }
    }//GEN-LAST:event_btnAgregarAutorActionPerformed

    private void btnEliminarIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarIdiomaActionPerformed
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ComboBoxModel cbm = cbIdioma.getModel();
        for(int i = 0; i<cbm.getSize(); i++ ){
            dcbm.addElement(cbm.getElementAt(i));
        }            
        dcbm.addElement(jListIdioma.getSelectedValue());
        cbIdioma.setModel(dcbm);
        DefaultListModel dlm = (DefaultListModel) jListIdioma.getModel(); 
        int index = jListIdioma.getSelectedIndex();
        dlm.remove(index);
    }//GEN-LAST:event_btnEliminarIdiomaActionPerformed

    private void btnAgregarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCategoriaActionPerformed
        DefaultListModel dlm = new DefaultListModel();
        ListModel lm = jListCategoria.getModel();
        for(int i = 0; i<lm.getSize(); i++ ){
            dlm.addElement(lm.getElementAt(i));
        }
        try{
            int selItem = cbCategoria.getSelectedIndex();
            dlm.addElement(((Categoria)cbCategoria.getSelectedItem()));
            jListCategoria.setModel(dlm);
            cbCategoria.removeItemAt(selItem);
            cbCategoria.setSelectedIndex(0);
        }catch(Exception e){
            System.err.println("Error :" + e);
        }
    }//GEN-LAST:event_btnAgregarCategoriaActionPerformed

    private void btnEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCategoriaActionPerformed
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ComboBoxModel cbm = cbCategoria.getModel();
        for(int i = 0; i<cbm.getSize(); i++ ){
            dcbm.addElement(cbm.getElementAt(i));
        }            
        dcbm.addElement(jListCategoria.getSelectedValue());
        cbCategoria.setModel(dcbm);
        DefaultListModel dlm = (DefaultListModel) jListCategoria.getModel(); 
        int index = jListCategoria.getSelectedIndex();
        dlm.remove(index);
    }//GEN-LAST:event_btnEliminarCategoriaActionPerformed

    private void cbCategoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbCategoriaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoriaFocusLost

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed
//        BDMantenedor.obtenerLista(cbCategoria, "id_categoria", "categoria", "categoria");
        comprobarCampos();
    }//GEN-LAST:event_cbCategoriaActionPerformed

    private void btnAgregarIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarIdiomaActionPerformed
        DefaultListModel dlm = new DefaultListModel();
        ListModel lm = jListIdioma.getModel();
        for(int i = 0; i<lm.getSize(); i++ ){
            dlm.addElement(lm.getElementAt(i));
        }
        try{
            int selItem = cbIdioma.getSelectedIndex();
            dlm.addElement(cbIdioma.getSelectedItem());
            jListIdioma.setModel(dlm);
            cbIdioma.removeItemAt(selItem);
            cbIdioma.setSelectedIndex(0);
        }catch(Exception e){
            System.err.println("Error :" + e);
        }
    }//GEN-LAST:event_btnAgregarIdiomaActionPerformed

    private void btnEliminarAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAutorActionPerformed
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ComboBoxModel cbm = cbAutor.getModel();
        for(int i = 0; i<cbm.getSize(); i++ ){
            dcbm.addElement(cbm.getElementAt(i));
        }            
        dcbm.addElement(jListAutor.getSelectedValue());
        cbAutor.setModel(dcbm);
        DefaultListModel dlm = (DefaultListModel) jListAutor.getModel(); 
        int index = jListAutor.getSelectedIndex();
        dlm.remove(index);
    }//GEN-LAST:event_btnEliminarAutorActionPerformed

    private void txtISBNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyTyped
        int limite=15;
        if (txtISBN.getText().length()>=limite) {
            evt.consume();
            JOptionPane.showMessageDialog(null,"Máximo 15 caracteres");
        }
    }//GEN-LAST:event_txtISBNKeyTyped

    private void txtTituloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloKeyTyped
        int limite=50;
        if (txtTitulo.getText().length()>=limite) {
            evt.consume();
            JOptionPane.showMessageDialog(null,"Máximo 50 caracteres");
        }
    }//GEN-LAST:event_txtTituloKeyTyped

    private void txtPaginasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaginasKeyTyped
        int limite = 4;
        if (txtPaginas.getText().length() >= limite) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Máximo 4 dígitos");
        }
        char enter = evt.getKeyChar();
        if ( !(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtPaginasKeyTyped

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        jYearAnio.requestFocus();
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtPrecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioFocusLost
        comprobarCampos();
    }//GEN-LAST:event_txtPrecioFocusLost

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        int limite=10;
        if (txtPrecio.getText().length()>=limite) {
            evt.consume();
            JOptionPane.showMessageDialog(null,"Máximo 10");
        }
        //Solo se aceptan numero o decimal "./,"
        char enter = evt.getKeyChar();
        if ( !(Character.isDigit(enter)) && !String.valueOf(enter).toLowerCase().equals(".") && !String.valueOf(enter).toLowerCase().equals(",")){
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
        btnFiltrar.requestFocus();
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void txtBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        int largo = 60;
        if (txtBusqueda.getText().length()>=largo) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Máximo 60 caracteres");
        }
    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void btnNuevaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCategoriaActionPerformed
        BDMantenedor.ventanaAgregar("Categoría", "categoria", "categoria");
    }//GEN-LAST:event_btnNuevaCategoriaActionPerformed

    private void btnNuevoAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAutorActionPerformed
        PantallaMantenedorLibro pm = new PantallaMantenedorLibro();
        pm.setLocationRelativeTo(null);
        pm.setVisible(true);
    }//GEN-LAST:event_btnNuevoAutorActionPerformed

    private void btnNuevoIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoIdiomaActionPerformed
        BDMantenedor.ventanaAgregar("Idioma", "idioma", "idioma");
    }//GEN-LAST:event_btnNuevoIdiomaActionPerformed

    private void btnQuitarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarFiltroActionPerformed
        txtBusqueda.setText("");
        BDLibro.listar(tableTablaDatos);
        comprobarCampos();
    }//GEN-LAST:event_btnQuitarFiltroActionPerformed

    private void tableTablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTablaDatosMouseClicked
        //Al seleccionar item de la tabla, se actualizan campos y se activa botón Modificar
        if(btnAgregar.isSelected()){
            btnAgregar.setSelected(false);
            btnAgregar.setBackground(null);
            btnModificar.setSelected(true);
            btnModificar.setBackground(Color.GRAY);
        }
        comprobarCampos();
        obtenerCampos();
    }//GEN-LAST:event_tableTablaDatosMouseClicked

    private void jListCategoriaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListCategoriaValueChanged
        comprobarCampos();
    }//GEN-LAST:event_jListCategoriaValueChanged

    private void cbAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutorActionPerformed
        comprobarCampos();
    }//GEN-LAST:event_cbAutorActionPerformed

    private void cbIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbIdiomaActionPerformed
        comprobarCampos();
    }//GEN-LAST:event_cbIdiomaActionPerformed

    private void jListAutorValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAutorValueChanged
        comprobarCampos();
    }//GEN-LAST:event_jListAutorValueChanged

    private void jListIdiomaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListIdiomaValueChanged
        comprobarCampos();
    }//GEN-LAST:event_jListIdiomaValueChanged

    private void cbCategoriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCategoriaMouseEntered
//        BDMantenedor.obtenerLista(cbCategoria, "id_categoria", "categoria", "categoria");
    }//GEN-LAST:event_cbCategoriaMouseEntered

    private void cbEditorialMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbEditorialMouseEntered
//        BDEditorial.obtenerLista(cbEditorial);
    }//GEN-LAST:event_cbEditorialMouseEntered

    private void cbAutorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbAutorMouseEntered
//        BDAutor.obtenerLista(cbAutor);
    }//GEN-LAST:event_cbAutorMouseEntered

    private void cbIdiomaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIdiomaMouseEntered
//        BDMantenedor.obtenerLista(cbIdioma, "id_idioma", "idioma", "idioma");
    }//GEN-LAST:event_cbIdiomaMouseEntered

    private void tableTablaDatosCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tableTablaDatosCaretPositionChanged
        //Al seleccionar item de la tabla, se actualizan campos y se activa botón Modificar
        if(btnAgregar.isSelected()){
            btnAgregar.setSelected(false);
            btnAgregar.setBackground(null);
            btnModificar.setSelected(true);
            btnModificar.setBackground(Color.GRAY);
        }
        comprobarCampos();
        obtenerCampos();        // TODO add your handling code here:
    }//GEN-LAST:event_tableTablaDatosCaretPositionChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarAutor;
    private javax.swing.JButton btnAgregarCategoria;
    private javax.swing.JButton btnAgregarIdioma;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarAutor;
    private javax.swing.JButton btnEliminarCategoria;
    private javax.swing.JButton btnEliminarIdioma;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaCategoria;
    private javax.swing.JButton btnNuevoAutor;
    private javax.swing.JButton btnNuevoIdioma;
    private javax.swing.JButton btnQuitarFiltro;
    private javax.swing.JComboBox<String> cbAutor;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JComboBox<String> cbEditorial;
    private javax.swing.JComboBox<String> cbIdioma;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JList<String> jListAutor;
    private javax.swing.JList<String> jListCategoria;
    private javax.swing.JList<String> jListIdioma;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JYearChooser jYearAnio;
    Calendar cal= Calendar.getInstance();
    int anioActual= cal.get(Calendar.YEAR);
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblEditorial;
    private javax.swing.JLabel lblEtiquetaID;
    private javax.swing.JLabel lblEtiquetaID1;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblISBN;
    private javax.swing.JLabel lblIdioma;
    private javax.swing.JLabel lblPaginas;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloLibro;
    private javax.swing.JPanel panelCampos;
    private javax.swing.JPanel panelFiltrar;
    private javax.swing.JPanel panelInferiorBotones;
    private javax.swing.JPanel panelSuperiorBotones;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tableTablaDatos;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtPaginas;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
