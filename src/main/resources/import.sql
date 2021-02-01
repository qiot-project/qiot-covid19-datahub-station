CREATE TABLE IF NOT EXISTS `qiot`.`station` (
  `id` binary(16) NOT NULL,
  `serial` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `geometry` geometry NOT NULL /*!80003 SRID 4326 */,
  `city` varchar(255) ,
  `country` varchar(255) ,
  `country_code` varchar(10) ,
  `registered_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n8mucdp0jk0ykhjyikkjifjnp` (`serial`),
  SPATIAL KEY `coordinates_idx` (`geometry`),
  KEY `location_idx` (`city`,`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
