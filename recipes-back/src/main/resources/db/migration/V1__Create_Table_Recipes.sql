CREATE TABLE IF NOT EXISTS `recipes` (
   `recipes_id` varchar(255) NOT NULL,
   `recipes_ingredients` varchar(15) NOT NULL,
   `recipes_method_preparation` varchar(255) NOT NULL,
   `recipes_name` varchar(15) NOT NULL,
   `recipes_title` varchar(20) NOT NULL,
   `enabled` bit(1) NOT NULL,
   PRIMARY KEY (`recipes_id`)
);