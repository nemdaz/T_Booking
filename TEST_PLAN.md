# PLAN DE PRUEBAS DE SISTEMAS

### 1. Introducción

##### Nombre

| Nombre              | Detalle                                | Versión |
| :------------------ | :------------------------------------- | -------- |
| PPS_010100_00000002 | Plan de pruebas para el APK de Booking | v1.0.0   |

##### Componentes

| Tipo       | Código | Descripción       |
| :--------- | :------ | :----------------- |
| Sistema    | 01      | Booking            |
| Subsistema | 0101    | Booking Mobile App |
| Módulo    | 010100  | Todos              |

##### Responsables

| Código | Rol                | Nombre  | Función                                  |
| :------ | :----------------- | :------ | :---------------------------------------- |
| 01      | Autor              | Nem Daz | Elaboración del Plan de Pruebas          |
| 01      | Líder de proyecto | Nem Daz | Liderar la ejecución del Plan de Pruebas |

##### Documentos relacionados

| Descripcion                                      | Origen/Fuente        | Version |
| :----------------------------------------------- | :------------------- | :------ |
| Definicion y especificación del sistema Booking | booking-com-32-9.apk | v32.9.0 |

### 2. Objetivo

La finalidad del presente documento es definir las pautas y estrategias que nos permitiran cumplir con la certificacion de calidad del aplicativo móvil 010100 del sistema Booking.

El objetivo general es definir las condiciones que nos permitan ejecutar las pruebas y en consecuencia nos entregue un sistema que cumpla con las funcionalidades requeridas por todos los interesados.

El plan de pruebas debe incluir las pautas necesarias para recrear un ambiente de pruebas automatizados.

### 3. Alcance

El presente documento cumple con servir de guía en el proceso de ejecucion de pruebas para los responsables del proyecto.

El plan de pruebas se enmarca al modulo 010100 del sistema Booking la cual corresponde a funcionalidades del Aplicativo Movil (Ver punto 1 en "Documentos relacionados").

### 4. Entorno de Pruebas

##### Ambiente de Pruebas

| Tipo               | Nombre               | Detalle                                                                                                                                                               |
| :----------------- | :------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Dispositivo Laptop | Lenovo Yoga 7        | Ryzen 7 Serie 4000,<br />16GB RAM, 500GB SSD, <br />Windows 11 HSL                                                                                                    |
| App Android (APK)  | booking-com-32-9.apk | [https://drive.google.com/file/d/1ruY_5qcqWlsE3-W3rgXpFyyMiO59wFUc/view?usp=sharing](https://drive.google.com/file/d/1ruY_5qcqWlsE3-W3rgXpFyyMiO59wFUc/view?usp=sharing) |

##### Herramientas

| Tipo     | Nombre                                                         | Detalle                                                                                                                                |
| :------- | :------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------- |
| Software | VSCode v1.75.0                                                 | Editor/IDE.                                                                                                                            |
| Software | Eclipse IDE 2022-12                                            | Java IDE                                                                                                                               |
| Software | SourceTree 3.4.8                                               | Gestor de control de versiones git.                                                                                                    |
| Software | Git                                                            | Herramienta de controlde versiones.                                                                                                    |
| Servicio | GitHub                                                         | Repositorio en la nube para el control de versiones git.                                                                              |
| Software | NodeJS v18.14.0                                                | Necesario para instalar Appium v2 y su driver.                                                                                         |
| Software | Android SDK Tools                                              | Herramientas de android (Google).                                                                                                      |
| Software | scrcpy                                                         | Visor de pantalla de Android en Windows                                                                                                |
| Software | appium v2                                                      | Appium es un Framework de automatización de pruebas de código abierto para usar con aplicaciones web nativas, híbridas y móviles. |
| Software | uiautomator2                                                   | Driver necesario para la conección entre Appium y el dispositivo                                                                      |
| Software | appium-inspector                                               | Aplicativo desktop para inspeccionar apps/apk en el proceso de pruebas.                                                                |
| Software | appium-doctor                                                  | Para verificar si cumplimos con lo requisitos para usar Appium                                                                         |
| Hardware | Smartphone Xiaomi (Android 13)<br />Tablet Lenovo (Android 11) | Dispositivos de pruebas Android.                                                                                                       |

### 5. Configuracion del Entorno de Pruebas

De acuerdo a lo indicado en el punto "4. Entorno de Pruebas" se realiza la configuracion del entorno de pruebas.

##### Instalacion y cuentas

- Descargar el sistema de control de versiones desde la url https://git-scm.com/download/win en su version para el SO Windows.
- Descargar e instalar el sistema adminsitrador de control de versiones SourceTree desde la url https://www.sourcetreeapp.com/ en su version para el SO Windows.
- Descargar e instalar el editor/ide VScode desde la url https://code.visualstudio.com/sha/download?build=stable&os=win32-x64.
- Crear una cuenta en el servicio cloud de control de versiones https://github.com/.
- Descargar e instalar NodeJs de manera global, usar la url https://nodejs.org/en/.
- Descargar e instalar Eclipse IDE 2022-12.
- Descargar e instalar Appium Inspector desde [https://github.com/appium/appium-inspector](https://github.com/appium/appium-inspector).

##### Configuración

**GitHub**

- En el panel de trabajo de la plataforma ubicarnos en la opción "New repository" y crear el repositorio "YpTest_APIBooker", esto por defecto nos crea en branch main y el archivo README.md.

**SourceTree**

- Abrimos el aplicativo, seguimos los pasos que nos indica para el uso del aplicativo (solo la primera vez que se inicia), una vez que estamos en la interfaz principal nos ubicamos en la opcion "*File - Clone/New*", seleccionamos la opción Clone e ingresamos los datos que nos solicita dicha interfaz:

  - La url del repositorio, creado previamente en GitHub.
  - La ruta local donde se clonará el repositorio en nuestro equipo de trabajo. Esta ruta será nuestro espacio de trabajo local para este plan de pruebas.
- Una vez clonado el repositorio de GitHub, verificamos la creacion de nuestro espacion de trabajo, de acuerdo a configuraciones previas nuestra carpeta debe tener el nombre "YpTest_Booking". *Ejm: D:\Workspace\Yape\YpTest_Booking*

**VSCode**

- Abrimos el aplicativo, nos uicamos en la opcion *"File - Open Folder"* y abrimos el forlder "YpTest_Booking" que corresponde a nuestro espacio de trabajo. Aqui trabajemos con los archivos necesarios para el plan de pruebas.

**Eclipse IDE**

- Creamos un proyecto Maven de nombre *TestBooking.*
- Definimos las librerias en el fichero `pom.xml`. Las librerias `appium java-client` y `selenium-java`.
- Configuramos nuestro proyecto Maven para que se ejecute con Java 1.8.

**Android Platforms Tools**

- Existen diversas maneras de instalar estas herramientas; usaremos la mas fácil, Instalar Android Studio solo para usar su SDK.
- Ubicamos la ruta del SDK (post instalación de Android Studio).
- Configuramos las ***Variables de entorno del Sistema*** en Windows.

  ```
  ANDROID_HOME = C:\Users\user\AppData\Local\Android\Sdk

  Y en la variable existente PATH, añadimos:

  %ANDROID_HOME%\build-tools
  %ANDROID_HOME%\platform-tools
  %ANDROID_HOME%\tools
  ```

**NodeJS**

- Instalamos Appium y el driver para poder enlazar nuestro set de pruebas con el dispositivo y archivo apk a probar. Para mayor informacion al respecto revisar la documentación oficial [https://appium.github.io/appium/docs/en/2.0/](https://appium.github.io/appium/docs/en/2.0/).

  ```
  (Appium v2)
  npm install -g appium@next
  appium driver install uiautomator2
  ```

**Otras configuraciones**

- Establecemos las variables de entorno para Maven y Java. Los valores de las variables son referenciales ya que pueden variar segun los criterios o configuraciones personalizadas de Tester/Probador.

  ```
  JAVA_HOME = C:\Program Files\AdoptOpenJDK\jdk8u362-b09
  MAVEN_HOME = D:\Program Files\apache-maven-3.8.3
  M2_HOME = D:\Program Files\apache-maven-3.8.3
  ```

### 6. Estrategías de Pruebas

Para el cumplimiento de los objetivos se plantean estrategías de pruebas las cuales estarán en funcion a los siguientes tipos, niveles y técnicas de pruebas.

| Tipo de Prueba      | Nivel de Prueba       | Técnica de Prueba                                                             |
| :------------------ | :-------------------- | :----------------------------------------------------------------------------- |
| Prueba Funcional    | Prueba de componentes | Pruebas de equivalencia y valores limites.<br />Pruebas de humo/exploratorias. |
| Prueba No Funcional | Prueba de componentes | Pruebas de humo/exploratorias.                                                 |

###### Actividades estratégicas:

- Identificar la estructura DOM del componente APK usando herramientas para este fin.
- Desarrollar las pruebas usando el patron POM (Page Object Model).
- Realizar las definiciones de las pruebas usando la filosofía Cucumber y diseñar las pruebas usando Gherkin.

### 7. Escenario de Pruebas

Dado las funcionalidades definidas para el sistema objeto (Ver punto 1 en "Documentos relacionados") de este plan de pruebas se debe abordar los escenarios de pruebas:

a) Realizar la búsqueda de hoteles.

b) Escoger el hotel y la habitacion.

c) Reservar la habitacion.

### 8. Diseño de Pruebas

Haciendo uso de el o los documentos de definicion del sistema y en concordancia con los escenarios de pruebas contemplados a efecto de este plan de pruebas, se constuye los casos de pruebas.

Los casos de pruebas son de tipo funcional y serán diseñadas usando Cucumber/Gherkin:

- Visualizar el proyecto y ubicar las definiciones en la ruta `src/test/resources/features/`

### 9. Criterios de Aceptación

El proyecto es aprobado si se satisface los siguientes criterios de aceptación:

| Nro. | Descripción                                                                                                       |
| :--- | :----------------------------------------------------------------------------------------------------------------- |
| 1    | El sistema/módulo debe cumplir satisfactoriamente el 100% de las ejecuciones de los casos de pruebas funcionales. |

### 10. Entregables de Pruebas

| Tipo de Prueba      | Tipo de Ejecución | Entregable                                 | Descripción                                                                                                                                                                                                 |
| :------------------ | :----------------- | :----------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Pruebas Funcionales | Automatizada       | Proyecto JAVA (Eclipse)                    | Todo el código de la automatización, haciendo uso de Gjerkin, Java y otros.                                                                                                                                |
| N/A                 | N/A                | TestBooking.appium_inspector.appiumsession | Archivo de entrada para Appium Inspector.<br />Cambiar el identificador `appium:udid` por el del dispositivo en uso. <br />Usar el comando `adb devices` para ver el id de los dispositivos conectados. |

END
