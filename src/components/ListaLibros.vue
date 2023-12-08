<!-- src/components/ListaLibros.vue -->
<template>
    <div>
      <h2>Lista de Libros</h2>
      <ul>
        <li v-for="libro in libros" :key="libro.id">
          {{ libro.titulo }} - {{ libro.autor }}
          <button @click="editarLibro(libro.id)">Editar</button>
          <button @click="eliminarLibro(libro.id)">Eliminar</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      libros: Array,
    },
    methods: {
      editarLibro(libroId) {
        this.$emit("editar-libro", libroId);
      },
      eliminarLibro(libroId) {
        this.$emit("eliminar-libro", libroId);
      },
    },
  };
  </script>
  
  <style scoped>
  /* Estilos específicos del componente */
  </style>
  