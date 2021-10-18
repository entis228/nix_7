INSERT INTO users (id, name, surname,lastname, phone) VALUES (1, 'Zeek', 'Yegger','Grishevych', '+380682280000');
INSERT INTO users (id, name, surname,lastname, phone) VALUES (2, 'Zaycev', 'Ivan','Ivanovich', '+380682283390');
INSERT INTO accounts (id, balance, user_id) VALUES (1, 3800, 1);
INSERT INTO accounts (id, balance, user_id) VALUES (2, 3500, 1);

INSERT INTO expense_categories (id, name) VALUES (1, 'Shop');
INSERT INTO expense_categories (id, name) VALUES (2, 'Clothes');
INSERT INTO expense_categories (id, name) VALUES (3, 'Products');
INSERT INTO expense_categories (id, name) VALUES (4, 'Entertainment');

INSERT INTO income_categories (id, name) VALUES (1, 'Salary');
INSERT INTO income_categories (id, name) VALUES (2, 'Gift');
INSERT INTO income_categories (id, name) VALUES (3, 'Found');
INSERT INTO income_categories (id, name) VALUES (4, 'Birthday');
INSERT INTO income_categories (id, name) VALUES (5, 'New Year');

INSERT INTO operations (id, date_time, result, account_id) VALUES (21, '2021-11-10 19:40:40', -1000, 1);
INSERT INTO operations (id, date_time, result, account_id) VALUES (22, '2021-11-10 19:40:41', 1500, 1);
INSERT INTO operations (id, date_time, result, account_id) VALUES (23, '2021-11-10 21:45:10', -1000, 1);
INSERT INTO operations (id, date_time, result, account_id) VALUES (24, '2021-11-10 21:45:10', 1500, 1);

INSERT INTO operation_categories (id, expense_category_id, income_category_id, operation_id) VALUES (1, 1, NULL, 21);
INSERT INTO operation_categories (id, expense_category_id, income_category_id, operation_id) VALUES (2, 2, NULL, 21);
INSERT INTO operation_categories (id, expense_category_id, income_category_id, operation_id) VALUES (3, NULL, 1, 22);
INSERT INTO operation_categories (id, expense_category_id, income_category_id, operation_id) VALUES (4, 1, NULL, 23);
INSERT INTO operation_categories (id, expense_category_id, income_category_id, operation_id) VALUES (5, 2, NULL, 23);
INSERT INTO operation_categories (id, expense_category_id, income_category_id, operation_id) VALUES (6, NULL, 1, 24);