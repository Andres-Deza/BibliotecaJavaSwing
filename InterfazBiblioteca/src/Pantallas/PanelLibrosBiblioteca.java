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
public class PanelLibrosBiblioteca extends javax.swing.JPanel {

    /**
     * Creates new form AgregarIdioma
     */
    public PanelLibrosBiblioteca() {
        initComponents();
        
        tableTablaDatos.getTableHeader().setReorderingAllowed(false);
        BDLibro.listarLibrosBiblio(tableTablaDatos);
        comprobarCampos();
        jPanelOculto.setVisible(false); 
        
        txtTitulo.setEditable(false);
        txtTitulo.setBackground(Color.LIGHT_GRAY);
        txtISBN.setEditable(false);
        txtISBN.setBackground(Color.LIGHT_GRAY);
        txtAnio.setEditable(false);
        txtAnio.setBackground(Color.LIGHT_GRAY);
        txtPaginas.setEditable(false);
        txtPaginas.setBackground(Color.LIGHT_GRAY);
        txtPrecio.setEditable(false);
        txtPrecio.setBackground(Color.LIGHT_GRAY);
        txtEditorial.setEditable(false);
        txtEditorial.setBackground(Color.LIGHT_GRAY);

    }
    
    public void limpiarListas(){
        DefaultListModel lm = new DefaultListModel();
        lm.removeAllElements();
        
        jListAutor.setModel(lm);
        jListCategoria.setModel(lm);
        jListIdioma.setModel(lm);
    }
        
    /**
     * Método para comprobar los campos, habilitar y deshabilitar botones de acción
     */
    public void comprobarCampos(){
        //Se compruebas campos vacíos
        String cc = txtSerie.getText() + txtTitulo.getText() + txtPaginas.getText() + txtPrecio.getText();
        if( cc.equals("")){
            btnGuardar.setEnabled(false);
//            btnLimpiar.setEnabled(false);            
        }else if (txtSerie.getText().isEmpty() ||
                txtTitulo.getText().isEmpty() || txtPaginas.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            btnGuardar.setEnabled(false);
//            btnLimpiar.setEnabled(true);
        }else{
            btnGuardar.setEnabled(true);
//            btnLimpiar.setEnabled(true);
        }        
            
    }
    
    public void obtenerCampos(){
        
        BDMantenedor.obtenerLista(cbEstado, "id_estado", "estado", "descripcion_estado");
        
        int rowSel = tableTablaDatos.getSelectedRow();
        int idLibB = (Integer)tableTablaDatos.getValueAt(rowSel, 0);
        int idLibL = (Integer)tableTablaDatos.getValueAt(rowSel, 1);
        Libro lib = BDLibro.buscarLibroBiblioteca(idLibB, idLibL );
        
        
        txtTitulo.setText(lib.getTitulo());
        txtSerie.setText(lib.getSerie());
        txtPaginas.setText(String.valueOf(lib.getPaginas()));
        txtPrecio.setText(String.valueOf(lib.getPrecio()));
        txtAnio.setText(String.valueOf(lib.getAnio()));
        txtISBN.setText(lib.getISBN());
        
        ComboBoxModel cbm = cbEstado.getModel();
        for(int i = 1; i < cbm.getSize(); i++){
            if( ((Estado)cbm.getElementAt(i)).getId()==lib.getIdEstado() ){
                cbEstado.setSelectedIndex(i);
            }
        } 
                
        txtEditorial.setText( BDEditorial.buscar(lib.getIdEditorial()).toString() );
        
        BDMantenedor.obtenerLista(cbOculto, "id_categoria", "categoria", "categoria");
        //LlenarJList y eliminar items del jComboBox
        DefaultListModel dlm = new DefaultListModel();
        for(int i=0; i< lib.getIdCategoria().size(); i++){
            boolean encontrado = false;
            cbm = cbOculto.getModel();
            int index = 1;
            while(!encontrado){
                if( ((Categoria)cbm.getElementAt(index)).getId()==lib.getIdCategoria().get(i) ){
                    dlm.addElement(cbm.getElementAt(index));
                    cbOculto.removeItemAt(index);
                    encontrado = true;
                }
                index++;
            }            
        }
        jListCategoria.setModel(dlm);      
        
        
        BDAutor.obtenerListaCompleta(cbOculto);
        dlm = new DefaultListModel();
        for(int i=0; i< lib.getIdAutor().size(); i++){
            boolean encontrado = false;
            cbm = cbOculto.getModel();
            int index = 1;
            while(!encontrado){
                if( ((Autor)cbm.getElementAt(index)).getId()==lib.getIdAutor().get(i) ){
                    dlm.addElement(cbm.getElementAt(index));
                    cbOculto.removeItemAt(index);
                    encontrado = true;
                }
                index++;
            }            
        }
        jListAutor.setModel(dlm);
        
        BDMantenedor.obtenerLista(cbOculto, "id_idioma", "idioma", "idioma");
        dlm = new DefaultListModel();
        for(int i=0; i< lib.getIdIdioma().size(); i++){
            boolean encontrado = false;
            cbm = cbOculto.getModel();
            int index = 1;
            while(!encontrado){
                if( ((Idioma)cbm.getElementAt(index)).getId()==lib.getIdIdioma().get(i) ){
                    dlm.addElement(cbm.getElementAt(index));
                    cbOculto.removeItemAt(index);
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
        lblISBN = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        lblTituloLibro = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblPaginas = new javax.swing.JLabel();
        txtPaginas = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblAnio = new javax.swing.JLabel();
        lblAutor = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCategoria = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListIdioma = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListAutor = new javax.swing.JList<>();
        lblIdioma = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        lblEditorial = new javax.swing.JLabel();
        lblPeso = new javax.swing.JLabel();
        lblISBN1 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        lblISBN2 = new javax.swing.JLabel();
        txtEditorial = new javax.swing.JTextField();
        cbEstado = new javax.swing.JComboBox<>();
        txtAnio = new javax.swing.JTextField();
        jPanelOculto = new javax.swing.JPanel();
        cbOculto = new javax.swing.JComboBox<>();
        panelFiltrar = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnQuitarFiltro = new javax.swing.JButton();
        panelInferiorBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        setMaximumSize(new java.awt.Dimension(800, 570));
        setMinimumSize(new java.awt.Dimension(800, 570));
        setPreferredSize(new java.awt.Dimension(800, 570));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("LIBROS BIBLIOTECA");

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

        lblISBN.setText("ISBN");

        txtSerie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSerieFocusLost(evt);
            }
        });
        txtSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSerieActionPerformed(evt);
            }
        });
        txtSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSerieKeyTyped(evt);
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

        lblAutor.setText("Autor");

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

        lblIdioma.setText("Idioma");

        lblCategoria.setText("Categoría");

        lblEditorial.setText("Editorial");

        lblPeso.setText("$");

        lblISBN1.setText("Serie");

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

        lblISBN2.setText("Estado");

        txtEditorial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEditorialFocusLost(evt);
            }
        });
        txtEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditorialActionPerformed(evt);
            }
        });
        txtEditorial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEditorialKeyTyped(evt);
            }
        });

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtAnio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAnioFocusLost(evt);
            }
        });
        txtAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnioActionPerformed(evt);
            }
        });
        txtAnio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnioKeyTyped(evt);
            }
        });

        cbOculto.setMaximumSize(new java.awt.Dimension(20, 32767));
        cbOculto.setMinimumSize(new java.awt.Dimension(20, 20));
        cbOculto.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout jPanelOcultoLayout = new javax.swing.GroupLayout(jPanelOculto);
        jPanelOculto.setLayout(jPanelOcultoLayout);
        jPanelOcultoLayout.setHorizontalGroup(
            jPanelOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOcultoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbOculto, 0, 80, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelOcultoLayout.setVerticalGroup(
            jPanelOcultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOcultoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbOculto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelCamposLayout = new javax.swing.GroupLayout(panelCampos);
        panelCampos.setLayout(panelCamposLayout);
        panelCamposLayout.setHorizontalGroup(
            panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCamposLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCategoria)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(54, 54, 54)
                                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelCamposLayout.createSequentialGroup()
                                                .addComponent(lblAutor)
                                                .addGap(47, 47, 47))
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addComponent(lblEditorial)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblISBN1)
                                    .addComponent(lblISBN))
                                .addGap(32, 32, 32)
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelCamposLayout.createSequentialGroup()
                                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtISBN, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                            .addComponent(txtSerie))
                                        .addGap(23, 23, 23)
                                        .addComponent(lblISBN2)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(104, 104, 104)
                                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(panelCamposLayout.createSequentialGroup()
                                                .addComponent(lblIdioma)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                                .addComponent(jPanelOculto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelCamposLayout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addComponent(lblPeso)
                                                .addGap(18, 18, 18)
                                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                                    .addComponent(txtAnio)
                                                    .addComponent(txtPaginas)))
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituloLibro)
                            .addGroup(panelCamposLayout.createSequentialGroup()
                                .addGap(456, 456, 456)
                                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAnio)
                                    .addComponent(lblPaginas)
                                    .addComponent(lblPrecio))))
                        .addGap(0, 261, Short.MAX_VALUE))))
        );
        panelCamposLayout.setVerticalGroup(
            panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCamposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloLibro)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAnio)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaginas)
                    .addComponent(txtPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblISBN1)
                    .addComponent(lblISBN2)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblISBN)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrecio)
                    .addComponent(lblPeso))
                .addGap(18, 18, 18)
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditorial)
                            .addComponent(txtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAutor)
                            .addComponent(lblIdioma))
                        .addGap(9, 9, 9))
                    .addGroup(panelCamposLayout.createSequentialGroup()
                        .addComponent(jPanelOculto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        btnEliminar.setText("Eliminar");
        btnEliminar.setMaximumSize(new java.awt.Dimension(200, 23));
        btnEliminar.setMinimumSize(new java.awt.Dimension(200, 23));
        btnEliminar.setPreferredSize(new java.awt.Dimension(200, 23));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        panelInferiorBotones.add(btnEliminar);

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
                    .addComponent(panelFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            String serie = txtSerie.getText();
            int idEstado = ((Estado)cbEstado.getSelectedItem()).getId();
            int rowSel = tableTablaDatos.getSelectedRow();            
            int idLibB = (Integer)tableTablaDatos.getValueAt(rowSel, 0);
            int idLibL = (Integer)tableTablaDatos.getValueAt(rowSel, 1);
            BDLibro.modificar(idLibB, idLibL, idEstado, serie);
            
            BDLibro.listarLibrosBiblio(tableTablaDatos);
            Limpiar.contenidoPanel(panelCampos);
            comprobarCampos();
            
            JOptionPane.showMessageDialog(this,"Registro almacenado correctamente","INGRESO",1);
            BDLibro.listarLibrosBiblio(tableTablaDatos);
        }else{
            JOptionPane.showMessageDialog(this,"Debe ingresar todos los datos","ERROR",0);            
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSerieActionPerformed
        txtTitulo.requestFocus();
    }//GEN-LAST:event_txtSerieActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        txtPaginas.requestFocus();
    }//GEN-LAST:event_txtTituloActionPerformed

    private void txtPaginasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaginasActionPerformed
        txtPrecio.requestFocus();
    }//GEN-LAST:event_txtPaginasActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Se limpian campos y se oculta panel
        Limpiar.contenidoPanel(panelCampos);
        comprobarCampos();
        panelCampos.setVisible(false);
        panelInferiorBotones.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if ( respuesta == 0){
            //Se obtiene ID para eliminar registro
            int rowSel = tableTablaDatos.getSelectedRow();
            int reg = (Integer) tableTablaDatos.getValueAt(rowSel, 0);
            BDLibro.eliminar(reg);
            BDLibro.listarLibrosBiblio(tableTablaDatos);
            Limpiar.contenidoPanel(panelCampos);
        }        
        comprobarCampos();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtSerieFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSerieFocusLost
        txtSerie.setText(txtSerie.getText().toUpperCase());
        comprobarCampos();
    }//GEN-LAST:event_txtSerieFocusLost

    private void txtTituloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTituloFocusLost
        txtTitulo.setText(txtTitulo.getText().toUpperCase());
        comprobarCampos();
    }//GEN-LAST:event_txtTituloFocusLost

    private void txtPaginasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaginasFocusLost
        comprobarCampos();
    }//GEN-LAST:event_txtPaginasFocusLost

    private void txtSerieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerieKeyTyped
        int limite=15;
        if (txtSerie.getText().length()>=limite) {
            evt.consume();
            JOptionPane.showMessageDialog(null,"Máximo 15 caracteres");
        }
    }//GEN-LAST:event_txtSerieKeyTyped

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
//        jYearAnio.requestFocus();
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

    private void btnQuitarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarFiltroActionPerformed
        txtBusqueda.setText("");
        BDLibro.listarLibrosBiblio(tableTablaDatos);
        comprobarCampos();
    }//GEN-LAST:event_btnQuitarFiltroActionPerformed

    private void tableTablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTablaDatosMouseClicked
        //Al seleccionar item de la tabla, se actualizan campos y se activa botón Modificar
//        if(btnAgregar.isSelected()){
//            btnAgregar.setSelected(false);
//            btnAgregar.setBackground(null);
//            btnModificar.setSelected(true);
//            btnModificar.setBackground(Color.GRAY);
//        }
        comprobarCampos();
        obtenerCampos();
    }//GEN-LAST:event_tableTablaDatosMouseClicked

    private void jListCategoriaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListCategoriaValueChanged
        comprobarCampos();
    }//GEN-LAST:event_jListCategoriaValueChanged

    private void jListAutorValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAutorValueChanged
        comprobarCampos();
    }//GEN-LAST:event_jListAutorValueChanged

    private void jListIdiomaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListIdiomaValueChanged
        comprobarCampos();
    }//GEN-LAST:event_jListIdiomaValueChanged

    private void tableTablaDatosCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tableTablaDatosCaretPositionChanged
        //Al seleccionar item de la tabla, se actualizan campos y se activa botón Modificar
//        if(btnAgregar.isSelected()){
//            btnAgregar.setSelected(false);
//            btnAgregar.setBackground(null);
//            btnModificar.setSelected(true);
//            btnModificar.setBackground(Color.GRAY);
//        }
        comprobarCampos();
        obtenerCampos();        // TODO add your handling code here:
    }//GEN-LAST:event_tableTablaDatosCaretPositionChanged

    private void txtISBNFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtISBNFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtISBNFocusLost

    private void txtISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtISBNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtISBNActionPerformed

    private void txtISBNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtISBNKeyTyped

    private void txtEditorialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEditorialFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditorialFocusLost

    private void txtEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditorialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditorialActionPerformed

    private void txtEditorialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEditorialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditorialKeyTyped

    private void txtAnioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAnioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioFocusLost

    private void txtAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioActionPerformed

    private void txtAnioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitarFiltro;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbOculto;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JList<String> jListAutor;
    private javax.swing.JList<String> jListCategoria;
    private javax.swing.JList<String> jListIdioma;
    private javax.swing.JPanel jPanelOculto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblEditorial;
    private javax.swing.JLabel lblISBN;
    private javax.swing.JLabel lblISBN1;
    private javax.swing.JLabel lblISBN2;
    private javax.swing.JLabel lblIdioma;
    private javax.swing.JLabel lblPaginas;
    private javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloLibro;
    private javax.swing.JPanel panelCampos;
    private javax.swing.JPanel panelFiltrar;
    private javax.swing.JPanel panelInferiorBotones;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tableTablaDatos;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtEditorial;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtPaginas;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
