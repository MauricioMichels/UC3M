DROP TABLE IF EXISTS Invitations;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS GroupUser;
DROP TABLE IF EXISTS Groups;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
       id INT NOT NULL auto_increment,
       userName VARCHAR(255) NOT NULL,
       email VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       PRIMARY KEY(id)
)ENGINE=INNODB;


CREATE TABLE Groups (
       id INT NOT NULL auto_increment,
       groupName VARCHAR(255) NOT NULL,
       creator INT NOT NULL,
       creationDate DATE NOT NULL,
       lastChangeDate DATE NOT NULL,
       actualState INT NOT NULL,
       PRIMARY KEY(id),
       CONSTRAINT FOREIGN KEY (creator) REFERENCES Users(id)
)ENGINE=INNODB; /*actualState: 1-inactive 2-active 3-closed*/

CREATE TABLE GroupUser (
       userId INT NOT NULL,
       groupId INT NOT NULL,
       state INT NOT NULL,
       balance DOUBLE NOT NULL,
       PRIMARY KEY(userId,groupId),
       CONSTRAINT FOREIGN KEY (userId) REFERENCES Users(id),
       CONSTRAINT FOREIGN KEY (groupId) REFERENCES Groups(id)
)ENGINE=INNODB; /*state: 1-invited 2-accepted 3-rejected*/

CREATE TABLE Transactions (
       id INT NOT NULL auto_increment,
       subject VARCHAR(255) NOT NULL,
       moment DATE NOT NULL,
       groupId INT NOT NULL,
       payerUser INT NOT NULL,
       recieverUser INT NOT NULL,
       amount DOUBLE NOT NULL,
       PRIMARY KEY(id),
       CONSTRAINT FOREIGN KEY (groupId) REFERENCES Groups(id),
       CONSTRAINT FOREIGN KEY (payerUser) REFERENCES Users(id),
       CONSTRAINT FOREIGN KEY (recieverUser) REFERENCES Users(id)
)ENGINE=INNODB;

CREATE TABLE Invitations (
       userId INT NOT NULL,
       groupId INT NOT NULL,
       responseState VARCHAR(255) NOT NULL,
       invitationDate DATE NOT NULL,
       responseDate DATE,
       PRIMARY KEY(userId,groupId),
       CONSTRAINT FOREIGN KEY (userId) REFERENCES Users(id),
       CONSTRAINT FOREIGN KEY (groupId) REFERENCES Groups(id)
)ENGINE=INNODB; /*responseState: 1-noResponse 2-accepted 3-rejected*/

