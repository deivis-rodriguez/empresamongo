package edu.mintic.empresamongo.controller.payloads.responses;

public class MessageResponse {
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public MessageResponse(String mensaje) {
        this.mensaje = mensaje;
    }

}
