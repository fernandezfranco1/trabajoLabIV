# Biblioteca Virtual: Proyecto Final

Este repositorio contiene el código fuente para una Biblioteca Virtual, una aplicación que permite gestionar libros, usuarios y préstamos de una biblioteca.

## Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes componentes en tu sistema:

- Docker
- Node.js y npm (ambos incluidos con Node.js)

## Configuración del Backend

1. **Clonar el Repositorio:**

    git clone https://github.com/tu-usuario/tu-repositorio.git
    cd tu-repositorio/backend

2. **Crear Archivo `.env`:**

    Crea un archivo `.env` en el directorio `backend` y configura las variables de entorno necesarias. Puedes utilizar el archivo `.env.example` proporcionado como guía.

3. **Construir y Ejecutar el Contenedor Docker para el Backend:**
   
    
    docker-compose up -d

    Esto iniciará el servidor de Spring Boot y la base de datos PostgreSQL.

## Configuración del Frontend

1. **Navegar al Directorio del Frontend:**

    
    cd ../frontend/vueproject
  

2. **Instalar Dependencias:**

   
    npm install
  

3. **Configurar Variables de Entorno del Frontend:**

    Crea un archivo `.env` en el directorio `frontend` y configura las variables según sea necesario. Puedes basarte en el archivo `.env.example`.

4. **Iniciar la Aplicación Vue:**

    
    npm run serve
    

    Esto iniciará el servidor de desarrollo de Vue.js.

## Acceder a la Aplicación

Una vez que esta todo en ejecucion la aplicación estará disponible en http://localhost:5173.

¡Listo! 

