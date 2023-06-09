drop database facebook;
create database facebook;
use facebook;

-- SELECT COUNT(*)
-- FROM information_schema.tables
-- WHERE table_schema = 'facebook';

CREATE TABLE Gender (
  id INT PRIMARY KEY,
  gender_name VARCHAR(10) NOT NULL
);

INSERT INTO Gender (id, gender_name)
VALUES (1, 'Male'),
       (2, 'Female'),
       (3, 'Non-binary'),
       (4, 'Other');

      
create table relationship_status(id int primary key auto_increment, status varchar(30));
insert into relationship_status values(1,"Single"),(2,"In a relationship"),(3,"Engaged"),
(4,"Married"),(5,"It's Complicated"),(6, "Divorced");


create table users(id int primary key auto_increment, fname varchar(30) not null, lname varchar(30),
dob date, gender_id int,ph_no varchar(30) unique key ,email varchar(40) unique key not null,passcode varchar(255) not null,
profile_image varchar(2048),cover_image varchar(2048), bio varchar(110), website_link varchar(30),
relationship_status_id int ,foreign key(relationship_status_id) references relationship_status(id));

alter table users 
add constraint gender_fk
foreign key(gender_id) references Gender(id);

insert into users values(1001,'Asma','Shaheen','2001-08-13',2,'9876113834','asma.shaheen@wavemaker.com','Asma123',null,null,null,null,1);

insert into users values(1003,'Virat','Kohli','1995-08-13',1,'9176243834','virat.kohli@wavemaker.com','Kohli123',null,null,null,null,4);

insert into users values(1002,'David','Warner','1978-08-13',1,'9876243834','david.warner@wavemaker.com','Warner123',null,null,null,null,4);

insert into users values(1004,'Rahul','Dravid','1970-08-13',1,'9876293834','dravid.rahul@wavemaker.com',MD5('Rahul123'),null,null,null,null,4);



create table hobbie_type(id int primary key auto_increment,hobbie_name varchar(20) unique key);
insert into hobbie_type values(1,"Listening Music"),(2,"Travelling"),(3,"Reading"),(4,"Studying"),
(5,"Sleeping"),(6,"Photography"),(7,"Drawing"),(8,"Cooking"),(9,"Shopping");
create table hobbies(id int primary key auto_increment,user_id int,hobbie_id int,
foreign key(user_id) references users(id),foreign key(hobbie_id) references hobbie_type(id));



create table place_type(id int primary key auto_increment, city_type varchar(30) unique key);
insert into place_type values( 11, "home"),(12,"current");

create table places_lived(id int primary key auto_increment, place_type_id int,city_name varchar(20), user_id int not null,
foreign key(place_type_id) references place_type(id),foreign key(user_id) references users(id));



create table working(id int primary key auto_increment, user_id int not null, company_name varchar(30) not null,
designation varchar(20), city varchar(20), from_year year not null, to_year year, description varchar(60),
foreign key(user_id) references users(id));



create table education_type(id int primary key auto_increment, edu_type varchar(30) not null);
insert into education_type values(1, "high school"), (2, "graduation"),(3,"Post graduation") ;

create table education(id int primary key auto_increment, user_id int, collage_name varchar(40),
from_year year,to_year year, education_type_id int,foreign key(user_id) references users(id),
foreign key(education_type_id) references education_type(id));


create table relation(id int primary key auto_increment, rel_name varchar(20)not null unique key);
insert into relation values(1,"Mother"),(2, "Father"),(3,"Son"),(4,"Daughter"),(5,"Brother"),(6,"Sister"),
(7,"Grand Mother"),(8,"Grand Father"),(9,"Nephew"),(10,"Neice"),(11,"Cousin");
create table family(id int primary key auto_increment, user_id int , family_member_name varchar(20), relation_id int,
foreign key(user_id) references users(id),foreign key(relation_id) references relation(id));


create table post_type(id int primary key auto_increment,post_type varchar(30) not null unique key);
insert into post_type values(1, "Vedio"),(2,"Image"),(3,"Text");

create table post(id int primary key auto_increment,from_user_id int not null,with_user_id int,
post_type_id int, post varchar(2048), created_at TIMESTAMP  not null DEFAULT CURRENT_TIMESTAMP, post_content varchar(100),
foreign key(from_user_id) references users(id),foreign key(with_user_id) references users(id),
foreign key(post_type_id) references post_type(id));

alter table post
add likes INT not null DEFAULT 0;


insert into post values(13,1002,1003,2,"https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dXNlcnxlbnwwfHwwfHw%3D&w=1000&q=80",default,"my image",8);
insert into post values(11,1001,1002,2,"https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dXNlcnxlbnwwfHwwfHw%3D&w=1000&q=80",default,"my image",8);

create table liked_post(id int primary key auto_increment, post_id int not null ,liked_user_id  int not null,
foreign key(liked_user_id) references users(id), foreign key(post_id) references post(id));

alter table liked_post 
add constraint unique_like unique(post_id,liked_user_id);


create table events(id int primary key auto_increment,image varchar(2048),user_id int not null,
event_title varchar(30) not null,content varchar(80),start_date date not null,end_date date,
FOREIGN KEY(user_id) REFERENCES users(id));


CREATE TABLE saved_post(
  id INT AUTO_INCREMENT PRIMARY KEY,
  saved_user_id INT,
  post_id INT,
  saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (saved_user_id) REFERENCES users (id),
  FOREIGN KEY (post_id) REFERENCES post (id)
);
alter table saved_post 
add constraint unique_like unique(post_id,saved_user_id);



create table notify_type(id int primary key auto_increment, noti_type varchar(20)not null unique key);
insert into notify_type values(1, 'friend request'),(2, 'request accepted'), (3, 'liked post'),(4, 'commented post');

create table notification(id int primary key auto_increment, sender_id int not null, receiver_id int not null,
notify_type_id int, is_read boolean not null default 0,notify_time TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
foreign key(notify_type_id) references notify_type(id), FOREIGN KEY (sender_id) REFERENCES users (id),
FOREIGN KEY (receiver_id) REFERENCES users (id));


create table feedback(id int primary key auto_increment,user_id int not null, feedback varchar(60) not null,
feedback_time TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
foreign key(user_id) references users(id));


create table comment(id int primary key auto_increment, commentee_id int, post_id int, created_at
TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, foreign key(commentee_id) references users(id),
foreign key(post_id) references post(id));


create table chat(id int primary key auto_increment,user1_id int, user2_id int,
created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
foreign key(user1_id) references users(id),foreign key(user2_id) references users(id));

alter table chat
add constraint unique_chat
unique(user1_id,user2_id);



CREATE TABLE messages (
  id INT PRIMARY KEY AUTO_INCREMENT,
  sender_id INT NOT NULL,
  receiver_id INT NOT NULL,
  message_content TEXT NOT NULL,
  is_read BOOLEAN NOT NULL DEFAULT 0,
  chat_id int,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender_id) REFERENCES users (id),
  FOREIGN KEY (receiver_id) REFERENCES users (id),
  FOREIGN KEY (chat_id) REFERENCES chat (id)
);



CREATE TABLE blocked_users (
  block_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  blocked_user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (blocked_user_id) REFERENCES users (id)
);


CREATE TABLE friend (
    id INT NOT NULL AUTO_INCREMENT,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    status ENUM('pending', 'accepted') NOT NULL DEFAULT 'pending',
    requested_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user1_id) REFERENCES users(id),
    FOREIGN KEY (user2_id) REFERENCES users(id)
);


alter table friend
add constraint unique_friends
unique(user1_id,user2_id);


insert into friend values(1,1001,1002,2,default);
insert into friend values(2,1001,1003,2,default);
insert into friend values(3,1001,1004,2, default);
 
 


-- insert into users values(1005,"Sachin","Tendulkar",'1990-08-13',1,8346578293,"sachin.tendulkar@gmail.com","Sachin123",null,null,null,null,4);
-- ALTER TABLE users ALTER COLUMN profile_image SET DEFAULT "https://cdn.stealthoptional.com/images/ncavvykf/stealth/f60441357c6c210401a1285553f0dcecc4c4489e-564x564.jpg?w=328&h=328&auto=format";
 


-- SELECT u.fname AS friend_name
-- FROM users u
-- JOIN friend f ON (u.id = f.user2_id OR u.id = f.user1_id)
-- WHERE (f.user1_id = 'id' OR f.user2_id = 'id')
--   AND f.status = 'accepted';



-- drop table friend_status ;
-- create table friend_status(id int primary key auto_increment, friend_status varchar(20) not null default 1);
-- insert into  friend_status values(1,"Pending"),(2,"Accepted");
-- CREATE TABLE Friends (
--   id int primary key auto_increment,
--   user1_id INT NOT NULL,
--   user2_id INT NOT NULL,
--   status_id int NOT null default 1,
--   requested_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
--   foreign key(user1_id) references users(id),
--   foreign key(user2_id) references users(id),
--   foreign key(status_id) references friend_status(id)
-- );