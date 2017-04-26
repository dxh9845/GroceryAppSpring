CREATE TABLE Customer(
	user_id int, 
	pref_store_id int,
	primary key(user_id),
	foreign key(user_id) references App_User(user_id),
	foreign key(pref_store_id) references Store(store_id));