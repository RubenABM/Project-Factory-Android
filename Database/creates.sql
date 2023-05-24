create table users (
					user_id SERIAL not null,
					user_name VARCHAR(50) not null,	
                    user_email VARCHAR(50) UNIQUE not null,	
                    user_password VARCHAR(50) not null,	
                    user_points INT not null,
					user_subscription VARCHAR(50) not null,							
					primary key (user_id)	
);

create table route (
					route_id SERIAL not null,
					route_name VARCHAR(30) not null,
					--Geometry ?
					route_coord Geometry not null,		
					route_fav boolean not null,	
					route_user_id INT not null,				
					primary key (route_id)	
);

create table data (
					data_id SERIAL not null,
					data_bpm VARCHAR(30) not null,		
					data_temp VARCHAR(30) not null,	
					data_hum VARCHAR(30) not null,	
					data_user_id INT not null,	
					data_route_id INT not null,						
					primary key (data_id)	
);

create table helmet (
					helmet_id SERIAL not null,
					helmet_name VARCHAR(30) not null,		
					helmet_description VARCHAR(30) not null,	
					helmet_state boolean not null,	
					helmet_user_id INT not null,					
					primary key (helmet_id)	
);

create table challenge (
					chall_id SERIAL not null,
					chall_coord Geometry not null,		
					chall_points INT not null,	
					chall_award VARCHAR(50) not null,
					chall_totalKM VARCHAR(30) not null,
					chall_provider VARCHAR(30) not null,
					primary key (chall_id)	
);

alter table data
add constraint data_fk_users
foreign key (data_user_id) references users(user_id);

alter table data
add constraint data_fk_route
foreign key (data_route_id) references route(route_id);

alter table helmet
add constraint helmet_fk_users
foreign key (helmet_user_id) references users(user_id);

---------TEST TABLE-------------------------

create table students (
					stu_id SERIAL not null,
					stu_name VARCHAR(30) not null,		
					stu_phone VARCHAR(30) not null,						
					primary key (stu_id)	
);