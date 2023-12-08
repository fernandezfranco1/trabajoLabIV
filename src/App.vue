<!-- src/App.vue -->
<template>
  <div id="app">
    <div v-if="!usuarioRegistrado">
      <InicioSesionRegistro @usuarioRegistrado="onUsuarioRegistrado" />
    </div>
    <div v-else>
      <LibrosComponent />
      <UsuariosComponent />
      <PrestamosComponent />
    </div>
  </div>
</template>

<script>
import InicioSesionRegistro from "@/components/InicioSesionRegistro.vue";
import LibrosComponent from "@/components/LibrosComponent.vue";
import UsuariosComponent from "@/components/UsuariosComponent.vue";
import PrestamosComponent from "@/components/PrestamosComponent.vue";

export default {
  components: {
    InicioSesionRegistro,
    LibrosComponent,
    UsuariosComponent,
    PrestamosComponent
  },
  data() {
    return {
      usuarioRegistrado: false,
    };
  },
  methods: {
    onUsuarioRegistrado() {
      this.usuarioRegistrado = true;
    },
  },
};
</script>

<style>
/* Estilos globales de la aplicación */
</style>
