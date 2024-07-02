show  databases;
create database library_db;
use library_db;
create table books(
book_id int auto_increment primary key,
title varchar(30) not null,
author varchar(30) not null,
publisher varchar(30),
year_published date);

show tables;


show tables;
drop table members;

create table members(
member_id int auto_increment primary key,
name varchar(30) not null,
email varchar(30) not null,
phone int not null);

create table loans(
loan_id int auto_increment primary key,
book_id int not null,
member_id int not null,
loan_date date not null,
due_date date not null,
return_date date not null,
foreign key (book_id) references books(book_id),
foreign key (member_id) references members(member_id));

show tables;
select*from books;
select*from members;
select*from loans;
alter table books modify column title varchar(30);
alter table books modify column author varchar(30);

desc books;
desc members;
alter table members modify column phone varchar(30);

desc loans;
alter table loans modify column loan_date date;
alter table loans modify column due_date date;
alter table loans modify column return_date date;

select*from books;
select*from loans;





