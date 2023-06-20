CREATE DATABASE  IF NOT EXISTS `final_project` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `final_project`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: final_project
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `date_start` date NOT NULL,
  `date_end` date NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `index_courses_name` (`name`(4))
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Java Spring 2022','2022-09-01','2022-10-19','The main advantage of this programming language is the compatibility of old and new versions of code. Nowadays Java applications run on any device or OS: PC, smartphone, and even on automobile Tesla. A great amount of various companies uses this universal language. Java spreading allows developers to find interesting work and to change business areas if they want.','2022-08-30 20:05:56','FINISHED'),(2,'Quality Assurance (On-line training)','2022-08-01','2022-09-25','Necessary knowledge\r\nBasic knowledge of Software testing\r\nBasic knowledge of programming\r\nBasic knowledge of SQL/CSS\r\nEnglish: B1 or higher. \r\nWe recommend use \"Software Functional Testing\" template for Students who attend EPAM External Training Program','2022-09-01 13:22:41','FINISHED'),(12,'Data and Analytics','2022-01-03','2022-05-16','We invite talented students and beginners with a basic level of knowledge in software development to participate in the educational program for future juniors. Participants will receive the necessary level of knowledge and skills, and candidates who successfully complete all stages of training and show good level of knowledge and skills will be able to pass interviews for projects and join the largest IT company in Ukraine in a couple of months.','2022-09-01 17:00:10','FINISHED'),(13,'Data Software Engineering','2022-07-18','2022-09-21','This online training is for \"the very beginners\" in Relational Databases (i.e. it is NOT for experienced professionals, sorry :) ). It covers all key ideas of relational databases (from the concept of data to the logic of transactions), fundamental theory and demonstrative practice of database design: tables, keys, relationships, normal forms, views, triggers, stored procedures and much more.','2022-09-01 18:25:44','FINISHED'),(15,'Scala','2022-09-07','2022-11-15','Get an introduction to the programming language Scala. You\'ll learn why and how companies like Netflix, Airbnb, and Morgan Stanley are choosing Scala for large-scale applications and data engineering infrastructure. You\'ll learn the basics of the language, including syntax and style, focusing on the most commonly used features in the Scala standard library.','2022-09-01 19:07:42','FINISHED'),(17,'.NET Development','2022-10-01','2023-01-31','If you are eager to create a wide variety of applications of any complexity level and keep up with the latest IT technologies, this training program is for you. Internet of things (IoT), Cloud technologies, gamedev, neural networks, and web applications – you\'ll be up to all of those.','2022-09-05 16:52:29','FINISHED'),(18,'DevOps Fundamentals','2022-10-10','2023-03-17','DevOps is a philosophy and culture of software development. DevOps specialists are among the most demanded in the international IT market. And the demand for them continues to grow. DevOps engineers accompany the product at all stages of the life cycle, automate and accelerate development processes, increasing its efficiency and safety.','2022-09-05 19:13:24','FINISHED'),(22,'Ruby Online Program','2022-10-03','2023-05-23','This educational program was developed for talented students with no prior engineering experience. This is an introduction to software engineering, and the most important factor is your motivation to become a Ruby developer. This educational program is completely free of charge and will be held online, so we invite candidates from across Ukraine to join us.','2022-09-15 19:21:00','FINISHED'),(28,'Android Development 2023','2023-02-02','2023-05-31','This course starts with the basic concepts of Android and continues with the application components. You will learn how to build UIs (user interfaces), download from the network, and save information locally. Moreover, you will dive into architecture principles and commonly used libraries. The end goal is for you to perform Android-specific testing, create and upload applications to stores.','2022-10-21 17:51:23','FINISHED'),(29,'Node.js 2023','2023-02-07','2023-05-30','The Program covers the essentials of the Node.js back-end development. Starting with the fundamentals of JavaScript, you will proceed with Node.js development and mastering Express – the most popular Node.js framework. You will get acquainted with NoSQL databases and MySQL, acquire skills in building RESTful and GraphQL APIs, and finally, observe the best practices in testing, and Node.js application debugging and profiling.','2022-10-21 17:59:27','FINISHED'),(30,'Big Data 2023','2023-03-21','2023-09-18','Big data concerns ways to analyze, systematically extract information from, or otherwise deal with data sets that are too large or complex to be dealt with by traditional data-processing application software. Obtain the necessary theoretical and practical background that covers best engineering practices, resulting in productive software development process along with product quality and aimed to improve the expertise in Big Data and prepare for real-time Big','2022-10-21 18:10:20','CURRENT'),(31,'CLOUD AND DEVOPS 2023','2023-04-21','2023-08-31','The internship will be held in hybrid working condition, combining remote and office studies. \r\n\r\nOur experienced mentor from production will monitor your progress, provide help when needed, give tasks and check the results during the whole learning path. DETAILS\r\nEPAM leads the industry in digital platform engineering and product development services. Since 1993, it has been helping its customers to navigate the waves of digital transformation, developing','2022-10-21 18:16:19','CURRENT'),(32,'WEB SECURITY TESTING 2023','2023-08-21','2023-12-15','This training program will teach you how to find and exploit the most common security vulnerabilities in web-based applications. Vulnerabilities include but are not limited to XSS, SQL Injection, CSRF, and session manipulation. This is a beginner-friendly introduction to Web Application Security Testing which starts from the very basics and then takes on more advanced topics.','2022-10-21 18:21:28','NOT_STARTED');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `courses_BEFORE_INSERT` BEFORE INSERT ON `courses` FOR EACH ROW BEGIN
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
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `courses_BEFORE_UPDATE` BEFORE UPDATE ON `courses` FOR EACH ROW BEGIN
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
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `courses_has_topics`
--

DROP TABLE IF EXISTS `courses_has_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_has_topics` (
  `courses_id` int NOT NULL,
  `topics_id` int NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`courses_id`,`topics_id`),
  KEY `fk_courses_has_topics_topics1_idx` (`topics_id`),
  KEY `fk_courses_has_topics_courses1_idx` (`courses_id`),
  CONSTRAINT `fk_courses_has_topics_courses1` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_courses_has_topics_topics1` FOREIGN KEY (`topics_id`) REFERENCES `topics` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_has_topics`
--

LOCK TABLES `courses_has_topics` WRITE;
/*!40000 ALTER TABLE `courses_has_topics` DISABLE KEYS */;
INSERT INTO `courses_has_topics` VALUES (1,2,'2022-09-18 19:06:37'),(1,3,'2022-09-09 18:41:56'),(1,7,'2022-09-08 18:36:58'),(1,20,'2022-09-08 18:35:15'),(2,23,'2022-10-19 18:24:51'),(2,24,'2022-10-19 18:24:51'),(2,25,'2022-10-19 18:24:51'),(2,26,'2022-10-19 18:24:51'),(2,27,'2022-10-19 18:24:51'),(12,28,'2022-10-19 18:33:30'),(12,29,'2022-10-19 18:33:30'),(12,30,'2022-10-19 18:33:30'),(12,31,'2022-10-19 18:41:11'),(13,32,'2022-10-19 18:47:35'),(13,33,'2022-10-19 18:47:35'),(13,34,'2022-10-19 18:47:35'),(13,35,'2022-10-19 18:47:35'),(13,36,'2022-10-19 18:47:35'),(15,1,'2022-09-14 18:01:40'),(15,2,'2022-09-14 18:01:40'),(15,3,'2022-09-14 18:01:40'),(15,11,'2022-09-10 18:18:15'),(17,16,'2022-10-21 16:49:57'),(17,17,'2022-10-21 16:49:57'),(17,18,'2022-10-21 16:49:57'),(18,37,'2022-10-21 17:25:53'),(18,38,'2022-10-21 17:25:53'),(18,39,'2022-10-21 17:25:53'),(18,40,'2022-10-21 17:25:53'),(22,8,'2022-10-21 17:30:42'),(22,9,'2022-10-21 17:30:42'),(22,10,'2022-10-21 17:30:42'),(22,11,'2022-10-21 17:30:42'),(28,41,'2022-10-21 17:51:23'),(28,42,'2022-10-21 17:51:23'),(28,43,'2022-10-21 17:51:23'),(29,44,'2022-10-21 17:59:27'),(29,45,'2022-10-21 17:59:27'),(29,46,'2022-10-21 17:59:27'),(29,47,'2022-10-21 17:59:27'),(30,48,'2022-10-21 18:10:20'),(30,49,'2022-10-21 18:10:20'),(30,50,'2022-10-21 18:10:20'),(30,51,'2022-10-21 18:10:20'),(31,52,'2022-10-21 18:16:19'),(31,53,'2022-10-21 18:16:19'),(31,54,'2022-10-21 18:16:19'),(31,55,'2022-10-21 18:16:19'),(31,56,'2022-10-21 18:16:19'),(32,57,'2022-10-21 18:21:28'),(32,58,'2022-10-21 18:21:28'),(32,59,'2022-10-21 18:21:28'),(32,60,'2022-10-21 18:21:28');
/*!40000 ALTER TABLE `courses_has_topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performance`
--

DROP TABLE IF EXISTS `performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grade` int unsigned NOT NULL DEFAULT '0',
  `users_id` int NOT NULL,
  `topics_id` int NOT NULL,
  `courses_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_performance_users1_idx` (`users_id`),
  KEY `fk_performance_topics1_idx` (`topics_id`),
  KEY `fk_performance_courses1_idx` (`courses_id`),
  CONSTRAINT `fk_performance_courses1` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_performance_topics1` FOREIGN KEY (`topics_id`) REFERENCES `topics` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_performance_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance`
--

LOCK TABLES `performance` WRITE;
/*!40000 ALTER TABLE `performance` DISABLE KEYS */;
INSERT INTO `performance` VALUES (7,90,10,20,1),(8,100,12,20,1),(13,70,10,7,1),(14,100,12,7,1),(30,65,10,3,1),(31,100,12,3,1),(43,100,13,11,15),(44,100,14,11,15),(71,90,11,20,1),(72,100,11,7,1),(73,60,11,3,1),(77,90,13,1,15),(78,100,14,1,15),(80,70,13,2,15),(81,66,14,2,15),(83,0,13,3,15),(84,70,14,3,15),(94,90,11,11,15),(95,91,11,1,15),(96,93,11,2,15),(97,0,11,3,15),(116,100,10,2,1),(117,62,11,2,1),(118,100,12,2,1),(195,70,11,28,12),(196,80,11,29,12),(197,60,11,30,12),(198,90,11,31,12),(199,70,11,32,13),(200,80,11,33,13),(201,60,11,34,13),(202,95,11,35,13),(203,100,11,36,13),(204,100,10,23,2),(205,100,10,24,2),(206,100,10,25,2),(207,100,10,26,2),(208,100,10,27,2),(209,90,13,23,2),(210,80,13,24,2),(211,70,13,25,2),(212,60,13,26,2),(213,100,13,27,2),(214,65,14,23,2),(215,75,14,24,2),(216,85,14,25,2),(217,90,14,26,2),(218,96,14,27,2),(232,80,14,28,12),(233,80,14,29,12),(234,90,14,30,12),(235,80,14,31,12),(236,70,10,16,17),(237,60,11,16,17),(238,100,12,16,17),(239,80,13,16,17),(240,80,10,17,17),(241,70,11,17,17),(242,100,12,17,17),(243,80,13,17,17),(244,0,10,18,17),(245,90,11,18,17),(246,0,12,18,17),(247,0,13,18,17),(262,100,11,37,18),(263,80,22,37,18),(264,90,11,38,18),(265,0,22,38,18),(266,0,11,39,18),(267,0,22,39,18),(268,0,11,40,18),(269,0,22,40,18),(270,88,11,8,22),(271,0,11,9,22),(272,0,11,10,22),(273,0,11,11,22),(278,77,22,8,22),(279,0,22,9,22),(280,0,22,10,22),(281,0,22,11,22),(285,0,22,41,28),(286,0,22,42,28),(287,0,22,43,28),(292,0,12,44,29),(293,0,12,45,29),(294,0,12,46,29),(295,0,12,47,29),(304,0,11,48,30),(305,0,11,49,30),(306,0,11,50,30),(307,0,11,51,30),(308,0,11,44,29),(309,0,11,45,29),(310,0,11,46,29),(311,0,11,47,29),(317,0,11,41,28),(318,0,11,42,28),(319,0,11,43,28);
/*!40000 ALTER TABLE `performance` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `performance_BEFORE_INSERT` BEFORE INSERT ON `performance` FOR EACH ROW BEGIN
DECLARE msg TEXT DEFAULT ' ';
DECLARE course_status varchar(25);

select c.`status` from courses c where c.id = new.courses_id into course_status;

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
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `performance_BEFORE_UPDATE` BEFORE UPDATE ON `performance` FOR EACH ROW BEGIN
DECLARE msg TEXT DEFAULT ' ';
DECLARE course_status TEXT DEFAULT ' ';

SELECT `status` FROM courses WHERE courses.id = new.courses_id INTO course_status;

if new.grade <> 0 then
	if new.grade < 60 or new.grade > 100 then
		SET msg = concat('Score cannot be less than 60 and bigger than 100');
		SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
	end if;
    if course_status != 'CURRENT' then
		SET msg = concat('Cannot update scores due to status');
		SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
	end if;
end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `performance_BEFORE_DELETE` BEFORE DELETE ON `performance` FOR EACH ROW BEGIN
DECLARE msg TEXT DEFAULT ' ';
declare grade_sum int;
    select sum(grade) from performance where old.courses_id = courses_id AND old.topics_id = topics_id into grade_sum;
    if grade_sum > 0 then
			SET msg = concat('Cannot delete topic into performance!');
			SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
      end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(25) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN','2022-08-25 15:38:00','ACTIVE'),(2,'STUDENT','2022-08-25 15:38:09','ACTIVE'),(3,'TEACHER','2022-08-25 15:38:09','ACTIVE');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(25) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'Fundamentals','2022-08-28 12:46:47','ACTIVE'),(2,'OOP','2022-08-28 12:48:04','ACTIVE'),(3,'Abstract classes','2022-08-28 12:48:27','ACTIVE'),(5,'Interfaces','2022-08-28 12:49:09','ACTIVE'),(6,'Nested classes','2022-09-05 15:54:32','ACTIVE'),(7,'Generics','2022-09-05 15:54:41','ACTIVE'),(8,'Collection','2022-09-05 15:54:57','ACTIVE'),(9,'Exceptions','2022-09-05 15:55:14','ACTIVE'),(10,'Streams I/O','2022-09-05 15:55:23','ACTIVE'),(11,'Multithreading','2022-09-05 15:55:31','ACTIVE'),(12,'Design Introduction','2022-09-05 15:55:52','ACTIVE'),(13,'GOF-Patterns','2022-09-05 15:56:01','ACTIVE'),(14,'Testing & Logging','2022-09-05 15:56:24','ACTIVE'),(15,'Database & SQL','2022-09-05 15:56:41','ACTIVE'),(16,'JDBC & DAO','2022-09-05 15:57:01','ACTIVE'),(17,'XML JSON','2022-09-05 15:57:22','ACTIVE'),(18,'WEB','2022-09-05 15:57:36','ACTIVE'),(19,'Filters & Sessions','2022-09-05 15:57:45','ACTIVE'),(20,'JSP & JSTL','2022-09-05 15:57:54','ACTIVE'),(23,'Intro to software testing','2022-10-19 18:23:39','ACTIVE'),(24,'Software testing in SDLC: Scrum','2022-10-19 18:23:48','ACTIVE'),(25,'Test planning and classification','2022-10-19 18:24:12','ACTIVE'),(26,'Requirements analysis','2022-10-19 18:24:24','ACTIVE'),(27,'Test cases and test design','2022-10-19 18:24:34','ACTIVE'),(28,'General Tech','2022-10-19 18:30:52','ACTIVE'),(29,'SQL Basics','2022-10-19 18:32:29','ACTIVE'),(30,'Database Management System','2022-10-19 18:33:16','ACTIVE'),(31,'DBMS Basics','2022-10-19 18:41:05','ACTIVE'),(32,'Databases Fundamentals','2022-10-19 18:46:31','ACTIVE'),(33,'Relations, Keys, Relationships, Indexes','2022-10-19 18:46:41','ACTIVE'),(34,'Normalization and Normal Forms','2022-10-19 18:46:51','ACTIVE'),(35,'Database Modelling','2022-10-19 18:47:03','ACTIVE'),(36,'Database Quality','2022-10-19 18:47:14','ACTIVE'),(37,'Virtualization','2022-10-21 17:24:43','ACTIVE'),(38,'Networking essentials','2022-10-21 17:24:53','ACTIVE'),(39,'WebServer: IIS','2022-10-21 17:25:05','ACTIVE'),(40,'CentOS Linux','2022-10-21 17:25:24','ACTIVE'),(41,'Deep Dive into Java Development','2022-10-21 17:50:16','ACTIVE'),(42,'Kotlin','2022-10-21 17:50:27','ACTIVE'),(43,'Android','2022-10-21 17:50:37','ACTIVE'),(44,'JavaScript Basics','2022-10-21 17:56:56','ACTIVE'),(45,'Node.js Baseline','2022-10-21 17:57:06','ACTIVE'),(46,'Node.js and Express','2022-10-21 17:57:17','ACTIVE'),(47,'Databases and GraphQL','2022-10-21 17:57:29','ACTIVE'),(48,'Hadoop','2022-10-21 18:08:10','ACTIVE'),(49,'Hive','2022-10-21 18:08:19','ACTIVE'),(50,'Spark','2022-10-21 18:08:27','ACTIVE'),(51,'Kafka','2022-10-21 18:08:46','ACTIVE'),(52,'Scripting','2022-10-21 18:13:34','ACTIVE'),(53,'SDLC','2022-10-21 18:13:45','ACTIVE'),(54,'CI/CD','2022-10-21 18:13:55','ACTIVE'),(55,'laC','2022-10-21 18:14:05','ACTIVE'),(56,'Clouds','2022-10-21 18:14:36','ACTIVE'),(57,'Cross-site scripting','2022-10-21 18:19:18','ACTIVE'),(58,'Injecting SQL code','2022-10-21 18:19:29','ACTIVE'),(59,'SQL injections','2022-10-21 18:19:42','ACTIVE'),(60,'CSRF, Flash','2022-10-21 18:20:05','ACTIVE');
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(25) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  `reset_password_token` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role_id` int NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_users_roles_idx` (`role_id`),
  KEY `index_users_login` (`login`(4)),
  CONSTRAINT `fk_users_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','Denis','Davydov','davydov654@gmail.com','zV8TZ0AxUxlPmAgpoQmJ1A==','2022-08-27 15:16:38','ACTIVE',NULL,1),(2,'sidorenko','Vasyl','Sidorenko','sidorenkopost@gmail.com','09ebzytltI4wer0TYgcwNg==','2022-09-18 19:10:29','ACTIVE',NULL,3),(5,'arkadiy','Nival','Arkadiy','planetMars@gmail.com','y6VMj9gmhJ8BaGkjySbASw==','2022-08-29 09:31:43','ACTIVE',NULL,3),(7,'zakk','Zakk','Tarkiv','tarkiv@gmail.com','eyhHVD7AnN2BWvRwKMXdzw==','2022-08-29 09:59:00','ACTIVE',NULL,3),(9,'alex','Alexsander','Sidorov','alexSidorov@gmail.com','PYL6umd86wEjhYwYLgKHmg==','2022-08-29 10:19:21','ACTIVE',NULL,3),(10,'alex2','Alexsander','Popov','alexPopov@gmail.com','8DIwWrf9IZy296szPM28YA==','2022-08-29 10:20:40','ACTIVE',NULL,2),(11,'rustam','Rustam','Kivar','denys.davydov1@nure.ua','AbCF2BghINTOXlsvOcfDWA==','2022-08-29 10:25:44','ACTIVE',NULL,2),(12,'петро','Петро','Петрович','petrovich@gmail.com','KF5gCKMBJT2kBt19zu2PPw==','2022-08-29 10:26:19','ACTIVE',NULL,2),(13,'taras','Taras','Martishko','martishko342@gmail.com','XH2l6pEhUgt9aqhmmffjFg==','2022-08-29 16:15:51','ACTIVE',NULL,2),(14,'ferdinand','Tolya','Ferdinand','ferdinand@gmail.com','m5HXdBMN8xAS3TY2DWDO7w==','2022-09-01 17:59:00','ACTIVE',NULL,2),(22,'olga','Olga','Derevyanko','derevyanko@gmail.com','WHdhPSXBSt6abQtOga+eMw==','2022-10-21 17:13:11','ACTIVE',NULL,2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `users_BEFORE_UPDATE` BEFORE UPDATE ON `users` FOR EACH ROW BEGIN
DECLARE count_courses_var int;
DECLARE msg TEXT DEFAULT ' ';

select count(courses_id) from users_has_courses where users_id = old.id into count_courses_var;
	  if count_courses_var > 0 AND old.role_id != new.role_id then
					SET msg = concat('Cannot be done in a users table');
					SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
      end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `users_BEFORE_DELETE` BEFORE DELETE ON `users` FOR EACH ROW BEGIN
DECLARE count_courses_var int;
DECLARE msg TEXT DEFAULT ' ';

select count(courses_id) from users_has_courses where users_id = old.id into count_courses_var;
	  if count_courses_var > 0 then
					SET msg = concat('Cannot be done in a users table');
					SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
      end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `users_has_courses`
--

DROP TABLE IF EXISTS `users_has_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_has_courses` (
  `users_id` int NOT NULL,
  `courses_id` int NOT NULL,
  PRIMARY KEY (`users_id`,`courses_id`),
  KEY `fk_users_has_courses_courses1_idx` (`courses_id`),
  KEY `fk_users_has_courses_users1_idx` (`users_id`),
  CONSTRAINT `fk_users_has_courses_courses1` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_courses_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_courses`
--

LOCK TABLES `users_has_courses` WRITE;
/*!40000 ALTER TABLE `users_has_courses` DISABLE KEYS */;
INSERT INTO `users_has_courses` VALUES (2,1),(10,1),(11,1),(12,1),(2,2),(10,2),(13,2),(14,2),(5,12),(11,12),(14,12),(5,13),(11,13),(2,15),(11,15),(13,15),(14,15),(2,17),(10,17),(11,17),(12,17),(13,17),(7,18),(11,18),(22,18),(7,22),(11,22),(22,22),(2,28),(11,28),(22,28),(2,29),(11,29),(12,29),(9,30),(11,30),(5,31),(7,32);
/*!40000 ALTER TABLE `users_has_courses` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `users_has_courses_BEFORE_DELETE` BEFORE DELETE ON `users_has_courses` FOR EACH ROW BEGIN
DECLARE msg TEXT DEFAULT ' ';
DECLARE course_status TEXT DEFAULT ' ';

SELECT `status` FROM courses WHERE courses.id = old.courses_id INTO course_status;
    if course_status != 'NOT_STARTED' then
		SET msg = concat('Cannot delete user from course due to course status ');
		SIGNAL SQLSTATE '45000' SET mysql_errno=30001, message_text = msg;
	end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'final_project'
--

--
-- Dumping routines for database 'final_project'
--
/*!50003 DROP PROCEDURE IF EXISTS `checking_status` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `checking_status`()
BEGIN
	UPDATE `final_project`.`courses`
    set id = id
		WHERE (`id` > '0');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-20 16:47:21
