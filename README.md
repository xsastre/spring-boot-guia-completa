# Spring Boot Guia Completa - Repositori Git

Guia completa de Spring Boot en català amb exemples de codi funcional, diagrames explicatius i totes les millors pràctiques.

## 📋 Contingut

- ✅ Arquitectura de tres capes (Controller-Service-Repository)
- ✅ Autenticació amb JWT (JSON Web Tokens)
- ✅ Seguretat amb Spring Security
- ✅ Base de dades amb JPA i Hibernate
- ✅ Motor de plantilles Thymeleaf
- ✅ Validació de dades
- ✅ Gestió d'excepcions
- ✅ Logging adequat
- ✅ Proves unitàries
- ✅ Documentació Swagger
- ✅ Diagrames d'arquitectura

## 🚀 Começar

### Requisits Previs

- Java 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 o superior
- Un IDE (IntelliJ IDEA recomanat)

### Instal·lació

1. **Clona el repositori**

```bash
git clone https://github.com/TeuUsuari/spring-boot-guia-completa.git
cd spring-boot-guia-completa
```

2. **Crea la base de dades**

```sql
CREATE DATABASE spring_boot_db;
USE spring_boot_db;
```

3. **Configura les propietats**

Edita `src/main/resources/application.properties` amb les teves dades de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_db
spring.datasource.username=root
spring.datasource.password=teva_contrasenya
```

4. **Instal·la les dependències i executa l'aplicació**

```bash
# Instal·la dependències
mvn clean install

# Executa l'aplicació amb MySQL (requereix MySQL instal·lat)
mvn spring-boot:run

# O executa amb H2 (base de dades en memòria, ideal per a proves)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

L'aplicació estarà disponible en: `http://localhost:8080`

**Nota**: Amb el perfil `dev`, pots accedir a la consola H2 a `http://localhost:8080/h2-console` amb:
- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password: (deixar buit)

## 📚 Estructura del Projecte

```
src/main/java/cat/xaviersastre/daw/dwes/codisapunts/
├── AppInicial.java                # Classe principal
├── controller/                    # Controladors REST
│   ├── ControladorAutenticacio.java
│   └── ControladorUsuaris.java
├── service/                       # Lògica de negoci
│   └── ServeiUsuaris.java
├── repository/                    # Accés a dades
│   └── RepositoriUsuaris.java
├── model/                         # Entitats JPA
│   ├── Usuari.java
│   └── Rol.java
├── security/                      # Configuració JWT i seguretat
│   ├── GeneradorJWT.java
│   ├── FiltreJWT.java
│   └── ServeiDetallsUsuari.java
├── config/                        # Configuració de Spring Security
│   └── ConfiguracioSeguretat.java
├── dto/                          # Data Transfer Objects
│   ├── CredencialLogin.java
│   └── RespostaLogin.java
├── mapper/                        # Gestors d'excepcions
│   └── ManejadorExcepcions.java
└── exception/                     # Excepcions personalitzades
    └── UsuariNoTrobatException.java
```

## 🔐 Autenticació

### Login

```bash
POST /api/auth/login
Content-Type: application/json

{
  "mail": "usuari@example.com",
  "contrasenya": "password123"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "missatge": "Login correcte"
}
```

### Ús del Token

Afegeix el token en la capçalera `Authorization`:

```bash
GET /api/usuaris
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📖 Endpoints Principals

### Usuaris

| Mètode | Endpoint | Descripció |
|--------|----------|-----------|
| GET | `/api/usuaris` | Llistar tots els usuaris |
| GET | `/api/usuaris/{id}` | Obtenir usuari per ID |
| POST | `/api/usuaris` | Crear nou usuari |
| PUT | `/api/usuaris/{id}` | Actualitzar usuari |
| DELETE | `/api/usuaris/{id}` | Eliminar usuari |

### Usuaris - Endpoints addicionals

| Mètode | Endpoint | Descripció |
|--------|----------|-----------|
| GET | `/api/usuaris/cerca?nom={nom}` | Cercar usuaris per nom |
| GET | `/api/usuaris/actius` | Obtenir usuaris actius |

### Autenticació

| Mètode | Endpoint | Descripció |
|--------|----------|-----------|
| GET | `/api/auth/health` | Verificar que el servidor està actiu |
| POST | `/api/auth/login` | Login d'usuari |

## 🧪 Proves

```bash
# Executar totes les proves
mvn test

# Executar una classe de proves específica
mvn test -Dtest=TestServeiUsuaris

# Executar amb cobertura
mvn test jacoco:report
```

## 📖 Documentació

La documentació automàtica amb Swagger està disponible en:

```
http://localhost:8080/swagger-ui.html
```

## 🔄 Git Workflow

### Crear una nova branca per a una funcionalitat

```bash
git checkout -b feature/nova-funcionalitat
```

### Fer commits

```bash
git add .
git commit -m "[FEATURE] Afegida nova funcionalitat"
```

### Crear un Pull Request

```bash
git push origin feature/nova-funcionalitat
```

## 📝 Commit Messages

Utilitza el format següent per a commits:

- `[FEATURE]` - Nova funcionalitat
- `[BUGFIX]` - Correcció d'error
- `[DOCS]` - Documentació
- `[REFACTOR]` - Refactorització de codi
- `[TEST]` - Proves
- `[CHORE]` - Tasques de manteniment

Exemple:
```
[FEATURE] Afegida autenticació amb JWT
[BUGFIX] Corregit error en la validació de mail
[DOCS] Actualitzada documentació de l'API
```

## 📚 Recursos Útils

- [Spring Boot Oficial](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/)
- [Thymeleaf](https://www.thymeleaf.org/)

## 🤝 Contribucions

Les contribucions són benvingudes! Per favor:

1. Fork el repositori
2. Crea una branca per a la teva funcionalitat (`git checkout -b feature/AmazingFeature`)
3. Commit els teus canvis (`git commit -m '[FEATURE] Afegida AmazingFeature'`)
4. Push a la branca (`git push origin feature/AmazingFeature`)
5. Obri un Pull Request

## 📄 Llicència

Aquest projecte està sota la llicència MIT. Veure el fitxer `LICENSE` per a més detalls.

## 👤 Autor

Guia completa de Spring Boot en català.

## 📞 Suport

Per a preguntes o problemes, obri un [Issue](https://github.com/TeuUsuari/spring-boot-guia-completa/issues) al repositori.

---

**Última actualització:** 23 de novembre de 2025