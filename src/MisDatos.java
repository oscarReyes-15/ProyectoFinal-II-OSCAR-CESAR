/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danilos
 */
public class MisDatos {
    private String usuario;
    private String contrasena;
    private int id;
    private static int contadorId = 0;

    public MisDatos(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.id = ++contadorId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Usuario: " + usuario + "\n" +
               "ID: " + id;
    }
}
    private String fechaCreacion;

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
