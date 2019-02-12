insert into usr (id, username, password, active, email, score, name, surname, phone)
    values (1, 'admin', '123', true, 'marseleene@gmail.com', 0, 'Marsel', 'Sabirzyanov', '89274003372');

insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');