package com.hotel.reservas;

import com.hotel.reservas.model.*;
import com.hotel.reservas.model.enums.*;
import com.hotel.reservas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

// MODULARIDAD: DataInitializer se ejecuta una única vez al arrancar la aplicación
// e inserta datos de prueba solo si las tablas están vacías.
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private HuespedRepository huespedRepository;
    @Autowired private HabitacionRepository habitacionRepository;
    @Autowired private ServicioRepository servicioRepository;
    @Autowired private OpcionMenuRepository opcionMenuRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        inicializarUsuarios();
        inicializarHabitaciones();
        inicializarServicios();
        inicializarMenu();
    }

    private void inicializarUsuarios() {
        if (usuarioRepository.count() > 0) return;

        // ENCAPSULAMIENTO: Las contraseñas nunca se almacenan en texto plano.
        Usuario admin = new Usuario("admin", passwordEncoder.encode("admin123"), Rol.ADMIN, null);
        usuarioRepository.save(admin);

        Huesped h1 = new Huesped("Carlos Gomez", "carlos@email.com", "3001234567",
                TipoDocumento.CEDULA, "12345678");
        huespedRepository.save(h1);
        usuarioRepository.save(new Usuario("carlos", passwordEncoder.encode("carlos123"), Rol.HUESPED, h1));

        Huesped h2 = new Huesped("Maria Lopez", "maria@email.com", "3109876543",
                TipoDocumento.CEDULA, "87654321");
        huespedRepository.save(h2);
        usuarioRepository.save(new Usuario("maria", passwordEncoder.encode("maria123"), Rol.HUESPED, h2));
    }

    private void inicializarHabitaciones() {
        if (habitacionRepository.count() > 0) return;

        // POLIMORFISMO: Cada subtipo de Habitacion implementa calcularPrecio() de forma diferente.
        HabitacionEstandar h101 = new HabitacionEstandar(
                "101", 1, 2, "Habitacion estandar confortable",
                true, TipoHabitacion.ESTANDAR, 150000.0);
        habitacionRepository.save(h101);

        HabitacionFamiliar h201 = new HabitacionFamiliar(
                "201", 2, 4, "Habitacion familiar amplia",
                true, TipoHabitacion.FAMILIAR, 200000.0, 2);
        habitacionRepository.save(h201);

        HabitacionSuite h301 = new HabitacionSuite(
                "301", 3, 2, "Suite de lujo con vista al mar",
                true, TipoHabitacion.SUITE, 350000.0, true, true);
        habitacionRepository.save(h301);

        HabitacionEjecutiva h401 = new HabitacionEjecutiva(
                "401", 4, 1, "Habitacion ejecutiva premium",
                true, TipoHabitacion.EJECUTIVA, 280000.0, true, true);
        habitacionRepository.save(h401);
    }

    private void inicializarServicios() {
        if (servicioRepository.count() > 0) return;

        servicioRepository.save(new Servicio("Desayuno", "Desayuno buffet incluido", 25000.0));
        servicioRepository.save(new Servicio("Spa", "Sesion de spa y relajacion", 80000.0));
        servicioRepository.save(new Servicio("Transporte", "Transporte aeropuerto-hotel", 40000.0));
    }

    private void inicializarMenu() {
        if (opcionMenuRepository.count() > 0) return;

        // RECURSIVIDAD: Los nodos del menú se relacionan entre sí mediante padre/hijos,
        // formando un árbol de N niveles que se recorre recursivamente en MenuService.

        // ── MENÚ ADMIN ──
        OpcionMenu sistemaHotel = save("Sistema Hotel", null, "hotel", 1, "ADMIN", null);

        OpcionMenu gestion = save("Gestion", null, "manage", 1, "ADMIN", sistemaHotel);
        save("Huespedes",   "/admin/huespedes",   "people",   1, "ADMIN", gestion);
        save("Habitaciones","/admin/habitaciones","bed",       2, "ADMIN", gestion);
        save("Reservas",    "/admin/reservas",    "calendar", 3, "ADMIN", gestion);

        OpcionMenu reportes = save("Reportes", null, "bar_chart", 2, "ADMIN", sistemaHotel);
        save("Facturas",  "/admin/facturas",  "receipt",      1, "ADMIN", reportes);
        save("Servicios", "/admin/servicios", "room_service", 2, "ADMIN", reportes);

        // ── MENÚ HUESPED ──
        OpcionMenu miCuenta = save("Mi Cuenta", null, "account_circle", 1, "HUESPED", null);

        OpcionMenu misReservas = save("Reservas", null, "calendar_today", 1, "HUESPED", miCuenta);
        save("Mis Reservas",  "/admin/reservas",       "list",       1, "HUESPED", misReservas);
        save("Nueva Reserva", "/admin/reservas/nueva", "add_circle", 2, "HUESPED", misReservas);

        OpcionMenu misPagos = save("Pagos", null, "payment", 2, "HUESPED", miCuenta);
        save("Mis Facturas", "/admin/facturas", "description", 1, "HUESPED", misPagos);
    }

    // Método auxiliar para reducir repetición al crear opciones de menú.
    private OpcionMenu save(String nombre, String ruta, String icono,
                             int orden, String rol, OpcionMenu padre) {
        return opcionMenuRepository.save(
                new OpcionMenu(nombre, ruta, icono, orden, rol, padre));
    }
}
