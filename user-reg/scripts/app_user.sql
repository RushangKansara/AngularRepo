create table app_user(user_id INT, user_name varchar(50), user_password varchar(50), user_enabled boolean , user_role varchar(50))

select * from app_user

insert into app_user (user_id, user_name, user_password, user_enabled, user_role)
values
(1,'admin','password',true,'ADMIN')