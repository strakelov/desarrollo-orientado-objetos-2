package modelo;

import util.RutInvalidoException;

public class Rut {
    private final String numero;

    public Rut(String numero) throws RutInvalidoException {
        if(!numero.matches("[0-9]{7,8}-[0-9kK]")) {
            throw new RutInvalidoException("Formato de rut invalido. Ejemplo: 12345678-K");
        }

        this.numero = numero.toUpperCase();
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return numero;
    }
}
