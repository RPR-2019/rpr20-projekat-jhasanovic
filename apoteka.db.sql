BEGIN TRANSACTION;
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
CREATE TABLE IF NOT EXISTS "korpa" (
	"id"	INTEGER,
	"name"	TEXT,
	"quantity"	INTEGER,
	"price"	REAL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "prodani" (
	"idSold"	INTEGER,
	"id"	INTEGER,
	"name"	TEXT,
	"quantity"	INTEGER,
	"seller"	TEXT,
	"date"	TEXT,
	PRIMARY KEY("idSold")
);
CREATE TABLE IF NOT EXISTS "korisnici" (
	"username"	TEXT,
	"password"	TEXT,
	"email"	TEXT,
	PRIMARY KEY("username")
);
INSERT INTO "proizvod" VALUES ('Brufen',1,4.0,1234,'Analgetik','Bijele, duguljaste film tablete.Kartonska kutija sa 3 PVC/ALU blistera sa po 10 film tableta.','Peroralno','Abbott Logistics B.V.','Lijek BRUFEN se može kratkotrajno upotrebljavati u terapiji bolnih stanja poput zubobolje, bolova nakon operativnih zahvata, bolnih menstruacija i glavobolje (uključujući migrenu).Brufen može da se koristi kod povreda mekih tkiva kao što su uganuća i istegnuća. ','600 mg ibuprofena','Tablete');
INSERT INTO "proizvod" VALUES ('Paracetamol',2,3.0,120,'Analgetik','','Peroralno','Bosnalijek d.d.','Tablete bijele do gotovo bijele boje, okruglog oblika, sa diobenom crtom na jednoj strani.PARACETAMOL BOSNALIJEK je blagi analgetik i antipiretik i preporučuje se za liječenje većine bolnih i febrilnih stanja, naprimjer: glavobolje uključujući i migrenu i tenzione glavobolje, zubobolje, bola u leđima, reumatskog bola i bola u mišićima, dismenoreje, grlobolje, te za olakšanje povišene tjelesne temperature i bolova uzrokovanih s prehladom i gripom. Također, preporučuje se za simptomatsko olakšanje bola kod blažeg oblika artritisa.','Paracetamola 500,00 mg','Tablete');
INSERT INTO "proizvod" VALUES ('Analgin',3,2.0,151,'Analgetik','',NULL,'Bosnalijek d.d.','','',NULL);
INSERT INTO "prodani" VALUES (1,2,'Paracetamol',1,'root','2021-09-05');
INSERT INTO "prodani" VALUES (2,3,'Analgin',1,'root','2021-09-05');
INSERT INTO "korisnici" VALUES ('root','1q2w3e4r',NULL);
COMMIT;
