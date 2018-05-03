DROP TABLE IF EXISTS `zuul_route_config`;
CREATE TABLE `zuul_route_config` (
  `id` varchar(50) NOT NULL,
  `path` varchar(255) NOT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `retry_able` tinyint(1) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `strip_prefix` tinyint(1) DEFAULT NULL,
  `api_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `zuul_route_config` VALUES ('ad', '/ad/**', 'spring-cloud-ad', null, '0', '1', '1', null);
INSERT INTO `zuul_route_config` VALUES ('gateway', '/api/**', 'spring-cloud-gateway', null, '0', '1', '1', null);
