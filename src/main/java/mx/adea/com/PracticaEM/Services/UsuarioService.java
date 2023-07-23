package mx.adea.com.PracticaEM.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.adea.com.PracticaEM.Models.*;
import mx.adea.com.PracticaEM.Util.Utileria;
import mx.adea.com.PracticaEM.dao.impl.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    Calendar calendar;
    String originalInput;
    String encodedString;

    public ResponseEntity<UsuariosResponse> getUsuarios(UsuarioRepository repo){
        ObjectMapper om = new ObjectMapper();
        UsuariosResponse usuarios = new UsuariosResponse();

        try {
            log.info(repo.toString());
            log.info("repo "+om.writeValueAsString(repo.findAll()));
            usuarios.setUsuarios(repo.findAll(Sort.by(Sort.Direction.DESC, "fechaAlta")));
        }catch (Exception e){
            log.error("Error ",e);
        }
        return ResponseEntity.ok(usuarios);
    }

    public ResponseEntity<MsjResponse> addUsuario(UsuarioRepository repo, UsuarioRequest request){

        MsjResponse response = new MsjResponse();
        Utileria util = new Utileria();
        ObjectMapper om = new ObjectMapper();
        Usuario usuario, usuarioEncontrado;

        calendar = Calendar.getInstance();

        try {

            log.info("validando request "+om.writeValueAsString(request));
            String validaParametros = util.validaParams(request);
            if (!validaParametros.equals("0000")){
                throw new Exception(validaParametros);
            }

            originalInput = request.getPassword();
            byte[] decodedBytes = Base64.getDecoder().decode(originalInput);
            String decodedString = new String(decodedBytes);
            encodedString = Base64.getEncoder().encodeToString(decodedString.getBytes());
            log.info("Decodificado: " + decodedString);

            usuarioEncontrado = repo.findById(request.getLogin()).get(); // Si no existe manda excepsion
            response.setCodigo("9999");
            response.setMensaje("Nombre de usuario no disponible");
            log.error("Nombre de usuario no disponible ");
        } catch(NoSuchElementException e){

            usuario = new Usuario(
                    request.getLogin(),
                    encodedString,
                    request.getNombre(),
                    request.getCliente(),
                    request.getEmail(),
                    new Date(),
                    null,
                    'A',
                    request.getIntentos(),
                    null,
                    new Date(calendar.get(Calendar.YEAR) - 1896, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)),
                    request.getNoAcceso(),
                    request.getApellidoPaterno(),
                    request.getApellidoMaterno(),
                    request.getArea(),
                    new Date()
            );
            log.info("Usuario a registrar: "+(usuario.getLogin()));
            repo.save(usuario);

            response.setCodigo("0000");
            response.setMensaje("Insertado con exito el usuario " +request.getLogin());
        } catch(Exception e){
            response.setCodigo("9999");
            response.setMensaje("Error al guardar usuario "+e.getMessage());
            log.error("track ",e);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MsjResponse> doLogin(LoginRequest request, UsuarioRepository repo){
        MsjResponse response = new MsjResponse();
        Usuario usuario;
        try{
            //Desencripto de SHA Base 64 contrasenia proporcionada
            byte[] decodedBytes = Base64.getDecoder().decode(request.getPassword());
            String decodedString = new String(decodedBytes);
            log.info("Buscando usuario");

            usuario = repo.findById(request.getUsuario()).get();

            Date fechaVigencia = usuario.getFechaVigencia() == null?null:usuario.getFechaVigencia();
            Date fechaRevocado = usuario.getFechaRevocado() == null ? null : usuario.getFechaRevocado();
            Date fechaBaja = usuario.getFechaBaja() == null ? null : usuario.getFechaBaja();
            Date actual = new Date();

            if (fechaVigencia != null) {
                if (!actual.before(fechaVigencia)) {
                    throw new Exception("Cuenta revocada");
                }
            }
            if (fechaRevocado != null) {
                if (!actual.before(fechaRevocado)) {
                    throw new Exception("Cuenta vencida");
                }
            }
            if(fechaBaja != null) {
                if (!actual.before(fechaBaja)) {
                    throw new Exception("Cuenta inactiva");
                }
            }
            if (usuario.getStatus() != 'A') {
                throw new Exception("Estatus de la cuenta no válido");
            }
            //Desencripto de SHA Base 64 contrasenia del usuario en BD
            byte[] decodedBytesUsuario = Base64.getDecoder().decode(usuario.getPassword());
            String decodedStringUsuario = new String(decodedBytesUsuario);

            if (decodedString.equals(decodedStringUsuario)){
                response.setMensaje("Iniciado con exito ");
                response.setCodigo("0000");
            }else throw new Exception("Las contraseñas no coinciden");
        }catch( NoSuchElementException ex){
            response.setMensaje("Error al procesar login Usuario no existente");
            response.setCodigo("9999");
            log.error("error", ex);
        }catch (Exception e){
            response.setMensaje("Error al procesar login "+e.getMessage());
            response.setCodigo("9999");
            log.error("error", e);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MsjResponse> editUsuario(ModificarUsuario request, UsuarioRepository repo) {
        MsjResponse response = new MsjResponse();
        Usuario usuario, usuarioNuevo;
        try{
            log.info("Editando usuario");

            usuario = repo.findById(request.getLogin()).get();
            log.info("Usuario antiguo "+usuario);
            String nombre = request.getNombre().split("\\|")[0];
            String apat = request.getNombre().split("\\|")[1];
            String amat = request.getNombre().split("\\|")[2];
            log.info("nombre => "+nombre);
            log.info("nombre => "+apat);
            log.info("nombre => "+amat);

            usuarioNuevo = new Usuario(
                    request.getLogin(),
                    usuario.getPassword(),
                    nombre, usuario.getCliente(),
                    usuario.getEmail(),
                    usuario.getFechaAlta(),
                    usuario.getFechaBaja(),
                    request.getEstatus(),
                    usuario.getIntentos(),
                    request.getEstatus().equals("R")? new Date(): usuario.getFechaRevocado(),
                    usuario.getFechaVigencia(),
                    usuario.getNoAcceso(),
                    apat,
                    amat,
                    usuario.getArea(),
                    new Date());

            repo.save(usuarioNuevo);

            usuario = repo.findById(request.getLogin()).get();
            log.info("Usuario Nuevo "+usuario);

            response.setMensaje("editado con exito");
            response.setCodigo("0000");

        }catch( NoSuchElementException ex){
            response.setMensaje("Error al editar Usuario no existente");
            response.setCodigo("9999");
        }catch (Exception e){
            response.setMensaje("Error al editar "+e.getMessage());
            response.setCodigo("9999");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MsjResponse> eliminarUsuario(ModificarUsuario request, UsuarioRepository repo) {
        MsjResponse response = new MsjResponse();
        Usuario usuario, nuevoUsuario;
        ObjectMapper om = new ObjectMapper();
        try{
            log.info("Dando de baja usuario");

            String[] nombres = request.getNombre().split("\\|");

            usuario = repo.findById(request.getLogin()).get();

            log.info("Request "+om.writeValueAsString(request));
            log.info("Nombre completo "+om.writeValueAsString(nombres));
            log.info("Usuario encontrado "+om.writeValueAsString(usuario));
            log.info("Usuario existente bajando");
            nuevoUsuario = new Usuario(
                    request.getLogin(),
                    usuario.getPassword(),
                    usuario.getNombre(),
                    usuario.getCliente(),
                    usuario.getEmail(),
                    usuario.getFechaAlta(),
                    new Date(),
                    'B',
                    usuario.getIntentos(),
                    usuario.getFechaRevocado(),
                    usuario.getFechaVigencia(),
                    usuario.getNoAcceso(),
                    usuario.getApellidoPaterno(),
                    usuario.getApellidoMaterno(),
                    usuario.getArea(),
                    new Date());
            repo.save(nuevoUsuario);

            try{
                log.info("Buscando usuario en baja");
                usuario = repo.findById(request.getLogin()).get();
                if (usuario.getStatus().equals('B')){
                    response.setMensaje("Se ha dado de baja "+usuario.getLogin());
                    response.setCodigo("0000");
                    log.info("Usuario dado de baja con exito "+usuario.getLogin());
                }
            }catch(Exception e){
                response.setMensaje("El usuario no existe ");
                response.setCodigo("9999");
                log.info("El usuario no existe", e);
            }

        }catch( NoSuchElementException ex){
            response.setMensaje("Error al eliminar Usuario no existente");
            response.setCodigo("9999");
            log.info("Error al eliminar Usuario no existente", ex);
        }catch (Exception e){
            response.setMensaje("Error al eliminar "+e.getMessage());
            response.setCodigo("9999");
            log.info("Error al eliminar", e);
        }
        return ResponseEntity.ok(response);
    }

}
