INSERT INTO Users (userName, email, password)
VALUES
('pepito','pepitoperez@email.com','pepito1234'),
('juanito','juanitosanz@email.com','juanito1234'),
('paco','pacogarcia@email.com','paco1234'),
('sergio','sergiosaez@email.com','sergio1234'),
('hectorgraez','hectorgraez@email.com','hector79'),
('mauricio','mauricio@email.com','a');


INSERT INTO Groups (groupName, creator, creationDate, lastChangeDate, actualState)
VALUES
('Lisboa Trip',4, NOW(), NOW(),1),
('London Calling',3, NOW(), NOW(),2),
('Lugano Lake Trip',2, NOW(), NOW(),1);

INSERT INTO GroupUser (userId, groupId, state, balance)
VALUES
(1, 1, 2, -15),
(1, 2, 2, 0),
(1, 3, 1, 0),
(4, 1, 2, 15),
(4, 2, 3, 0),
(2, 2, 1, 0),
(2, 3, 2, 0),
(3, 2, 2, 0),
(5, 1, 2, -13),
(5, 2, 1, 20),
(5, 3, 3, 0);

INSERT INTO Transactions (subject, moment, groupId, payerUser, recieverUser, amount)
VALUES
('Beers before plane', NOW(), 1, 4, 1, 15),
('Lunch in the airport', NOW(), 2, 5, 2, 20);

INSERT INTO Invitations (userId, groupId, responseState, invitationDate, responseDate)
VALUES
(1, 1, 2, NOW(), NOW()),
(1, 2, 2, NOW(), NOW()),
(1, 3, 1, NOW(), null),
(4, 1, 2, NOW(), NOW()),
(4, 2, 3, NOW(), NOW()),
(2, 2, 1, NOW(), null),
(2, 3, 2, NOW(), NOW()),
(3, 2, 2, NOW(), NOW()),
(5, 1, 2, NOW(), NOW()),
(5, 2, 1, NOW(), null),
(5, 3, 3, NOW(), NOW());
