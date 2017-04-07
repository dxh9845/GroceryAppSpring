CREATE TABLE App_User(
	user_id int,
	username varchar(25), 
	name varchar(25), 
	password varchar(16), 
	phone char(10), 
	address varchar(100), 
	role_id int,
	PRIMARY KEY(user_id),
	FOREIGN KEY(role_id) REFERENCES Role(role_id));