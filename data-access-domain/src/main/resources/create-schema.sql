CREATE TABLE t_User (
    User_Id INTEGER NOT NULL,
    Username VARCHAR(30) NOT NULL,
    Password VARCHAR(30) NOT NULL,
    Password_Changed_On TIMESTAMP NOT NULL,
    Expire_On DATE NULL,
    Locked BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE t_User ADD CONSTRAINT PK_User PRIMARY KEY (User_Id);

ALTER TABLE t_User ADD CONSTRAINT UK_User_Username UNIQUE (Username);

CREATE TABLE t_Role (
    Role_Id INTEGER NOT NULL,
    Role_Name VARCHAR(30) NOT NULL,
    Description VARCHAR(255)
);

ALTER TABLE t_Role ADD CONSTRAINT PK_Role PRIMARY KEY (Role_Id);

ALTER TABLE t_Role ADD CONSTRAINT UK_Role_Role_Name UNIQUE (Role_Name);

CREATE TABLE t_Right (
    Right_Id INTEGER NOT NULL,
    Right_Name VARCHAR(30) NOT NULL,
    Description VARCHAR(255)
);

ALTER TABLE t_Right ADD CONSTRAINT PK_Right PRIMARY KEY (Right_Id);

ALTER TABLE t_Right ADD CONSTRAINT UK_Right_Right_Name UNIQUE (Right_Name);

CREATE TABLE t_User_Role (
    User_Id INTEGER NOT NULL,
    Role_Id INTEGER NOT NULL
);

ALTER TABLE t_User_Role ADD CONSTRAINT UQ_User_Role UNIQUE (User_Id, Role_Id);
ALTER TABLE t_User_Role ADD CONSTRAINT FK_User_Role_User FOREIGN KEY (User_Id) REFERENCES t_User(User_Id);
ALTER TABLE t_User_Role ADD CONSTRAINT FK_User_Role_Role FOREIGN KEY (Role_Id) REFERENCES t_Role(Role_Id);

CREATE TABLE t_Role_Right (
    Role_Id INTEGER NOT NULL,
    Right_Id INTEGER NOT NULL
);

ALTER TABLE t_Role_Right ADD CONSTRAINT UQ_Role_Right UNIQUE (Role_Id, Right_Id);
ALTER TABLE t_Role_Right ADD CONSTRAINT FK_Role_Right_Role FOREIGN KEY (Role_Id) REFERENCES t_Role(Role_Id);
ALTER TABLE t_Role_Right ADD CONSTRAINT FK_Role_Right_Right FOREIGN KEY (Right_Id) REFERENCES t_Right(Right_Id);

--
-- Sample values
--

INSERT INTO t_Right(Right_Id, Right_Name, Description) VALUES(1, 'user:read', 'Read users');
INSERT INTO t_Right(Right_Id, Right_Name, Description) VALUES(2, 'user:write', 'Write users');
INSERT INTO t_Right(Right_Id, Right_Name, Description) VALUES(3, 'role:read', 'Read roles');
INSERT INTO t_Right(Right_Id, Right_Name, Description) VALUES(4, 'role:write', 'Write roles');

INSERT INTO t_Role(Role_Id, Role_Name, Description) VALUES(1, 'read_only', 'Read-only');
INSERT INTO t_Role(Role_Id, Role_Name, Description) VALUES(2, 'user_manager', 'User manager');
INSERT INTO t_Role(Role_Id, Role_Name, Description) VALUES(3, 'role_manager', 'Role manager');

INSERT INTO t_User(User_Id, Username, Password, Password_Changed_On, Expire_On, Locked)
    VALUES(1, 'Alice', 'password_1', '2022-11-01 15:17:01', '2025-12-31', FALSE);
INSERT INTO t_User(User_Id, Username, Password, Password_Changed_On, Expire_On, Locked)
    VALUES(2, 'Bob', 'password_2', '2022-11-04 08:26:58', NULL, FALSE);
INSERT INTO t_User(User_Id, Username, Password, Password_Changed_On, Expire_On, Locked)
    VALUES(3, 'Clarence', 'password_3', '2022-01-01 15:52:26', '2022-06-01', FALSE);
INSERT INTO t_User(User_Id, Username, Password, Password_Changed_On, Expire_On, Locked)
    VALUES(4, 'Dan', 'password_4', '2022-10-15 09:37:42', NULL, TRUE);
INSERT INTO t_User(User_Id, Username, Password, Password_Changed_On, Expire_On, Locked)
    VALUES(5, 'Eva', 'password_5', '2021-01-01 16:47:17', '2024-12-31', TRUE);

INSERT INTO t_Role_Right(Role_Id, Right_Id) VALUES(1, 1);
INSERT INTO t_Role_Right(Role_Id, Right_Id) VALUES(1, 3);

INSERT INTO t_Role_Right(Role_Id, Right_Id) VALUES(2, 1);
INSERT INTO t_Role_Right(Role_Id, Right_Id) VALUES(2, 2);

INSERT INTO t_Role_Right(Role_Id, Right_Id) VALUES(3, 3);
INSERT INTO t_Role_Right(Role_Id, Right_Id) VALUES(3, 4);

INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(1, 1);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(2, 1);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(2, 2);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(3, 1);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(3, 2);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(3, 3);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(4, 1);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(5, 1);
INSERT INTO t_User_Role(User_Id, Role_Id) VALUES(5, 2);
