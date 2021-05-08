-- MariaDB dump 10.18  Distrib 10.4.17-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: anbulatorioa
-- ------------------------------------------------------
-- Server version	10.4.17-MariaDB

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
-- Current Database: `anbulatorioa`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `anbulatorioa` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `anbulatorioa`;

--
-- Table structure for table `botika`
--

DROP TABLE IF EXISTS `botika`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `botika` (
  `GAIXONAN` int(11) DEFAULT NULL,
  `SSZENB` int(11) DEFAULT NULL,
  `KODEA` int(11) NOT NULL,
  `IZENA` varchar(15) DEFAULT NULL,
  `MARKA` varchar(15) DEFAULT NULL,
  `DOSIKOP` float DEFAULT NULL,
  `IRAUNGIDATA` date DEFAULT NULL,
  PRIMARY KEY (`KODEA`),
  KEY `GAIXONAN` (`GAIXONAN`),
  CONSTRAINT `botika_ibfk_1` FOREIGN KEY (`GAIXONAN`) REFERENCES `gaixoa` (`NAN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `botika`
--

LOCK TABLES `botika` WRITE;
/*!40000 ALTER TABLE `botika` DISABLE KEYS */;
/*!40000 ALTER TABLE `botika` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gaixoa`
--

DROP TABLE IF EXISTS `gaixoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gaixoa` (
  `NAN` int(11) NOT NULL,
  `SSZENB` int(11) DEFAULT NULL,
  `IZENA` varchar(15) NOT NULL,
  `ABIZENA` varchar(15) DEFAULT NULL,
  `SEXUA` varchar(8) DEFAULT NULL,
  `JAIODATA` date DEFAULT NULL,
  `OHIKOZENTROA` varchar(15) DEFAULT NULL,
  `HOSPITALEANDAGO` tinyint(1) DEFAULT NULL,
  `NONBIZI` varchar(15) DEFAULT NULL,
  `ADINA` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAN`),
  UNIQUE KEY `SSZENB` (`SSZENB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gaixoa`
--

LOCK TABLES `gaixoa` WRITE;
/*!40000 ALTER TABLE `gaixoa` DISABLE KEYS */;
/*!40000 ALTER TABLE `gaixoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medikua`
--

DROP TABLE IF EXISTS `medikua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medikua` (
  `NAN` int(11) NOT NULL,
  `KOLEGIATUZENB` int(11) DEFAULT NULL,
  `IZENA` varchar(15) DEFAULT NULL,
  `ABIZENA` varchar(15) DEFAULT NULL,
  `ZENTROA` varchar(15) DEFAULT NULL,
  `JAIODATA` date DEFAULT NULL,
  `BADAFAMILIAKOMEDIKUA` tinyint(1) DEFAULT NULL,
  `ESPEZIALITATEA` varchar(15) DEFAULT NULL,
  `TELF` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAN`),
  UNIQUE KEY `KOLEGIATUZENB` (`KOLEGIATUZENB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medikua`
--

LOCK TABLES `medikua` WRITE;
/*!40000 ALTER TABLE `medikua` DISABLE KEYS */;
/*!40000 ALTER TABLE `medikua` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zita`
--

DROP TABLE IF EXISTS `zita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zita` (
  `MEDIKUNAN` int(11) DEFAULT NULL,
  `KOLEGIATUZENB` int(11) DEFAULT NULL,
  `GAIXONAN` int(11) DEFAULT NULL,
  `SSZENB` int(11) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  `ORDUA` time DEFAULT NULL,
  `LEKUA` varchar(15) DEFAULT NULL,
  `GELA` varchar(4) DEFAULT NULL,
  KEY `MEDIKUNAN` (`MEDIKUNAN`),
  KEY `GAIXONAN` (`GAIXONAN`),
  CONSTRAINT `zita_ibfk_1` FOREIGN KEY (`MEDIKUNAN`) REFERENCES `medikua` (`NAN`),
  CONSTRAINT `zita_ibfk_2` FOREIGN KEY (`GAIXONAN`) REFERENCES `gaixoa` (`NAN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zita`
--

LOCK TABLES `zita` WRITE;
/*!40000 ALTER TABLE `zita` DISABLE KEYS */;
/*!40000 ALTER TABLE `zita` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-06 13:09:25
