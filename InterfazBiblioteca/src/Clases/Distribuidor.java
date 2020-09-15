package Clases;

/**
 *
 * @author jsoto
 */
public class Distribuidor {
    private int id;
    private String rut;
    private String nombre;
    private String direccion;
    private int telefono;
    private Short anio;

    public Distribuidor() {
    }

    public Distribuidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Distribuidor(int id, String rut, String nombre) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
