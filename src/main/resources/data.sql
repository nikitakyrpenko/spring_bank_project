

INSERT INTO users (firstname,surname,email,passwords,telephone,fk_roles_users)
VALUES
("Freya","Rodriguez","dolor.Donec@etmagnaPraesent.net","1","715-0987","0"),
("Fleur","Morgan","Vivamus.rhoncus.Donec@lacusEtiam.net","1","612-4806","0"),
("Kim","Butler","ante.iaculis@Donecnibhenim.ca","451-2416","1","0"),
("Amaya","Davis","Vivamus.euismod.urna@ligulaAeneangravida.edu","1","338-6234","0"),
("Kellie","Jackson","rutrum.eu.ultrices@ultricesposuerecubilia.net","1","1-310-474-8458","0"),
("Clinton","Mckay","erat.volutpat@nibhDonec.net","168-6165","1","0"),
("Wesley","Merritt","lobortis@sitametrisus.ca","136-2862","1","0"),
("Cameron","Beasley","tellus.Suspendisse@magnamalesuadavel.com","1","770-3791","0"),
("Blossom","Cole","quis@auctorvelit.org","219-8751","1","0"),
("Mollie","Macias","mauris.Morbi.non@arcu.org","1-856-583-3206","1","0");

INSERT INTO accounts (expiration_date,balance,deposit_account_rate,fk_users_accounts,fk_accounts_type_accounts)
VALUES
("2020-09-06 13:48:20",88732,0.3,1,1),
("2020-05-13 14:10:42",55339,0.12,2,1),
("2019-07-19 16:00:38",21912,0.1,3,1),
("2019-08-19 21:35:21",18086,0.1,4,1),
("2019-03-27 15:27:37",22737,0.7,5,1),
("2019-04-01 05:36:09",12630,0.3,6,1),
("2020-03-13 10:27:05",10371,0.15,7,1);

INSERT INTO accounts (expiration_date,balance,credit_limit,credit_rate,fk_users_accounts,fk_accounts_type_accounts)
VALUES
("2020-09-06 13:48:20",88732,100000,0.12,8,2),
("2020-05-13 14:10:42",55339,100000,0.12,9,2),
("2019-07-19 16:00:38",21912,100000,0.12,10,2);

INSERT INTO operations (purpose,operation_date,transfer,fk_accounts_sender,fk_accounts_receiver) VALUES
 ("Aliquam erat volutpat.","2020-08-16 13:57:11",65305,1,2),
 ("ipsum leo","2020-11-22 16:19:32",19866,3,1),
 ("at, nisi.","2019-06-19 01:05:53",70987,1,3),
 ("eget metus. In","2019-08-12 22:10:12",57559,2,4),
 ("Vivamus molestie","2019-06-10 08:24:11",84986,4,1),
 ("sed","2020-10-23 04:56:20",98213,7,9),
 ("diam luctus lobortis.","2021-01-11 23:54:46",35457,8,3),
 ("nec, euismod in,","2019-05-31 01:49:21",64770,10,1),
 ("pretium","2020-03-12 12:12:36",3362,4,9),
 ("urna. Ut tincidunt","2020-11-07 23:19:28",45139,7,5),
 ("eu, ligula.","2020-03-17 13:44:01",42686,5,9),
 ("hendrerit","2021-01-03 16:07:56",32746,3,4),
 ("varius. Nam porttitor","2021-01-23 15:20:12",52567,1,2),
 ("ligula. Aenean","2019-07-28 09:51:20",49014,6,5),
 ("quis, pede.","2020-12-01 09:40:37",3977,8,9),
 ("a sollicitudin orci","2020-01-16 04:19:35",35720,9,1),
 ("iaculis","2020-03-03 12:06:32",66096,3,9),
 ("eu","2020-11-23 03:24:42",43977,4,2),
 ("tortor.","2019-02-12 07:28:52",80161,1,10),
 ("pellentesque,","2019-05-26 22:05:15",28397,9,2);


INSERT INTO charges
(charge,fk_charge_types_charge,fk_account_charge)
VALUES
(581,1,10),(967,0,1),(521,0,5),(660,0,1),(570,0,3),(933,0,4),(563,0,6),(303,1,8),(726,1,7),(444,1,9);
