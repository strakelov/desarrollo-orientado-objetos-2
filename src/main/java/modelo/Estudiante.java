package modelo;

public class Estudiante extends Usuario {
    private String curso;

    public Estudiante(int id, Rut rut, String nombre, String correo, String curso) {
        super(id, rut, nombre, correo);
        this.curso = curso;
    }

    public String getCurso() {return curso;}
    public void setCurso(String curso) {this.curso = curso;}

    @Override
    public String toString() {
        return super.toString() + "Estudiante{" +
                "curso='" + curso + '\'' +
                '}';
    }
}
