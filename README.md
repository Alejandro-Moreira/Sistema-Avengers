# Sistema-Avengers
**Resumen:**
- Este repo contiene el código Java y un workflow de GitHub Actions que, en cada push a `main`, compila y ejecuta los tests con Maven.

**Archivos relevantes:**
- File: [pom.xml](pom.xml) — build Maven.
- File: [.github/workflows/ci.yml](.github/workflows/ci.yml) — workflow CI (checkout, JDK17, cache Maven, `mvn -B clean verify`).

**Requisitos (local):**
- Java 17
- Maven 3.6+ (solo si quieres ejecutar builds localmente)

**Comandos locales (desde la raíz del repo):**

Build y tests:
```bash
mvn -B clean verify
```

**Integración continua (GitHub Actions)**
- El workflow `CI Spring Boot` se activa en cada push a `main` y ejecuta:
- `actions/checkout@v3`
- `actions/setup-java@v3` (Temurin JDK 17)
- Caché de Maven (`~/.m2/repository`) para acelerar builds
- `mvn -B clean verify` (build + tests)

No se realiza ningún build o push de imágenes Docker en el workflow actual.
