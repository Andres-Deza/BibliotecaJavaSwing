package Clases;

import java.util.List;


/**
 *
 * @author jsoto
 */
public class Libro {
    private int id;
    private int idL;
    private int idEstado;
    private int idEditorial;
    private String serie;
    private String ISBN;
    private String titulo;
    private int paginas;
    private float precio;
    private int anio;
    private List<Integer> idCategoria;
    private List<Integer> idAutor;
    private List<Integer> idIdioma;
    private short estado_libro;

    public Libro() {
    }

    public Libro(int id, String titulo, Float precio) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
    }

    public int getIdL() {
        return idL;
    }

    public void setIdL(int idL) {
        this.idL = idL;
    }

    public Libro(int id, int idEditorial, String ISBN, String titulo, int paginas, Float precio, int anio) {
        this.id = id;
        this.idEditorial = idEditorial;
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.paginas = paginas;
        this.precio = precio;
        this.anio = anio;
    }

    
    public Libro(int idEstado, int idEditorial, String serie, String ISBN, String titulo, int paginas, float precio, int anio, List<Integer> idCategoria, List<Integer> idAutor, List<Integer> idIdioma) {
        this.id = id;
        this.idEstado = idEstado;
        this.idEditorial = idEditorial;
        this.serie = serie;
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.paginas = paginas;
        this.precio = precio;
        this.anio = anio;
        this.idCategoria = idCategoria;
        this.idAutor = idAutor;
        this.idIdioma = idIdioma;
    }
    
    //Constructor Listado de libros
    public Libro(int idEditorial, String ISBN, String titulo, int paginas, Float precio, int anio, List<Integer> idCategoria, List<Integer> idAutor, List<Integer> idIdioma) {
        this.idEditorial = idEditorial;
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.paginas = paginas;
        this.precio = precio;
        this.anio = anio;
//        this.precio = precio;
        this.idCategoria = idCategoria;
        this.idAutor = idAutor;
        this.idIdioma = idIdioma;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<Integer> getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(List<Integer> idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<Integer> getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(List<Integer> idAutor) {
        this.idAutor = idAutor;
    }

    public List<Integer> getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(List<Integer> idIdioma) {
        this.idIdioma = idIdioma;
    }
    
    public short getEstado_libro() {
        return estado_libro;
    }

    public void setEstado_libro(short estado_libro) {
        this.estado_libro = estado_libro;
    }
    
    @Override
    public String toString(){
        return titulo;
    }
}
