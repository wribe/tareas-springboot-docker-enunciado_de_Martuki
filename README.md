# API de Xestión de Tarefas con Spring Boot

Aplicación web para xestionar tarefas desenvolvida con Spring Boot e base de datos H2 en ficheiro.

## 🛠️ Tecnoloxías

- **Java 21** (Eclipse Temurin)
- **Spring Boot 3.2.2**
  - Spring Web (API REST)
  - Spring Data JPA
  - Spring Boot Actuator
  - Spring Boot Validation
- **H2 Database** (base de datos en ficheiro)
- **Swagger/OpenAPI** para documentación da API
- **Maven** para xestión de dependencias

## 📋 Requisitos Previos

- **Java 21** (JDK) — [Descargar Eclipse Temurin](https://adoptium.net/)
- **Maven 3.9+** — [Descargar Maven](https://maven.apache.org/download.cgi)
- **Git**

Para verificar que tes os requisitos instalados:

```bash
java -version    # Debe mostrar versión 21
mvn -version     # Debe mostrar versión 3.9 ou superior
```

## 🚀 Execución

### 1. Clonar o Repositorio

```bash
git clone https://github.com/mrey-profe/tareas-springboot-h2.git
cd tareas-springboot-h2
```

### 2. Compilar e Executar

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

Ou nun só paso:

```bash
mvn spring-boot:run
```

### 3. Acceder á Aplicación

- **Aplicación web:** http://localhost:8080
- **Consola H2:** http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/tareasdb`
  - Usuario: `sa`
  - Contrasinal: *(baleiro)*
- **Documentación Swagger:** http://localhost:8080/swagger-ui.html
- **Health Check:** http://localhost:8080/actuator/health

## 📡 Endpoints da API

| Método | Endpoint | Descrición |
|--------|----------|------------|
| `GET` | `/api/tareas` | Lista todas as tarefas |
| `GET` | `/api/tareas/{id}` | Obtén unha tarefa polo seu ID |
| `GET` | `/api/tareas/buscar?titulo=texto` | Busca tarefas por título |
| `POST` | `/api/tareas` | Crea unha nova tarefa |
| `PUT` | `/api/tareas/{id}` | Actualiza unha tarefa existente |
| `PUT` | `/api/tareas/{id}/completar` | Marca unha tarefa como completada |
| `DELETE` | `/api/tareas/{id}` | Elimina unha tarefa |

## 📂 Estrutura do Proxecto

```
.
├── src/
│   └── main/
│       ├── java/com/ejemplo/tareas/
│       │   ├── TareasApplication.java      # Clase principal
│       │   ├── controller/
│       │   │   └── TareaController.java    # Controlador REST
│       │   ├── model/
│       │   │   └── Tarea.java              # Entidade JPA
│       │   └── repository/
│       │       └── TareaRepository.java    # Repositorio JPA
│       └── resources/
│           ├── application.properties      # Configuración de Spring
│           └── static/
│               └── index.html              # Interface web
├── .gitignore
├── pom.xml                                 # Dependencias Maven
└── README.md
```

## ⚠️ Notas sobre H2

- A base de datos gárdase no directorio `data/` (excluído de Git)
- Os datos persisten entre reinicios da aplicación
- Para resetear a base de datos, elimina o directorio `data/`
- A consola H2 está habilitada **só para desenvolvemento**; en produción debería desactivarse

## 📜 Licenza

Este proxecto ten fins educativos.
