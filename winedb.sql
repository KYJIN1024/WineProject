-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: winedb
-- ------------------------------------------------------
-- Server version	10.11.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Sequence structure for `hibernate_sequence`
--

DROP SEQUENCE IF EXISTS `hibernate_sequence`;
CREATE SEQUENCE `hibernate_sequence` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 1 cache 1000 nocycle ENGINE=InnoDB;
SELECT SETVAL(`hibernate_sequence`, 1001, 0);

--
-- Table structure for table `confirmation_token`
--

DROP TABLE IF EXISTS `confirmation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `confirmation_token` (
  `token_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `confirmation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirmationtoken`
--

DROP TABLE IF EXISTS `confirmationtoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `confirmationtoken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `confirmationToken` varchar(255) NOT NULL,
  `createdDate` datetime NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `confirmationtoken_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmationtoken`
--

LOCK TABLES `confirmationtoken` WRITE;
/*!40000 ALTER TABLE `confirmationtoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `confirmationtoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventboard`
--

DROP TABLE IF EXISTS `eventboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventboard` (
  `evboardid` int(11) NOT NULL AUTO_INCREMENT,
  `evboardwriter` varchar(20) NOT NULL,
  `evboardhits` int(11) DEFAULT NULL,
  `evboardcreatetime` varchar(100) NOT NULL,
  `evboardupdatetime` varchar(100) DEFAULT NULL,
  `evboardevname` varchar(200) NOT NULL,
  `evboardevplace` varchar(150) NOT NULL,
  `evboardevfee` varchar(30) NOT NULL,
  `evboardevperiod` varchar(100) NOT NULL,
  `evboardevopentime` varchar(100) NOT NULL,
  `evboardevcontent` varchar(500) NOT NULL,
  `filename` varchar(500) DEFAULT NULL,
  `filepath` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`evboardid`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventboard`
--

LOCK TABLES `eventboard` WRITE;
/*!40000 ALTER TABLE `eventboard` DISABLE KEYS */;
INSERT INTO `eventboard` VALUES
(1,'testuser',0,'23-07-12 11:36',NULL,'133123','123123','132123','123123','123123','123123','3c981796-c7a4-463e-b4d3-68318cdddabatestimg01.PNG','/files/3c981796-c7a4-463e-b4d3-68318cdddabatestimg01.PNG'),
(2,'testuser',0,'23-07-12 11:37',NULL,'2023 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','84f8c006-97f7-4614-a216-e704b640c92etestimg01.PNG','/files/84f8c006-97f7-4614-a216-e704b640c92etestimg01.PNG'),
(3,'testuser',0,'23-07-12 11:37',NULL,'2024 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','28ede7c0-2ba4-42bb-a5ab-87bfad9d9b38testimg01.PNG','/files/28ede7c0-2ba4-42bb-a5ab-87bfad9d9b38testimg01.PNG'),
(4,'testuser',0,'23-07-12 11:37',NULL,'2025 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','6afdd78f-0436-4ae0-a00e-87422ff61f0dtestimg01.PNG','/files/6afdd78f-0436-4ae0-a00e-87422ff61f0dtestimg01.PNG'),
(5,'testuser',0,'23-07-12 11:37',NULL,'2026 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','e99314dc-546d-4d18-91d6-33e804d25aeftestimg01.PNG','/files/e99314dc-546d-4d18-91d6-33e804d25aeftestimg01.PNG'),
(6,'testuser',0,'23-07-12 11:37',NULL,'2027 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','9ad56a16-07f9-4d21-b4d0-c8e4614621eftestimg01.PNG','/files/9ad56a16-07f9-4d21-b4d0-c8e4614621eftestimg01.PNG'),
(7,'testuser',0,'23-07-12 11:38',NULL,'2028 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','02c12f88-0b55-4c55-baa5-d0fd50bfa28ctestimg01.PNG','/files/02c12f88-0b55-4c55-baa5-d0fd50bfa28ctestimg01.PNG'),
(8,'testuser',0,'23-07-12 11:38',NULL,'2029 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','d70f533c-0d7a-41f4-a8b9-157a960a0378testimg01.PNG','/files/d70f533c-0d7a-41f4-a8b9-157a960a0378testimg01.PNG'),
(9,'testuser',0,'23-07-12 11:40',NULL,'2030 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','58f67d56-f287-4d48-9bf9-29cd7ddd2945testimg01.PNG','/files/58f67d56-f287-4d48-9bf9-29cd7ddd2945testimg01.PNG'),
(10,'testuser',0,'23-07-12 11:40',NULL,'2031 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','f8272ff1-1a40-4222-9769-f3a4c849f6f3testimg01.PNG','/files/f8272ff1-1a40-4222-9769-f3a4c849f6f3testimg01.PNG'),
(11,'testuser',0,'23-07-12 11:40',NULL,'2032 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','7508cb3a-bbfe-46b7-89f3-af39c4bdededtestimg01.PNG','/files/7508cb3a-bbfe-46b7-89f3-af39c4bdededtestimg01.PNG'),
(12,'testuser',0,'23-07-12 11:40',NULL,'2033 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','0a4433e8-bfca-4a3e-897b-2613c05e7413testimg01.PNG','/files/0a4433e8-bfca-4a3e-897b-2613c05e7413testimg01.PNG'),
(13,'testuser',0,'23-07-12 11:41',NULL,'2034 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','898f08ae-9ea1-4ff0-accd-f56746b3db2ctestimg01.PNG','/files/898f08ae-9ea1-4ff0-accd-f56746b3db2ctestimg01.PNG'),
(14,'testuser',0,'23-07-12 11:41',NULL,'2035 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','716a6549-17b2-4c77-bb2d-eff540502fb5testimg01.PNG','/files/716a6549-17b2-4c77-bb2d-eff540502fb5testimg01.PNG'),
(15,'testuser',0,'23-07-12 11:41',NULL,'2036 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','f4f9a0cf-8bb1-416c-8831-8a82a606b4e5testimg01.PNG','/files/f4f9a0cf-8bb1-416c-8831-8a82a606b4e5testimg01.PNG'),
(16,'testuser',0,'23-07-12 11:41',NULL,'2037 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','fccd0086-7a8f-4d6c-8827-28effcc5aabctestimg01.PNG','/files/fccd0086-7a8f-4d6c-8827-28effcc5aabctestimg01.PNG'),
(17,'testuser',0,'23-07-12 11:41',NULL,'2038 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','0f0e4e57-c131-45fc-9e82-c51f463b8a8etestimg01.PNG','/files/0f0e4e57-c131-45fc-9e82-c51f463b8a8etestimg01.PNG'),
(18,'testuser',0,'23-07-12 11:41',NULL,'2039 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','d47e79b0-c746-4a7b-a734-2d48e0e5a1cetestimg01.PNG','/files/d47e79b0-c746-4a7b-a734-2d48e0e5a1cetestimg01.PNG'),
(19,'testuser',0,'23-07-12 11:41',NULL,'2040 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','64a51b50-3da8-4a11-ba53-c4df3a62a91btestimg01.PNG','/files/64a51b50-3da8-4a11-ba53-c4df3a62a91btestimg01.PNG'),
(20,'testuser',0,'23-07-12 11:41',NULL,'2041 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','62525087-ce4b-4e0f-a0ef-c77c801f9440testimg01.PNG','/files/62525087-ce4b-4e0f-a0ef-c77c801f9440testimg01.PNG'),
(21,'testuser',0,'23-07-12 11:41',NULL,'2042 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','d1b00bd5-e2d2-4705-8d8a-0d6cf946b8e5testimg01.PNG','/files/d1b00bd5-e2d2-4705-8d8a-0d6cf946b8e5testimg01.PNG'),
(22,'testuser',0,'23-07-12 11:41',NULL,'2043 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','263bc69d-ed49-47ac-a6c4-c6ca0e356c40testimg01.PNG','/files/263bc69d-ed49-47ac-a6c4-c6ca0e356c40testimg01.PNG'),
(23,'testuser',0,'23-07-12 11:41',NULL,'2044 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','e831df9a-8870-4937-9d55-d35a6aa67997testimg01.PNG','/files/e831df9a-8870-4937-9d55-d35a6aa67997testimg01.PNG'),
(24,'testuser',0,'23-07-12 11:41',NULL,'2045 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','cdfd618b-1d5a-43b6-bb0b-97f07bc69a04testimg01.PNG','/files/cdfd618b-1d5a-43b6-bb0b-97f07bc69a04testimg01.PNG'),
(25,'testuser',0,'23-07-12 11:41',NULL,'2046 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','90833d0b-d830-47cf-a5d1-3307e5624652testimg01.PNG','/files/90833d0b-d830-47cf-a5d1-3307e5624652testimg01.PNG'),
(26,'testuser',0,'23-07-12 11:41',NULL,'2047 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','21e491cc-c02b-4401-b62a-dd23595b66c4testimg01.PNG','/files/21e491cc-c02b-4401-b62a-dd23595b66c4testimg01.PNG'),
(27,'testuser',0,'23-07-12 11:41',NULL,'2048 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','16b0c99b-319d-4c6a-9ea1-28663a35c697testimg01.PNG','/files/16b0c99b-319d-4c6a-9ea1-28663a35c697testimg01.PNG'),
(28,'testuser',0,'23-07-12 11:42',NULL,'2049 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','cf80b7fe-32b2-46bc-bdcc-90db043a2673testimg01.PNG','/files/cf80b7fe-32b2-46bc-bdcc-90db043a2673testimg01.PNG'),
(29,'testuser',0,'23-07-12 11:42',NULL,'2050 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','0a515555-44fa-4e0e-91d0-d607007adec0testimg01.PNG','/files/0a515555-44fa-4e0e-91d0-d607007adec0testimg01.PNG'),
(30,'testuser',0,'23-07-12 11:42',NULL,'2051 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','8a6c22bd-cde5-4c7b-b17c-cdc0c14328b3testimg01.PNG','/files/8a6c22bd-cde5-4c7b-b17c-cdc0c14328b3testimg01.PNG'),
(31,'testuser',0,'23-07-12 11:42',NULL,'2052 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','a8ff2cd1-ca07-40a8-8bba-f0f7f35bafectestimg01.PNG','/files/a8ff2cd1-ca07-40a8-8bba-f0f7f35bafectestimg01.PNG'),
(32,'testuser',0,'23-07-12 11:42',NULL,'2053 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','948d5675-6fe0-42b4-9d4f-1499bc1246cdtestimg01.PNG','/files/948d5675-6fe0-42b4-9d4f-1499bc1246cdtestimg01.PNG'),
(33,'testuser',0,'23-07-12 11:42',NULL,'2054 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','034c9d78-b197-4433-994c-da2f12d88b3btestimg01.PNG','/files/034c9d78-b197-4433-994c-da2f12d88b3btestimg01.PNG'),
(34,'testuser',0,'23-07-12 11:42',NULL,'2055 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','0801fe56-2d34-4ac3-bfaa-473a63155b00testimg01.PNG','/files/0801fe56-2d34-4ac3-bfaa-473a63155b00testimg01.PNG'),
(35,'testuser',0,'23-07-12 11:42',NULL,'2056 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','2e91fba0-e932-4062-bbf7-2ab2d9e72e2ftestimg01.PNG','/files/2e91fba0-e932-4062-bbf7-2ab2d9e72e2ftestimg01.PNG'),
(36,'testuser',0,'23-07-12 11:42',NULL,'2057 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','a01d3519-35b8-470d-b648-364f3f4b430dtestimg01.PNG','/files/a01d3519-35b8-470d-b648-364f3f4b430dtestimg01.PNG'),
(37,'testuser',0,'23-07-12 11:42',NULL,'2058 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','9e76590e-8de8-4e56-bd7f-ea534e2a5140testimg01.PNG','/files/9e76590e-8de8-4e56-bd7f-ea534e2a5140testimg01.PNG'),
(38,'testuser',0,'23-07-12 11:42',NULL,'2059 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','e44b2ecd-b8cd-474f-bc24-d8d2891cd07ftestimg01.PNG','/files/e44b2ecd-b8cd-474f-bc24-d8d2891cd07ftestimg01.PNG'),
(39,'testuser',0,'23-07-12 11:42',NULL,'2060 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','1d71e385-391a-43ba-b42a-7a894c61e5bctestimg01.PNG','/files/1d71e385-391a-43ba-b42a-7a894c61e5bctestimg01.PNG'),
(40,'testuser',0,'23-07-12 11:42',NULL,'2061 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','ef751787-0e64-40e0-bb49-3097c561ed81testimg01.PNG','/files/ef751787-0e64-40e0-bb49-3097c561ed81testimg01.PNG'),
(41,'testuser',0,'23-07-12 11:42',NULL,'2062 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','e73d957e-0f7b-4f73-a370-922b6e5d60d9testimg01.PNG','/files/e73d957e-0f7b-4f73-a370-922b6e5d60d9testimg01.PNG'),
(42,'testuser',0,'23-07-12 11:42',NULL,'2063 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','98646b3a-9c01-4f91-aa3a-d5f2c5e0ed68testimg01.PNG','/files/98646b3a-9c01-4f91-aa3a-d5f2c5e0ed68testimg01.PNG'),
(43,'testuser',0,'23-07-12 11:42',NULL,'2064 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','7e634732-5345-4ffe-8a28-7aec290d02e5testimg01.PNG','/files/7e634732-5345-4ffe-8a28-7aec290d02e5testimg01.PNG'),
(44,'testuser',0,'23-07-12 11:43',NULL,'2065 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','3ea84ef8-1aa8-4747-99c9-972db853eb32testimg01.PNG','/files/3ea84ef8-1aa8-4747-99c9-972db853eb32testimg01.PNG'),
(45,'testuser',0,'23-07-12 11:43',NULL,'2066 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','882f3c3a-441d-48c8-8f56-0e327dcac815testimg01.PNG','/files/882f3c3a-441d-48c8-8f56-0e327dcac815testimg01.PNG'),
(46,'testuser',0,'23-07-12 11:43',NULL,'2067 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','aea80d4e-e998-4c9c-a6cd-f2c709bdbdcftestimg01.PNG','/files/aea80d4e-e998-4c9c-a6cd-f2c709bdbdcftestimg01.PNG'),
(47,'testuser',4,'23-07-12 11:43','수정되지 않은 글','2068 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','99ca788a-4c5d-4e02-ab31-10230aeaeae3testimg01.PNG','/files/99ca788a-4c5d-4e02-ab31-10230aeaeae3testimg01.PNG'),
(48,'testuser',0,'23-07-12 11:43',NULL,'2069 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','5f35d1ef-7eda-46d6-b5be-8798904b20c4testimg01.PNG','/files/5f35d1ef-7eda-46d6-b5be-8798904b20c4testimg01.PNG'),
(49,'testuser',2,'23-07-12 11:43','수정되지 않은 글','2070 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','6283cc56-2b47-4b81-aaf9-d667f9503ba5testimg01.PNG','/files/6283cc56-2b47-4b81-aaf9-d667f9503ba5testimg01.PNG'),
(50,'testuser',1,'23-07-12 11:43','수정되지 않은 글','2071 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','06bbb6a3-2243-4d28-82a3-564acb7e8b10testimg01.PNG','/files/06bbb6a3-2243-4d28-82a3-564acb7e8b10testimg01.PNG'),
(51,'testuser',3,'23-07-12 11:43','수정되지 않은 글','2072 부산국제주류 & 와인박람회',' 부산 벡스코 제1전시장 2홀','무료',' 2023년 12월 1일(금) ~ 3일(일)/ 3일간',' 11시 ~ 19시','2023 부산국제주류 & 와인박람회\r\nBusan International Wines & Sprits Expo 2023','ec4a94c0-f255-4d78-aa35-1b58e748df43testimg01.PNG','/files/ec4a94c0-f255-4d78-aa35-1b58e748df43testimg01.PNG');
/*!40000 ALTER TABLE `eventboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freeboard`
--

DROP TABLE IF EXISTS `freeboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `freeboard` (
  `frboardid` int(11) NOT NULL AUTO_INCREMENT,
  `frboardwriter` varchar(20) NOT NULL,
  `frboardtitle` varchar(100) NOT NULL,
  `frboardcontent` varchar(1000) NOT NULL,
  `frboardhits` int(11) DEFAULT NULL,
  `frboardcreatetime` varchar(100) NOT NULL,
  `frboardupdatetime` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`frboardid`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freeboard`
--

LOCK TABLES `freeboard` WRITE;
/*!40000 ALTER TABLE `freeboard` DISABLE KEYS */;
INSERT INTO `freeboard` VALUES
(1,'testuser','가나다라','가나다라마바사아찾차ㅏ',NULL,'2023-07-12T05:57:21.579194',NULL),
(2,'testuser','테스트 게시글 1','내용 1',NULL,'2023-07-12T06:02:00.213218200',NULL),
(3,'testuser','테스트 게시글 2','내용 2',1,'2023-07-12T06:02:06.431243500','수정되지 않은 글'),
(4,'testuser','테스트 게시글 3','내용 3',NULL,'2023-07-12T06:02:12.270645400',NULL),
(5,'testuser','테스트 게시글 4','내용 4',1,'2023-07-12T06:02:18.289690500','수정되지 않은 글'),
(6,'testuser','테스트 게시글 5','내용 5',NULL,'2023-07-12T06:02:28.572833',NULL),
(7,'testuser','테스트 게시글 6','내용 6',NULL,'2023-07-12T06:02:35.954585100',NULL),
(8,'testuser','테스트 게시글 7','내용 7',NULL,'2023-07-12T06:02:43.055617200',NULL),
(9,'testuser','테스트 게시글 8','내용 8',NULL,'2023-07-12T06:02:48.115304700',NULL),
(10,'testuser','테스트 게시글 9','내용 9',1,'2023-07-12T06:02:53.796555500','수정되지 않은 글'),
(11,'testuser','테스트 게시글 10','내용 10',0,'2023-07-12T06:03:00.141876400','수정되지 않은 글'),
(12,'testuser','테스트 게시글 11','내용 11',19,'2023-07-12T06:03:08.389138800','수정되지 않은 글'),
(13,'testuser','테스트 게시글 12','내용 12',NULL,'2023-07-12T06:03:17.386547800',NULL),
(14,'testuser','테스트 게시글 13','내용 13',NULL,'2023-07-12T06:03:22.684864200',NULL),
(15,'testuser','테스트 게시글 14','내용 14',NULL,'2023-07-12T06:03:28.010593500',NULL),
(16,'testuser','테스트 게시글 15','내용 15',NULL,'2023-07-12T06:03:32.383509400',NULL),
(17,'testuser','테스트 게시글 16','내용 16',NULL,'2023-07-12T06:03:37.538092100',NULL),
(18,'testuser','테스트 게시글 17','내용 17',NULL,'2023-07-12T06:03:43.442371900',NULL),
(19,'testuser','테스트 게시글 18','내용 18',NULL,'2023-07-12T06:03:47.973659400',NULL),
(20,'testuser','테스트 게시글 19','내용 19',NULL,'2023-07-12T06:03:52.085272400',NULL),
(21,'testuser','테스트 게시글 20','내용 20',NULL,'2023-07-12T06:03:56.477249',NULL),
(22,'testuser','테스트 게시글 21','내용 21',NULL,'2023-07-12T06:04:00.497762600',NULL),
(23,'testuser','테스트 게시글 22','내용 22',NULL,'2023-07-12T06:04:04.723695700',NULL),
(24,'testuser','테스트 게시글 23','내용 23',NULL,'2023-07-12T06:04:08.626085',NULL),
(25,'testuser','테스트 게시글 24','내용 24',NULL,'2023-07-12T06:04:13.136309300',NULL),
(26,'testuser','테스트 게시글 25','내용 25',NULL,'2023-07-12T06:04:17.549690',NULL),
(27,'testuser','테스트 게시글 26','내용 26',NULL,'2023-07-12T06:04:22.021698700',NULL),
(28,'testuser','테스트 게시글 27','내용 27',0,'2023-07-12T06:04:27.779220500','수정되지 않은 글'),
(29,'testuser','테스트 게시글 28','내용 28',NULL,'2023-07-12T06:04:38.820436400',NULL),
(30,'testuser','테스트 게시글 29','내용 29',NULL,'2023-07-12T06:04:43.739769200',NULL),
(31,'testuser','테스트 게시글 30','내용 30',3,'2023-07-12T06:04:47.688552100','수정되지 않은 글'),
(32,'testuser','테스트 게시글 31','내용 31',NULL,'2023-07-12T06:04:51.638116100',NULL),
(33,'testuser','테스트 게시글 32','내용 32',NULL,'2023-07-12T06:04:55.850117700',NULL),
(34,'testuser','테스트 게시글 33','내용 33',NULL,'2023-07-12T06:04:59.919404200',NULL),
(35,'testuser','테스트 게시글 34','내용 34',NULL,'2023-07-12T06:05:03.834517',NULL),
(36,'testuser','테스트 게시글 35','내용 35',NULL,'2023-07-12T06:05:07.404305200',NULL),
(37,'testuser','테스트 게시글 36','내용 36',NULL,'2023-07-12T06:05:11.021607100',NULL),
(38,'testuser','테스트 게시글 37','내용 37',NULL,'2023-07-12T06:05:14.381466200',NULL),
(39,'testuser','테스트 게시글 38','내용 38',NULL,'2023-07-12T06:05:23.425753100',NULL),
(40,'testuser','테스트 게시글 39','내용 39',NULL,'2023-07-12T06:05:29.234957',NULL),
(41,'testuser','테스트 게시글 40','내용 40',NULL,'2023-07-12T06:05:33.505530800',NULL),
(42,'testuser','수정된 테스트글41','글 내용이 수정되었습니다. 과연 어떻게 나올까요??? 내용 41',0,'2023-07-12T06:05:37.416232100','23-07-12 07:57'),
(44,'testuser','테스트 게시글 43','내용 43',NULL,'2023-07-12T06:05:44.543327900',NULL),
(45,'testuser','테스트 게시글 44','내용 44',NULL,'2023-07-12T06:05:49.691328',NULL),
(46,'testuser','테스트 게시글 45','내용 45',NULL,'2023-07-12T06:05:53.839815200',NULL),
(47,'testuser','테스트 게시글 46','내용 46',NULL,'2023-07-12T06:05:57.241501500',NULL),
(48,'testuser','테스트 게시글 47','내용 47',NULL,'2023-07-12T06:06:00.994253300',NULL),
(49,'testuser','테스트 게시글 48','내용 48',NULL,'2023-07-12T06:06:04.746390400',NULL),
(50,'testuser','테스트 게시글 49','내용 49',NULL,'2023-07-12T06:06:08.381352500',NULL),
(51,'testuser','테스트 게시글 50','내용 50',NULL,'2023-07-12T06:06:12.123485800',NULL),
(52,'testuser','테스트 게시글 51','내용 51',NULL,'2023-07-12T06:06:15.178000500',NULL),
(53,'testuser','테스트 게시글 52','내용 52',NULL,'2023-07-12T06:06:18.264551400',NULL),
(54,'testuser','테스트 게시글 53','내용 53',NULL,'2023-07-12T06:06:21.542292800',NULL),
(55,'testuser','테스트 게시글 54','내용 54',NULL,'2023-07-12T06:06:24.651717600',NULL),
(56,'testuser','테스트 게시글 55','내용 55',NULL,'2023-07-12T06:06:27.788461500',NULL),
(57,'testuser','테스트 게시글 56','내용 56',NULL,'2023-07-12T06:06:31.081748300',NULL),
(58,'testuser','테스트 게시글 57','내용 57',NULL,'2023-07-12T06:06:34.706002900',NULL),
(59,'testuser','테스트 게시글 58','내용 58',NULL,'2023-07-12T06:06:37.812177800',NULL),
(60,'testuser','테스트 게시글 59','내용 59',NULL,'2023-07-12T06:06:46.011820200',NULL),
(61,'testuser','테스트 게시글 60','내용 60',NULL,'2023-07-12T06:06:52.366686500',NULL),
(62,'testuser','테스트 게시글 61','내용 61',NULL,'2023-07-12T06:06:56.151786600',NULL),
(63,'testuser','테스트 게시글 62','내용 62',NULL,'2023-07-12T06:07:00.412177',NULL),
(64,'testuser','테스트 게시글 63','내용 63',NULL,'2023-07-12T06:07:04.677530200',NULL),
(65,'testuser','테스트 게시글 64','내용 64',NULL,'2023-07-12T06:07:07.890781200',NULL),
(66,'testuser','테스트 게시글 65','내용 65',NULL,'2023-07-12T06:07:11.130303400',NULL),
(67,'testuser','테스트 게시글 66','내용 66',NULL,'2023-07-12T06:07:14.664521100',NULL),
(68,'testuser','테스트 게시글 67','내용 67',NULL,'2023-07-12T06:07:17.887449400',NULL),
(69,'testuser','테스트 게시글 68','내용 68',NULL,'2023-07-12T06:07:21.101329800',NULL),
(70,'testuser','테스트 게시글 69','내용 69',NULL,'2023-07-12T06:07:25.467688400',NULL),
(71,'testuser','테스트 게시글 70','내용 70',NULL,'2023-07-12T06:07:29.720184',NULL),
(72,'testuser','테스트 게시글 71','내용 71',NULL,'2023-07-12T06:07:33.127748100',NULL),
(73,'testuser','테스트 게시글 72','내용 72',NULL,'2023-07-12T06:07:36.811118100',NULL),
(74,'testuser','테스트 게시글 73','내용 73',NULL,'2023-07-12T06:07:40.071913600',NULL),
(75,'testuser','테스트 게시글 74','내용 74',NULL,'2023-07-12T06:07:43.165198100',NULL),
(76,'testuser','테스트 게시글 75','내용 75',NULL,'2023-07-12T06:07:46.401101400',NULL),
(77,'testuser','테스트 게시글 76','내용 76',NULL,'2023-07-12T06:07:49.877136',NULL),
(78,'testuser','테스트 게시글 77','내용 77',NULL,'2023-07-12T06:07:53.090388500',NULL),
(79,'testuser','테스트 게시글 78','내용 78',NULL,'2023-07-12T06:07:56.358677300',NULL),
(80,'testuser','테스트 게시글 79','내용 79',NULL,'2023-07-12T06:07:59.687734500',NULL),
(81,'testuser','테스트 게시글 80','내용 80',NULL,'2023-07-12T06:08:04.233666400',NULL),
(82,'testuser','테스트 게시글 81','내용 81',NULL,'2023-07-12T06:08:07.725037',NULL),
(83,'testuser','테스트 게시글 82','내용 82',NULL,'2023-07-12T06:08:10.621563600',NULL),
(84,'testuser','테스트 게시글 83','내용 83',NULL,'2023-07-12T06:08:13.543582',NULL),
(85,'testuser','테스트 게시글 84','내용 84',NULL,'2023-07-12T06:08:16.392130300',NULL),
(86,'testuser','테스트 게시글 85','내용 85',NULL,'2023-07-12T06:08:19.428643900',NULL),
(87,'testuser','테스트 게시글 86','내용 86',NULL,'2023-07-12T06:08:22.249579900',NULL),
(88,'','테스트 게시글 87','내용 87',NULL,'2023-07-12T06:08:27.356294200',NULL),
(89,'','테스트 게시글 88','내용 88',NULL,'2023-07-12T06:08:36.912995900',NULL),
(90,'','테스트 게시글 89','내용 89',NULL,'2023-07-12T06:08:41.394605700',NULL),
(91,'','테스트 게시글 90','내용 90',NULL,'2023-07-12T06:08:45.657000400',NULL),
(92,'','테스트 게시글 91','내용 91',NULL,'2023-07-12T06:08:49.285529100',NULL),
(93,'','테스트 게시글 92','내용 92',NULL,'2023-07-12T06:08:52.528993',NULL),
(94,'','테스트 게시글 93','내용 93',NULL,'2023-07-12T06:08:55.877757900',NULL),
(95,'','테스트 게시글 94','내용 94',NULL,'2023-07-12T06:08:58.968522200',NULL),
(96,'','테스트 게시글 95','내용 95',NULL,'2023-07-12T06:09:02.310640100',NULL),
(97,'','테스트 게시글 96','내용 96',NULL,'2023-07-12T06:09:05.691878300',NULL),
(98,'','테스트 게시글 97','내용 97',NULL,'2023-07-12T06:09:09.396721100',NULL),
(99,'','테스트 게시글 98','내용 98',NULL,'2023-07-12T06:09:12.856869500',NULL),
(100,'','테스트 게시글 99','내용 99',NULL,'2023-07-12T06:09:16.540451',NULL),
(101,'testuser','테스트 게시글 100','내용 100\r\n',NULL,'2023-07-12T06:09:41.831636',NULL),
(102,'testuser','테스트 게시글 101','내용 101\r\n',NULL,'2023-07-12T06:09:50.804303500',NULL),
(103,'testuser','테스트 게시글 102','내용 102',0,'2023-07-12T06:09:56.067167800','수정되지 않은 글'),
(104,'testuser','테스트 게시글 103','내용 103',NULL,'2023-07-12T06:10:00.195497500',NULL),
(105,'testuser','테스트 게시글 104','내용 104',NULL,'2023-07-12T06:10:03.240404',NULL),
(106,'testuser','테스트 게시글 105','내용 105',NULL,'2023-07-12T06:10:06.414021',NULL),
(107,'testuser','테스트 게시글 106','내용 106',NULL,'2023-07-12T06:10:09.352483',NULL),
(108,'testuser','테스트 게시글 107','내용 107',NULL,'2023-07-12T06:10:12.716244400',NULL),
(109,'testuser','테스트 게시글 108','내용 108',NULL,'2023-07-12T06:10:15.717707600',NULL),
(110,'testuser','테스트 게시글 109','내용 109',NULL,'2023-07-12T06:10:19.068073300',NULL),
(111,'testuser','테스트 게시글 110','내용 110',NULL,'2023-07-12T06:10:22.672425',NULL),
(112,'testuser','테스트 게시글 111','내용 111',NULL,'2023-07-12T06:10:26.089018500',NULL),
(113,'testuser','테스트 게시글 112','내용 112',NULL,'2023-07-12T06:10:29.038078100',NULL),
(114,'testuser','테스트 게시글 113','내용 113',NULL,'2023-07-12T06:10:32.221399300',NULL),
(115,'testuser','테스트 게시글 114','내용 114',NULL,'2023-07-12T06:10:35.635181900',NULL),
(116,'testuser','테스트 게시글 115','내용 115',NULL,'2023-07-12T06:10:39.488002800',NULL),
(117,'testuser','테스트 게시글 116','내용 116',NULL,'2023-07-12T06:10:42.275331800',NULL),
(118,'testuser','테스트 게시글 117','내용 117',NULL,'2023-07-12T06:10:45.762756200',NULL),
(119,'testuser','테스트 게시글 118','내용 118',NULL,'2023-07-12T06:10:49.541821',NULL),
(120,'testuser','가나다라 ','작성시간을 알고싶어!\r\n',NULL,'2023-07-12T06:40:48.583905800',NULL),
(121,'testuser','가나다라 ','작성시간을 알고싶어!\r\n',NULL,'23-07-12T06:42',NULL),
(122,'testuser','작성시간이 궁금해요','궁금한데수응',NULL,'23-07-12T06:42',NULL),
(123,'testuser','살려주세요','살려주세요 진짜로 ㅠㅠㅠㅠㅠ',NULL,'23-07-12 06:43',NULL),
(124,'testuser','글 작성을 한다면?','어떻게 될까요',NULL,'23-07-12 06:46',NULL),
(125,'testuser','글 작성을 한다면?','어떻게 될까요',NULL,'23-07-12 06:47',NULL),
(126,'testuser','글 작성을 한다면?','어떻게 될까요',NULL,'23-07-12 06:47',NULL),
(127,'testuser','글 작성을 한다면?','어떻게 될까요',NULL,'23-07-12 06:48',NULL),
(128,'testuser','글 작성을 한다면?','어떻게 될까요',NULL,'23-07-12 06:49',NULL),
(129,'testuser','글 작성을 한다면?','어떻게 될까요',1,'23-07-12 06:54','수정되지 않은 글'),
(130,'testuser','몇번째 글이게요','그러게요??',7,'23-07-12 07:26','수정되지 않은 글'),
(131,'testuser','가아아악','나아아악다아아악',0,'23-07-12 08:18',NULL),
(132,'testuser','작성한다아아악','키야아아아악',0,'23-07-12 08:21',NULL),
(133,'testuser','써져라아아아','제발요오오오ㅗ',1,'23-07-12 08:21','23-07-12 13:36'),
(134,'testuser','작성해주세요','ㅈ[발요 써주세요',0,'23-07-12 08:23',NULL),
(135,'testuser','글 작성하기 ㅈ[발요','제발 들어가주세요',3,'23-07-12 08:26','수정되지 않은 글'),
(136,'testuser','안될까요?','부탁합니다 제방료',0,'23-07-12 08:27',NULL),
(137,'testuser','키에에에에ㅔㄱ','살려주세요 진짜로',1,'23-07-12 08:28','수정되지 않은 글'),
(138,'testuser','이건 되나요?','되게 해주세요 제발요',2,'23-07-12 08:31','수정되지 않은 글');
/*!40000 ALTER TABLE `freeboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mail`
--

DROP TABLE IF EXISTS `mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receiver` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mail`
--

LOCK TABLES `mail` WRITE;
/*!40000 ALTER TABLE `mail` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `members` (
  `mem_no` int(11) NOT NULL AUTO_INCREMENT,
  `mem_id` varchar(255) NOT NULL,
  `mem_passwd` varchar(255) NOT NULL,
  `mem_name` varchar(255) DEFAULT NULL,
  `mem_email` varchar(255) DEFAULT NULL,
  `mem_regdate` datetime DEFAULT current_timestamp(),
  `kakao` int(11) DEFAULT NULL,
  `mem_role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mem_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(100) NOT NULL,
  `voiid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `tempPassword` varchar(255) DEFAULT NULL,
  `temp_Password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_code`
--

DROP TABLE IF EXISTS `verification_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification_code` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_code`
--

LOCK TABLES `verification_code` WRITE;
/*!40000 ALTER TABLE `verification_code` DISABLE KEYS */;
INSERT INTO `verification_code` VALUES
(18,'141087','kyjj1024@gmail.com'),
(19,'263420','kyjj1024@gmail.com'),
(20,'468842','kyjj1024@gmail.com'),
(21,'215545','kyjj1024@gmail.com'),
(22,'844297','kyjj1024@gmail.com'),
(23,'198689','kyjj1024@gmail.com'),
(24,'677196','kyjj1024@gmail.com'),
(25,'640108','kyjj1024@gmail.com'),
(26,'126551','kyjj1024@gmail.com'),
(27,'753716','kyjj1024@gmail.com'),
(28,'778706','kyjj1024@gmail.com'),
(29,'939096','kyjj1024@gmail.com'),
(30,'716654','kyjj1024@gmail.com'),
(31,'720151','kyjj1024@gmail.com'),
(32,'281796','kyjj1024@gmail.com'),
(33,'789073','kyjj1024@gmail.com'),
(34,'493978','kyjj1024@gmail.com'),
(35,'194982','kyjj1024@gmail.com');
/*!40000 ALTER TABLE `verification_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verificationcode`
--

DROP TABLE IF EXISTS `verificationcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verificationcode` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verificationcode`
--

LOCK TABLES `verificationcode` WRITE;
/*!40000 ALTER TABLE `verificationcode` DISABLE KEYS */;
INSERT INTO `verificationcode` VALUES
(1,'kyjj1024@gmail.com','893794'),
(2,'kyjj1024@gmail.com','877196'),
(3,'kyjj1024@gmail.com','933673'),
(4,'kyjj1024@gmail.com','563360'),
(5,'kyjj1024@gmail.com','963183'),
(6,'kyjj1024@gmail.com','280445'),
(7,'kyjj1024@gmail.com','712474'),
(8,'kyjj1024@gmail.com','890577'),
(9,'kyjj1024@gmail.com','829402'),
(10,'kyjj1024@gmail.com','399187'),
(11,'kyjj1024@gmail.com','362841'),
(12,'kyjj1024@gmail.com','493931'),
(13,'kyjj1024@gmail.com','365077'),
(14,'kyjj1024@gmail.com','407058'),
(15,'kyjj1024@gmail.com','296783'),
(16,'kyjj1024@gmail.com','270729'),
(17,'kyjj1024@gmail.com','394916');
/*!40000 ALTER TABLE `verificationcode` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-14 14:30:45
