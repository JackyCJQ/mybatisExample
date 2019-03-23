
CREATE DATABASE `mybatis` DEFAULT CHARACTER SET utf8 ;

USE `mybatis`;

DROP TABLE IF EXISTS `tbl_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `tbl_dept` WRITE;
/*!40000 ALTER TABLE `tbl_dept` DISABLE KEYS */;
INSERT INTO `tbl_dept` VALUES (1,'开发部'),(2,'测试部');
/*!40000 ALTER TABLE `tbl_dept` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `gender` varchar(16) DEFAULT NULL,
  `d_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_emp_dept` (`d_id`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`d_id`) REFERENCES `tbl_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10010 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `tbl_employee` WRITE;
/*!40000 ALTER TABLE `tbl_employee` DISABLE KEYS */;
INSERT INTO `tbl_employee` VALUES (1,'jacky','348561945@qq.com','1',1),(2,'jacky01','1234567@qq.com','0',1),(3,'smith0x1','smith0x1@atguigu.com','1',1),(4,'allen0x1','allen0x1@atguigu.com','0',1),(5,'smith0x1','smith0x1@atguigu.com','1',1),(10008,'smith0x1','smith0x1@atguigu.com','1',1),(10009,'allen0x1','allen0x1@atguigu.com','0',1);
/*!40000 ALTER TABLE `tbl_employee` ENABLE KEYS */;
UNLOCK TABLES;
