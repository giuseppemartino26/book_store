CREATE DATABASE  IF NOT EXISTS `book_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `book_store`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: book_store
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
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `idauthor` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `biography` varchar(511) NOT NULL,
  PRIMARY KEY (`idauthor`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'francesco','milo','vivo'),(2,'giuseppe','yamio','morto'),(3,'marco','d\'andrea','vivo'),(4,'michele','dedo','vivo'),(5,'vincenzo','la','vivo'),(6,'vincenzo','do','morto'),(7,'michele','ciao','vivo'),(8,'dante','alighieri','vivo'),(9,'francesco','totti','vivo'),(10,'francesco','michelin','vivo');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `idbook` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `price` float NOT NULL,
  `category` varchar(45) NOT NULL,
  `numpages` int NOT NULL,
  `quantity` int NOT NULL,
  `pub_id` int NOT NULL,
  `publicationYEAR` int NOT NULL,
  PRIMARY KEY (`idbook`),
  KEY `pub_id_idx` (`pub_id`),
  CONSTRAINT `pub_id` FOREIGN KEY (`pub_id`) REFERENCES `publisher` (`idpublisher`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'decamerone',20,'dramamtico',300,2,1,1600),(2,'delitto e castigo',12,'horror',233,5,2,1900),(3,'pocahontas',24,'fantasy',555,1,3,1999),(4,'biancaneve',35,'fantasy',43,1,4,2000),(5,'divina commedia',44,'dramamtico',444,2,5,1450),(6,'ventannidopo',32,'fantasy',33,3,1,1789),(7,'caosi parlo bellavista',34,'dramamtico',234,4,2,1987),(8,'il dio del fiume',23,'romantico',345,5,3,1988),(9,'figli del nilo',45,'sentimentale',456,2,4,1567),(10,'il settimo paino',65,'fantasy',567,3,5,1765),(11,'sesto senso',3,'horror',765,4,1,1987),(12,'la cupola',33,'storico',543,5,2,1345),(13,'the society',43,'fantasy',345,6,3,1234),(14,'mille e una notte',11,'dramamtico',43,4,4,1900),(15,'ilpiccolo principe',12,'horror',23,5,5,1900),(16,'ciao',13,'horror',2345,4,1,1897),(17,'francesca',14,'horror',23,22,2,1987),(18,'orgoglio e pregiudizio',15,'fantasy',300,3,3,1888),(19,'la belle epoque',33,'drammatico',400,4,4,1678),(20,'dddddddddd',4,'nonso',23,7,3,2020),(22,'centodue',22,'avventura',345,4,2,2000),(23,'atlante',23,'avventura',345,5,3,1998),(24,'storia dell\'arte',23,'avvenura',543,6,4,1990),(25,'leonardo',23,'thriller',345,5,5,1990),(26,'la bella e la bestia',23,'dramamtico',334,4,1,1998),(27,'peter pan',23,'horror',334,3,2,1993),(28,'le vie dell\'amore',23,'sentimentale',3345,2,3,1435),(29,'via col vento',22,'dramamtico',345,4,4,1333),(30,'sedia',45,'horror',345,5,5,1987),(31,'ooo',8,'ooooo',88,9,3,2020);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_has_author`
--

DROP TABLE IF EXISTS `book_has_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_has_author` (
  `book_idbook` int NOT NULL,
  `author_idauthor` int NOT NULL,
  KEY `book_idbook_idx` (`book_idbook`),
  KEY `author_idauthor_idx` (`author_idauthor`),
  CONSTRAINT `author_idauthor` FOREIGN KEY (`author_idauthor`) REFERENCES `author` (`idauthor`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_idbook` FOREIGN KEY (`book_idbook`) REFERENCES `book` (`idbook`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_has_author`
--

LOCK TABLES `book_has_author` WRITE;
/*!40000 ALTER TABLE `book_has_author` DISABLE KEYS */;
INSERT INTO `book_has_author` VALUES (20,3),(21,3),(3,5),(4,5),(5,1),(30,2),(29,5),(8,1),(9,10),(10,1),(11,7),(12,6),(13,7),(14,5),(15,7),(16,2),(17,5),(31,3);
/*!40000 ALTER TABLE `book_has_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `idpublisher` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  PRIMARY KEY (`idpublisher`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES (1,'mondadori','roma'),(2,'giunti','milano'),(3,'feltrinelli','salerno'),(4,'minimumFax','roma'),(5,'LaLepre','milano');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-21 15:24:35
