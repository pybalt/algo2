El objetivo del trabajo es desarrollar una serie de algoritmos basados en un conjunto de TDA’s que modela campos de cultivo y las precipitaciones diarias en cada campo.

Los campos de cultivo se registrarán en una estructura de tipo árbol binario de búsqueda (ABBPrecipitacionesTDA) que contiene el nombre del campo (una cadena de caracteres) que tiene asociado una estructura de tipo un diccionario simple (DiccionarioSimpleStringTDA).

Este diccionario tiene como clave el año y el mes (denominado periodo) convertidos a caracteres concatenados y como valores un diccionario simple (DiccionarioSimpleTDA) que tiene al número de día como clave y como valor la cantidad diaria de lluvia caída expresada en milímetros en ese día de ese periodo.

Deberá controlar y validar la cantidad de días de cada mes en función del mes y del año, como así también el valor de los meses y de los años. Los años, meses, días y precipitaciones serán números enteros para su ingreso.

La conversión de números enteros a cadenas puede hacerlo mediante la clase String, por ejemplo, si tengo una variable entera llamada número para convertirla a carácter puedo hacer String.valueOf(numero);


Se le Suministrará:

· Todos los TDA’s necesarios para la implementación.

· Todas aas clases donde se deben deberán implementar los TDA’s definidos. Las mismas ya tienen definidos los nodos correspondientes a las implementaciones a realizar.

· Una clase Algoritmos donde deberán realizar los algoritmos solicitados en la misma.

· La estructura de paquetes donde están definidos los TDA’s, las implementaciones y los algoritmos.


Consideraciones:

· Debe respetar el nombre de los TDA y su forma. Los mismos no pueden ser modificados de ninguna manera.

· Debe respetar los nombres de las clases y la forma de los nodos. Las clases pueden agregar más métodos, pero estos deben ser privados.

· Debe respetar el nombre de los paquetes entregados y mantener dentro a las clases e interfaces entregadas. Si los cambia de lugar o de nombre no se podrá probar el código y se considerará que no funciona.