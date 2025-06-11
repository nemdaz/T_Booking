# PLAN DE PRUEBAS

## 1. Introducción

##### Nombre


| Nombre              | Detalle                                | Versión |
| :-------------------- | :--------------------------------------- | ---------- |
| PPS_010100_00000002 | Plan de pruebas para el APK de Booking | v1.0.0   |

##### Componentes


| Tipo       | Código | Descripción       |
| :----------- | :-------- | :------------------- |
| Sistema    | 01      | Booking            |
| Subsistema | 0101    | Booking Mobile App |
| Módulo    | 010100  | Todos              |

### Responsables


| Código | Rol                | Nombre  | Función                                  |
| :-------- | :------------------- | :-------- | :------------------------------------------ |
| 01      | Autor              | Nem Daz | Elaboración del Plan de Pruebas          |
| 01      | Líder de proyecto | Nem Daz | Liderar la ejecución del Plan de Pruebas |

### Objetos relacionados


| Descripción                              | Origen/Fuente                                                  | Version |
| :------------------------------------------ | :--------------------------------------------------------------- | :-------- |
| Definición y especificación del sistema | https://play.google.com/store/apps/details?id=com.booking      | NA      |
| Repositorio de instalador APK             | https://apkarchive.org/booking-com-hotels-and-more/com.booking | NA      |

## 2. Objetivo

La finalidad del presente documento es definir las pautas y estrategias que nos permitirán cumplir con la certificación de calidad del aplicativo móvil 010100 del sistema Booking.

El objetivo general es definir las condiciones que nos permitan ejecutar las pruebas y en consecuencia nos entregue un sistema que cumpla con las funcionalidades requeridas por todos los interesados.

El plan de pruebas debe incluir las pautas necesarias para recrear un ambiente de pruebas automatizados.

## 3. Alcance

El presente documento cumple con servir de guía en el proceso de ejecución de pruebas para los responsables del proyecto.

El plan de pruebas se enmarca al modulo 010100 del sistema Booking la cual corresponde a funcionalidades de la aplicación móvil (Ver punto 1 en "Objetos relacionados").

## 4. Objetos de Pruebas


| Componente                 | Nombre/Descripción         | Versión | Identificador        | Observaciones                                                                                                                                                                                                        |
| :--------------------------- | :---------------------------- | :--------- | :--------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Aplicación móvil Android | Booking.com Rservas Hoteles | 56.6     | Booking.com-56.6.apk | Build source:<br /> [https://apkarchive.org/download/split/booking-com-hotels-and-more/com.booking/22581209/2001769](https://apkarchive.org/download/split/booking-com-hotels-and-more/com.booking/22581209/2001769) |

Cada uno de estos componentes será sometido a pruebas según el alcance y criterios definidos en este documento.

## 5. Entorno de Pruebas

### Hardware del entorno


| Tipo                   | Nombre           | Detalle                                                 |
| :----------------------- | :----------------- | :-------------------------------------------------------- |
| Dispositivo Laptop     | Lenovo Yoga 7    | Ryzen 7 Serie 4000, 16GB RAM, 500GB SSD, Windows 11 HSL |
| Dispositivo Smartphone | Motorola Edge 30 | Android 14, 8GB RAM, 128GB ROM                          |

### Software requerido


| Tipo     | Nombre / Versión     | Descripción / Uso                                                                          |
| :--------- | :---------------------- | :-------------------------------------------------------------------------------------------- |
| Software | VSCode v1.100.3       | Editor/IDE.                                                                                 |
| Software | SourceTree 3.4.23     | Gestor de control de versiones git.                                                         |
| Software | Git v2.49.0           | Herramienta de control de versiones.                                                        |
| Servicio | GitHub                | Repositorio en la nube para el control de versiones git.                                   |
| Software | NodeJS v22.16.0       | Necesario para instalar Appium v2 y su driver.                                              |
| Software | Android SDK Tools     | Herramientas de android (Google).                                                           |
| Software | scrcpy v3.2           | Visor de pantalla de Android en Windows                                                     |
| Software | Appium v2.19.0        | Framework de automatización de pruebas en aplicaciones web nativas, híbridas y móviles. |
| Software | uiautomator2          | Driver necesario para la conexión entre Appium y el dispositivo                            |
| Software | appium-inspector      | Aplicativo desktop para inspeccionar apps/apk en el proceso de pruebas.                     |

## 6. Configuración del Entorno de Pruebas

Guía para la descarga, instalación y configuración de cada software requerido para el entorno de pruebas.

---

Nota:

- Seguir el orden de los pasos para evitar problemas de dependencias.
- Ante errores, consultar la documentación oficial de cada herramienta.

---

### 6.1 Instalación de software requerido

#### - Git

- **Descarga:**
  [https://git-scm.com/download/win](https://git-scm.com/download/win)
- **Instalación:**
  Ejecutar el instalador descargado y seguir el asistente. Mantener opciones por defecto es recomendado.
- **Configuración (opcional):**
  Abrir Git Bash y configurar nombre y correo global:
  ```bash
  git config --global user.name "tu nombre"
  git config --global user.email "tu@correo.com"
  ```

#### - SourceTree

- **Descarga:**
  [https://www.sourcetreeapp.com/](https://www.sourcetreeapp.com/)
- **Instalación:**
  Ejecutar el instalador. Seguir los pasos para la creación o vinculación de una cuenta Atlassian (requerido para primer uso).
- **Configuración:**
  Vincular tu cuenta de GitHub en el menú **Tools > Options > Authentication**.

#### - VSCode

- **Descarga:**
  [https://code.visualstudio.com/sha/download?build=stable&os=win32-x64](https://code.visualstudio.com/sha/download?build=stable&os=win32-x64)
- **Instalación:**
  Ejecutar el instalador y aceptar los valores predeterminados.
- **Configuración (opcional):**
  Instalar extensiones recomendadas: Java Extension Pack, Maven for Java.

#### - NodeJS

- **Descarga:**
  [https://nodejs.org/en/](https://nodejs.org/en/)
- **Instalación:**
  Descargar el instalador LTS y ejecutarlo. Seguir los pasos del asistente.

#### - GitHub (Cuenta en la nube)

- **Registro:**
  [https://github.com/](https://github.com/)
- **Configuración:**
  Crear una cuenta nueva o usar una existente.

#### - Android Studio & Android SDK Tools

- **Descarga:**
  [https://developer.android.com/studio](https://developer.android.com/studio)
- **Instalación:**
  Ejecutar el instalador y seguir el asistente.
- **Configuración:**
  Ubicar la ruta del SDK, normalmente:
  `C:\Users\<tu_usuario>\AppData\Local\Android\Sdk`
- **Variables de entorno del sistema:**
  Creamos y/o verificamos:

  ```
  Creamos/Verificamos la variable:

  ANDROID_HOME=C:\Users\<tu_usuario>\AppData\Local\Android\Sdk

  Actualizamos la variable PATH existente:

  %ANDROID_HOME%\platform-tools
  %ANDROID_HOME%\build-tools
  %ANDROID_HOME%\build-tools\adb
  ```

#### - scrcpy

- **Descarga:**
  [https://github.com/Genymobile/scrcpy/releases](https://github.com/Genymobile/scrcpy/releases)
- **Instalación:**
  Descargar el archivo ZIP, extraerlo y ejecutar `scrcpy.exe`.
- **Requisito:**
  El dispositivo Android debe tener activada la **depuración USB**.

#### - NodeJS Dependencias para Automatización

- **Appium y uiautomator2**

  ```cmd
  npm install -g appium
  appium driver install uiautomator2

  [Verificación (CMD)]
  appium --version
  appium driver list
  ```

- **Appium Inspector**
  Descargar de: [https://github.com/appium/appium-inspector/releases](https://github.com/appium/appium-inspector/releases)
  Ejecutar el instalador descargado.

#### - Variables de entorno adicionales

Configurar/verificar en **Propiedades del Sistema > Configuración avanzada del sistema > Variables de entorno**:

```cmd
JAVA_HOME=D:\Program Files\java\jdk
MAVEN_HOME=D:\Program Files\apache-maven

[Verificación (CMD)]
java --version
mvn --version
```

### 6.2 Despliegue/Instalación de objetos de pruebas

- **Descarga:**
  Obtener el archivo del repositorio indicado en la sección "Objetos de prueba".
- **Instalación:**
  Abrir el terminal (CMD) y ejecutar el siguiente comando en la ruta donde se encuentre el objecto de prueba:

  ```
  Desinstalar versiones previas:
  adb uninstall com.booking

  Instalar aplicación:
  adb install-multiple Booking.com-56.6.apk config.arm64_v8a.apk config.xxhdpi.apk

  ```

---

## 7. Estrategias de Pruebas

Para el cumplimiento de los objetivos se plantean estrategias de pruebas las cuales estarán en función a los siguientes tipos, niveles y técnicas de pruebas.


| Tipo de Prueba      | Nivel de Prueba       | Técnica de Prueba                                                             |
| :-------------------- | :---------------------- | :------------------------------------------------------------------------------- |
| Prueba Funcional    | Prueba de componentes | Pruebas de equivalencia y valores limites.<br />Pruebas de humo/exploratorias. |
| Prueba No Funcional | Prueba de componentes | Pruebas de humo/exploratorias.                                                 |

###### Actividades estratégicas:

- Identificar la estructura DOM del componente APK usando herramientas para este fin.
- Desarrollar las pruebas usando el patron POM (Page Object Model).
- Realizar las Definiciones de las pruebas usando la filosofía Cucumber y diseñar las pruebas usando Gherkin.

## 8. Escenario de Pruebas

Dado las funcionalidades definidas para el sistema objeto (Ver punto 1 en "Documentos relacionados") de este plan de pruebas se debe abordar los escenarios de pruebas:

a) Realizar la búsqueda de hoteles.

b) Escoger el hotel y la habitación.

c) Reservar la habitación.

## 9. Diseño de Pruebas

Haciendo uso de el o los documentos de Definición del sistema y en concordancia con los escenarios de pruebas contemplados a efecto de este plan de pruebas, se construye los casos de pruebas.

Los casos de pruebas son de tipo funcional y serán diseñadas usando Cucumber/Gherkin:

- Visualizar el proyecto y ubicar las Definiciones en la ruta `src/test/resources/features/`

## 10. Criterios de Aceptación

El proyecto es aprobado si se satisface los siguientes criterios de aceptación:


| Nro. | Descripción                                                                                                       |
| :----- | :------------------------------------------------------------------------------------------------------------------- |
| 1    | El sistema/módulo debe cumplir satisfactoriamente el 100% de las ejecuciones de los casos de pruebas funcionales. |

## 11. Producto de trabajo de pruebas


| Tipo de Prueba      | Tipo de Ejecución | Producto                                   | Descripción                                                                                                                                                                                             |
| :-------------------- | :------------------- | :------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Pruebas Funcionales | Automatizada       | Proyecto JAVA (VScode/Maven)                    | Todo el código de la automatización, haciendo uso de Gherkin, Java y otros.                                                                                                                            |
| N/A                 | N/A                | TestBooking.appium_inspector.appiumsession | Archivo de entrada para Appium Inspector.<br />Cambiar el identificador `appium:udid` por el del dispositivo en uso. <br />Usar el comando `adb devices` para ver el id de los dispositivos conectados. |

END
