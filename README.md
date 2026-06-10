# Sistema-Avengers

Proyecto Java

**Resumen:**
- Proyecto Java simple ubicado en este repositorio. El flujo de CI está configurado para ejecutarse en cada push a la rama `main` y realiza: compilación con Maven, ejecución de tests, y construcción de una imagen Docker.

**Archivos relevantes:**
- File: [pom.xml](pom.xml) — archivo de build Maven.
- File: [.github/workflows/ci.yml](.github/workflows/ci.yml) — workflow de GitHub Actions (build, test, docker build, push to registries).
- File: [Dockerfile](Dockerfile) — imagen Docker que ejecuta el JAR en `target/`.

**Requisitos (local):**
- Java 17
- Maven 3.6+ (opcional si usas el Docker multi-stage)
- Docker (opcional, para construir/ejecutar la imagen)

**Comandos locales (desde la raíz del repo):**

Build y tests (local, requiere Maven):
```bash
mvn -B clean verify
```

Construir la imagen Docker (multi-stage, no requiere Maven local):
```bash
docker build -t myapp:latest .
```

Probar la imagen Docker:
```bash
docker run --rm -p 8080:8080 myapp:latest
```

**Si no tienes Maven instalado localmente**
- Opción A — construir y empaquetar dentro del `Dockerfile` (multi-stage):
  - El `Dockerfile` compila el proyecto usando Maven en la etapa de build, por lo que `docker build -t myapp:latest .` generará la imagen completa sin necesidad de `mvn` local.
- Opción B — ejecutar Maven usando una imagen de Docker (sin instalar Maven):
  ```powershell
  docker run --rm -v ${PWD}:/work -w /work maven:3.9.4-eclipse-temurin-17 mvn -B clean verify
  ```
  (En PowerShell `${PWD}` funciona como el directorio actual; en CMD usa `%cd%`.)

**Notas sobre errores comunes**
- Error `mvn: command not found`: instala Maven o usa la Opción B anterior.
- Error al hacer `docker build` por falta de `target/*.jar`: ocurre si usas un `Dockerfile` que copia el JAR generado localmente; con el `Dockerfile` multi-stage actual esto ya no debería ocurrir.

**Integración continua (GitHub Actions)**
- El workflow `CI Spring Boot` se activa en cada `push` a `main`.
- Pasos: checkout, setup JDK17, cache Maven (`~/.m2/repository`), `mvn -B clean verify`, build docker image, push image a GHCR y opcionalmente a Docker Hub.

Publicación de imágenes desde el workflow
- GHCR (GitHub Container Registry): configurado por defecto — no se necesita configurar secretos adicionales; el workflow usa `GITHUB_TOKEN` para autenticarse y publicar en `ghcr.io/<owner>/sistema-avengers:<sha>`.
- Docker Hub (opcional): si quieres publicar también en Docker Hub, añade estos `secrets` en el repositorio: `DOCKERHUB_USERNAME` y `DOCKERHUB_TOKEN` (un token de acceso). Si están presentes, el workflow hará login y push a `docker.io/<username>/sistema-avengers:latest`.

Cómo añadir `secrets` en GitHub
1. Ve a tu repositorio en GitHub.
2. En la pestaña `Settings` > `Secrets and variables` > `Actions` > `New repository secret`.
3. Añade `DOCKERHUB_USERNAME` con tu nombre de usuario de Docker Hub.
4. Añade `DOCKERHUB_TOKEN` con un token generado en Docker Hub (o tu contraseña — el token es preferible).

Cómo probar el workflow
1. Asegúrate de que los `secrets` están añadidos (si quieres push a Docker Hub).
2. Haz un commit y push a la rama `main` (por ejemplo cambia el `README.md` y push).
3. Ve a la pestaña `Actions` en GitHub y abre el workflow `CI Spring Boot` para ver la ejecución y logs.

Notas:
- El push a GHCR se realiza siempre; el push a Docker Hub solo se ejecuta si los secrets `DOCKERHUB_USERNAME` y `DOCKERHUB_TOKEN` están presentes.
- Si necesitas que yo adapte el workflow para usar únicamente Docker Hub o para añadir tags semánticos, indícamelo.

**Seguridad / secrets**
- Añade secretos en el repo: `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN` para activar el push a Docker Hub.

**Siguientes pasos opcionales:**
- Cambiar el tag y la política de versiones de la imagen (por ejemplo `:latest` y semver tags).
- Añadir publicación a un registry privado o a un despliegue automático.
- Adaptar `pom.xml` a Spring Boot si lo deseas (puedo hacerlo y añadir plugin `spring-boot-maven-plugin`).

Si quieres, configuro ahora la publicación exclusiva a Docker Hub (remplazando GHCR), o añado más tags/etiquetas en el workflow. Dime cuál prefieres.
# Sistema-Avengers

Proyecto Java

**Resumen:**
- Proyecto Java simple ubicado en este repositorio. El flujo de CI está configurado para ejecutarse en cada push a la rama `main` y realiza: compilación con Maven, ejecución de tests, y construcción de una imagen Docker.

**Archivos relevantes:**
- File: [pom.xml](pom.xml) — archivo de build Maven.
- File: [.github/workflows/ci.yml](.github/workflows/ci.yml) — workflow de GitHub Actions (build, test, docker build).
- File: [Dockerfile](Dockerfile) — imagen Docker que ejecuta el JAR en `target/`.

**Requisitos (local):**
- Java 17
- Maven 3.6+
- Docker (opcional, para construir/ejecutar la imagen)

**Comandos locales (desde la raíz del repo):**

Build y tests:
```bash
mvn -B clean verify
```

Construir la imagen Docker (asegúrate de haber ejecutado `mvn package` antes para generar `target/*.jar`):
```bash
docker build -t myapp:latest .
```

Probar la imagen Docker:
```bash
docker run --rm -p 8080:8080 myapp:latest
```

Nota: Si tu aplicación es una GUI o no expone HTTP en el puerto 8080, ajusta el `Dockerfile` y el mapeo de puertos según corresponda.

**Si no tienes Maven instalado localmente**
- Opción A — construir y empaquetar dentro del `Dockerfile` (multi-stage):
	- El `Dockerfile` ahora compila el proyecto usando Maven en la etapa de build, por lo que `docker build -t myapp:latest .` generará la imagen completa sin necesidad de `mvn` local.
- Opción B — ejecutar Maven usando una imagen de Docker (sin instalar Maven):
	```powershell
	docker run --rm -v ${PWD}:/work -w /work maven:3.9.4-eclipse-temurin-17 mvn -B clean verify
	```
	(En PowerShell `${PWD}` funciona como el directorio actual; en CMD usa `%cd%`.)

**Notas sobre errores comunes**
- Error `mvn: command not found`: instala Maven o usa la Opción B anterior.
- Error al hacer `docker build` por falta de `target/*.jar`: ocurre si usas un `Dockerfile` que copia el JAR generado localmente; con el `Dockerfile` multi-stage actual esto ya no debería ocurrir.

**Integración continua (GitHub Actions):**
- El workflow se activa en cada `push` a `main`.
- Pasos: checkout, setup JDK17, cache Maven, `mvn -B clean verify`, `docker build -t myapp:latest .`.

Si quieres que el workflow suba la imagen a un registro (Docker Hub, GHCR), dime y lo configuro (se necesitarán secrets: `DOCKER_USERNAME` / `DOCKER_PASSWORD` o `CR_PAT`).

**Siguientes pasos opcionales:**
- Publicar la imagen en un registry automático desde el workflow.
- Convertir el `Dockerfile` a multi-stage para construir dentro del contenedor.
- Adaptar `pom.xml` a Spring Boot si tu proyecto es Spring Boot.
# Sistema-Avengers
