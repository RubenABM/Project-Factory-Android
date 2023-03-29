create table users (
					user_id SERIAL not null,
					user_name VARCHAR(50) not null,	
                    user_email VARCHAR(50) not null,	
                    user_password VARCHAR(50) not null,	
                    user_points VARCHAR(50) not null,						
					primary key (user_id)	
);

---------TEST TABLE-------------------------

create table students (
					stu_id SERIAL not null,
					stu_name VARCHAR(30) not null,		
					stu_phone VARCHAR(30) not null,						
					primary key (stu_id)	
);