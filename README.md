# 🧱 Wompi API Test Automation Framework

Este repositorio contiene el **framework de pruebas automatizadas** para validar los servicios REST de **Wompi**, diseñado bajo el **patrón Screenplay** con **Java 21**, **Maven**, **Cucumber** y **RestAssured**.

---

## 🎯 Objetivo

Diseñar una arquitectura **modular, mantenible y escalable** que permita:
- Automatizar pruebas funcionales y de integración sobre la API de Wompi.
- Separar claramente la lógica técnica de los escenarios BDD.
- Reutilizar código y ejecutar pruebas en distintos entornos (Sandbox, Producción).

---

## 🧩 Componentes Principales

| Capa | Componente | Rol |
|------|-------------|-----|
| **Configuración** | `EnvConfig (Singleton)` | Centraliza variables de entorno (.env o CI/CD). |
| **Cliente HTTP** | `WompiClient` | Encapsula la comunicación con la API de Wompi (RestAssured). |
| **Habilidad (Ability)** | `CallAnApi` | Permite al *Actor* usar el cliente HTTP. |
| **Actor** | `Actor` | Representa al usuario o sistema que ejecuta acciones. |
| **Tareas (Tasks)** | `CreatePseTransaction`, `PollTransactionStatus`, etc. | Describen acciones realizadas contra la API. |
| **Preguntas (Questions)** | `TransactionStatus`, etc. | Consultan o validan resultados de la API. |
| **Escenarios BDD** | `*.feature` + Step Definitions | Describen los flujos de prueba con Gherkin. |

---

## 🧠 Relación de la Arquitectura

```
           ┌──────────────────────────────┐
           │        EnvConfig             │
           │ (Singleton: lee .env, keys)  │
           └──────────────┬───────────────┘
                          │
                          ▼
                ┌─────────────────┐
                │   WompiClient   │
                │ (usa RestAssured│
                │  para llamar API)│
                └───────┬─────────┘
                        │
                        ▼
              ┌────────────────────┐
              │     CallAnApi      │
              │ (Ability del Actor │
              │  que contiene el   │
              │  WompiClient)      │
              └────────┬───────────┘
                       │
                       ▼
                ┌────────────┐
                │   Actor    │
                │ (quien     │
                │ ejecuta)   │
                └────┬───────┘
                     │
       ┌─────────────┼───────────────────┐
       ▼             ▼                   ▼
┌────────────┐ ┌──────────────┐ ┌─────────────────┐
│  Task:     │ │  Task:       │ │  Question:      │
│ CreatePSE  │ │ PollStatus   │ │ TransactionStatus│
└────────────┘ └──────────────┘ └─────────────────┘
       │
       ▼
┌─────────────────────┐
│ Cucumber Steps      │
│ (BDD integration)   │
│ ejecutan Tasks y    │
│ validan Questions   │
└─────────────────────┘
```

---

## ⚙️ Flujo de Ejecución

1. El test crea un **Actor** (por ejemplo, `"QA Tester"`).
2. El actor obtiene la habilidad `CallAnApi.usingSandbox()`.
3. La habilidad contiene un `WompiClient` configurado desde `EnvConfig`.
4. El actor ejecuta tareas como `CreatePseTransaction` o `PollTransactionStatus`.
5. El `WompiClient` realiza las llamadas HTTP reales al API.
6. Las *Questions* verifican resultados (`TransactionStatus`, etc.).
7. Cucumber reporta los resultados (HTML, JSON o consola).

---

## 📦 Estructura del Proyecto

```
com.company.wompi
│
├── config/                 →  Configuración global (Singleton EnvConfig)
├── clients/                →  Cliente REST (WompiClient)
├── screenplay/
│    ├── actor/             →  Clase Actor
│    ├── ability/           →  CallAnApi
│    ├── task/              →  Tareas del API
│    └── question/          →  Validaciones
│
├── steps/                  →  Step Definitions de Cucumber
└── resources/features/     →  Escenarios BDD (Gherkin)
```

---

## 🔒 Variables de Entorno

Se definen en un archivo `.env` o en CI/CD:

```env
WOMPI_SANBOX_URL=https://api.co.uat.wompi.dev/v1
WOMPI_PROD_URL=https://api.wompi.co/v1
WOMPI_PUBLIC_KEY=pub_stagtest_xxxxxxxxxxxxxxxxxxx
WOMPI_PRIVATE_KEY=prv_stagtest_xxxxxxxxxxxxxxxxxxx
```

---

## 🚀 Ejecución de Pruebas

### 🧠 Local
```bash
mvn clean test
```

### 🤖 CI/CD
Configura los `secrets` en GitHub Actions o Jenkins:

```yaml
env:
  WOMPI_SANBOX_URL: https://api.co.uat.wompi.dev/v1
  WOMPI_PROD_URL: https://api.wompi.co/v1
  WOMPI_PUBLIC_KEY: ${{ secrets.WOMPI_PUBLIC_KEY }}
  WOMPI_PRIVATE_KEY: ${{ secrets.WOMPI_PRIVATE_KEY }}
```

---

## 🧪 Ejemplo de Escenario (BDD)

```gherkin
Feature: PSE payment integration

  Scenario: Create and validate a PSE transaction
    Given a merchant public key configured
    When the actor creates a PSE transaction with amount 10000 and customer "Juan Perez"
    Then the API returns a 201 created and a transaction id
    When the actor polls the transaction status until a terminal state
    Then the final transaction state should be "APPROVED"
```

---

## 🏁 Conclusión

Este framework implementa un enfoque profesional de automatización BDD usando el **Patrón Screenplay**, donde:
- `EnvConfig` centraliza configuración sensible.
- `WompiClient` encapsula las llamadas HTTP.
- `CallAnApi` da al *Actor* la capacidad de conectarse al API.
- `Tasks` y `Questions` abstraen la lógica de negocio.
- `Cucumber` integra todo en escenarios legibles, ejecutables y trazables.

**Resultado:** un entorno sólido, escalable y mantenible para pruebas automatizadas de servicios Wompi.
