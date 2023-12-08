<!-- src/components/LibrosComponent.vue -->
<template>
    <div>
      <h2>Lista de Libros</h2>
      <!-- Campos de búsqueda -->
      <div>
        <label for="tituloBusqueda">Título:</label>
        <input v-model="filtro.titulo" type="text" id="tituloBusqueda" />
        <label for="autorBusqueda">Autor:</label>
        <input v-model="filtro.autor" type="text" id="autorBusqueda" />
        <label for="generoBusqueda">Género:</label>
        <input v-model="filtro.genero" type="text" id="generoBusqueda" />
        <button @click="filtrarLibros">Buscar</button>
      </div>
      <!-- Lista de libros filtrados -->
      <ul v-if="librosFiltrados.length > 0">
        <li v-for="libro in librosFiltrados" :key="libro.id">
          <!-- Mostrar todos los atributos del libro -->
          <div v-for="(value, key) in libro" :key="key">
            <strong>{{ key }}:</strong> {{ value }}
          </div>
          <button @click="editarLibro(libro)">Editar</button>
          <button @click="eliminarLibro(libro.id)">Eliminar</button>
        </li>
      </ul>
      <!-- Mensaje si no hay resultados -->
      <p v-else>No se encontraron resultados.</p>
  
      <h2>{{ modoEdicion ? 'Editar Libro' : 'Agregar Libro' }}</h2>
      <form @submit.prevent="guardarLibro">
        <label for="titulo">Título:</label>
        <input v-model="libro.titulo" type="text" id="titulo" required />
        <label for="autor">Autor:</label>
        <input v-model="libro.autor" type="text" id="autor" required />
        <label for="genero">Género:</label>
        <input v-model="libro.genero" type="text" id="genero" required />
        <label for="numPaginas">Num de Páginas:</label>
        <input v-model="libro.numPaginas" type="number" id="numPaginas" required />
        <label for="sinopsis">Sinopsis:</label>
        <input v-model="libro.sinopsis" type="text" id="sinopsis" required />
        <button type="submit">{{ modoEdicion ? 'Actualizar' : 'Agregar' }}</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        libros: [],
        librosFiltrados: [], // Nuevo arreglo para almacenar libros filtrados
        libro: {
          titulo: '',
          autor: '',
          genero: '',
          numPaginas: 0,
          sinopsis: '',
        },
        filtro: {
          titulo: '',
          autor: '',
          genero: '',
        },
        modoEdicion: false,
      };
    },
    mounted() {
      // Llama a la función para cargar libros al inicio
      this.cargarLibros();
    },
    methods: {
      async cargarLibros() {
        try {
          const response = await axios.get('http://localhost:8080/api/v1/libros');
          this.libros = response.data;
          this.librosFiltrados = [...this.libros]; // Inicializar libros filtrados con todos los libros
        } catch (error) {
          console.error('Error al cargar los libros:', error);
        }
      },
      async editarLibro(libro) {
        this.modoEdicion = true;
        // Copia el libro original antes de la edición
        this.libroOriginal = { ...libro };
        this.libro = { ...libro }; // Copia el libro para editar sin afectar el original
      },
      async eliminarLibro(libroId) {
        try {
          await axios.delete(`http://localhost:8080/api/v1/libros/${libroId}`);
          // Recargar la lista de libros después de eliminar
          this.cargarLibros();
          this.cancelarEdicion(); // Cancelar la edición después de eliminar
        } catch (error) {
          console.error('Error al eliminar el libro:', error);
        }
      },
      async guardarLibro() {
        try {
          if (this.modoEdicion) {
            // Modo edición: Actualizar el libro existente
            await axios.put(`http://localhost:8080/api/v1/libros/${this.libro.id}`, this.libro);
          } else {
            // Modo agregar: Crear un nuevo libro
            await axios.post('http://localhost:8080/api/v1/libros', this.libro);
          }
          // Recargar la lista de libros después de guardar
          this.cargarLibros();
          this.cancelarEdicion(); // Cancelar la edición después de guardar
        } catch (error) {
          console.error('Error al guardar el libro:', error);
        }
      },
      cancelarEdicion() {
        // Limpiar el formulario y cambiar al modo de agregar
        this.libro = {
          titulo: '',
          autor: '',
          genero: '',
          numPaginas: 0,
          sinopsis: '',
        };
        // Restaurar el libro original
        this.libro = { ...this.libroOriginal };
        this.modoEdicion = false;
      },
      filtrarLibros() {
        // Filtrar libros según los criterios de búsqueda
        this.librosFiltrados = this.libros.filter(libro =>
          this.cumpleCriterios(libro, this.filtro)
        );
      },
      cumpleCriterios(libro, filtro) {
        // Función auxiliar para verificar si un libro cumple con los criterios de búsqueda
        return (
          this.contieneCriterio(libro.titulo, filtro.titulo) &&
          this.contieneCriterio(libro.autor, filtro.autor) &&
          this.contieneCriterio(libro.genero, filtro.genero)
        );
      },
      contieneCriterio(valor, criterio) {
        // Función auxiliar para verificar si un valor contiene el criterio de búsqueda
        return valor.toLowerCase().includes(criterio.toLowerCase());
      },
    },
  };
  </script>
  
  <style scoped>
  /* Agrega estilos específicos si es necesario */
  </style>
  