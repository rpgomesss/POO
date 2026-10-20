# DomusControl

A smart home management system developed in Java as part of the **Object-Oriented Programming** course (University of Minho, 2025/26).

The application allows managing users, houses, rooms and devices, defining automations, scenarios and schedules, and simulating the passage of time — all through an interactive text-based interface (TUI).

> **Note:** This project is written entirely in **Portuguese**, this includes the source code, file names, and the runtime interface.

## Setup & Running

### Build and run

```bash
./gradlew run
```

The application starts directly in the terminal and displays the main menu, structured as follows:

```
╔══════════════════════╗
║     DomusControl     ║
╚══════════════════════╝
 Horário Atual: 2025-10-01T09:30

 1 - Gestão de Utilizadores
 2 - Gestão de Casas
 3 - Estatísticas
 4 - Avançar Tempo
 5 - Guardar Estado
 6 - Carregar Estado
 0 - Sair
```

Navigate by typing the number of the desired option and pressing `Enter`. Sub-menus follow the same pattern. A typical session looks like:

1. Create a user → `1 → 1`
2. Create a house → `2 → 1` and link it to the user
3. Add rooms → inside the house, use the rooms sub-menu
4. Add devices to the rooms
5. Define automations, scenarios or schedules as needed
6. Advance time (`4`) to trigger scheduled actions
7. Save state (`5`) to persist data between sessions

### Run tests

```bash
./gradlew test
```

## Project Structure

```
POO/
├── app/
│   ├── src/
│   │   ├── main/java/org/domuscontrol/
│   │   │   ├── Main.java                  # Entry point
│   │   │   ├── Utilizador.java            # User entity
│   │   │   ├── casa/                      # House and Room
│   │   │   ├── dispositivos/              # All device types
│   │   │   ├── mecanismos/                # Automations, Scenarios, Schedules, Actions
│   │   │   ├── model/                     # Core model (DomusControl) and services
│   │   │   ├── exceptions/                # Custom exceptions
│   │   │   └── ui/                        # Text-based interface (menus)
│   │   └── test/                          # JUnit 5 tests
│   └── estado.bin                         # Serialized state file (generated at runtime)
├── gradlew / gradlew.bat
└── settings.gradle.kts
```

## Features

### User Management
- Create and remove users
- Associate users to houses as a **resident** or **administrator**
- View the interaction history per user
- Generate **automatic scheduling suggestions** based on repeated usage patterns

### House & Room Management
- Create houses with a name and location
- Add and remove **rooms** within each house
- List all devices in a house or specific room

### Device Management
Each device can be turned on/off and has type-specific properties:

| Device               | Specific Properties         |
|----------------------|-----------------------------|
| Light (Lâmpada)      | Brightness (%)              |
| Air Conditioning     | Temperature (°C)            |
| Oven (Forno)         | Temperature (°C)            |
| Television           | Channel                     |
| Speaker (Coluna)     | Volume                      |
| Vacuum (Aspirador)   | Suction level               |
| Security Camera      | Recording state             |
| Blind (Persiana)     | Position (open/closed)      |
| Gate (Portão)        | State (open/closed)         |

### Automations
Condition -> action rules evaluated on demand:
- **Available conditions:** device on, device off, consumption greater than X watts, temperature greater than X °C
- **Available actions:** turn device on/off, change temperature, change lamp brightness

### Scenarios
Groups of actions that can be **activated all at once**. Useful for profiles like "Cinema Mode" or "Night Mode".

### Schedules
Actions scheduled to trigger automatically at a given time of day (in minutes since midnight). Executed automatically when **time is advanced**.

### Statistics
- Top devices by energy consumption
- Top houses by total consumption
- User interaction history

### Time Simulation
- Advance the internal clock by a number of minutes
- Automatically triggers all **schedules** whose time has been reached

### State Persistence
- **Save:** serializes the full application state to a `.bin` file
- **Load:** restores state from a previously saved `.bin` file
- The default file is `app/estado.bin`

