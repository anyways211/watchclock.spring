drop table zeiteintrag;
drop table users;

Create table users (
	personalNr INT(6) auto_increment primary key,
    vorname VARCHAR(30) NOT NULL,
	nachname VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
	geburtsdatum date,
    soll_Arbeitszeit INT (2),
    passwort binary (100) NOT NULL,
    ist_Admin boolean,
    is_enabled boolean);

Insert into users values (1, "Annika", "Paß", "annika.pass@gmx.de", "annika.pass", "1998-01-21", 40,"abcd", true, true);
Insert into users values (2, "Juliane", "Ruß", "juliane.russ@gmx.de", "juliane.russ", "1998-01-21", 40,"$2y$12$zdF/W2rjaTusmEeHDQEsb.hOtCtYntdrC/D7foM72QoaXZTOj4Yuu", false, true);


create table zeiteintrag(
	eintragNr Int(6) auto_increment primary key,
    datum date not null,
    von datetime,
    bis datetime,
    kommentar varchar(100),
    personalNr int(6),
    foreign key (personalNr) references users(personalNr));

insert into zeiteintrag values(1, "20.04.21", "9.00.00", "15.00.00","", 1);

Select * from users;
Select * from zeiteintrag;