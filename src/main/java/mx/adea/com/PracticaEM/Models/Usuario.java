package mx.adea.com.PracticaEM.Models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@XmlRootElement
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CLIENTE")
    private BigDecimal cliente;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FECHAALTA")
    private Date fechaAlta;
    @Column(name = "FECHABAJA")
    private Date fechaBaja;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "INTENTOS")
    private BigDecimal intentos;
    @Column(name = "FECHAREVOCADO")
    private Date fechaRevocado;
    @Column(name = "FECHA_VIGENCIA")
    private Date fechaVigencia;
    @Column(name = "NO_ACCESO")
    private BigInteger noAcceso;
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Column(name = "AREA")
    private BigInteger area;
    @Column(name = "FECHAMODIFICACION")
    private Date fechaModificacion;

    public Usuario() {}

    public Usuario(String login, String password, String nombre, BigDecimal cliente, String email, Date fechaAlta, Date fechaBaja, Character status, BigDecimal intentos, Date fechaRevocado, Date fechaVigencia, BigInteger noAcceso, String apellidoPaterno, String apellidoMaterno, BigInteger area, Date fechaModificacion) {
        this.login = login;
        this.password = password;
        this.nombre = nombre;
        this.cliente = cliente;
        this.email = email;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.status = status;
        this.intentos = intentos;
        this.fechaRevocado = fechaRevocado;
        this.fechaVigencia = fechaVigencia;
        this.noAcceso = noAcceso;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.area = area;
        this.fechaModificacion = fechaModificacion;
    }

    public Usuario(String login, String nombre, Character status, String apellidoPaterno, String apellidoMaterno, Date fechaModificacion) {
        this.login = login;
        this.nombre = nombre;
        this.status = status;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaModificacion = fechaModificacion;
    }

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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public BigDecimal getIntentos() {
        return intentos;
    }

    public void setIntentos(BigDecimal intentos) {
        this.intentos = intentos;
    }

    public Date getFechaRevocado() {
        return fechaRevocado;
    }

    public void setFechaRevocado(Date fechaRevocado) {
        this.fechaRevocado = fechaRevocado;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString(){
        return "Usuario { login => "+login+", nombre => "+nombre+", " +
                "apellidoPat => "+apellidoPaterno+", apellidoMat => "+apellidoMaterno+",  " +
                " estatus => "+status+", Fecha modificacion => "+fechaModificacion+"}";
    }
}
