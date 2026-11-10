# Smart Home Management System
Aplicación de escritorio desarrollada en **Java 21**, **JavaFX** y **FXML** para la gestión, automatización y control centralizado de un hogar inteligente. El sistema permite administrar dispositivos individuales (luces, termostatos y cerraduras inteligentes) y grupos de dispositivos mediante una arquitectura limpia basada en patrones de diseño orientados a objetos.

---

##  Miembros del equipo

* **Jose Alberto Hernández León**
* **Alejandra Ibarra Ortiz** 

---

##  Funcionalidades Principales

* **Control de Iluminación:** Encendido, apagado y ajuste de nivel de brillo en luces de forma individual o grupal.
* **Climatización Dinámica:** Configuración de temperaturas objetivo y selección de modos climáticos (Comfort, Eco, Night) con simulación en tiempo real mediante bucles de eventos (Timelines).
* **Seguridad y Control de Acceso:** Gestión avanzada de cerraduras inteligentes (`SmartLock`) con validación de PINs, bloqueo automático tras intentos fallidos y gestión de credenciales de usuario/maestro.
* **Automatización y Macros:** Ejecución y reversión de comandos mediante un historial centralizado (`CommandHistory`) y soporte para acciones compuestas (`MacroCommand`).
* **Coordinación del Hogar (Mediador):** Desbloqueo y bloqueo global del hogar que sincroniza múltiples dispositivos de manera simultánea (ej. al abrir la casa se encienden las luces y se ajusta el termostato).
* **Agrupación Jerárquica:** Creación de grupos de dispositivos (`DeviceGroup`) aplicando el patrón Composite para enviar órdenes masivas (apagar todo, cambiar temperatura grupal, etc.).

---

##  Estructura General del Proyecto

```text
src/main/java/edu/smarthome/
├── app/                  # Punto de entrada de la aplicación JavaFX (Launcher, SmartHomeApplication)
├── controller/           # Controladores FXML de la interfaz gráfica (HomeController, ThermostatController, SmartLockController, GroupController, SecurityController, DeviceController)
├── model/                # Entidades de dominio y estados (Device, Light, SmartLock, Thermostat, DeviceEvent)
├── pattern/              # Implementación de los patrones de diseño
│   ├── behavior/         # Comportamiento: Command, Mediator, Observer, State, Strategy
│   ├── creational/       # Creacionales: Factory Method (DeviceCreator, DeviceSpec)
│   └── structural/       # Estructurales: Composite (DeviceGroup), Facade (SmartHomeFacade)
└── service/              # Servicios de fachada del sistema (SmartHomeFacade)
---
# Patrones de comportamiento
## observer (DeviceObserver, DeviceEvent, Device)
- Ubicación: edu.smarthome.pattern.behavior.observer y edu.smarthome.model.Device
- Problema que resuelve: Notificar de manera desacoplada a los componentes interesados 
 (como registros de eventos o la interfaz) cuando ocurre un cambio de estado relevante 
 en un dispositivo (ej. cambio de brillo, temperatura alcanzada, PIN incorrecto).

## Strategy (ClimateStrategy, ComfortStrategy, EcoStrategy, NightStrategy)
- Ubicación: edu.smarthome.pattern.behavior.strategy
- Problema que resuelve: Permite intercambiar dinámicamente las reglas de
  ajuste de temperatura y tasas de cambio del termostato según el modo operativo
  seleccionado por el usuario, evitando estructuras condicionales rígidas.

## Command (Command, CommandHistory, LockDoorCommand, UnlockDoorCommand, SetBrightnessCommand, MacroCommand)
- Ubicación: edu.smarthome.pattern.behavior.command
- Problema que resuelve: Encapsula las peticiones y operaciones del usuario como objetos independientes, 
  posibilitando el almacenamiento en un historial para soportar operaciones de deshacer (undo) y rehacer (redo).

## State (LockState, LockedState, UnlockedState, BlockedState, SmartLock)
- Ubicación: edu.smarthome.pattern.behavior.state y edu.smarthome.model.SmartLock
-Problema que resuelve: Modifica el comportamiento interno de la cerradura inteligente 
en función de su estado actual (Bloqueada, Desbloqueada o Bloqueada por seguridad),
controlando de forma limpia las transiciones y restricciones de gestión de PINs.

## Mediator (HomeMediator, SmartHomeMediator)
- Ubicación: edu.smarthome.pattern.behavior.mediator
- Problema que resuelve: Centraliza la comunicación 
  compleja entre múltiples dispositivos 
  (cerradura, luces, termostatos) al ejecutar acciones
  globales como "Desbloquear casa" o "Bloquear casa",
  evitando el acoplamiento directo entre ellos.

# patrones adicionales
## Factory Method — Creacional
- Ubicación: edu.smarthome.pattern.creational.factory.DeviceCreator
- Justificación: Centraliza y desacopla la lógica de instanciación 
  de dispositivos complejos mediante especificaciones (DeviceSpec),
  asegurando una correcta asignación de identificadores y observadores
  por defecto.

## Facade — Estructural
- Ubicación: edu.smarthome.service.SmartHomeFacade
- Justificación: Proporciona una interfaz unificada y simplificada 
  para que los controladores gráficos (HomeController, ThermostatController,
  etc.) interactúen con los subsistemas complejos sin conocer los detalles de bajo nivel.

## Composite — Estructural
- Ubicación: edu.smarthome.pattern.structural.composite
- Justificación: Permite tratar de manera uniforme tanto a
  dispositivos individuales como a agrupaciones de ellos (DeviceGroup), 
  facilitando operaciones masivas implementadas en el GroupController.

## Configuración y Ejecución
-Prerrequisitos
**Java Development Kit (JDK 21) instalado y configurado en el sistema.**

**Gradle (o uso del wrapper incluido en el proyecto).**

**Entorno de desarrollo compatible con JavaFX (IntelliJ IDEA recomendado).**

##Instrucciones para clonar y ejecutar
- Clonar el repositorio:
 git clone <URL-DEL-REPOSITORIO-GITHUB>
 cd smart-home

- Ejecutar la aplicación mediante Gradle:

 ** En sistemas Linux/macOS: ./gradlew run
 ** En sistemas Windows (Command Prompt / PowerShell): gradlew.bat run
