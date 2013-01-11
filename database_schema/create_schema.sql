-- MySQL dump 10.13  Distrib 5.5.28, for osx10.6 (i386)
--
-- Host: localhost    Database: solderingup
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;


-- -----------------------------------------------------
-- Table `solderingup`.`divisions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`divisions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solderingup`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `first_name` VARCHAR(50) NOT NULL ,
  `last_name` VARCHAR(100) NOT NULL ,
  `email` VARCHAR(100) NOT NULL ,
  `address` VARCHAR(500) NOT NULL ,
  `primary_number` VARCHAR(20) NOT NULL ,
  `secondary_number` VARCHAR(20) NULL DEFAULT NULL ,
  `password` CHAR(40) NOT NULL ,
  `salt` BIGINT(20) NOT NULL ,
  `zip` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `zip` (`zip` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solderingup`.`experience`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`experience` (
  `uid` INT(11) NOT NULL ,
  `did` INT(11) NOT NULL ,
  `TitleName` VARCHAR(100) NOT NULL ,
  `StartDate` DATETIME NOT NULL ,
  `EndDate` DATETIME NOT NULL ,
  `Description` VARCHAR(500) NOT NULL ,
  PRIMARY KEY (`uid`, `did`, `TitleName`) ,
  INDEX `did` (`did` ASC) ,
  CONSTRAINT `experience_ibfk_1`
    FOREIGN KEY (`uid` )
    REFERENCES `solderingup`.`users` (`id` ),
  CONSTRAINT `experience_ibfk_2`
    FOREIGN KEY (`did` )
    REFERENCES `solderingup`.`divisions` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solderingup`.`passwordkeys`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`passwordkeys` (
  `id` INT(11) NOT NULL ,
  `password_key` CHAR(40) NOT NULL ,
  `creation_date` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `passwordkeys_ibfk_1`
    FOREIGN KEY (`id` )
    REFERENCES `solderingup`.`users` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solderingup`.`soldierexperience`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`soldierexperience` (
  `VID` INT(11) NOT NULL ,
  `DID` INT(11) NOT NULL ,
  `TitleName` VARCHAR(100) NOT NULL ,
  `StartDate` DATETIME NOT NULL ,
  `EndDate` DATETIME NOT NULL ,
  `Description` VARCHAR(500) NOT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solderingup`.`veterans`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`veterans` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `Goal` VARCHAR(200) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `veterans_ibfk_1`
    FOREIGN KEY (`id` )
    REFERENCES `solderingup`.`users` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solderingup`.`zip`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `solderingup`.`zip` (
  `zip` VARCHAR(20) NOT NULL ,
  `city` VARCHAR(100) NOT NULL ,
  `state` CHAR(2) NOT NULL ,
  `latitude` DECIMAL(7,4) NULL DEFAULT NULL ,
  `longitude` DECIMAL(7,4) NULL DEFAULT NULL ,
  PRIMARY KEY (`zip`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  `cellphone` varchar(45) DEFAULT NULL,
  `homephone` varchar(45) DEFAULT NULL,
  `businessphone` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `zip` varchar(11) DEFAULT NULL,
  `password` blob,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roster`
--

DROP TABLE IF EXISTS `roster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `description` blob,
  `tags` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roster`
--

LOCK TABLES `roster` WRITE;
/*!40000 ALTER TABLE `roster` DISABLE KEYS */;
/*!40000 ALTER TABLE `roster` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-16 23:36:20
