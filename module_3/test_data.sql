INSERT INTO users (id, name, surname,lastname, phone) VALUES (1, 'Zeek', 'Yegger','Grishevych', '+380682280000');
INSERT INTO users (id, name, surname,lastname, phone) VALUES (2, 'Zaycev', 'Ivan','Ivanovich', '+380682283390');

INSERT INTO accounts (id, balance, name, user_id) VALUES (1, 3800,'Main', 1);
INSERT INTO accounts (id, balance, name, user_id) VALUES (2, 100,'Party', 1);

INSERT INTO expense_category (id, name, type) VALUES (2,'Expenses',1);
INSERT INTO income_category (id, name, type) VALUES (1,'Incomes',0);

--не обязательно
INSERT INTO operations (id, description, time, account_id, category_id) VALUES (1, 'exp1','2021-11-10 19:40:40',1, 2);
INSERT INTO operations (id, description, time, account_id, category_id) VALUES (2, 'exp2','2021-11-10 19:40:50',1, 2);
INSERT INTO operations (id, description, time, account_id, category_id) VALUES (3, 'inc1','2021-12-10 19:40:40',1, 1);