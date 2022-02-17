CREATE TABLE `actors_movies` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`movie_id` BIGINT(20) NOT NULL,
	`actor_id` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
