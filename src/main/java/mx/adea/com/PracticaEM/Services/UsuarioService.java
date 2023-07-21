package mx.adea.com.PracticaEM.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.adea.com.PracticaEM.Models.*;
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

        calendar = Calendar.getInstance();
        try {
            originalInput = request.getPassword();
            encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            log.info("Decodificado: " + decodedString);
            Usuario usuario = new Usuario(
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
            repo.save(usuario);

            response.setCodigo("0000");
            response.setMensaje("Insertado con exito el usuario " +request.getLogin());
        }catch (Exception e){
            response.setCodigo("0000");
            response.setMensaje("Error al guardar usuario "+e.getMessage());
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
        }catch (Exception e){
            response.setMensaje("Error al procesar login "+e.getMessage());
            response.setCodigo("9999");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MsjResponse> editUsuario(ModificarUsuario request, UsuarioRepository repo) {
        MsjResponse response = new MsjResponse();
        Usuario usuario, usuarioNuevo;
        try{
            log.info("Editando usuario");

            usuario = repo.findById(request.getLogin()).get();

            usuarioNuevo = new Usuario(request.getLogin(), request.getNombre(), request.getEstatus(), "", "", new Date());

            repo.save(usuarioNuevo);

            usuario = repo.findById(request.getLogin()).get();

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
        Usuario usuario;
        try{
            log.info("Eliminando usuario");

            usuario = repo.findById(request.getLogin()).get();

            repo.deleteById(request.getLogin());

            try{
                log.info("Buscando usuario eliminado");
                usuario = repo.findById(request.getLogin()).get();
            }catch(Exception e){
                response.setMensaje("Eliminado con exito ");
                response.setCodigo("0000");
            }

        }catch( NoSuchElementException ex){
            response.setMensaje("Error al eliminar Usuario no existente");
            response.setCodigo("9999");
        }catch (Exception e){
            response.setMensaje("Error al eliminar "+e.getMessage());
            response.setCodigo("9999");
        }
        return ResponseEntity.ok(response);
    }

}
