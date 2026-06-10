# Sistema-Avengers

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
