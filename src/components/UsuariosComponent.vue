<template>
  <div class="container mt-4">
    <h2>
      <button @click="mostrarListaUsuarios = !mostrarListaUsuarios">
        {{ mostrarListaUsuarios ? 'Ocultar' : 'Mostrar' }} lista de usuarios
      </button>
    </h2>

    <!-- Agregado v-if para mostrar u ocultar la lista de usuarios -->
    <ul v-if="mostrarListaUsuarios" class="list-group">
      <li v-for="usuario in usuarios" :key="usuario.id" class="list-group-item d-flex justify-content-between align-items-center">
        {{ usuario.nomusuario }}
        <div>
          <button @click="editarUsuario(usuario)" class="btn btn-warning">Editar</button>
          <button @click="eliminarUsuario(usuario.id)" class="btn btn-danger">Eliminar</button>
        </div>
      </li>
    </ul>

    <!-- Eliminada la sección de agregar usuario -->

    <!-- Sección de edición de usuario -->
    <div v-if="modoEdicion">
      <h2>Editar Usuario</h2>
      <form @submit.prevent="guardarUsuario">
        <div class="mb-3">
          <label for="nomusuario" class="form-label">Nombre de Usuario:</label>
          <input v-model="usuario.nomusuario" type="text" id="nomusuario" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="contraseña" class="form-label">Contraseña:</label>
          <input v-model="usuario.contraseña" type="password" id="contraseña" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary">Actualizar</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      usuarios: [],
      usuario: {
        nomusuario: '',
        contraseña: '',
      },
      usuarioOriginal: {},
      modoEdicion: false,
      mostrarListaUsuarios: true,
    };
  },
  mounted() {
    this.cargarUsuarios();
  },
  methods: {
    async cargarUsuarios() {
      try {
        const response = await axios.get("http://localhost:8080/api/v1/usuarios");
        this.usuarios = response.data;
      } catch (error) {
        console.error("Error al cargar los usuarios:", error);
      }
    },
    async editarUsuario(usuario) {
      this.modoEdicion = true;
      this.usuarioOriginal = { ...usuario };
      this.usuario = { ...usuario };
    },
    async eliminarUsuario(usuarioId) {
      try {
        await axios.delete(`http://localhost:8080/api/v1/usuarios/${usuarioId}`);
        this.cargarUsuarios();
        this.cancelarEdicion();
      } catch (error) {
        console.error("Error al eliminar el usuario:", error);
      }
    },
    async guardarUsuario() {
      try {
        if (this.modoEdicion) {
          await axios.put(`http://localhost:8080/api/v1/usuarios`, this.usuario);
        }
        this.cargarUsuarios();
        this.cancelarEdicion();
      } catch (error) {
        console.error("Error al guardar el usuario:", error);
      }
    },
    cancelarEdicion() {
      this.usuario = {
        nomusuario: "",
        contraseña: "",
      };
      this.usuario = { ...this.usuarioOriginal };
      this.modoEdicion = false;
    },
  },
};
</script>

<style scoped>
/* Agrega estilos específicos si es necesario */
</style>
