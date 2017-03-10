INSERT INTO Editoriales (editorial)
VALUES
('Booket'),
('Salamandra'),
('Suma'),
('Alfaguara'),
('Algaida'),
('Planeta');


INSERT INTO Autores (autor)
VALUES
('Arturo Perez-Reverte'),
('Luis de Val'),
('Carlos Ruiz Zafon'),
('J.K. Rowling'),
('Gloria Fuertes');

INSERT INTO Libros (titulo, isbn, editorial, anyo, precio)
VALUES
('VOLVEREMOS A VENECIA', '8484335984', 5, 2005, 18.05),
('CABO TRAFALGAR', '8420467170', 4, 2004, 12.24),
('FALCO', '9788420419', 4, 2016, 19.90),
('LA SOMBRA DEL VIENTO', '8408043641', 6, 2001, 13.25),
('UN DIA DE COLERA', '8470667571', 4, 2008, 16.24),
('HARRY POTTER Y EL CALIZ DE FUEGO', '8493023875', 6, 2004, 12.99);

INSERT INTO LibroAutor (titulo, autor)
VALUES
(1, 2),
(2, 1),
(3, 1),
(4, 3),
(5, 1),
(6, 4);

INSERT INTO NumeroLibros (libro, numerounidades)
VALUES
(1, 8),
(2, 43),
(3, 111),
(4, 35),
(5, 18),
(6, 135);

INSERT INTO VentaLibros (libro, unidadesvendidas)
VALUES
(1, 1),
(2, 2),
(3, 4),
(4, 3),
(5, 5),
(6, 10);



/*

VOLVEREMOS A VENECIA / LUIS DEL VAL / ALGAIDA / 8484335984 ,2005, 18,05
CABO TRAFALGAR / ARTURO Perez-Reverte / Alfaguara / 8420467170, 2004, 12,24
FALCO / Arturo Perez-Reverte / Alfaguara / 9788420419, 2016, 19,90
LA SOMBRA DEL VIENTO / Carlos Ruiz Zafon / Planeta / 8408043641, 2001, 13,25
UN DIA DE COLERA / ARTURO Perez-Reverte / Alfaguara / 8470667571, 2008, 16,24
HARRY POTTER Y EL CALIZ DE FUEGO / J.K. ROWLING / PLANETA / 8493023875, 2004, 12,99
*/