
# Guia de Configuració del Repositori Git de Spring Boot

## Estructura del Repositori

```
spring-boot-guia-completa/
├── .gitignore
├── README.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/example/miapp/
│   │   │   ├── MiAplicacioApplication.java
│   │   │   ├── config/
│   │   │   │   ├── ConfiguracioSeguretat.java
│   │   │   │   └── ConfiguracióCORS.java
│   │   │   ├── controller/
│   │   │   │   ├── ControladorUsuaris.java
│   │   │   │   ├── ControladorUsuarisMVC.java
│   │   │   │   └── ControladorAutenticacio.java
│   │   │   ├── service/
│   │   │   │   └── cat.xaviersastre.daw.dwes.codisapunts.service.ServeiUsuaris.java
│   │   │   ├── repository/
│   │   │   │   └── cat.xaviersastre.daw.dwes.codisapunts.repository.RepositoriUsuaris.java
│   │   │   ├── model/
│   │   │   │   ├── cat.xaviersastre.daw.dwes.codisapunts.model.Usuari.java
│   │   │   │   └── cat.xaviersastre.daw.dwes.codisapunts.model.Rol.java
│   │   │   ├── security/
│   │   │   │   ├── cat.xaviersastre.daw.dwes.codisapunts.security.GeneradorJWT.java
│   │   │   │   └── FiltreJWT.java
│   │   │   ├── dto/
│   │   │   │   ├── cat.xaviersastre.daw.dwes.codisapunts.dto.CredencialLogin.java
│   │   │   │   ├── cat.xaviersastre.daw.dwes.codisapunts.dto.RespostaLogin.java
│   │   │   │   ├── UsuariDTO.java
│   │   │   │   └── UsuariCreacioDTO.java
│   │   │   ├── cat.xaviersastre.daw.dwes.codisapunts.mapper/
│   │   │   │   └── MapejadorUsuari.java
│   │   │   ├── exception/
│   │   │   │   ├── cat.xaviersastre.daw.dwes.codisapunts.exception.UsuariNoTrobatException.java
│   │   │   │   └── cat.xaviersastre.daw.dwes.codisapunts.mapper.ManejadorExcepcions.java
│   │   │   └── util/
│   │   │       └── UtilitatsDiverses.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── templates/
│   │       │   └── usuaris/
│   │       │       ├── llista.html
│   │       │       └── formulari.html
│   │       └── static/
│   │           ├── css/
│   │           │   └── estil.css
│   │           └── js/
│   │               └── scripts.js
│   └── test/
│       └── java/com/example/miapp/
│           ├── MiAplicacioApplicationTests.java
│           └── service/
│               └── TestServeiUsuaris.java
└── docs/
    └── API.md
```

## Passos per Crear el Repositori

### 1. Inicialitzar el Repositori Git

```bash
# Crea la carpeta del projecte
mkdir spring-boot-guia-completa
cd spring-boot-guia-completa

# Inicialitza el repositori Git
git init

# Configura les teves dades de Git (opcional però recomanat)
git config user.name "El Teu Nom"
git config user.email "teu@email.com"
```

### 2. Estructura de Carpetes

```bash
# Crea la estructura de carpetes
mkdir -p src/main/java/com/example/miapp/{config,controller,service,repository,model,security,dto,cat.xaviersastre.daw.dwes.codisapunts.mapper,exception,util}
mkdir -p src/main/resources/templates/usuaris
mkdir -p src/main/resources/static/{css,js}
mkdir -p src/test/java/com/example/miapp/service
mkdir -p docs
```

### 3. Afegir Fitxers al Git

```bash
# Afegeix tots els fitxers
git add .

# Primer commit
git commit -m "Commit inicial: Estructura del projecte Spring Boot"

# Veure el status
git status
```

### 4. Remot (si vols pujar a GitHub, GitLab, etc.)

```bash
# Afegir un remot (exemple amb GitHub)
git remote add origin https://github.com/TeuUsuari/spring-boot-guia-completa.git

# Pujar els canvis
git branch -M main
git push -u origin main
```

## Fitxers Essencials per Incloure

Els fitxers que **SEMPRE** s'han d'incloure en el repositori:

- ✅ `pom.xml` - Dependències del projecte
- ✅ `src/` - Tot el codi font
- ✅ `README.md` - Documentació
- ✅ `.gitignore` - Fitxers a ignorar
- ✅ `docs/` - Documentació addicional

Els fitxers que **NO** s'han d'incloure:

- ❌ `target/` - Compilats
- ❌ `.classpath`, `.project` - Fitxers d'IDE
- ❌ `.idea/` - Carpeta d'IntelliJ IDEA
- ❌ `.vscode/` - Carpeta de VS Code
- ❌ `*.log` - Logs
- ❌ `.DS_Store` - Fitxers de macOS

## Branques Recomanades

```bash
# Crear branques de treball
git checkout -b develop
git push -u origin develop

git checkout -b feature/autenticacio
git push -u origin feature/autenticacio

git checkout -b feature/thymeleaf
git push -u origin feature/thymeleaf

git checkout -b bugfix/jwt-expiracio
git push -u origin bugfix/jwt-expiracio
```

## Workflow de Desenvolupament

```bash
# 1. Crear una branca per a la funció
git checkout -b feature/nova-funcio

# 2. Fer canvis i commits
git add src/
git commit -m "Afegida nova funcionalitat"

# 3. Pujar la branca
git push origin feature/nova-funcio

# 4. Crear un Pull Request (PR) a GitHub/GitLab
# (Es fa a través de la interfície web)

# 5. Quan s'accepte el PR, eliminar la branca local
git checkout main
git pull origin main
git branch -d feature/nova-funcio
```

## Comandos Útils de Git

```bash
# Veure el historial de commits
git log --oneline

# Veure les branques
git branch -a

# Canviar de branca
git checkout develop

# Crear i canviar a una nova branca
git checkout -b feature/nova

# Fusionar branques
git merge feature/nova

# Veure els canvis no confirmats
git status

# Veure les diferències
git diff

# Revertir els canvis
git checkout -- src/

# Esborrar una branca local
git branch -d feature/nova

# Esborrar una branca remota
git push origin --delete feature/nova
```

## Commit Messages Recomanats

```
[FEATURE] Afegida autenticació amb JWT
[BUGFIX] Corregit error en validació de mail
[DOCS] Actualitzada documentació de l'API
[REFACTOR] Millorada estructura de carpetes
[TEST] Afegides proves unitàries per a cat.xaviersastre.daw.dwes.codisapunts.service.ServeiUsuaris
[CHORE] Actualitzades dependències de Maven
```