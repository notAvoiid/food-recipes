CREATE TABLE IF NOT EXISTS `users` (
    `id` varchar(255) PRIMARY KEY UNIQUE NOT NULL,
    `name` varchar(255) NOT NULL,
    `username` varchar(255) UNIQUE NOT NULL,
    `password` varchar(255) NOT NULL,
    `role` varchar(50) NOT NULL
) ENGINE=InnoDB;