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
INSERT INTO "proizvod" VALUES ('Brufen',2,4.0,0,'Analgetik','Bijele, duguljaste film tablete.
Kartonska kutija sa 3 PVC/ALU blistera sa po 10 film tableta.','Peroralno','Abbott Logistics B.V.','Lijek BRUFEN se može kratkotrajno upotrebljavati u terapiji bolnih stanja poput zubobolje, bolova nakon operativnih zahvata, bolnih menstruacija i glavobolje (uključujući migrenu).
Brufen može da se koristi kod povreda mekih tkiva kao što su uganuća i istegnuća. ','600 mg ibuprofena','Tablete');
INSERT INTO "proizvod" VALUES ('Analgin',3,2.0,152,NULL,'',NULL,'Bosnalijek d.d.','','',NULL);
INSERT INTO "proizvod" VALUES ('Paracetamol',1112,3.0,120,'Analgetik','','Peroralno','Bosnalijek d.d.','Tablete bijele do gotovo bijele boje, okruglog oblika, sa diobenom crtom na jednoj strani.
PARACETAMOL BOSNALIJEK je blagi analgetik i antipiretik i preporučuje se za liječenje većine bolnih i febrilnih stanja, naprimjer: glavobolje uključujući i migrenu i tenzione glavobolje, zubobolje, bola u leđima, reumatskog bola i bola u mišićima, dismenoreje, grlobolje, te za olakšanje povišene tjelesne temperature i bolova uzrokovanih s prehladom i gripom. Također, preporučuje se za simptomatsko olakšanje bola kod blažeg oblika artritisa.','Paracetamola 500,00 mg','tablete');
COMMIT;
