insert into login(username, firstname, lastname, password, role, email)
            values('alex', 'Aliaksei', 'Bialiauski', encode('alex', 'base64'), 'ADMIN', 'alex@gmail.com');

insert into task(title, about, assigne, priority, status, estimate, tracked)
            values ('Integration tests', 'Implements integration test for PgTasks', 1, 1, 'HIGH', now(), now());