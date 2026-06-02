# 🏨 Sistema de Reservas de Hotel

**Trabajo Final — Programación Orientada a Objetos**  
**Autor:** Jack Anderson Limas Solarte  
**Semestre:** VI

[🌐 Ver Aplicación](https://sistema-hotel-frontend.vercel.app) • [⚙️ API Backend](https://sistema-hotel-reservas.onrender.com)

</div>

---

## 📋 Descripción del Proyecto

Sistema web de gestión hotelera para un hotel boutique pequeño. Permite registrar huéspedes, gestionar habitaciones, crear reservas, solicitar servicios adicionales, realizar check-in y check-out, y generar facturas con registro de pagos.

---

## 🎯 Conceptos POO Aplicados

| Concepto | Dónde se aplica |
|---|---|
| **Abstracción** | Clase abstracta `Habitacion` con método abstracto `calcularPrecio()` |
| **Herencia** | `HabitacionSuite`, `HabitacionEstandar`, `HabitacionFamiliar`, `HabitacionEjecutiva` heredan de `Habitacion` |
| **Polimorfismo** | `calcularPrecio()` se comporta diferente en cada subclase |
| **Encapsulamiento** | Atributos privados con getters/setters en todas las clases |
| **Recursividad** | `OpcionMenu` se referencia a sí misma — `MenuService.construirHijos()` recorre el árbol recursivamente |
| **Modularidad** | Arquitectura por capas: Controller → Service → Repository → Model |

---

## 🛠️ Tecnologías

**Backend**
- Java 21 + Spring Boot 3.x
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA + Hibernate
- PostgreSQL 16

**Frontend**
- Angular 19 (Standalone Components)
- Tailwind CSS
- TypeScript

**Despliegue**
- Backend + BD: [Render](https://render.com)
- Frontend: [Vercel](https://vercel.com)

---

## 📁 Arquitectura por Capas

```
com.hotel.reservas/
├── controller/     → Recibe peticiones HTTP REST
├── service/        → Lógica de negocio
├── repository/     → Acceso a base de datos (JPA)
├── model/          → Entidades JPA
│   └── enums/      → TipoHabitacion, EstadoReserva, etc.
├── dto/            → Objetos de transferencia de datos
└── security/       → JWT, filtros y configuración
```

---

## 👤 Roles del Sistema

| Rol | Descripción |
|---|---|
| **ADMIN** | Recepcionista o administrador del hotel. Acceso total al sistema. |
| **HUESPED** | Cliente registrado. Puede crear reservas y ver sus facturas. |

---

## 📖 Historias de Usuario

### MÓDULO 1 — Autenticación

---

**HU-001 — Registro de huésped**

| | |
|---|---|
| **Como** | Huésped nuevo |
| **Quiero** | Crear una cuenta en el sistema con mis datos personales |
| **Para** | Poder acceder al sistema y hacer reservas |

**Criterios de aceptación:**
- CA1: El sistema valida que el correo no esté registrado previamente
- CA2: La contraseña se almacena encriptada con BCrypt
- CA3: Al registrarse exitosamente, el sistema devuelve un token JWT válido

---

**HU-002 — Inicio de sesión**

| | |
|---|---|
| **Como** | Usuario registrado (ADMIN o HUESPED) |
| **Quiero** | Iniciar sesión con mi usuario y contraseña |
| **Para** | Acceder a las funciones del sistema según mi rol |

**Criterios de aceptación:**
- CA1: El sistema valida credenciales y devuelve un token JWT con duración de 15 minutos
- CA2: Si las credenciales son incorrectas, el sistema muestra un mensaje de error claro
- CA3: El token incluye el rol del usuario para controlar el acceso a los módulos

---

### MÓDULO 2 — Gestión de Huéspedes

---

**HU-003 — Gestión de huéspedes (CRUD)**

| | |
|---|---|
| **Como** | ADMIN |
| **Quiero** | Crear, ver, editar y eliminar registros de huéspedes |
| **Para** | Mantener actualizada la base de datos de clientes del hotel |

**Criterios de aceptación:**
- CA1: El administrador puede registrar un huésped con nombre, correo, teléfono, tipo y número de documento
- CA2: Se puede buscar un huésped por ID y ver todos sus datos
- CA3: El sistema impide eliminar un huésped que tenga reservas activas

---

**HU-004 — Ver y editar perfil propio**

| | |
|---|---|
| **Como** | HUESPED autenticado |
| **Quiero** | Ver y editar mis propios datos de perfil |
| **Para** | Mantener mi información personal actualizada |

**Criterios de aceptación:**
- CA1: El huésped solo puede ver y editar su propio perfil, no el de otros
- CA2: El correo no puede cambiarse si ya está en uso por otra cuenta
- CA3: Los cambios se reflejan inmediatamente en el sistema

---

### MÓDULO 3 — Gestión de Habitaciones

---

**HU-005 — Gestión de habitaciones (CRUD)**

| | |
|---|---|
| **Como** | ADMIN |
| **Quiero** | Crear, ver, editar y eliminar habitaciones del hotel |
| **Para** | Administrar el inventario de habitaciones disponibles |

**Criterios de aceptación:**
- CA1: Se puede crear una habitación especificando su tipo (Suite, Estándar, Familiar, Ejecutiva) y precio base
- CA2: El sistema calcula el precio final automáticamente según el tipo usando polimorfismo
- CA3: Solo el ADMIN puede crear, editar o eliminar habitaciones

---

**HU-006 — Consultar habitaciones disponibles**

| | |
|---|---|
| **Como** | HUESPED o ADMIN |
| **Quiero** | Ver la lista de habitaciones disponibles con su tipo y precio |
| **Para** | Elegir la habitación adecuada antes de hacer una reserva |

**Criterios de aceptación:**
- CA1: El sistema muestra solo las habitaciones con estado disponible = true
- CA2: Se muestra el precio calculado por cada tipo de habitación
- CA3: Cualquier usuario autenticado puede consultar las habitaciones disponibles

---

### MÓDULO 4 — Reservas

---

**HU-007 — Crear reserva**

| | |
|---|---|
| **Como** | HUESPED |
| **Quiero** | Reservar una habitación disponible para un rango de fechas |
| **Para** | Garantizar mi alojamiento en el hotel |

**Criterios de aceptación:**
- CA1: El sistema verifica que la habitación esté disponible en las fechas solicitadas
- CA2: Al crear la reserva, el sistema calcula el total estimado (precio × noches)
- CA3: La reserva queda en estado PENDIENTE y la habitación pasa a no disponible

---

**HU-008 — Ver mis reservas**

| | |
|---|---|
| **Como** | HUESPED |
| **Quiero** | Consultar el historial y estado de mis reservas |
| **Para** | Hacer seguimiento de mis estadías pasadas y futuras |

**Criterios de aceptación:**
- CA1: El huésped puede ver todas las reservas del sistema
- CA2: Cada reserva muestra: habitación, fechas, estado y total estimado
- CA3: El ADMIN puede ver y gestionar todas las reservas

---

**HU-009 — Cancelar reserva**

| | |
|---|---|
| **Como** | HUESPED o ADMIN |
| **Quiero** | Cancelar una reserva existente |
| **Para** | Liberar la habitación si no podré hacer uso de ella |

**Criterios de aceptación:**
- CA1: Solo se pueden cancelar reservas en estado PENDIENTE o CONFIRMADA
- CA2: Al cancelar, la habitación vuelve a estar disponible
- CA3: No se puede cancelar una reserva en estado CHECKIN o CHECKOUT

---

### MÓDULO 5 — Servicios Adicionales

---

**HU-010 — Solicitar servicios adicionales**

| | |
|---|---|
| **Como** | HUESPED con reserva activa |
| **Quiero** | Agregar servicios adicionales a mi reserva (desayuno, spa, transporte) |
| **Para** | Personalizar mi estadía según mis necesidades |

**Criterios de aceptación:**
- CA1: Solo se pueden solicitar servicios a reservas en estado CONFIRMADA o CHECKIN
- CA2: El sistema registra la cantidad de cada servicio y calcula el subtotal
- CA3: Los servicios solicitados se incluirán en la factura final

---

**HU-011 — Gestión de servicios (CRUD)**

| | |
|---|---|
| **Como** | ADMIN |
| **Quiero** | Crear y administrar los servicios adicionales disponibles |
| **Para** | Mantener actualizado el catálogo de servicios del hotel |

**Criterios de aceptación:**
- CA1: El ADMIN puede crear un servicio con nombre, descripción y precio
- CA2: Los servicios se pueden activar o desactivar sin eliminarlos
- CA3: Solo los servicios activos son visibles para los huéspedes

---

### MÓDULO 6 — Check-in y Check-out

---

**HU-012 — Hacer check-in**

| | |
|---|---|
| **Como** | ADMIN |
| **Quiero** | Registrar el check-in de un huésped con reserva confirmada |
| **Para** | Iniciar formalmente la estadía del huésped en el hotel |

**Criterios de aceptación:**
- CA1: Solo se puede hacer check-in a reservas en estado CONFIRMADA o PENDIENTE
- CA2: Al hacer check-in, el estado de la reserva cambia a CHECKIN
- CA3: El sistema registra la fecha y hora del check-in

---

**HU-013 — Hacer check-out**

| | |
|---|---|
| **Como** | ADMIN |
| **Quiero** | Registrar el check-out de un huésped y generar su factura |
| **Para** | Cerrar la estadía y cobrar los servicios consumidos |

**Criterios de aceptación:**
- CA1: Solo se puede hacer check-out a reservas en estado CHECKIN
- CA2: Al hacer check-out, el estado cambia a CHECKOUT y se puede generar la factura
- CA3: La factura incluye el costo de la habitación más todos los servicios solicitados

---

### MÓDULO 7 — Facturación

---

**HU-014 — Ver y pagar factura**

| | |
|---|---|
| **Como** | HUESPED o ADMIN |
| **Quiero** | Consultar la factura detallada de una estadía y registrar el pago |
| **Para** | Conocer el desglose de los cobros y completar el proceso de pago |

**Criterios de aceptación:**
- CA1: La factura muestra el detalle de noches y cada servicio con su subtotal
- CA2: Se puede registrar el método de pago (EFECTIVO, TARJETA, TRANSFERENCIA)
- CA3: Al registrar el pago, la factura cambia a estado PAGADA

---

### MÓDULO 8 — Menú de Navegación Recursivo

---

**HU-015 — Navegación por menú recursivo**

| | |
|---|---|
| **Como** | Usuario autenticado (ADMIN o HUESPED) |
| **Quiero** | Navegar por el sistema usando un menú lateral estructurado por niveles |
| **Para** | Acceder rápidamente a los módulos según mi rol |

**Criterios de aceptación:**
- CA1: El menú se construye dinámicamente desde la base de datos usando la relación padre-hijo
- CA2: Solo se muestran las opciones que corresponden al rol del usuario autenticado
- CA3: El menú soporta 3 niveles de profundidad mediante recursividad


## 🚀 Despliegue

| Componente | Plataforma | URL |
|---|---|---|
| Frontend Angular | Vercel | https://sistema-hotel-frontend.vercel.app |
| Backend Spring Boot | Render | https://sistema-hotel-reservas.onrender.com |
| Base de Datos PostgreSQL | Render | Internal |

> ⚠️ **Nota:** El backend usa el plan gratuito de Render. La primera petición después de inactividad puede tardar hasta 50 segundos (cold start).

---

## 🔐 Credenciales de Prueba

| Rol | Usuario | Contraseña |
|---|---|---|
| ADMIN | `admin` | `admin123` |
| HUESPED | `jack` | `jack123` |

---

## 📡 Endpoints Principales

| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| POST | `/auth/signin` | Login | ❌ |
| POST | `/auth/register` | Registro | ❌ |
| GET | `/api/huespedes` | Listar huéspedes | ✅ |
| GET | `/api/habitaciones/disponibles` | Habitaciones disponibles | ✅ |
| POST | `/api/reservas` | Crear reserva | ✅ |
| PUT | `/api/reservas/{id}/checkin` | Hacer check-in | ✅ |
| PUT | `/api/reservas/{id}/checkout` | Hacer check-out | ✅ |
| POST | `/api/facturas/generar/{idReserva}` | Generar factura | ✅ |
| GET | `/api/menu` | Menú según rol JWT | ✅ |

---

## 🗄️ Modelo de Datos

```
Habitacion (abstracta)
├── HabitacionEstandar
├── HabitacionFamiliar
├── HabitacionSuite
└── HabitacionEjecutiva

Huesped ←── Reserva ──→ Habitacion
                │
                └──→ ReservaServicio ──→ Servicio

Reserva ──→ Factura ──→ DetalleFactura
                │
                └──→ Pago

Usuario ──→ Huesped

OpcionMenu ──→ OpcionMenu (recursiva: padre → hijos)
```

---

## ⚙️ Ejecución Local

### Backend
```bash
cd reservas
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

### Frontend
```bash
cd sistema-hotel
npm install
ng serve
```

---

## 👨‍💻 Autor

**Jack Anderson Limas Solarte**  
Estudiante de Ingeniería — VI Semestre  
Programación Orientada a Objetos  

---

<div align="center">
  <sub>Desarrollado con ❤️ usando Spring Boot + Angular + PostgreSQL</sub>
</div>