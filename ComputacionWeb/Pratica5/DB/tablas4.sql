DROP TABLE IF EXISTS NumeroLibros;
DROP TABLE IF EXISTS VentaLibros;
DROP TABLE IF EXISTS LibroAutor;
DROP TABLE IF EXISTS Libros;
DROP TABLE IF EXISTS Autores;
DROP TABLE IF EXISTS Editoriales;

CREATE TABLE Autores (
       id INT NOT NULL auto_increment,
       autor VARCHAR(255) NOT NULL,
       PRIMARY KEY(id)
)ENGINE=INNODB;

CREATE TABLE Editoriales (
       id INT NOT NULL auto_increment,
       editorial VARCHAR(255) NOT NULL,
       PRIMARY KEY(id)
)ENGINE=INNODB;

CREATE TABLE Libros (
       id INT NOT NULL auto_increment,
       titulo VARCHAR(255) NOT NULL,
       isbn VARCHAR(255) NOT NULL,
       editorial INT NOT NULL,
       anyo YEAR(4) NOT NULL,
       precio DOUBLE NOT NULL,
       /*autores VARCHAR(255) NOT NULL,*/
       PRIMARY KEY(id),
       CONSTRAINT FOREIGN KEY (editorial) 
              REFERENCES Editoriales (id)
)ENGINE=INNODB;

CREATE TABLE LibroAutor (
       titulo INT NOT NULL,
       autor INT NOT NULL,
       PRIMARY KEY(titulo, autor),
       CONSTRAINT FOREIGN KEY (titulo) 
              REFERENCES Libros (id),
       CONSTRAINT FOREIGN KEY (autor) 
              REFERENCES Autores (id)
)ENGINE=INNODB;

CREATE TABLE NumeroLibros (
       libro INT NOT NULL,
       numerounidades INT,
       PRIMARY KEY(numerounidades),
       CONSTRAINT FOREIGN KEY (libro) REFERENCES Libros(id)
)ENGINE=INNODB;

CREATE TABLE VentaLibros (
       libro INT NOT NULL ,
       fecha DATE,
       unidadesvendidas INT NOT NULL,
       PRIMARY KEY(unidadesvendidas),
       CONSTRAINT FOREIGN KEY (libro) REFERENCES Libros(id)
)ENGINE=INNODB;