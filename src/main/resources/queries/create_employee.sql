CREATE TABLE Employee(
	user_id int, 
	schedule varchar(100), 
	salary numeric(10,2), 
	work_store_id int,
	PRIMARY KEY(user_id)
	FOREIGN KEY(work_store_id) REFERENCES Store(store_id)));