-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: e_center
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `id` int NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhjuy9y4fd8v5m3klig05ktofg` (`username`),
  CONSTRAINT `FKhjuy9y4fd8v5m3klig05ktofg` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES (2,'ROLE_USER','tik');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certifficate`
--

DROP TABLE IF EXISTS `certifficate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certifficate` (
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certifficate`
--

LOCK TABLES `certifficate` WRITE;
/*!40000 ALTER TABLE `certifficate` DISABLE KEYS */;
INSERT INTO `certifficate` VALUES ('A2'),('B1');
/*!40000 ALTER TABLE `certifficate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certificate_sequence`
--

DROP TABLE IF EXISTS `certificate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certificate_sequence` (
  `id` int NOT NULL,
  `examinee_sequence` int DEFAULT NULL,
  `room_sequence` int DEFAULT NULL,
  `certificate_id` varchar(255) DEFAULT NULL,
  `examination_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmyb8lwkxmh0ol5n7uktld22iu` (`certificate_id`),
  KEY `FKjan6fs5wyuxml8tgcfp1w8o1w` (`examination_id`),
  CONSTRAINT `FKjan6fs5wyuxml8tgcfp1w8o1w` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`),
  CONSTRAINT `FKmyb8lwkxmh0ol5n7uktld22iu` FOREIGN KEY (`certificate_id`) REFERENCES `certifficate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate_sequence`
--

LOCK TABLES `certificate_sequence` WRITE;
/*!40000 ALTER TABLE `certificate_sequence` DISABLE KEYS */;
INSERT INTO `certificate_sequence` VALUES (4,20,2,'A2',3),(5,0,0,'B1',3);
/*!40000 ALTER TABLE `certificate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination`
--

DROP TABLE IF EXISTS `examination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination` (
  `id` int NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination`
--

LOCK TABLES `examination` WRITE;
/*!40000 ALTER TABLE `examination` DISABLE KEYS */;
INSERT INTO `examination` VALUES (3,'2021-09-20 10:41:20','2021-09-20 10:41:20','Init Examination','2021-09-20 10:41:20','2021-09-20 10:41:20');
/*!40000 ALTER TABLE `examination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examinee`
--

DROP TABLE IF EXISTS `examinee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examinee` (
  `id` int NOT NULL,
  `certificate_id` varchar(255) DEFAULT NULL,
  `examinee_id` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `identity` varchar(13) DEFAULT NULL,
  `listenning` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `reading` float DEFAULT NULL,
  `speaking` float DEFAULT NULL,
  `writing` float DEFAULT NULL,
  `examination_id` int NOT NULL,
  `manager_id` int DEFAULT NULL,
  `room_id` int DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi52e3hmx1c3gj3ev8uy7puhl3` (`examination_id`),
  KEY `FKiylwiycwxtghy09351k7lcnwp` (`manager_id`),
  KEY `FK9qwgapy8722u9hix21gptx8i2` (`room_id`),
  KEY `FKktoyhybga3knapps1adw1v1gp` (`user_id`),
  CONSTRAINT `FK9qwgapy8722u9hix21gptx8i2` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `FKi52e3hmx1c3gj3ev8uy7puhl3` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`),
  CONSTRAINT `FKiylwiycwxtghy09351k7lcnwp` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FKktoyhybga3knapps1adw1v1gp` FOREIGN KEY (`user_id`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examinee`
--

LOCK TABLES `examinee` WRITE;
/*!40000 ALTER TABLE `examinee` DISABLE KEYS */;
INSERT INTO `examinee` VALUES (8,'A2','A2003','F','95701972181',0,'FWAwEKLHgByyNUhEJvHm','8340967485',0,0,0,3,NULL,6,NULL),(9,'A2',NULL,'M','742805835',0,'ROSwKzHLPptTVHaryMTB','0409990456',0,0,0,3,NULL,NULL,NULL),(10,'A2','A2019','M','5623267403',0,'QuunnbgxYZsvooeXCgcP','2744494066',0,0,0,3,NULL,7,NULL),(11,'A2','A2013','F','95221624089',0,'NsbYLwlRmpGYzzrxHQlL','4775114167',0,0,0,3,NULL,7,NULL),(12,'A2','A2015','M','9788382513',0,'nXZVJSAgMuKwAZiYrytQ','1659076800',0,0,0,3,NULL,7,NULL),(13,'A2',NULL,'M','41803183232',0,'ZXuCKSyFcxNZbxtQYVjd','4001976951',0,0,0,3,NULL,NULL,NULL),(14,'A2','A2008','F','203031505',0,'MqCeujHOflFEoxEoLqwn','7507508021',0,0,0,3,NULL,6,NULL),(15,'A2','A2000','M','6160403167',5,'CRyHiZpOuYguqWvvWZdt','6655221609',2,4,6,3,NULL,6,NULL),(16,'A2',NULL,'M','964143225',0,'YEzRblDweCHTsdUWRHKs','2319586136',0,0,0,3,NULL,NULL,NULL),(17,'A2','A2006','F','0222130623',0,'lURhZocDCKlvrufTXSAs','4561709845',0,0,0,3,NULL,6,NULL),(18,'A2',NULL,'M','42481177231',0,'rDQTNooRgmzneNHTKTul','0693603276',0,0,0,3,NULL,NULL,NULL),(19,'A2','A2010','M','875334984',0,'ncOLpidekfwhrAmhBRYg','1117229206',0,0,0,3,NULL,7,NULL),(20,'A2',NULL,'F','82991897871',0,'yzbEZyqIoSIQHYdqRjEM','2340449289',0,0,0,3,NULL,NULL,NULL),(21,'A2','A2012','M','44626188712',0,'nodshHznpqgQELXNvQEI','6353432689',0,0,0,3,NULL,7,NULL),(22,'A2','A2016','M','6120300179',0,'OfohFgGBpWjToJxGQIvL','3363630733',0,0,0,3,NULL,7,NULL),(23,'A2',NULL,'F','621720325',0,'RGDCGNgFFuoWplWNsSIF','8053406107',0,0,0,3,NULL,NULL,NULL),(24,'A2',NULL,'M','6555341806',0,'WPjhDBlenenVXKMhAMaV','5565994107',0,0,0,3,NULL,NULL,NULL),(25,'A2','A2005','M','35905510694',0,'JrzruFfJhsCmlpphYKni','4898400524',0,0,0,3,NULL,6,NULL),(26,'A2','A2004','F','25815364047',0,'GHMjNSaBFiRQbVQELYDT','0345845513',0,0,0,3,NULL,6,NULL),(27,'A2','A2009','M','52095178509',0,'mxgSjMsCwRVAJosxqCJS','6787412135',0,0,0,3,NULL,6,NULL),(28,'A2',NULL,'M','965251918',0,'wOoVYWDQVRBhTlGgoNlO','6003922166',0,0,0,3,NULL,NULL,NULL),(29,'A2','A2001','F','658571916',0,'cteTYrQQBdXUQDfpnzTu','3887898281',0,0,0,3,NULL,6,NULL),(30,'A2','A2002','M','1010841550',0,'FVdfizgFxcragAycPDjt','8704195358',0,0,0,3,NULL,6,NULL),(31,'A2','A2018','M','1026409045',0,'qDYNNAtWUZWoQyUeLZPP','0812253419',0,0,0,3,NULL,7,NULL),(32,'A2','A2007','F','2979578995',0,'lVDcgRAgYIwixjYJEtVE','0392252837',0,0,0,3,NULL,6,NULL),(33,'A2','A2017','M','4731199095',0,'OtxuwsvwVzWGHMVxXnXq','6803124282',0,0,0,3,NULL,7,NULL),(34,'A2','A2011','M','22395558231',0,'nHobJJQZgTVcmpaFsrkk','3230881847',0,0,0,3,NULL,7,NULL),(35,'A2','A2014','F','0097647327',0,'NWgfTJtwbQXkdINTeRkH','1975727274',0,0,0,3,NULL,7,NULL),(36,'A2',NULL,'M','45512464173',0,'ZuvWZZWCuRquNsocmlpY','8802504357',0,0,0,3,NULL,NULL,NULL),(37,'A2',NULL,'M','9876223787',0,'UiSLODkmUoguAGredLfm','7145607994',0,0,0,3,NULL,NULL,NULL);
/*!40000 ALTER TABLE `examinee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (38);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `id` int NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'M','281231','123','0858267296','manager');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` int NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `certificate_id` varchar(255) DEFAULT NULL,
  `examination_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk4mnaq0eak2davodx98bcur1c` (`certificate_id`),
  KEY `FKitiv6n4d86gw37h0rj4dp1sc0` (`examination_id`),
  CONSTRAINT `FKitiv6n4d86gw37h0rj4dp1sc0` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`),
  CONSTRAINT `FKk4mnaq0eak2davodx98bcur1c` FOREIGN KEY (`certificate_id`) REFERENCES `certifficate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (6,NULL,'A2P001',NULL,'A2',3),(7,NULL,'A2P002',NULL,'A2',3);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('tik',_binary '',NULL,NULL,'password',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-20 16:06:50
