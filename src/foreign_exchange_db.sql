DROP TABLE IF EXISTS `source_data_dump`;

CREATE TABLE source_data_dump (
  Source_Data_ID VARCHAR(255) PRIMARY KEY,
  Type TEXT NOT NULL,
  URL_to_Loc TEXT NOT NULL,
  Date_Time TEXT,
  Data TEXT);
