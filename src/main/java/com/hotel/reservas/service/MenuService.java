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

    // RECURSIVIDAD — este método construye el árbol de menú de forma recursiva
    // FLUJO: 1) obtiene opciones raíz (sin padre) del repositorio
    //        2) llama construirHijos() por cada raíz
    //        3) construirHijos() se llama a sí mismo por cada hijo encontrado
    //        4) el caso base es cuando un nodo no tiene más hijos
    // RESULTADO: árbol completo OpcionMenu con hijos anidados en memoria
    public List<OpcionMenu> obtenerMenuPorRol(String rol) {
        List<OpcionMenu> todas = opcionMenuRepository.findByRolAndActivoTrue(rol);
        List<OpcionMenu> raices = opcionMenuRepository.findByPadreIsNullAndRolAndActivoTrue(rol);

        for (OpcionMenu raiz : raices) {
            construirHijos(raiz, todas);//trae SOLO los que no tienen padre (los raíz)
        }
        return raices;
    }

    // RECURSIVIDAD — método recursivo que construye el subárbol de un nodo
    // PARÁMETROS: padre=nodo actual, todas=lista plana de todas las opciones
    // CASO BASE: cuando ninguna opción tiene como padre al nodo actual
    //            el for no ejecuta y la recursión termina naturalmente
    // LLAMADA RECURSIVA: construirHijos(hijo, todas) — mismo método, diferente nodo
    // NIVEL 1 padre=null → NIVEL 2 padre=nivel1 → NIVEL 3 padre=nivel2
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
