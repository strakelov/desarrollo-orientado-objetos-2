package model;

public enum TipoPedido {
    COMIDA,
    ENCOMIENDA,
    EXPRESS;

    public String toString() {
        String texto = name().toLowerCase();
        return texto.substring(0,1).toUpperCase() + texto.substring(1);
    }
}
