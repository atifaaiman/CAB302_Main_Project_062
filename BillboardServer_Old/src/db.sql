CREATE SCHEMA IF NOT EXISTS `cab302` DEFAULT CHARACTER SET utf8 ;
USE `cab302` ;
CREATE TABLE IF NOT EXISTS `cab302`.`user` (`username` VARCHAR(45) NOT NULL,`password` VARCHAR(45) NOT NULL,`permission` VARCHAR(45) NOT NULL,PRIMARY KEY (`username`));
CREATE TABLE IF NOT EXISTS `cab302`.`billboard` (`name_billboard` VARCHAR(45) NOT NULL,`xml_data` LONGBLOB NOT NULL,`username` VARCHAR(45) NOT NULL,PRIMARY KEY (`name_billboard`),FOREIGN KEY (`username`)REFERENCES `cab302`.`user` (`username`));
CREATE TABLE IF NOT EXISTS `cab302`.`schedule` (`id_schedule` INT NOT NULL AUTO_INCREMENT,`date_time` DATETIME NOT NULL,`duration` INT NOT NULL,`repeat` VARCHAR(45) NOT NULL,`name_billboard` VARCHAR(45) NOT NULL,PRIMARY KEY (`id_schedule`),FOREIGN KEY (`name_billboard`)REFERENCES `cab302`.`billboard` (`name_billboard`));
CREATE TABLE IF NOT EXISTS `cab302`.`salt` (`username` VARCHAR(45) NOT NULL,`salt` VARCHAR(45) NOT NULL,PRIMARY KEY (`username`),FOREIGN KEY (`username`)REFERENCES `cab302`.`user` (`username`));  
INSERT INTO `cab302`.`user` VALUE ('admin', '520C5ABDB44D0A2E2D21FDD7E8DF8E7B', 'Edit Users') ON DUPLICATE KEY UPDATE `username`='admin';
INSERT INTO `cab302`.`salt` VALUE ('admin', '2DC6778D0543CED3025A37B6040390CF') ON DUPLICATE KEY UPDATE `username`='admin';