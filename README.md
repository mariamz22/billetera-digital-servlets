# Billetera Digital - Caso 1 (Servlet + JSP)

T1 - Soluciones Web y Aplicaciones Distribuidas (UPN).
Aplicación web en Java puro (sin frameworks) con Servlet controlador y vista JSP con JSTL. Los datos viven en la `HttpSession`.

## Versiones utilizadas

| Herramienta        | Versión                         |
|--------------------|---------------------------------|
| Java (JDK)         | 17                              |
| Apache Maven       | 3.9.x                           |
| Apache Tomcat      | 10.1.x (Jakarta EE 10)          |
| Servlet API        | Jakarta Servlet 6.0             |
| JSTL               | Jakarta JSTL 3.0                |
| Bootstrap          | 5.3.3 (CDN, requiere internet)  |

> Importante: Tomcat 10.1 usa el paquete `jakarta.*`. Este proyecto **no funciona en Tomcat 9** (que usa `javax.*`).

## Estructura

```
billetera-digital/
├── pom.xml
└── src/main/
    ├── java/pe/upn/billetera/
    │   ├── model/Billetera.java        # saldo + historial
    │   ├── model/Movimiento.java       # tipo + monto
    │   └── servlet/BilleteraServlet.java   # GET muestra, POST procesa
    └── webapp/WEB-INF/views/billetera.jsp  # vista (JSTL + Bootstrap)
```

## Pasos de ejecución

1. Instalar JDK 17, Maven y Tomcat 10.1.
2. Abrir la carpeta `billetera-digital` en VS Code (`File > Open Folder`).
3. En la terminal de VS Code, generar el `.war`:
   ```
   mvn clean package
   ```
   Se crea `target/billetera-digital.war`.
4. Copiar el `.war` a la carpeta `webapps` de Tomcat:
   ```
   cp target/billetera-digital.war <TOMCAT_HOME>/webapps/
   ```
   (En Windows: copiar el archivo con el explorador a `<TOMCAT_HOME>\webapps\`).
5. Iniciar Tomcat:
   - Linux/macOS: `<TOMCAT_HOME>/bin/startup.sh`
   - Windows: `<TOMCAT_HOME>\bin\startup.bat`
6. Abrir en el navegador: <http://localhost:8080/billetera-digital/>

Para detener Tomcat: `shutdown.sh` / `shutdown.bat`.

## Pruebas funcionales mínimas

| # | Acción                                | Resultado esperado                                  |
|---|---------------------------------------|-----------------------------------------------------|
| 1 | Abrir la aplicación                   | Saldo S/ 250.00; historial vacío                    |
| 2 | Registrar recarga de S/ 100.00        | Saldo S/ 350.00; recarga en el historial            |
| 3 | Registrar gasto de S/ 50.00           | Saldo S/ 300.00; gasto aparece antes que la recarga |
| 4 | Intentar gasto de S/ 400.00           | Error "Saldo insuficiente."; saldo sigue en S/ 300.00 |
| 5 | Ingresar cero, negativo o monto vacío | Se rechaza; no cambia saldo ni historial            |
