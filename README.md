# Evaluación Final Transversal S9 – DOO II
- **Nombre completo:** Gabriel Alejandro Gomez Pizarro
- **Carrera:** Analista Programador Computacional
- **Sección:** 005A
- **Sede:** Campus Virtual
- **Fecha de entrega:** 11/10/2026

---

## Descripción general del sistema

Este programa con interfaz de usuario desarrollado en java implementa un sistema de gestión de biblioteca escolar. Se aplican conceptos de POO junto con patrones de diseño MVC, DAO y Singleton. Además, incorpora la concurrencia y procesamiento en segundo plano.

---

## Estructura general del proyecto

```plaintext
📁 src/
├── Main.java                         # Punto de entrada de la aplicación
├── controlador/                      # Lógica de negocio y manejo de concurrencia
│   └── ControladorPrestamos.java
├── dao/                              # Capa de acceso a datos y sus implementaciones
│   └── impl/
│   │   ├── EstudianteDAOImpl.java
│   │   ├── LibroDAOImpl.java
│   │   ├── PrestamoDAOImpl.java
│   │   ├── ReporteDAOImpl.java
│   │   └── UsuarioDAOImpl.java
│   ├── EstudianteDAO.java
│   ├── LibroDAO.java
│   ├── PrestamoDAO.java
│   ├── ReporteDAO.java
│   └── UsuarioDAO.java
├── modelo/                           # Entidades y clases abstractas
│   ├── Bibliotecario.java
│   ├── Estudiante.java
│   ├── Libro.java
│   ├── Prestamo.java
│   ├── Rut.java
│   └── Usuario.java
├── util/                            # Clases de conexión y Excepción personalizada
│   ├── ConexionBD.java
│   └── RutInvalidoException.java
├── vista/                           # Interfaz gráfica de usuario
│   ├── Login.java
│   ├── PanelEstudiantes.java
│   ├── PanelLibros.java
│   ├── PanelPrestamos.java
│   ├── PanelReportes.java
│   └── VentanaPrincipal.java
└── script.sql                      # Script SQL con la creación y poblamiento de la BD
````

---


## Instrucciones para ejecutar el proyecto

1. Clona el repositorio desde GitHub:

```bash
git clone https://github.com/strakelov/desarrollo-orientado-objetos-2
```

2. Abre el proyecto en IntelliJ IDEA.

3. Ejecuta el archivo `Main.java` ubicado en `src/main/java/Main.java`.
