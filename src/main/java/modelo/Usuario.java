package modelo;

public abstract class Usuario {
    private int id;
    private Rut rut;
    private String nombre, correo;

    public Usuario(int id, Rut rut, String nombre, String correo) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {return id;}
    public Rut getRut() {return rut;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rut=" + rut +
                ", correo='" + correo + '\'' +
                '}';
    }
}
