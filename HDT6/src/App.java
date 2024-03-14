public class App {
    /*Objetivos:
a. Utilización de Java Collection Framework: uso de la interface MAP y sus (tres) implementaciones, uso de algoritmos para función hash MD5 y SHA-1, (Otro a su elección).
b. Uso de algoritmos polimórficos proporcionados por el Java Collection Framework.
c. Control de versiones del programa.
Programa a realizar:
Utilice el de diseño Factory para seleccionar la implementación de MAP que usará su programa, en tiempo de ejecución. El usuario debe seleccionar entre: 1) HashMap, 2) TreeMap, 3) LinkedHashMap
El algoritmo para la función hash también se debe trabajar bajo un diseño factory, las opciones son: MD5, SHA-1, orgánico (Dado un dato, devuelve el mismo dato como llave).
La universidad se encuentra en búsqueda de estudiantes de diferentes nacionalidades para realizar intercambio académico, logra encontrar a 500 estudiantes cuyos datos se encuentran almacenados en el archivo JSON estudiantes.json
Se solicita que se cree un programa que lea el contenido del archivo y almacene los datos en una estructura Mapa, seleccionada por el usuario en pasos previos, la llave el usuario debe seleccionar si se genera a través de MD5 o SHA-1, usted debe elegir qué dato se convertirá en la llave, tomar en cuenta que ese dato será el necesario para buscar la información luegoEl programa también contará con una opción de búsqueda, en la cual, dado el dato que seleccionó de búsqueda en el paso anterior, si se encuentra el estudiante, entonces mostrará todos los datos que se poseen de este.
La tercera opción del sistema es la búsqueda a través de nacionalidad, en esta opción dada una nacionalidad, devolverá a los estudiantes que posean esa nacionalidad (Usar función hash orgánica y manejar las colisiones a través de listas).
Tareas:
a. Su programa principal debe usar Patrón de diseño Factory para seleccionar la implementación de MAP a utilizar.
b. Su programa principal debe usar Patrón de diseño Factory para seleccionar la función Hash a utilizar.
c. Su programa mostrará una opción para realizar estas configuraciones previo a iniciar el programa.
d. Su programa mostrará la opción de carga de datos previo a poder buscar.
e. Para la búsqueda de estudiante individual, seleccione cuidadosamente el dato a buscar, es decir, el nombre no es un buen candidato ya que puede repetirse.
f. Para la búsqueda por nacionalidad utilice una función hash orgánica y como esta producirá colisiones, debe manejarlas a través de la estrategia de listas.
g. Implemente las pruebas unitarias necesarias para solventar el problema.
SE REVISARÁ EN GIT QUE SE HAYA TRABAJADO DE LA SIGUIENTE MANERA

*** 1. Creación de Interfaces, implementación de Clases, implementación de Factory **
Estudiante 1: 
- Crear Interfaz para Las funciones hash: Organica, MD5, SHA-1
- Crear las clases para la implementación de la función Hash Organica y MD5
- Crear el Factory para las funciones hash:
- Crear los Unit test para las funciones Hash MD5, SHA-1 y Organica

Estudiante 2:
- Crear el Factory para los Mapas: HashMap, TreeMap, LinkedHashMap
- Crear la clase de implementación para la función Hash MD5
- Crear los unit test par Los Map

**** 2. Creación de clase estudiantes y lectura de datos: ****
Estudiante 1: 
- Crear la clase Estudiantes, que guardará los datos que se encuentran en el archivo
- Creación de la clase LectorArchivo, que se encargará de leer desde el archivo de texto a los estudiantes y guardarlos usando los factory en la estructura Map para la búsqueda individual
- Creación del método que permite guardar a los estudiantes por nacionalidad en un Mapa que sea capaz de manejar colisiones.

Estudiante 2:
- Creación del método de búsqueda de los estudiantes a través de la llave seleccionada
- Creación del método de búsqueda de los estudiantes a través de su nacionalidad
- Creación de la interfaz gráfica.*/
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
