CREATE TABLE `chat_messages` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `memory_id` VARCHAR(255) NOT NULL,
  `messages` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_memory_id` (`memory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;