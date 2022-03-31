CREATE TABLE `user`
(
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `gender`            varchar(255)                                                  DEFAULT NULL,
    `user_name_encrypt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `password_encrypt`  varchar(255)                                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;