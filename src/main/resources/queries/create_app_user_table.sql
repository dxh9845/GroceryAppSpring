CREATE TABLE App_User(
	user_id int, 
	name varchar(25), 
	password varchar(16), 
	phone char(10), 
	address varchar(100), 
	role_id int,
	primary key(user_id),
	foreign key(role_id) references Role(role_id));