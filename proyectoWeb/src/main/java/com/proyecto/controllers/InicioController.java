package com.proyecto.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {

    @GetMapping({"/", "/inicio"})
    public String inicio(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("usuarioCorreo", userDetails.getUsername());
        }
        return "index";
    }

        
    @GetMapping("/cerrar-sesion")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
