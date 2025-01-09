# Proyecto: [Nombre del Proyecto]

Este proyecto es una aplicación diseñada para [breve descripción del propósito del proyecto]. Sigue estos pasos para configurarlo y ejecutarlo en tu entorno local.

## Requisitos Previos

Antes de iniciar, asegúrate de cumplir con los siguientes requisitos:

1. **JDK 21**  
   Descarga e instala la versión 21 de Java Development Kit.
    - Verifica la instalación ejecutando:
      ```bash
      java -version
      ```
    - Si no está instalado, descárgalo desde:
        - [Oracle JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
        - [OpenJDK 21](https://openjdk.org/projects/jdk/21/).

2. **Base de datos**  
   Una base de datos compatible debe estar configurada y disponible para ejecutar los scripts DDL del proyecto.

## Pasos para configurar el proyecto

### Paso 1: Configurar el entorno
- Asegúrate de que **JDK 21** esté correctamente instalado y configurado en tu sistema.
- Configura las variables de entorno necesarias, como `JAVA_HOME`, apuntando a la instalación de JDK.

### Paso 2: Ejecutar los scripts DDL
1. Navega a la carpeta `database` ubicada en la raíz del proyecto.
2. Ejecuta los scripts DDL en tu base de datos. Estos scripts configuran las tablas y objetos necesarios para la aplicación.
### Paso 3: Verificar puerto de despliegue
- El puerto donde se despliega la aplicacion se encuentra por defecto en el 8080, cualquier problema con esto se ajustara en el application.yml
- Las colecciones postman se encuentran en la carpeta postman ubicada en la raiz del proyecto.
