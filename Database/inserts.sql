
--Atualizar insert--
insert into users (user_name, user_email, user_password, user_points, user_subscription) values ('Artur Teste','artur@iade.pt', 'arturiade', '0000', 'Free'); 
insert into users (user_name, user_email, user_password, user_points, user_subscription) values ('demo','demo', 'demo', '0000', 'Free'); 

insert into route (route_name, route_coord, route_fav, route_user_id) values ('Casa', 'LINESTRING (0 0, 1 1, 2 0)', false, 2);

---------TEST INSERT-------------------------

insert into students (stu_name, stu_phone) values ('André Santos','925478589'); 
insert into students (stu_name, stu_phone) values ('Joana Mendes','918822321'); 