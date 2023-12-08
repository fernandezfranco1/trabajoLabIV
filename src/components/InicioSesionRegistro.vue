<template>
    <div>
      <h2 v-if="!mostrarRegistro">Inicio de Sesión</h2>
      <h2 v-else>Registro de Usuario</h2>
  
      <form @submit.prevent="realizarAccion">
        <label for="nomusuario">Nombre de Usuario:</label>
        <input v-model="usuario.nomusuario" type="text" id="nomusuario" required />
  
        <label for="contraseña">Contraseña:</label>
        <input v-model="usuario.contraseña" type="password" id="contraseña" required />
  
        <button type="submit">{{ mostrarRegistro ? 'Registrar' : 'Iniciar Sesión' }}</button>
      </form>
  
      <p>
        {{ mostrarRegistro ? '¿Ya tienes una cuenta?' : '¿No tienes una cuenta?' }}
        <button @click="alternarAccion">{{ mostrarRegistro ? 'Inicia Sesión' : 'Regístrate' }}</button>
      </p>
  
      <p v-if="!mostrarRegistro && !hayUsuarios && mostrarMensajeNoUsuarios">
        No hay usuarios registrados. Regístrate primero para iniciar sesión.
      </p>
  
      <p v-if="mostrarMensajeError">
        {{ mensajeError }}
      </p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        usuario: {
          nomusuario: '',
          contraseña: '',
        },
        mostrarRegistro: false,
        hayUsuarios: false,
        mostrarMensajeNoUsuarios: false,
        mostrarMensajeError: false,
        mensajeError: '',
      };
    },
    mounted() {
      this.verificarUsuarios();
    },
    methods: {
      async verificarUsuarios() {
        try {
          const response = await axios.get('http://localhost:8080/api/v1/usuarios');
          this.hayUsuarios = response.data.length > 0;
  
          if (!this.hayUsuarios) {
            this.mostrarMensajeNoUsuarios = true;
          }
        } catch (error) {
          console.error('Error al verificar usuarios:', error.message);
        }
      },
      async realizarAccion() {
        try {
          this.mostrarMensajeNoUsuarios = false; // Ocultar el mensaje si hay usuarios
  
          if (this.mostrarRegistro) {
            await axios.post('http://localhost:8080/api/v1/usuarios', this.usuario);
          } else {
            await axios.post('http://localhost:8080/api/v1/usuarios/login', this.usuario);
          }
  
          this.$emit('usuarioRegistrado');
        } catch (error) {
          if (error.response && error.response.status === 401) {
            this.mensajeError = 'Credenciales incorrectas. Por favor, inténtelo de nuevo.';
            this.mostrarMensajeError = true;
          } else {
            console.error('Error al realizar la acción:', error.message);
          }
        }
      },
      alternarAccion() {
        this.mostrarRegistro = !this.mostrarRegistro;
        this.mostrarMensajeError = false; // Ocultar el mensaje de error al alternar
      },
    },
  };
  </script>
  
  <style scoped>
  /* Estilos específicos si es necesario */
  </style>
  