DROP TABLE IF EXISTS Editoriales;
DROP TABLE IF EXISTS VentaLibros;
DROP TABLE IF EXISTS NumeroLibros;
DROP TABLE IF EXISTS LibroAutor;
DROP TABLE IF EXISTS Libros;
DROP TABLE IF EXISTS Autores;

/*7WRaCQJf*/

CREATE TABLE Autores (
       id INT NOT NULL auto_increment,
       autor VARCHAR(255) NOT NULL,
       PRIMARY KEY(id)
)ENGINE=INNODB;

CREATE TABLE Libros (
       id INT NOT NULL auto_increment,
       titulo VARCHAR(255) NOT NULL,
       isbn INT NOT NULL,
       /*editorial VARCHAR(255) NOT NULL, /*Pongo aqui tambien el ID de las editoriales?*/
       anyo YEAR(4) NOT NULL,
       precio DOUBLE NOT NULL,
       PRIMARY KEY(id)
)ENGINE=INNODB;

CREATE TABLE LibroAutor (
       id INT NOT NULL auto_increment,
       libro INT NOT NULL,
       autor INT NOT NULL,
       PRIMARY KEY(id),
       CONSTRAINT FOREIGN KEY (autor) REFERENCES Autores(id),
       CONSTRAINT FOREIGN KEY (libro) REFERENCES Libros(id)
)ENGINE=INNODB;

CREATE TABLE NumeroLibros (
       id INT NOT NULL ,
       libro INT NOT NULL,
       numerounidades INT,
       PRIMARY KEY(id),
       CONSTRAINT FOREIGN KEY (libro) REFERENCES Libros(id)
)ENGINE=INNODB;

CREATE TABLE VentaLibros (
       id INT NOT NULL ,
       libro INT NOT NULL,
       fecha DATE NOT NULL,
       unidadesvendidas INT NOT NULL,
       PRIMARY KEY(id),
       CONSTRAINT FOREIGN KEY (libro) REFERENCES Libros(id)
)ENGINE=INNODB;



/* Seguro se necesita una tabla para editoriales? 
CREATE TABLE Editoriales (
       id INT NOT NULL auto_increment,
       editorial VARCHAR(255) NOT NULL,
       PRIMARY KEY(id)
)ENGINE=INNODB;
*/