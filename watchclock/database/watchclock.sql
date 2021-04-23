drop table zeiteintrag;
drop table user;

Create table user (
	personal_Nr INT(6) auto_increment primary key,
    vorname VARCHAR(30) NOT NULL,
	nachname VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
	geburtsdatum date,
    soll_Arbeitszeit INT (2),
    passwort varchar (50) NOT NULL,
    ist_Admin boolean);

Insert into user values (1, "Annika", "Paß", "annika.pass@gmx.de", "1998-01-21", 40,"sdrftgzhujikol", true);
Insert into user values (2, "Juliane", "Ruß", "juliane.russ@gmx.de", "1998-01-21", 40,"23e4rtgh", true); 
Insert into user values (3, "Sabine", "Lorenz", "sabine.lorenz@gmx.de", "1998-01-21", 40,"2wertgzh", true);
Insert into user values (4, "Jennifer", "Hildebrand", "jenni.hildebrand@gmx.de", "1998-01-21", 40,"adif", true);
Insert into user values (5, "Tobias", "Bruske", "Bruske@gmx.de", "1998-01-21", 40,"asrftght", true);
Insert into user values (6, "Max", "Mustermann", "max.muster@gmx.de", "1998-01-21", 40,"qwertz", false);

create table zeiteintrag(
	eintrag_Nr Int(6) auto_increment primary key,
    datum date not null,
    von datetime,
    bis datetime,
    kommentar varchar(100),
    personal_Nr int(6),
    foreign key (personal_Nr) references user(personal_Nr));
    
insert into zeiteintrag values(1, "20.04.21", "9.00.00", "15.00.00","", 1);

select * from user;
select * from zeiteintrag;