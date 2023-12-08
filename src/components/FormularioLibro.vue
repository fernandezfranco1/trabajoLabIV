<!-- src/components/FormularioLibro.vue -->
<template>
  <div>
    <h2>{{ modoEdicion ? 'Editar Libro' : 'Agregar Libro' }}</h2>
    <form @submit.prevent="guardarLibro">
      <label>Título:</label>
      <input v-model="libro.titulo" required />

      <label>Autor:</label>
      <input v-model="libro.autor" required />

      <label>Género:</label>
      <input v-model="libro.genero" required />

      <label>Número de Páginas:</label>
      <input v-model.number="libro.numPaginas" required />

      <label>Sinopsis:</label>
      <textarea v-model="libro.sinopsis" required></textarea>

      <button type="submit">{{ modoEdicion ? 'Actualizar' : 'Agregar' }}</button>
    </form>
  </div>
</template>

<script>
export default {
  props: {
    modoEdicion: Boolean,
    libro: Object,
  },
  data() {
    return {
      libro: {
        titulo: "",
        autor: "",
        genero: "",
        numPaginas: 0,
        sinopsis: "",
      },
    };
  },
  methods: {
    guardarLibro() {
      this.$emit("guardar-libro", this.libro);
    },
  },
};
</script>

<style scoped>
/* Estilos específicos del componente */
</style>
