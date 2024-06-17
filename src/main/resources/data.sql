-- Insert accounts
INSERT INTO account (id, name) VALUES (1, 'Jim');
INSERT INTO account (id, name) VALUES (2, 'Bon');
INSERT INTO account (id, name) VALUES (3, 'Martin');

-- Insert work orders for Account 1
INSERT INTO work_order (id, description, account_id) VALUES (1, 'Hot dogs for Jim', 1);
INSERT INTO work_order (id, description, account_id) VALUES (2, 'Hot dog Review for Jim', 1);

-- Insert work orders for Account 2
INSERT INTO work_order (id, description, account_id) VALUES (3, 'Iron suit for Bon', 2);
INSERT INTO work_order (id, description, account_id) VALUES (4, 'big job for Bon', 2);

-- Insert work orders for Account 3
INSERT INTO work_order (id, description, account_id) VALUES (5, 'Work Order 1 for Martin', 3);
INSERT INTO work_order (id, description, account_id) VALUES (6, 'Work Order 2 for Martin', 3);
