-- Задание 7.2

CREATE DATABASE Human_friends;

USE Human_friends;

CREATE TABLE animal_classes
(
	Id INT AUTO_INCREMENT PRIMARY KEY, 
	Class_name VARCHAR(20)
);

INSERT INTO animal_classes (Class_name)
VALUES ('вьючные животные'),
('домашние животные');  


CREATE TABLE packed_animals
(
	  Id INT AUTO_INCREMENT PRIMARY KEY,
    Genus_name VARCHAR (20),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO packed_animals (Genus_name, Class_id)
VALUES ('Лошадь', 1),
('Осел', 1),  
('Верблюд', 1); 
    
CREATE TABLE home_animals
(
	  Id INT AUTO_INCREMENT PRIMARY KEY,
    Genus_name VARCHAR (20),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO home_animals (Genus_name, Class_id)
VALUES ('Кошка', 2),
('Собака', 2),  
('Хомяк', 2); 

-- заполнить таблицу данными о животных, их камондах и даты рождения

CREATE TABLE cats 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO cats (Name, Birthday, Commands, Genus_id)
VALUES ('Мася', '2015-12-05', 'кс-кс-кс', 1),
('Вася', '2021-05-01', "сюда!", 1),  
('Леша', '2022-01-01', "кушать", 1); 

CREATE TABLE dogs 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO dogs (Name, Birthday, Commands, Genus_id)
VALUES ('Дружок', '2019-08-12', 'ко мне, лежать, лапу', 2),
('Шарик', '2021-06-12', "сидеть, лежать, лапу", 2),  
('Марсель', '2015-12-12', "лежать, лапу, след, фас", 2), 
('Блэк', '2021-08-16', "Умри, голос, рядом, место", 2);

CREATE TABLE hamsters 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO hamsters (Name, Birthday, Commands, Genus_id)
VALUES ('Плюш', '2021-11-22', 'Цып-цып', 3),
('Толстяк', '2021-03-28', "кушать, храпеть", 3),  
('Черныш', '2023-01-11', "бежать, умри", 3), 
('Белка', '2021-05-10', "в домик, Кошка, опасность!!!!", 3);

CREATE TABLE horses 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO horses (Name, Birthday, Commands, Genus_id)
VALUES ('Палач', '2019-11-12', 'тише, быстрее', 1),
('Шутчрый', '2015-10-25', "прыжок, шагом, хоп", 1),  
('Амур', '2020-07-12', "бегом, шагом, стой, брр", 1), 
('Мурат', '2021-08-10', "бегом, шагом, быстрее", 1);


CREATE TABLE donkeys 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO donkeys (Name, Birthday, Commands, Genus_id)
VALUES ('Фрося', '2016-08-25', "работать, ишак, быстрее", 2),
('Тайфун', '2019-04-12', "стой", 2),  
('Пятерка', '2021-11-23', "убегай, молчать", 2), 
('Первач', '2022-11-27', "пойдем, быстрее, пахать", 2);

CREATE TABLE camels 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Genus_id int,
    Foreign KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO camels (Name, Birthday, Commands, Genus_id)
VALUES ('Слюнявчик', '2016-04-25', 'пить, ляг', 3),
('Ишак', '2019-03-12', "быстрее, кушать", 3),  
('Леха', '2018-12-12', "ниже, встань", 3), 
('Наташа', '2022-12-18', "сядь, ляг, расслабься", 3);

-- удалить записи о верблюдах и удалить лошадей и ословALTER

SET SQL_SAFE_UPDATES = 0;
DELETE FROM camels;

SELECT Name, Birthday, Commands FROM horses
UNION SELECT  Name, Birthday, Commands FROM donkeys;

-- создать новую таблицу для животных в возрасте от 1 до 3 лет и вычислить их возраст с точностью до месяцы

CREATE TEMPORARY TABLE animals AS 
SELECT *, 'Лошадь' as genus FROM horses
UNION SELECT *, 'Осел' AS genus FROM donkeys
UNION SELECT *, 'Собака' AS genus FROM dogs
UNION SELECT *, 'Кошка' AS genus FROM cats
UNION SELECT *, 'Хомяк' AS genus FROM hamsters;

CREATE TABLE yang_animal AS
SELECT Name, Birthday, Commands, genus, TIMESTAMPDIFF(MONTH, Birthday, CURDATE()) AS Age_in_month
FROM animals WHERE Birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);
 
SELECT * FROM yang_animal;

-- собъединить все созданные таблицы в одну, сохраняя информацию о принадлежности к исходным таблицамALTER

		-- таблица вьючных животных
    
SELECT h.Name,  pa.Genus_name, h.Birthday, h.Commands
FROM horses h
LEFT JOIN packed_animals pa ON pa.Id = h.Genus_id
UNION 
SELECT d.Name, pa.Genus_name, d.Birthday, d.Commands
FROM donkeys d 
LEFT JOIN packed_animals pa ON pa.Id = d.Genus_id;

		-- таблица домашних животных
    
SELECT  c.Name, c.Birthday, ha.Genus_name , c.Commands
FROM cats c
LEFT JOIN home_animals ha ON ha.Id = c.Genus_id
UNION
SELECT  d.Name,  ha.Genus_name, d.Birthday, d.Commands
FROM dogs d
LEFT JOIN home_animals ha ON ha.Id = d.Genus_id
UNION
SELECT hm.Name, hm.Birthday, ha.Genus_name, hm.Commands
FROM hamsters hm
LEFT JOIN home_animals ha ON ha.Id = hm.Genus_id;
