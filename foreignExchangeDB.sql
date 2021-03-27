DROP TABLE IF EXISTS `foreign_exchange`;

CREATE TABLE `foreign_exchange`
(
  `Foreign_Exchange_Id` text,
  `Currency_Format` text,
  `Base_Format` text,
  `Currency_Value_Multiplier` text,
  `Country` text,
  `Date_Time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `source_data_dump`;

CREATE TABLE `source_data_dump`
(
  `Source_Data_Dump_Id` text,
  `Type` text,
  `Url_To_Loc` text,
  `Date_Time` datetime DEFAULT NULL,
  `Data` text,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;