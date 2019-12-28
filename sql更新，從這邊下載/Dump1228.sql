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
  `fb_link` varchar(255) DEFAULT '請新增fb連結',
  `password` varchar(45) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `login_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idMembers_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (5,'aaa@cc.ncu.edu.tw','Amber','https://www.facebook.com/ncu.tw','aaa1234567','2019-12-13 13:47:40','2019-12-23 07:38:27',1,'2019-12-28 10:43:13'),(6,'bbb@cc.ncu.edu.tw','Bob','https://www.facebook.com/ncu.tw','bbb1234567','2019-12-13 13:47:53','2019-12-19 16:58:37',1,'2019-12-28 10:20:56'),(7,'ccc@cc.ncu.edu.tw','Cindy','https://www.facebook.com/ncu.tw','ccc1234567','2019-12-13 13:48:02','2019-12-20 06:15:55',1,'2019-12-28 09:46:15'),(8,'ddd@cc.ncu.edu.tw','David','https://www.facebook.com/ncu.tw','ddd1234567','2019-12-16 14:07:52','2019-12-23 06:06:34',1,'2019-12-16 14:07:52'),(9,'eee@cc.ncu.edu.tw','Eve','https://www.facebook.com/ncu.tw','eee1234567','2019-12-18 16:55:39','2019-12-19 14:20:03',0,'2019-12-19 14:20:03'),(13,'fff@cc.ncu.edu.tw','Fiona','https://www.facebook.com/ncu.tw','fff1234567','2019-12-19 15:17:01','2019-12-19 15:17:01',0,'2019-12-19 15:17:01'),(19,'ggg@cc.ncu.edu.tw','Gina','https://www.facebook.com/Gina.Hello/','ggg1234567','2019-12-19 16:00:55','2019-12-19 16:00:55',0,'2019-12-19 16:00:55'),(20,'hhh@cc.ncu.edu.tw','Henry','https://www.facebook.com/henrylaufp','hhh1234567','2019-12-19 16:01:28','2019-12-19 16:58:31',1,'2019-12-19 16:08:04'),(22,'iii@cc.ncu.edu.tw','Ive','https://www.facebook.com/ncu.tw','aaa1234567','2019-12-22 09:06:17','2019-12-22 09:06:17',0,'2019-12-22 09:06:17'),(23,'jjj@cc.ncu.edu.tw','Jack','https://www.facebook.com/imayday555/','aaa1234567','2019-12-23 06:20:04','2019-12-23 06:20:04',0,'2019-12-23 06:20:04');
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
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (27,6,'Bob','bbb@cc.ncu.edu.tw',12,'機車','Anna','aaa@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw',10000,'2019-12-28 10:21:04',0,0);
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
  `seller_name` varchar(45) NOT NULL,
  `seller_email` varchar(100) NOT NULL,
  `seller_fb` varchar(255) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (3,5,'Anna','aaa@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','口紅','美妝',0,100,'不用了','3.jpg',1,0),(4,5,'Anna','aaa@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','腳踏車','交通工具',0,1500,'二手','4.jpg',1,0),(5,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','牛仔褲','服飾',0,300,'尺寸不合','5.jpg',1,0),(6,8,'David','ddd@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','牛奶2罐','食物',0,80,'買太多，喝不完','6.jpg',1,0),(7,7,'Cat','ccc@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','便當*3','贈送',0,0,'吃不完','7.jpg',1,0),(8,7,'Cat','ccc@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','耳機','3C周邊',0,900,'九成新','8.jpg',1,0),(9,8,'David','ddd@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','化妝水','美妝',0,70,'剩半罐','9.jpg',1,0),(10,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','外套','服飾',0,500,'8成新','10.jpg',1,0),(11,8,'David','ddd@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','運動褲','服飾',0,300,'7成新','11.jpg',1,0),(12,5,'Anna','aaa@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','機車','交通工具',0,10000,'二手車販售','12.jpg',1,1),(13,19,'Gina','ggg@cc.ncu.edu.tw','https://www.facebook.com/Gina.Hello/','羽絨外套','服飾',0,800,'9成新','',1,0),(14,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','蛋塔','食物',0,100,'好吃',NULL,0,0),(16,7,'Cindy','ccc@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','筆記本','贈送',1,0,'用不到',NULL,0,0),(17,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','蛋塔','食物',1,20,'好吃',NULL,0,0),(18,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','紅筆10枝','贈送',1,0,'別人送的，用不完',NULL,0,0),(19,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','側背包','其他',0,800,'買新的，故出售',NULL,0,0),(20,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','小桌子','家具',0,100,'用了2年，堪用。自己來搬走',NULL,0,0),(21,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','手機','3C周邊',0,6000,'用半年',NULL,0,0),(22,5,'Amber','aaa@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','遊戲點數','贈送',1,0,'用不到',NULL,0,0),(23,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','鉛筆盒','贈送',1,0,'抽獎抽到',NULL,0,0),(24,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','西裝整套(M)','服飾',0,500,'穿過3次',NULL,0,0),(25,6,'Bob','bbb@cc.ncu.edu.tw','https://www.facebook.com/ncu.tw','牛奶','食物',0,234,'32',NULL,0,0);
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

-- Dump completed on 2019-12-28 18:50:14
