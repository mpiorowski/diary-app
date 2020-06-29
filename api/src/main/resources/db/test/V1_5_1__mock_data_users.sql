INSERT INTO sys_users(user_name, user_email, user_password, user_role)
VALUES ('super', 'super@gmail.com', crypt('pass', gen_salt('bf', 8)), 'ROLE_SUPER');

INSERT INTO sys_users(user_name, user_email, user_password, user_role)
VALUES ('admin', 'admin@gmail.com', crypt('pass', gen_salt('bf', 8)), 'ROLE_ADMIN');

INSERT INTO sys_users(user_name, user_email, user_password, user_role)
VALUES ('user1', 'user@gmail.com', crypt('pass', gen_salt('bf', 8)), 'ROLE_USER');

INSERT INTO sys_users(user_name, user_email, user_password, user_role)
VALUES ('user2', 'user2@gmail.com', crypt('pass', gen_salt('bf', 8)), 'ROLE_USER');

INSERT INTO sys_users(user_name, user_email, user_password, user_role)
VALUES ('user3', 'user2@gmail.com', crypt('pass', gen_salt('bf', 8)), 'ROLE_USER');
