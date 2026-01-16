drop table IF EXISTS employee;

CREATE TABLE employee(
id int NOT NULL AUTO_INCREMENT, 
name varchar(50), 
department varchar(50), 
PRIMARY KEY(id));

INSERT INTO employee (id, name, department) VALUES (101, 'Manish', 'IT');
INSERT INTO employee (id, name, department) VALUES (102, 'Akki', 'IT');
INSERT INTO employee (id, name, department) VALUES (103, 'Sandeep', 'TELECOM');
INSERT INTO employee (id, name, department) VALUES (104, 'Alok', 'IT');
INSERT INTO employee (id, name, department) VALUES (105, 'Neelam', 'HR');