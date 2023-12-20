--
-- passwords for all users the same: 1234
--
INSERT INTO `users` (`name`, `password`)
VALUES ('admin', '$2a$10$3B4x1SWEoh.a2tIMxU/AieefCQXBK7qUS.wCaRwgBHiUs1E7OMuHC'),
       ('user', '$2a$10$5k0VsSjiZa3NeylU8/W7YOAjXG5U19FYoFwsuf8M7iXmY/7JUUuku'),
       ('Tymur', '$2a$10$NrGLYbvENlthOxrg9v3RNOpxcKG7etcXcVHnBeyua6VAKWPvpcQqy'),
       ('Vasya', '$2a$10$Okc968qzp.BMk4u.akWyxeYhk0Kv8eXgSu4xCFkPgv8AveMY..rLO'),
       ('Olya', '$2a$10$Ql7K9ksDymYNJlUaec.VY.e8PIDNqX/T8XWMaigw.IwXk.NjqWtai');

INSERT INTO `roles` (`name`)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

INSERT INTO `products` (`name`, `cost`)
VALUES ('product 1', 11.11),
       ('product 2', 22.22),
       ('product 3', 33.33),
       ('product 4', 44.44),
       ('product 5', 55.55),
       ('product 6', 66.66),
       ('product 7', 77.77),
       ('product 8', 88.88),
       ('product 9', 99.99),
       ('product 10', 100.10);
