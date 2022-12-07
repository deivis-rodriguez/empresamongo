package edu.mintic.empresamongo.controller.payloads.responses;

public class JwtResponse {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public JwtResponse() {
    }

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }
    
}
