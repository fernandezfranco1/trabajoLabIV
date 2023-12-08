// src/main.js
import { createApp } from "vue";
import App from "./App.vue";
import LibrosComponent from './components/LibrosComponent.vue';
import UsuariosComponent from './components/UsuariosComponent.vue';
import PrestamosComponent from './components/PrestamosComponent.vue';
import 'bootstrap/dist/css/bootstrap.css';
const app = createApp(App);
app.component('LibrosComponent', LibrosComponent);
app.component('UsuariosComponent', UsuariosComponent);
app.component('PrestamosComponent', PrestamosComponent);

app.mount("#app");
