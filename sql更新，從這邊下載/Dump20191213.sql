-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sa_project
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `administrators`
--

DROP TABLE IF EXISTS `administrators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `assign_date` datetime NOT NULL,
  `permission` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkmember_id_tbl_administrator_idx` (`member_id`),
  CONSTRAINT `fkmember_id_tbl_administrator` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrators`
--

LOCK TABLES `administrators` WRITE;
/*!40000 ALTER TABLE `administrators` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL DEFAULT 'no',
  `name` varchar(45) NOT NULL DEFAULT 'no',
  `fb_link` varchar(255) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `login_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idMembers_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (5,'aaa@cc.ncu.edu.tw','Anna','https://www.facebook.com/ncu.tw','aaa1234567','2019-12-13 13:47:40','2019-12-13 13:47:40',0,'2019-12-13 13:47:40'),(6,'bbb@cc.ncu.edu.tw','Bob','https://www.facebook.com/ncu.tw','bbb1234567','2019-12-13 13:47:53','2019-12-13 13:47:53',0,'2019-12-13 13:47:53'),(7,'ccc@cc.ncu.edu.tw','Cat','https://www.facebook.com/ncu.tw','ccc1234567','2019-12-13 13:48:02','2019-12-13 13:48:02',0,'2019-12-13 13:48:02');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buyer_id` int(11) NOT NULL,
  `buyer_name` varchar(45) NOT NULL,
  `buyer_email` varchar(100) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `seller_name` varchar(45) NOT NULL,
  `seller_email` varchar(100) NOT NULL,
  `seller_fb` varchar(255) DEFAULT NULL,
  `total` float NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '已完成未完成',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fkmember_id_tbl_orders_idx` (`buyer_id`),
  KEY `fkproduct_id_tbl_orders_idx` (`product_id`),
  CONSTRAINT `fkmember_id_tbl_orders` FOREIGN KEY (`buyer_id`) REFERENCES `members` (`id`),
  CONSTRAINT `fkproduct_id_tbl_orders` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL DEFAULT '0',
  `seller_name` varchar(45) NOT NULL DEFAULT 'null',
  `seller_email` varchar(100) NOT NULL DEFAULT 'null',
  `seller_fb` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `classification` varchar(45) NOT NULL,
  `product_status` tinyint(4) NOT NULL DEFAULT '0',
  `price` float NOT NULL,
  `product_overview` text,
  `image` text,
  `verification_status` tinyint(4) NOT NULL DEFAULT '0',
  `on_shelf` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fkmember_id_tblProducts_idx` (`member_id`),
  CONSTRAINT `fkmember_id_tblProducts` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (3,5,'Anna','aaa@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','口紅','美妝',0,100,'不用了','https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiFst3X9rHmAhUvy4sBHbUICQYQjRx6BAgBEAQ&url=https%3A%2F%2Fshopee.tw%2F%25E5%258F%25A3%25E7%25B4%2585-%25E9%259C%25A7%25E9%259D%25A2%25E5%2594%2587%25E8%2586%258F-%25E9%259C%25A7%25E9%259D%25A2%25E7%25B5%25B2%25E7%25B5%25A8%25E5%258F%25A3%25E7%25B4%2585-%25E5%2595%259E%25E5%2585%2589%25E5%2594%2587%25E8%2586%258F-%25E5%2595%259E%25E5%2585%2589%25E5%258F%25A3%25E7%25B4%2585-MAYCREATE-i.41902166.742086696&psig=AOvVaw13rIoLtOvXy7-g8QyCrOy-&ust=1576302534426910',0,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sa_project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-13 14:26:17
