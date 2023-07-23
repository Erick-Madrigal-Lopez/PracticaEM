package mx.adea.com.PracticaEM.Util;

import mx.adea.com.PracticaEM.Models.UsuarioRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Utileria {

    private final static Logger log = LoggerFactory.getLogger(Utileria.class);
    public String validaParams(UsuarioRequest request){
        String res = "";
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(request.getPassword());
            log.info("Validado login");
            if (request.getLogin() == null || !request.getLogin().matches("^[0-9A-Za-z ]+$")) throw new Exception("login inválido");
            log.info("Validado contrasenia");
            if (!new String(decodedBytes).matches("^[0-9A-Za-z._$&@ ]+$")) throw new Exception("Contraseña inválida");
            log.info("Validado nombre");
            if (request.getNombre() == null || !request.getNombre().matches("^[0-9A-Za-z ]+$")) throw new Exception("Nombre inválido");
            log.info("Validado cliente");
            if (request.getCliente() == null || !request.getCliente().toString().matches("^[0-9]+(\\.*[0-9]+)*$")) throw new Exception("Cliente inválido");
            log.info("Validado email");
            if (request.getEmail() == null || !request.getEmail().matches("^[0-9A-Za-z_.]+@(gmail.com|hotmail.com|outlook.com|live.com)$")) throw new Exception("Email inválido");
            log.info("Validado intentos");
            if (request.getIntentos() == null || !request.getIntentos().toString().matches("^[0-9]+(\\.*[0-9]+)*$")) throw new Exception("Intentos inválido");
            log.info("Validado acceso");
            if (request.getNoAcceso() == null || !request.getNoAcceso().toString().matches("^[0-9]+(\\.*[0-9]+)*$")) throw new Exception("NoAcceso inválido");
            log.info("Validado apellido pat");
            if (request.getApellidoPaterno() == null || !request.getApellidoPaterno().matches("^([0-9A-Za-z ]+)*$")) throw new Exception("Apellido paterno inválido");
            log.info("Validado apelido mat");
            if (request.getApellidoMaterno() == null || !request.getApellidoMaterno().matches("^([0-9A-Za-z ]+)*$")) throw new Exception("Apellido materno inválido");
            log.info("Validado area");
            if (request.getArea() == null || !request.getArea().toString().matches("^[0-9]+(\\.*[0-9]+)*$")) throw new Exception("Área inválida");
            log.info("Exito al validar");
            return "0000";
        }catch (Exception e){
            log.error("error de validacion", e);
            return e.getMessage();
        }

    }
}
