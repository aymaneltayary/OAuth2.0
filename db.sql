--
-- DB scheme&user creation
--
DROP DATABASE IF EXISTS oauthdb;
CREATE DATABASE oauthdb  CHARACTER SET utf8 COLLATE utf8_general_ci ;
DROP USER IF EXISTS 'mydxc'@'%';
CREATE USER 'mydxc'@'%' IDENTIFIED  BY 'mydxc';
GRANT SELECT, INSERT, UPDATE, DELETE ON oauthdb.* TO 'mydxc'@'%';

--
-- DB objects creation
-- Table structure for table roles
--
DROP TABLE IF EXISTS oauthdb.oauth_client_details ;
CREATE TABLE oauthdb.oauth_client_details (
  CLIENT_ID CHAR(3) PRIMARY KEY,
  RESOURCE_IDS VARCHAR(255),
  CLIENT_SECRET VARCHAR(255),
  SCOPE VARCHAR(255),
  AUTHORIZED_GRANT_TYPES VARCHAR(255),
  WEB_SERVER_REDIRECT_URI VARCHAR(255),
  AUTHORITIES VARCHAR(255),
  ACCESS_TOKEN_VALIDITY INTEGER,
  REFRESH_TOKEN_VALIDITY INTEGER,
  ADDITIONAL_INFORMATION VARCHAR(4096),
  AUTOAPPROVE VARCHAR(255)
);


--
-- DB objects creation
-- Table structure for table roles
--
DROP TABLE IF EXISTS oauthdb.roles;
DROP TABLE IF EXISTS oauthdb.role;
CREATE TABLE oauthdb.role (
  id int  NOT NULL AUTO_INCREMENT,
  role_name varchar(50) NOT NULL,
  role_description varchar(100) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(10) NOT NULL DEFAULT 'web',
  last_updated TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_roles primary key(id),
  CONSTRAINT uq_roles_name UNIQUE (role_name)
) ;




DROP TABLE IF EXISTS oauthdb.user;
CREATE TABLE oauthdb.user (
  id   bigint auto_increment NOT NULL,
  user_name varchar(50) NOT NULL,
  email varchar(100) NOT NULL,
  first_name varchar(100) NOT NULL,
  last_name varchar(100) NOT NULL,
  gender tinyint(1) NOT NULL,
  user_status tinyint(1) NOT NULL,
  user_password char(68) NOT NULL,
  role_id int  NOT NULL,
  employee_id  int,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(10) Not NULL,
  last_updated TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_user primary key(id),
  CONSTRAINT uq_users_email UNIQUE (email),
  CONSTRAINT uq_users_name UNIQUE (user_name),
  CONSTRAINT fk_users_roles foreign key(role_id) references role(id)
) ;




--
-- Table structure for table permissions
--
DROP TABLE IF EXISTS oauthdb.permission;
CREATE TABLE oauthdb.permission (
  id int NOT NULL AUTO_INCREMENT,
  permission_name varchar(50) NOT NULL,
  permission_description varchar(100) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(10) NOT NULL DEFAULT 'web',
  last_updated TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_permissions primary key(id),
  CONSTRAINT uq_permissions_name UNIQUE (permission_name)
) ;

--
-- Table structure for table roles_permissions
--
DROP TABLE IF EXISTS oauthdb.role_permission;
CREATE TABLE oauthdb.role_permission (
role_id int  NOT NULL ,
permission_id int  NOT NULL,
created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
last_updated TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
CONSTRAINT pk_roles_permission primary key(role_id,permission_id),
CONSTRAINT fk_roles_permission_role foreign key(role_id) references role(id),
CONSTRAINT Fk_role_permission_permission foreign key(permission_id)  references permission(id)
) ;





--
-- dml 
--
INSERT INTO 
oauthdb.OAUTH_CLIENT_DETAILS(CLIENT_ID, 
RESOURCE_IDS, 
CLIENT_SECRET, 
SCOPE, 
AUTHORIZED_GRANT_TYPES, 
AUTHORITIES, 
ACCESS_TOKEN_VALIDITY, 
REFRESH_TOKEN_VALIDITY)
 VALUES ('web',
 'resource-server-rest-api',
 '{bcrypt}$2a$10$8fMsdA5YdvWdF1IWK/S7quN6onNxibSrGxbc9J4kKPQScOFtlKBnG',
 'read', 
 'password,authorization_code,refresh_token,implicit', 
 'USER', 
 10800, 
 2592000);




--
-- main DXC rule
--
INSERT INTO oauthdb.role (role_name, role_description, created_by) VALUES ('ROLE_EMPLOYEE', 'dxc employee default rule', 'web');
INSERT INTO oauthdb.role (role_name, role_description, created_by) VALUES ('ROLE_SALES', 'dxc sales rule', 'web');
INSERT INTO oauthdb.role (role_name, role_description, created_by) VALUES ('ROLE_PROJECT_MANAGER', 'dxc project manager rule', 'web');
INSERT INTO oauthdb.role (role_name, role_description, created_by) VALUES ('ROLE_MANAGER', 'dxc manager rule', 'web');
INSERT INTO oauthdb.role (role_name, role_description, created_by) VALUES ('ROLE_GRE', 'dxc gret rule', 'web');
INSERT INTO oauthdb.role (role_name, role_description, created_by) VALUES ('ROLE_APP_ADMIN', 'dxc administrator rule', 'web');


--
-- main DXC rule
--
INSERT INTO oauthdb.permission (permission_name, permission_description, created_by) VALUES ('REGISTER', 'user registertaion permission', 'web');
INSERT INTO oauthdb.permission (permission_name, permission_description, created_by) VALUES ('LOGIN', 'user login permission', 'web');
INSERT INTO oauthdb.permission (permission_name, permission_description, created_by) VALUES ('REMEMBER_PASSWORD', 'remember password permission', 'web');
INSERT INTO oauthdb.permission (permission_name, permission_description, created_by) VALUES ('FORGET_PASSWORD', 'forget passowrd permission', 'web');



--
-- dummy user password here is "pass"
--

insert into oauthdb.user( user_name, email, first_name, last_name, gender, user_status, user_password, role_id, employee_id, created_by)
values
('user1', 'user1@dxc.com', 'first_name1', 'last_name1', '1', '1', '{bcrypt}$2a$10$z5e/19vUBUHF8AKGKREFL.5MD0RiIC3MzB2m.G0GIMAIhdZxs8bpe', 1, 1,  1);

commit;