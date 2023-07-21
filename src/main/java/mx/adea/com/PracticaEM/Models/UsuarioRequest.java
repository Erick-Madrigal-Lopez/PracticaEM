package mx.adea.com.PracticaEM.Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class UsuarioRequest {

    private String login;
    private String password;
    private String nombre;
    private BigDecimal cliente;
    private String email;
    private BigDecimal intentos;
    private BigInteger noAcceso;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private BigInteger area;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCliente() {
        return cliente;
    }

    public void setCliente(BigDecimal cliente) {
        this.cliente = cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getIntentos() {
        return intentos;
    }

    public void setIntentos(BigDecimal intentos) {
        this.intentos = intentos;
    }

    public BigInteger getNoAcceso() {
        return noAcceso;
    }

    public void setNoAcceso(BigInteger noAcceso) {
        this.noAcceso = noAcceso;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public BigInteger getArea() {
        return area;
    }

    public void setArea(BigInteger area) {
        this.area = area;
    }
}
