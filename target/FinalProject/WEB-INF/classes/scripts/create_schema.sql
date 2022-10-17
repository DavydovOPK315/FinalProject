-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema final_project
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `final_project` ;

-- -----------------------------------------------------
-- Schema final_project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `final_project` DEFAULT CHARACTER SET utf8 ;
USE `final_project` ;

-- -----------------------------------------------------
-- Table `final_project`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`courses` (
                                                         `id` INT NOT NULL AUTO_INCREMENT,
                                                         `name` VARCHAR(45) NOT NULL,
    `date_start` DATE NOT NULL,
    `date_end` DATE NOT NULL,
    `description` LONGTEXT NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    INDEX `index_courses_name` (`name`(4) ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 23
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `final_project`.`topics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`topics` (
                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                        `name` VARCHAR(45) NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` VARCHAR(25) NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 22
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `final_project`.`courses_has_topics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`courses_has_topics` (
                                                                    `courses_id` INT NOT NULL,
                                                                    `topics_id` INT NOT NULL,
                                                                    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                                    PRIMARY KEY (`courses_id`, `topics_id`),
    INDEX `fk_courses_has_topics_topics1_idx` (`topics_id` ASC) VISIBLE,
    INDEX `fk_courses_has_topics_courses1_idx` (`courses_id` ASC) VISIBLE,
    CONSTRAINT `fk_courses_has_topics_courses1`
    FOREIGN KEY (`courses_id`)
    REFERENCES `final_project`.`courses` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_courses_has_topics_topics1`
    FOREIGN KEY (`topics_id`)
    REFERENCES `final_project`.`topics` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `final_project`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`roles` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `name` VARCHAR(25) NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` VARCHAR(25) NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 16
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `final_project`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`users` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `login` VARCHAR(30) NOT NULL,
    `first_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` VARCHAR(25) NOT NULL DEFAULT 'ACTIVE',
    `reset_password_token` VARCHAR(30) NULL DEFAULT NULL,
    `role_id` INT NOT NULL DEFAULT '2',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_users_roles_idx` (`role_id` ASC) VISIBLE,
    INDEX `index_users_login` (`login`(4) ASC) VISIBLE,
    CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`role_id`)
    REFERENCES `final_project`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 17
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `final_project`.`performance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`performance` (
                                                             `id` INT NOT NULL AUTO_INCREMENT,
                                                             `grade` INT UNSIGNED NOT NULL DEFAULT '0',
                                                             `users_id` INT NOT NULL,
                                                             `topics_id` INT NOT NULL,
                                                             `courses_id` INT NOT NULL,
                                                             PRIMARY KEY (`id`),
    INDEX `fk_performance_users1_idx` (`users_id` ASC) VISIBLE,
    INDEX `fk_performance_topics1_idx` (`topics_id` ASC) VISIBLE,
    INDEX `fk_performance_courses1_idx` (`courses_id` ASC) VISIBLE,
    CONSTRAINT `fk_performance_courses1`
    FOREIGN KEY (`courses_id`)
    REFERENCES `final_project`.`courses` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_performance_topics1`
    FOREIGN KEY (`topics_id`)
    REFERENCES `final_project`.`topics` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_performance_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `final_project`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 176
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `final_project`.`users_has_courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_project`.`users_has_courses` (
                                                                   `users_id` INT NOT NULL,
                                                                   `courses_id` INT NOT NULL,
                                                                   PRIMARY KEY (`users_id`, `courses_id`),
    INDEX `fk_users_has_courses_courses1_idx` (`courses_id` ASC) VISIBLE,
    INDEX `fk_users_has_courses_users1_idx` (`users_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_has_courses_courses1`
    FOREIGN KEY (`courses_id`)
    REFERENCES `final_project`.`courses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_users_has_courses_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `final_project`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

USE `final_project` ;

-- -----------------------------------------------------
-- procedure checking_status
-- -----------------------------------------------------

DELIMITER $$
USE `final_project`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checking_status`()
BEGIN
UPDATE `final_project`.`courses`
set id = id
WHERE (`id` > '0');
END$$

DELIMITER ;
USE `final_project`;

DELIMITER $$
USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`courses_BEFORE_INSERT`
BEFORE INSERT ON `final_project`.`courses`
FOR EACH ROW
BEGIN
DECLARE msg TEXT DEFAULT ' ';
      if NEW.date_start >= NEW.date_end or NEW.date_end < current_date() then
			SET msg = concat('Date started can not be less or equel to date ended or less than date now');
			SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;

    set NEW.`status` = case
		when current_date() < date(NEW.date_start) then "NOT_STARTED"
        when current_date() > date(NEW.date_end) then "FINISHED"
		when (current_date() >= date(NEW.date_start) AND current_date() <= date(NEW.date_end)) then "CURRENT"
end;
END$$

USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`courses_BEFORE_UPDATE`
BEFORE UPDATE ON `final_project`.`courses`
           FOR EACH ROW
BEGIN
DECLARE msg TEXT DEFAULT ' ';
	if new.date_start != old.date_start or new.date_end != old.date_end then
		if NEW.date_start >= NEW.date_end or NEW.date_end < current_date() then
			SET msg = concat('Date started can not be less or equel to date ended or less than date now');
			SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;
end if;
   set NEW.`status` = case
		when current_date() < date(NEW.date_start) then "NOT_STARTED"
        when current_date() > date(NEW.date_end) then "FINISHED"
        when current_date() between date(NEW.date_start) and date(NEW.date_end) then "CURRENT"
end;
END$$

USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`users_BEFORE_UPDATE`
BEFORE UPDATE ON `final_project`.`users`
           FOR EACH ROW
BEGIN
DECLARE count_courses_var int;
DECLARE msg TEXT DEFAULT ' ';

select count(courses_id) from users_has_courses where users_id = old.id into count_courses_var;
if count_courses_var > 0 AND old.role_id != new.role_id then
					SET msg = concat('Cannot be done in a users table');
					SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;
END$$

USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`users_BEFORE_DELETE`
BEFORE DELETE ON `final_project`.`users`
FOR EACH ROW
BEGIN
DECLARE count_courses_var int;
DECLARE msg TEXT DEFAULT ' ';

select count(courses_id) from users_has_courses where users_id = old.id into count_courses_var;
if count_courses_var > 0 then
					SET msg = concat('Cannot be done in a users table');
					SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;
END$$

USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`performance_BEFORE_DELETE`
BEFORE DELETE ON `final_project`.`performance`
FOR EACH ROW
BEGIN
DECLARE msg TEXT DEFAULT ' ';
declare grade_sum int;
select sum(grade) from performance where old.courses_id = courses_id AND old.topics_id = topics_id into grade_sum;
if grade_sum > 0 then
			SET msg = concat('Cannot delete topic into performance!');
			SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;
END$$

USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`performance_BEFORE_INSERT`
BEFORE INSERT ON `final_project`.`performance`
FOR EACH ROW
BEGIN
DECLARE msg TEXT DEFAULT ' ';
DECLARE course_status varchar(25);

select c.`status` from courses c where c.id = new.id into course_status;

if course_status != 'NOT_STARTED' then
	SET msg = concat('Student cannot be enrolled on course!');
		SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;

if new.grade <> 0 then
	if new.grade < 60 or new.grade > 100 then
		SET msg = concat('Score cannot be less than 60 and bigger than 100');
		SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;
end if;
END$$

USE `final_project`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `final_project`.`performance_BEFORE_UPDATE`
BEFORE UPDATE ON `final_project`.`performance`
           FOR EACH ROW
BEGIN
DECLARE msg TEXT DEFAULT ' ';
if new.grade <> 0 then
	if new.grade < 60 or new.grade > 100 then
		SET msg = concat('Score cannot be less than 60 and bigger than 100');
		SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
end if;
end if;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;