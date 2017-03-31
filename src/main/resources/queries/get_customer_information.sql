SELECT App_user.username,
       App_User.name,
       App_User.password,
       App_User.phone,
       App_User.address,
       Store.name
FROM App_User,
     Customer,
     Store
WHERE AppUser.user_id = Customer.user_id AND Customer.pref_store_id = Store.store_id