CarritoApp
Api Rest para gestionar tareas basica de carrito

Este proyecto es una aplicación web desarrollada en Spring Boot que permite a los usuarios interactuar con un carrito de compras y gestionar fechas promocionales a través de una interfaz RESTful. Además, cuenta con funcionalidades de autenticación y autorización utilizando Spring Security.

Características principales:
Gestión de usuarios (registro, autenticación).
Carrito de compras (agregar, quitar productos).
Administración de fechas promocionales.
Sistema de roles y permisos con Spring Security.

Tecnologias usadas:
Java 17 +; Maven 3+; Spring Boot 3.3.2; Spring Security; MySQL; 

Ejecución
Clonar el repositorio
El proyecto esta dentro de la carpeta demo
Crear la base de datos a partir del archivo carritodb.sql
Correr el proyecto spring. Ir a localhost:8080 y loguearse con alguno de los 3 usuarios de la base de datos: (usuario: ale, contraseña: 1234),(usuario: admin, contraseña: 1234),(usuario: usuario, contraseña: 1234)
Y te dirigira al front en /front/index

Para ver las funcionalidades de la api dirigirse a: http://localhost:8080/swagger-ui.html
