package com.hotel.reservas.service;

import com.hotel.reservas.model.OpcionMenu;
import com.hotel.reservas.repository.OpcionMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// MODULARIDAD: MenuService construye la estructura jerárquica del menú por rol.
// RECURSIVIDAD — FLUJO COMPLETO:
//   Backend:  obtenerMenuPorRol() obtiene todos los nodos del rol y los nodos raíz (padre IS NULL).
//             construirHijos() recorre recursivamente cada nivel del árbol en memoria.
//             MenuController expone GET /api/menu; el token JWT determina el rol a usar.
//   Frontend: MenuService de Angular llama a /api/menu y recibe el árbol completo.
//             MenuComponent se renderiza a sí mismo con <app-menu [opciones]="opcion.hijos">
//             dentro de su propio template, creando la recursividad en la vista Angular.
@Service
public class MenuService {

    @Autowired
    private OpcionMenuRepository opcionMenuRepository;

    public List<OpcionMenu> obtenerMenuPorRol(String rol) {
        List<OpcionMenu> todas = opcionMenuRepository.findByRolAndActivoTrue(rol);
        List<OpcionMenu> raices = opcionMenuRepository.findByPadreIsNullAndRolAndActivoTrue(rol);

        for (OpcionMenu raiz : raices) {
            construirHijos(raiz, todas);
        }
        return raices;
    }

    // RECURSIVIDAD: Este método se invoca a sí mismo para construir el árbol nivel por nivel.
    // Caso base: cuando no existen opciones cuyo padre sea el nodo actual, la recursión termina.
    // Cada llamada resuelve los hijos del nodo recibido y luego se llama a sí misma para cada hijo.
    private void construirHijos(OpcionMenu padre, List<OpcionMenu> todas) {
        List<OpcionMenu> hijos = todas.stream()
                .filter(o -> o.getPadre() != null
                        && o.getPadre().getId().equals(padre.getId()))
                .collect(Collectors.toList());

        padre.setHijos(hijos);

        for (OpcionMenu hijo : hijos) {
            construirHijos(hijo, todas);  // llamada recursiva
        }
    }
}
