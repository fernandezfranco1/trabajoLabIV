package com.back.spring.prestamo;

public class PrestamoRequest {
    private Long idLibro;
    private Long idUsuario;

    public PrestamoRequest(){

    }
    public PrestamoRequest(Long idLibro, Long idUsuario) {
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}