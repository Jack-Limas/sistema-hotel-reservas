package com.hotel.reservas.controller;

import com.hotel.reservas.model.OpcionMenu;
import com.hotel.reservas.security.JwtUtil;
import com.hotel.reservas.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MODULARIDAD: MenuController extrae el rol del token JWT y delega la construcción del árbol a MenuService.
@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> obtenerMenu(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String rol = jwtUtil.extractRol(token);
            List<OpcionMenu> menu = menuService.obtenerMenuPorRol(rol);
            return ResponseEntity.ok(menu);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
