package dao;

import modelo.Usuario;

public interface UsuarioDAO {
    Usuario autenticar(String correo, String contrasena);
}
