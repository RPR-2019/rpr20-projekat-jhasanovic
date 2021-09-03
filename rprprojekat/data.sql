BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "prodani" (
	"id"	INTEGER,
	"name"	TEXT,
	"seller"	TEXT,
	"date"	NUMERIC,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "proizvod" (
	"name"	TEXT,
	"id"	INTEGER,
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
INSERT INTO "prodani" VALUES (1,'Paracetamol','Jasmina','02-09-2021');
INSERT INTO "proizvod" VALUES ('Analgin',2,2.0,137,NULL,'',NULL,'Bosnalijek d.d.','','',NULL);
INSERT INTO "proizvod" VALUES ('Paracetamol',1112,3.0,134,'Analgetik','Neke napomene','nesto','nesto','neki opis','sastojci','tablete');
COMMIT;
