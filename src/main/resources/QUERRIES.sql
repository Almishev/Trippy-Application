CREATE SEQUENCE public.s_categories
	INCREMENT 1
	START 1;

create table categories(
	category_id int not null primary key default nextval('s_categories'),
	name varchar(100) not null
);

CREATE SEQUENCE public.s_users
	INCREMENT 1
	START 1;

CREATE TABLE users(
	user_id int not null primary key default nextval('s_users'),
	email varchar(200) not null unique,
	first_name varchar(100) not null,
	last_name varchar(100),
	created_on timestamp not null default NOW(),
	edited_on timestamp
);

insert into users (email,first_name,last_name) values ('toni@abv.bg','Anton','Almishev');
insert into users (email,first_name,last_name) values ('vask@abv.bg','Vasko','Milenkov');



insert into categories(name) values ('hotel');
insert into categories(name) values ('restaurant');
insert into categories(name) values ('bar');

CREATE SEQUENCE public.s_towns
	INCREMENT 1
	START 1;

CREATE TABLE towns(
	town_id int not null primary key default nextval('s_towns'),
	name varchar(100) not null
	);

insert into towns(name) values('Bansko');
insert into towns(name) values('Nevrokop');
insert into towns(name) values('Limassol');



CREATE SEQUENCE public.s_establishments
	INCREMENT 1
	START 1;

	CREATE TABLE establishments(
	est_id int not null primary key default nextval('s_establishments'),
	category_id int not null,CONSTRAINT FK_establishments_categories FOREIGN KEY(category_id)
        REFERENCES categories(category_id),
	name varchar(30) not null,
	town_id int not null, CONSTRAINT FK_establishments_towns FOREIGN KEY(town_id)
        REFERENCES towns(town_id)

		insert into establishments (category_id,name,town_id) values (1,'Glazne',1);
		insert into establishments (category_id,name,town_id) values (1,'Victoria',1);
		insert into establishments (category_id,name,town_id) values (2,'Manim Kolio',1);
		insert into establishments (category_id,name,town_id) values (2,'Old Three',1);
		insert into establishments (category_id,name,town_id) values (3,'Malkata Tekila',1);
		insert into establishments (category_id,name,town_id) values (3,'Pri Gosho',1);

		insert into establishments (category_id,name,town_id) values (1,'Valentino',2);
		insert into establishments (category_id,name,town_id) values (1,'Uva Nestum',2);
		insert into establishments (category_id,name,town_id) values (2,'Staro Bure',2);
		insert into establishments (category_id,name,town_id) values (2,'Vanita',2);
		insert into establishments (category_id,name,town_id) values (3,'Monro bar',2);
		insert into establishments (category_id,name,town_id) values (3,'Caffee Marria',2);

		insert into establishments (category_id,name,town_id) values (1,'Plaza',3);
		insert into establishments (category_id,name,town_id) values (1,'Mediterarium',3);
		insert into establishments (category_id,name,town_id) values (2,'Megali Psari',3);
		insert into establishments (category_id,name,town_id) values (2,'Fassouri',3);
		insert into establishments (category_id,name,town_id) values (3,'Starbacks',3);
		insert into establishments (category_id,name,town_id) values (3,'Costa coffee',3);

);



CREATE SEQUENCE IF NOT EXISTS public.s_comments
	INCREMENT 1
	START 1;




CREATE TABLE comments(
	comment_id int not null primary key default nextval('s_comments'),
	content text not null,
	rating numeric,
	est_id int not null references establishments(est_id) ON DELETE CASCADE,
	user_id int not null references users(user_id) ON DELETE CASCADE,
	created_on timestamp not null default NOW(),
	edited_on timestamp
);

insert into comments (content,rating,est_id,user_id) values('It was very good week, thank you to all staff',4,1,1)