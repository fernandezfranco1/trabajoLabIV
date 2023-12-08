<!-- src/components/PrestamosComponent.vue -->
<template>
  <div>
    <h2>Prestamos</h2>
    <button @click="mostrarFormulario = !mostrarFormulario">
      {{ mostrarFormulario ? 'Cerrar Formulario' : 'Nuevo Préstamo' }}
    </button>

    <div v-if="mostrarFormulario">
      <h3>{{ editarPrestamo ? 'Editar Préstamo' : 'Nuevo Préstamo' }}</h3>
      <!-- Mostrar el mensaje de error -->
      <div v-if="mensajeError" class="alert alert-danger">{{ mensajeError }}</div>
      <form @submit.prevent="realizarAccion">
        <label for="idLibro">ID del Libro:</label>
        <input v-model="nuevoPrestamo.idLibro" type="number" id="idLibro" required />

        <label for="idUsuario">ID del Usuario:</label>
        <input v-model="nuevoPrestamo.idUsuario" type="number" id="idUsuario" required />

        <button type="submit">{{ editarPrestamo ? 'Editar Préstamo' : 'Realizar Préstamo' }}</button>
      </form>
    </div>

    <h3>Listado de Préstamos</h3>
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Libro</th>
          <th>Usuario</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="prestamo in prestamos" :key="prestamo.id">
          <td>{{ prestamo.id }}</td>
          <td>{{ prestamo.libro.titulo }}</td>
          <td>{{ prestamo.usuario.nomusuario }}</td>
          <td>
            <button @click="editar(prestamo)">Editar</button>
            <button @click="eliminarPrestamo(prestamo.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      mostrarFormulario: false,
      editarPrestamo: null,
      nuevoPrestamo: {
        idLibro: null,
        idUsuario: null,
      },
      prestamos: [],
      mensajeError: "", // Nuevo campo para el mensaje de error
    };
  },
  mounted() {
    this.cargarPrestamos();
  },
  methods: {
    async realizarAccion() {
      try {
        // Verificar si el libro ya está en préstamo
        const libroEnPrestamo = this.prestamos.find((prestamo) => prestamo.libro.id === this.nuevoPrestamo.idLibro);
        if (libroEnPrestamo) {
          this.mensajeError = "¡Error! El libro ya está en préstamo.";
          return;
        }

        // Resto del código para realizar el préstamo
        if (this.editarPrestamo) {
          await axios.put(`http://localhost:8080/api/v1/prestamos/editar/${this.editarPrestamo.id}`, {
            idLibro: this.nuevoPrestamo.idLibro,
            idUsuario: this.nuevoPrestamo.idUsuario,
          });
        } else {
          await axios.post('http://localhost:8080/api/v1/prestamos', this.nuevoPrestamo);
        }

        this.cargarPrestamos();
        this.mostrarFormulario = false;
        this.editarPrestamo = null;
        this.mensajeError = ""; // Limpiar el mensaje de error
      } catch (error) {
        console.error('Error al realizar la acción:', error);
      }
    },
    async cargarPrestamos() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/prestamos');
        this.prestamos = response.data;
      } catch (error) {
        console.error('Error al cargar los préstamos:', error);
      }
    },
    async editar(prestamo) {
      this.nuevoPrestamo.idLibro = prestamo.libro.id;
      this.nuevoPrestamo.idUsuario = prestamo.usuario.id;
      this.editarPrestamo = prestamo;
      this.mostrarFormulario = true;
    },
    async eliminarPrestamo(idPrestamo) {
      try {
        await axios.delete(`http://localhost:8080/api/v1/prestamos/eliminar/${idPrestamo}`);
        this.cargarPrestamos();
      } catch (error) {
        console.error('Error al eliminar el préstamo:', error);
      }
    },
  },
};
</script>

<style scoped>
/* Estilos específicos si es necesario */
</style>
