CREATE DATABASE  IF NOT EXISTS `shop100` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `shop100`;
-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: shop100
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offer` (
  `product_code` varchar(20) NOT NULL,
  `supplier_code` varchar(20) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`product_code`,`supplier_code`),
  KEY `supplier_code_idx` (`supplier_code`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_code`) REFERENCES `product` (`code`),
  CONSTRAINT `supplier_id` FOREIGN KEY (`supplier_code`) REFERENCES `supplier` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
INSERT INTO `offer` VALUES ('P001','S001',0.5),('P001','S002',1),('P001','S011',0.6),('P001','S016',0.4),('P001','S017',0.33),('P001','S100',0.61),('P001','S107',0.62),('P002','S005',72.99),('P002','S006',69.99),('P002','S011',73.99),('P002','S016',70.04),('P002','S017',70.88),('P002','S102',70.06),('P002','S107',71.11),('P003','S016',1499.99),('P003','S088',1489.99),('P003','S090',1399.99),('P003','S108',1500.03),('P004','S007',21.99),('P004','S011',20.99),('P004','S016',19.99),('P004','S017',20.88),('P004','S103',21.11),('P004','S108',23.32),('P005','S011',173.99),('P005','S016',175.99),('P005','S017',172.88),('P005','S088',178.9),('P005','S108',180),('P006','S011',24.99),('P006','S012',22.99),('P006','S016',21.94),('P006','S017',22.88),('P006','S108',23.05),('P007','S009',19.99),('P007','S011',21.99),('P007','S016',20.01),('P007','S017',21.88),('P008','S005',33.74),('P008','S006',34.99),('P008','S011',33.75),('P008','S016',32.91),('P008','S017',33.88),('P008','S108',34.43),('P009','S004',8.99),('P009','S011',8.98),('P009','S016',8.97),('P009','S017',8.88),('P010','S008',3.38),('P010','S016',3.44),('P010','S017',3.88),('P010','S104',3.33),('P011','S013',65.99),('P011','S017',66.66),('P011','S104',67.77),('P011','S108',68.88),('P012','S014',1499.99),('P012','S015',1399.99),('P012','S016',1495.01),('P012','S108',1404.04),('P013','S013',300.5),('P013','S017',288.88),('P013','S108',301.11),('P014','S017',25.55),('P014','S068',25.4),('P014','S106',26.63),('P015','S003',64.76),('P015','S011',66.66),('P015','S012',69.99),('P015','S016',65.99),('P015','S106',63.77),('P016','S010',259),('P016','S011',251.99),('P016','S090',250),('P016','S108',27.07),('P017','S011',209.49),('P017','S013',209.99),('P017','S016',210.11),('P017','S102',207.7),('P018','S011',1699.99),('P018','S066',1729.9),('P018','S067',1700),('P018','S101',1719.8),('P019','S002',295.89),('P019','S003',297.79),('P019','S008',299.99),('P019','S011',280.88),('P019','S013',285.56),('P020','S001',85.52),('P020','S010',88.88),('P020','S066',99.99),('P020','S100',89.98),('P021','S001',8.99),('P021','S002',8.88),('P021','S005',7.78),('P021','S009',9.92),('P021','S010',9.99),('P021','S011',7.9),('P021','S021',8.1),('P021','S103',7.79),('P022','S002',23.32),('P022','S004',24.04),('P022','S008',23.75),('P022','S014',29.91),('P022','S100',28.88),('P023','S016',466),('P023','S017',459.8),('P023','S066',499.88),('P023','S067',449.9),('P023','S101',402.09),('P023','S106',506.66),('P024','S002',210.09),('P024','S007',211.12),('P024','S009',203.32),('P024','S012',209.09),('P024','S014',204.42),('P024','S017',201.18),('P024','S088',211.11),('P025','S014',29.93),('P025','S090',29.9),('P025','S100',28.8),('P025','S102',28.87),('P026','S003',21.13),('P026','S016',22.22),('P026','S021',20.88),('P026','S101',21.77),('P026','S102',20.66),('P026','S104',22.23),('P026','S106',23.33),('P027','S001',39.91),('P027','S003',24.43),('P027','S005',27.99),('P027','S007',28.81),('P028','S004',92.63),('P028','S007',91.19),('P028','S015',92.79),('P028','S101',94.49),('P028','S106',96),('P028','S108',99.91),('P029','S005',44.99),('P029','S105',45.54),('P030','S010',34.44),('P030','S066',33.33),('P030','S101',32.01),('P030','S102',32.23),('P030','S106',29.99),('P031','S001',21.13),('P031','S004',22.99),('P031','S008',20.09),('P031','S011',23.32),('P031','S016',24.46),('P032','S013',124.4),('P032','S014',109.99),('P032','S015',122.21),('P032','S067',108.8),('P032','S108',123),('P033','S003',12.22),('P033','S006',11.12),('P033','S009',9.93),('P033','S012',9.7),('P033','S015',10.01),('P033','S102',10.45),('P034','S003',9.04),('P034','S066',8.56),('P034','S067',8.06),('P034','S101',8.99),('P035','S066',89.98),('P035','S067',85.4),('P035','S090',88.8),('P036','S002',39.99),('P036','S007',46.64),('P036','S103',43.21),('P036','S108',45.98),('P037','S003',105.9),('P037','S005',117.41),('P037','S107',120.21),('P038','S011',1.99),('P038','S016',1.17),('P038','S017',1.18),('P038','S021',1.15),('P038','S022',2),('P038','S101',1.14),('P038','S102',2.66),('P038','S105',1.16),('P038','S107',1.93),('P038','S109',3);
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `code` int NOT NULL AUTO_INCREMENT,
  `suplier_code` varchar(20) NOT NULL,
  `user_email` varchar(45) NOT NULL,
  `total_value` float NOT NULL,
  `date_of_shipment` date NOT NULL,
  PRIMARY KEY (`code`),
  KEY `user_email_idx` (`user_email`),
  KEY `supllier_code_idx` (`suplier_code`),
  CONSTRAINT `supllier_code` FOREIGN KEY (`suplier_code`) REFERENCES `supplier` (`code`),
  CONSTRAINT `user_email` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'S016','leo@mail.it',99.95,'2023-07-06');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_composition`
--

DROP TABLE IF EXISTS `order_composition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_composition` (
  `code_order` int NOT NULL,
  `code_product` varchar(20) NOT NULL,
  PRIMARY KEY (`code_product`,`code_order`),
  KEY `order_code_idx` (`code_order`),
  KEY `product_code_idx` (`code_product`),
  CONSTRAINT `order_code` FOREIGN KEY (`code_order`) REFERENCES `order` (`code`),
  CONSTRAINT `product_code` FOREIGN KEY (`code_product`) REFERENCES `product` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_composition`
--

LOCK TABLES `order_composition` WRITE;
/*!40000 ALTER TABLE `order_composition` DISABLE KEYS */;
INSERT INTO `order_composition` VALUES (1,'P004');
/*!40000 ALTER TABLE `order_composition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `code` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` varchar(45) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('P001','Bic Blue Pen','stationary','high quality pen used for writing','pic/p001.jpeg'),('P002','Lavazza Classic','foods','quality coffee  since 1956','pic/p002.jpeg'),('P003','Samsung Galaxy S22','eletronics','optimal performance and fast charge','pic/p003.jpeg'),('P004','Lobyoh Music carpet','toys','A carpet used by children','pic/p004.jpeg'),('P005','Samsung Microwave','eletronics','microwave for fast cooking foods','pic/p005.jpeg'),('P006','La Sirenetta Ariel doll','toys','A disney princess Doll','pic/p006.jpeg'),('P007','Orsen Writeboard','toys','writeboard for children','pic/p007.jpeg'),('P008','Lavazza Mio Orzo','foods','Orzo Coffee','pic/p008.jpeg'),('P009','Educa Puzzle Disney','toys','Fun puzzle for childre, size 34 X 48 cm','pic/p009.jpeg'),('P010','Yomo Yogurt','foods','Yogurt with many flavour:strawberry, banana, blueberry','pic/p010.jpeg'),('P011','ZINUS mattress','furniture','Cool mattress, Max comfort','pic/p011.jpeg'),('P012','Huawei MateBook 16s','eletronics','Laptop RAM16GB DDR5, ROM 1TB SSD, Windows 11 ','pic/p012.jpeg'),('P013','ZINUS Jackie sofa','furniture','Sofa pure comfort','pic/p013.jpeg'),('P014','Caffe Alloia','foods','Delicious coffee','pic/p014.jpeg'),('P015','Electronic Photo Frame','eletronics','The digital photo frame has a 10.5 inch display with a resolution of 1280 x 720. HD IPS Screen with Wide Viewing Angle of 178 Degrees: IPS screen allows you to have a better viewing experience on the front and side. More better to view the image colours and details. Share the wonderful moment with your important people, grandparents, family, friends. Our products have a delicate box that is perfect as a gift.','pic/p015.jpeg'),('P016','Hisense refrigerator','eletronics','White refrigerator, high durability and resistance, optimal for food storing','pic/p016.jpeg'),('P017','ZINUS Mikhail Sofa','furniture','Red sofa, 2 person sofa','pic/p017.jpeg'),('P018','Apple iPhone 14 Pro ','eletronics','Smartphone iPhone 14 Pro Apple,512GB','pic/p018.jpeg'),('P019','Office Chair flash','furniture','Executive Office Chair High Back Swivel Chair ','pic/p019.jpeg'),('P020','Steampunk Lamp Iron ','furniture','Robot lamp fixture width: 20 cm, fixture height: 33 cm. Unique vintage design adds more personality to your home decor.','pic/p020.jpeg'),('P021','Desk Stapler ','stationary','Opens to attach information to a bulletin board, the rotating anvil offers two stapling options: temporary stapling and permanent stapling.','pic/p021.jpeg'),('P022','Aeroplane Toy','toys','2 in 1 aircraft toy: the combination of 3 foam planes and a starting cannon doubles the fun. When the trigger is pressed, the aircraft is started. It can also be flown when you use the plane alone, and the plane flies out with a light cast. Great fun. (PS: The color of the aircraft is random)','pic/p022.jpeg'),('P023','2015 Apple iPad Pro ','eletronics','Tablet iPad (12,9 inch, Wifi, 32GB,gray)','pic/p023.jpeg'),('P024','Soft Seat Cushion','furniture','Safe and stable: The frame of the chair is made of robust and sturdy steel tubing, the galvanised steel screws of the swing chair are protected from the elements by a high-quality powder coating. Maximum load capacity: 150 kg. The steel frame construction with four feet ensures a secure stand and a carefree rocking pleasure - it can be seesawed, swung, rocked and hung','pic/p024.jpeg'),('P025','Mixture of Notebook','stationary','Targeted self-reflection for more focus on progress and the good in you and around you – this journal allows you to communicate with yourself. You find out what makes you really happy. Then you build positive habits – like gratitude, optimism and daily self-love – that add more to your life. Constant and proactively in the right direction!','pic/p025.jpeg'),('P026','Coca Coke Original','foods','Coca cola 12 bottles * 350ml','pic/p026.jpeg'),('P027','Car Racing Track','toys','6 different types of construction vehicles – we have configured 6 simulated construction vehicles with unique designs and functions. Includes van, excavator, road roller, cement truck and 2 racing cars. The perfect gift for boys and girls aged 3456.','pic/p027.jpeg'),('P028','Roba Children\'s Sofa','furniture','The Roba children\'s sofa with armrests is a highlight in the children\'s or baby\'s room. Thanks to the side backrests, the children\'s sofa offers additional seating comfort and safety.','pic/p028.jpeg'),('P029','Barbell Set 30 kg','sports','Fully equipped for the perfect dumbbell training in the home gym - with the C.P.Sports 2-in-1 dumbbell and barbell set 30 kg. Change and combine the different weights depending on the training goal and train optionally with two dumbbells, each weighing up to 14.5 kg or a barbell with up to 30 kg total weight, for effective strength training, targeted muscle building as well as more power and fitness','pic/p029.jpeg'),('P030','Magnetic Timetable','stationary','By displaying the entire week from Monday to Sunday, it can also be used as a weekly planner and other appointments when there are pocket money and when it comes to the sports club.','pic/p030.jpeg'),('P031','Pecogo Volleyball','sports','Enjoy sports time: Do you want to train your sporting skills, but do you not suffer from a perfect volleyball made of soft PU leather? Well, come to Pecogo and get a high quality and durable indoor outdoor volleyball. A training volleyball can not only improve a person\'s body shape, but also promote a fun and crucial character. It\'s time to start training','pic/p031.jpeg'),('P032','Ultrasport F-Bike','sports','Foldable exercise bike, home trainer, fitness bike with 8 resistance levels, quick and easy to assemble and dismantle: the original, developed by F-bike market leader Ultrasport','pic/p032.jpeg'),('P033','Parker Ballpoint Pen','stationary','The iconic 50s have never been so trendy as with the Jotter Originals Pastel Edition.\nStreamlined style and clear lines of the well-known Parker Jotter design with a particularly scratch-resistant plastic shaft.','pic/p033.jpeg'),('P034','Blukar Skipping Rope','sports','STRONG AND DURABLE STEEL SKIPPING ROPE: Blukar skipping rope is made of high quality steel wire and covered with a strong PVC outer material, which can be durable even after a long training time. The wear-resistant, stable and non-floating wire rope can ensure a long service life and prevent wear.','pic/p034.jpeg'),('P035','JOOLA zugelassener ','sports','The knowledge in the production of table tennis products acquired over the years is the basis for the production of the Joola Infinity Carbon table tennis bat. Joola has stood for table tennis quality, competence and tradition for decades.','pic/p035.jpeg'),('P036','Parker Ballpoint Pen','stationary','Ballpoint pen: One ballpoint pen. Everyone will like them. - - - -\n- Branded product: Parker is the epitome of quality. The gift is designed to accompany the recipient for their life. For this reason, we trust the brand Parker. If the refill needs to be replaced, you get replacement for the brand Parker almost anywhere. Quality is convincing.','pic/p036.jpeg'),('P037','Northdeer Dumbbells','sports','Ultra-compact size – better exercise effect: The dumbbells are made from high quality steel with high density and are much smaller than conventional concrete or cast iron dumbbells. Exercises can be performed more effectively with this compact dumbbell, which deepens muscle stimulation and improves the training effect.','pic/p037.jpeg'),('P038','Barilla Pasta Penne','foods','Italian Pasta Penne','pic/p038.jpeg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spending_range`
--

DROP TABLE IF EXISTS `spending_range`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spending_range` (
  `id` varchar(20) NOT NULL,
  `supplier_code` varchar(20) NOT NULL,
  `min_qty` int NOT NULL,
  `max_qty` int NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`,`supplier_code`),
  KEY `supplier_code_idx` (`supplier_code`),
  CONSTRAINT `supplier_code` FOREIGN KEY (`supplier_code`) REFERENCES `supplier` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spending_range`
--

LOCK TABLES `spending_range` WRITE;
/*!40000 ALTER TABLE `spending_range` DISABLE KEYS */;
INSERT INTO `spending_range` VALUES ('R001','S001',1,30,0.99),('R002','S001',31,100,2.99),('R003','S001',101,999,4.99),('R004','S002',1,30,1.99),('R005','S002',31,100,2.99),('R006','S002',101,999,3.99),('R007','S003',1,2,1.99),('R008','S003',3,999,0.99),('R009','S004',1,999,2.99),('R010','S005',1,10,0.25),('R011','S005',11,20,0.49),('R012','S005',21,999,0.99),('R013','S006',1,20,0.99),('R014','S006',21,999,1.99),('R015','S007',1,1,0.99),('R016','S007',2,3,0.19),('R017','S007',4,999,0.99),('R018','S008',1,4,4.99),('R019','S008',5,7,3.99),('R020','S008',8,9,2.99),('R021','S008',10,15,1.99),('R022','S008',16,30,0.99),('R023','S008',31,100,0.45),('R024','S008',101,999,0.01),('R025','S009',1,9,0.99),('R026','S009',10,999,0.99),('R027','S010',1,999,0.99),('R028','S012',1,999,0.49),('R029','S013',1,999,0.99),('R030','S014',1,999,0.99),('R031','S015',1,999,0.99),('R032','S021',1,5,0.99),('R033','S021',6,999,0.99),('R034','S066',1,999,0.99),('R035','S067',1,999,5.99),('R036','S068',1,4,9.99),('R037','S068',5,9,5.99),('R038','S068',10,20,2.99),('R039','S068',21,50,0.99),('R040','S088',1,999,0.99),('R041','S090',1,2,2.99),('R042','S090',3,999,0.99),('R043','S011',1,4,4.99),('R044','S011',5,9,2.99),('R045','S011',10,19,1.99),('R046','S011',20,49,0.99),('R047','S011',50,999,0.99),('R048','S016',1,9,4.99),('R049','S016',10,49,2.99),('R050','S016',50,999,0.99),('R051','S017',1,49,0.99),('R052','S017',50,999,1.99),('R053','S100',1,9,19.99),('R054','S100',10,999,9.99),('R055','S101',1,99,0.99),('R056','S101',100,999,0.99),('R057','S102',1,99,0.49),('R058','S103',1,2,9.99),('R059','S103',3,999,0.99),('R060','S104',1,999,0.2),('R061','S105',1,9,2.99),('R062','S105',10,999,1.99),('R063','S106',1,999,1.99),('R064','S107',1,7,7.77),('R065','S107',8,59,5.99),('R066','S107',60,999,0.99),('R067','S108',1,2,3.99),('R068','S108',3,999,0.01),('R069','S109',1,11,2.99),('R070','S109',12,21,1.99),('R071','S109',22,31,0.99),('R072','S109',32,51,0.49),('R073','S109',52,91,0.39),('R074','S109',92,201,0.19),('R075','S109',202,401,0.09),('R076','S109',402,701,0.01),('R077','S109',702,999,0.99);
/*!40000 ALTER TABLE `spending_range` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `code` varchar(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `rating` int NOT NULL,
  `free_shipping` float DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('S001','BICGIB',3,250),('S002','BICib',5,50),('S003','CocaCoco',4,NULL),('S004','Edu',2,NULL),('S005','Lavazzavavazza',5,100),('S006','Lavavavava',3,NULL),('S007','Lobyoh',1,NULL),('S008','Yomoh',4,NULL),('S009','Orsen Downsen',2,NULL),('S010','Hisenze',4,NULL),('S011','SamSellALL',4,49.99),('S012','Tisney',5,NULL),('S013','Zenus',3,NULL),('S014','Kuawei',5,5000.99),('S015','Muawei reseller',4,NULL),('S016','Tom Have All srl',4,199.99),('S017','BigChina',5,300.99),('S021','Barille',5,NULL),('S022','Balira',4,NULL),('S066','Pinneple',5,NULL),('S067','Pinneple reseller',4,NULL),('S068','Aloiaeul',3,99.99),('S088','SamSu',4,1000),('S090','Ele Shop',4,1500),('S100','Fast sell',3,NULL),('S101','Super sell',2,45.55),('S102','Tony shop',4,NULL),('S103','Poppo shop',5,NULL),('S104','Lillo shop',1,66.66),('S105','Big seller',4,NULL),('S106','Huge seller',2,99.99),('S107','Super seller',3,NULL),('S108','I buy',4,29.99),('S109','every buy',5,NULL);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `card_number` varchar(16) NOT NULL,
  `card_expiration` date NOT NULL,
  `card_cvv` int NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('jaja','vava','java@mail.com','123','jjja','1200000023123123','2024-02-01',99),('ken','chen','ken@mail.it','123','via Roma','54321','2030-01-01',123),('leo','mao','leo@mail.it','123','via Milano','12345','2030-01-01',123),('zia','zia','zia@mail.it','123','via art','1242312123123123','2025-03-01',213),('zio','zio','zio@mail.it','123','via art','1213131231231231','2025-05-01',123);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'shop100'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-06 19:27:58
