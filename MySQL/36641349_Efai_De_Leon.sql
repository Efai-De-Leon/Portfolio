#Student Name: Efai De Leon
#Student ID: 36641349

DROP DATABASE IF EXISTS cs122a_hw;
create database cs122a_hw;
use cs122a_hw;

#SQL DDLs for Entities and their supporting tables:

CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);


CREATE TABLE `vehicles` (
  `state` varchar(30) NOT NULL,
  `license_plate` varchar(15) NOT NULL,
  `year` date DEFAULT NULL,
  `model` varchar(30) DEFAULT NULL,
  `make` varchar(30) NOT NULL,
  `color` varchar(30) NOT NULL,
  PRIMARY KEY (`state`,`license_plate`)
);

CREATE TABLE `stores` (
  `store_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `zip_code` int NOT NULL,
  `street` varchar(45) NOT NULL,
  `phone` int NOT NULL,
  PRIMARY KEY (`store_id`)
);

CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `category` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(100) NOT NULL,
  `list_price` decimal(10,0) NOT NULL,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE `customers` (
  `customer_id` int NOT NULL,
  PRIMARY KEY (`customer_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `shoppers` (
  `shopper_id` int NOT NULL,
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`shopper_id`),
  CONSTRAINT `shopper_id` FOREIGN KEY (`shopper_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `orders` (
  `order_id` int NOT NULL,
  `total_price` decimal(10,0) NOT NULL,
  `store` int NOT NULL,
  `fufilll_shopper` int NOT NULL,
  `c_state` varchar(30) NOT NULL,
  `c_license_plate` varchar(15) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `store_idx` (`store`),
  KEY `fulfill_shopper_idx` (`fufilll_shopper`),
  KEY `cstate_idx` (`c_state`,`c_license_plate`),
  CONSTRAINT `cstate` FOREIGN KEY (`c_state`, `c_license_plate`) REFERENCES `vehicles` (`state`, `license_plate`),
  CONSTRAINT `fulfill_shopper` FOREIGN KEY (`fufilll_shopper`) REFERENCES `shoppers` (`shopper_id`),
  CONSTRAINT `store` FOREIGN KEY (`store`) REFERENCES `stores` (`store_id`)
);

CREATE TABLE `orderitems` (
  `item_id` int NOT NULL,
  `qty` int NOT NULL,
  `selling_price` decimal(10,0) NOT NULL,
  `associated_product` int NOT NULL,
  `ordr_id` int NOT NULL,
  PRIMARY KEY (`item_id`,`ordr_id`),
  KEY `associated_product_idx` (`associated_product`),
  KEY `ordr_id_idx` (`ordr_id`),
  CONSTRAINT `associated_product` FOREIGN KEY (`associated_product`) REFERENCES `products` (`product_id`),
  CONSTRAINT `ordr_id` FOREIGN KEY (`ordr_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
);


CREATE TABLE `phone` (
  `user_id` int NOT NULL,
  `type` enum('home','office','mobile') NOT NULL,
  `number` int NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `categories` (
  `store_category_id` int NOT NULL,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`store_category_id`),
  CONSTRAINT `store_category_id` FOREIGN KEY (`store_category_id`) REFERENCES `stores` (`store_id`) ON DELETE CASCADE
);


#SQL DDLs for Relationships
CREATE TABLE `work_for` (
  `shppr_id` int NOT NULL,
  `str_id` int NOT NULL,
  PRIMARY KEY (`shppr_id`,`str_id`),
  KEY `store_id_idx` (`str_id`),
  CONSTRAINT `shppr_id` FOREIGN KEY (`shppr_id`) REFERENCES `shoppers` (`shopper_id`),
  CONSTRAINT `str_id` FOREIGN KEY (`str_id`) REFERENCES `stores` (`store_id`)
);

CREATE TABLE `friends_with` (
  `cust_id` int NOT NULL,
  `friend_cust_id` int NOT NULL,
  PRIMARY KEY (`cust_id`,`friend_cust_id`),
  KEY `friend_cust_id_idx` (`friend_cust_id`),
  CONSTRAINT `cust_id` FOREIGN KEY (`cust_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `friend_cust_id` FOREIGN KEY (`friend_cust_id`) REFERENCES `customers` (`customer_id`)
);

CREATE TABLE `stocked_by` (
  `prdct_id` int NOT NULL,
  ` stor_id` int NOT NULL,
  `qty` int NOT NULL,
  PRIMARY KEY (`prdct_id`,` stor_id`),
  KEY `str_id_idx` (` stor_id`),
  CONSTRAINT `prdct_id` FOREIGN KEY (`prdct_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `stor_id` FOREIGN KEY (` stor_id`) REFERENCES `stores` (`store_id`)
);

CREATE TABLE `own` (
  `customers_id` int NOT NULL,
  `own_state` varchar(30) NOT NULL,
  `own_license_plate` varchar(15) NOT NULL,
  PRIMARY KEY (`customers_id`),
  KEY `own_state_idx` (`own_state`,`own_license_plate`),
  CONSTRAINT `customers_id` FOREIGN KEY (`customers_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `own_vehicles` FOREIGN KEY (`own_state`, `own_license_plate`) REFERENCES `vehicles` (`state`, `license_plate`)
);
 