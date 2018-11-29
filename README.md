# spring-vete
Proyecto sistema veterinaria Spring-boot 2.0.3 / Thymeleaf 


Versión Sistema Veterinaria 1.9: 

Correcciones realizadas: 

Se corrige dirección de URL de foto para poder ser almacenada en producción y testing. 
Se soluciona checkbox de campos fecha de fallecimiento en entity Paciente.
Se estandarizan botones volver.
Foto en editar paciente, se muestra el nombre de la foto ingresada. 
Se cambia label de botón atención rápida.
Se verifica y casillero para llenar con diagnostico, efectivamente se puede agrandar. 
Se coloca comentario en el detalle de la ficha de atención. 
Se sacan los campos de id (nombrados con el signo # en la listas), para no mostrar cardinalidad en las tablas.
Se cambia valor de label “color” por “color base” 
Se colocan en orden alfabético los combobox
Se redirecciona a /login una vez terminada la sesión de usuario. 

No se pudo realizar y queda pendiente para una próxima iteración: 
 
Filtro con un combo box para las listas.
Control de registro en caso de que se borre una raza o especie. 
Que solo se pueda mostrar contraseña de sistema en navegador. 


Se realizan las siguientes observaciones para próxima iteración: 

Código de chip sea opcional al ingresar un nuevo paciente. 
Atender caso de que la especie o raza no se encuentre en la lista. 
Agrandar a 10 clientes por pagina en la lista. 
Cambiar label “ficha atención” a “nueva atención”. 
