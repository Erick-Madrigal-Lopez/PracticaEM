package mx.adea.com.PracticaEM.Controller;

import mx.adea.com.PracticaEM.Models.*;
import mx.adea.com.PracticaEM.dao.impl.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.adea.com.PracticaEM.Services.UsuarioService;

import java.util.Base64;
import java.util.Calendar;

@CrossOrigin(origins = {"*"})
@RestController
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    UsuarioRepository repo;
    Calendar calendar;
    String originalInput;
    String encodedString;

    @GetMapping(value = "/test")
    public String test(){
        calendar = Calendar.getInstance();
        originalInput = "Contrasenia";
        encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        log.info("Decodificado: "+decodedString);
        /*Usuario usuario = new Usuario(
                "Usaurio 5",
                encodedString,
                "String nombre",
                new BigDecimal(3),
                "eee@sss.com",
                new Date(),
                null,
                'A',
                new BigDecimal(0),
                null,
                new Date(calendar.get(Calendar.YEAR)-1896, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)),
                new BigInteger("2"),
                "Maridd",
                "Rtte",
                new BigInteger("34"),
                new Date()
        );*/
        //repo.save(usuario);

        /*LoginRequest l = new LoginRequest();
        l.setUsuario("Usaurio 2");
        repo.deleteById(l.getUsuario());
        l.setUsuario("Usaurio 3");
        repo.deleteById(l.getUsuario());
        l.setUsuario("Usaurio 4");
        repo.deleteById(l.getUsuario());*/

        return "repo.findById(l.getUsuario()).get().toString()";
    }

    @GetMapping(value = "/getUsuarios")
    public ResponseEntity<UsuariosResponse> getUsusarios (){

        log.info("Inicia /getUsuarios");
        UsuarioService uService = new UsuarioService();
        ResponseEntity<UsuariosResponse> response = uService.getUsuarios(repo);

        log.info("Termina /getUsuarios \n");
        return response;

    }

    @PostMapping(value = "/registrarUsuario")
    public ResponseEntity<MsjResponse> getUsusarios (@RequestBody UsuarioRequest request){

        log.info("Inicia /registrarUsuario");
        UsuarioService uService = new UsuarioService();
        ResponseEntity<MsjResponse> response = uService.addUsuario(repo, request);

        log.info("Termina /registrarUsuario \n");
        return response;

    }

    @PostMapping(value = "/login")
    public ResponseEntity<MsjResponse> Login (@RequestBody LoginRequest request){

        log.info("Inicia /login");
        UsuarioService uService = new UsuarioService();
        ResponseEntity<MsjResponse> response = uService.doLogin(request, repo);

        log.info("Termina /login \n");
        return response;

    }

    @PostMapping(value = "/editar")
    public ResponseEntity<MsjResponse> Editar (@RequestBody ModificarUsuario request){

        log.info("Inicia /login");
        UsuarioService uService = new UsuarioService();
        ResponseEntity<MsjResponse> response = uService.editUsuario(request, repo);

        log.info("Termina /login \n");
        return response;

    }

    @PostMapping(value = "/eliminar")
    public ResponseEntity<MsjResponse> Eliminar (@RequestBody ModificarUsuario request){

        log.info("Inicia /eliminar");
        UsuarioService uService = new UsuarioService();
        ResponseEntity<MsjResponse> response = uService.eliminarUsuario(request, repo);

        log.info("Termina /eliminar \n");
        return response;

    }
}
