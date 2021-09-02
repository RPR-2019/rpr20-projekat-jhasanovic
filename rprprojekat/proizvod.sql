BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "proizvod" (
	"name"	TEXT,
	"id"	TEXT,
	"price"	REAL,
	"quantity"	INTEGER,
	"purpose"	TEXT,
	"notes"	TEXT,
	"administrationMethod"	TEXT,
	"manufacturer"	TEXT,
	"description"	TEXT,
	"ingredients"	TEXT,
	"type"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "proizvod" VALUES ('Paracetamol','1',2.0,1,'n',NULL,'n','Bosnalijek d.d.','n','n','n');
INSERT INTO "proizvod" VALUES ('Flonidan','2',3.0,11,'n',NULL,'n','n','n','n','n');
INSERT INTO "proizvod" VALUES ('Analgin','3',4.0,17,'n',NULL,'n','n','n','n','n');
COMMIT;
