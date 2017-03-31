CREATE TABLE Employee(
	user_id int, 
	schedule varchar(100), 
	salary numeric(10,2), 
	work_store_id int,
	primary key(user_id)
	foreign key(work_store_id) references Store(store_id)));