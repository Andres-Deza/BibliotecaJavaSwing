package Clases;

/**
 *
 * @author jsoto
 */
public class Autor {
    private int id;
    private String nombre;
    private String apePaterno;    
    private String apeMaterno; 

    public Autor() {
    }

    public Autor(int id, String nombre, String apePat, String apeMat) {
        this.id = id;
        this.nombre = nombre;
        this.apePaterno = apePat;
        this.apeMaterno = apeMat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }
    
    public String getApeMaterno() {
        return apeMaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }
    
    /**
     * 
     * @return 
     */   
    @Override
    public String toString(){
        return nombre + " " + apePaterno + " " + apeMaterno;
    }
    
}
