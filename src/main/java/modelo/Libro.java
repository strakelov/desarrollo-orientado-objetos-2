package modelo;

public class Libro {
    private int id;
    private String titulo, autor, isbn, editorial;
    private int idCategoria;
    private int stock;

    public Libro(int id, String titulo, String autor, String isbn, String editorial, int idCategoria, int stock) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.idCategoria = idCategoria;
        this.stock = stock;
    }

    public int getId() {return id;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getAutor() {return autor;}
    public void setAutor(String autor) {this.autor = autor;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getEditorial() {return editorial;}
    public void setEditorial(String editorial) {this.editorial = editorial;}

    public int getIdCategoria() {return idCategoria;}
    public void setCategoria(int idCategoria) {this.idCategoria = idCategoria;}

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editorial='" + editorial + '\'' +
                ", categoria='" + idCategoria + '\'' +
                ", stock=" + stock +
                '}';
    }
}
