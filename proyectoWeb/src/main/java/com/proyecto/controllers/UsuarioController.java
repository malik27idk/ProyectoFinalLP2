package com.proyecto.controllers;

import com.proyecto.models.Usuario;
import com.proyecto.models.Rol;
import com.proyecto.models.UsuarioRol;
import com.proyecto.services.IUsuarioService;
import com.proyecto.repositories.IRolRepository;
import com.proyecto.repositories.IUsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolRepository rolRepo;

    @Autowired
    private IUsuarioRolRepository usuarioRolRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ✅ Mostrar login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // ✅ Mostrar registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // ✅ Procesar registro
    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEstado(1);

        Usuario usuarioGuardado = usuarioService.guardar(usuario);

        Rol rolCliente = rolRepo.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("El rol CLIENTE no está registrado"));

        UsuarioRol ur = new UsuarioRol();
        ur.setUsuario(usuarioGuardado);
        ur.setRol(rolCliente);
        usuarioRolRepo.save(ur);

        return "redirect:/productos";
    }

    // ✅ Mostrar "Mi cuenta"
    @GetMapping("/micuenta")
    public String miCuenta(Model model, Principal principal) {
        String correo = principal.getName();
        Usuario usuario = usuarioService.findByCorreo(correo);
        model.addAttribute("usuario", usuario);
        return "micuenta";
    }

    // ✅ Guardar cambios de datos personales
    @PostMapping("/micuenta/guardar")
    public String actualizarMiCuenta(@ModelAttribute("usuario") Usuario usuarioForm, Principal principal) {
        String correo = principal.getName();
        Usuario usuarioBD = usuarioService.findByCorreo(correo);

        // Solo actualiza los campos editables
        usuarioBD.setNombres(usuarioForm.getNombres());
        usuarioBD.setApellidos(usuarioForm.getApellidos());
        usuarioBD.setDireccion(usuarioForm.getDireccion());
        usuarioBD.setSexo(usuarioForm.getSexo());
        usuarioBD.setFechaNacimiento(usuarioForm.getFechaNacimiento());

        usuarioService.guardar(usuarioBD);
        return "redirect:/micuenta?success";
    }

    // ✅ Cambiar contraseña
    @PostMapping("/micuenta/cambiar-password")
    public String cambiarPassword(
            @RequestParam("actual") String actual,
            @RequestParam("nueva") String nueva,
            @RequestParam("confirmar") String confirmar,
            Principal principal) {

        String correo = principal.getName();
        Usuario usuario = usuarioService.findByCorreo(correo);

        // Verifica contraseña actual y coincidencia
        if (!passwordEncoder.matches(actual, usuario.getPassword()) || !nueva.equals(confirmar)) {
            return "redirect:/micuenta?errorPass";
        }

        // Guardar nueva contraseña
        usuario.setPassword(passwordEncoder.encode(nueva));
        usuarioService.guardar(usuario);

        return "redirect:/micuenta?successPass";
    }
}
