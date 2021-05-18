drop table zeiteintrag;
drop table users;

Create table users (
	personal_Nr INT(6) auto_increment primary key,
    vorname VARCHAR(30) NOT NULL,
	nachname VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
	geburtsdatum date,
    soll_Arbeitszeit INT (2),
    password varchar(100) NOT NULL,
    ist_Admin boolean);

Insert into users values (1, "Admin", "Admin", "watchclock", "admin", "2000-01-01", 40,"$2a$12$DlfnjD4YgCNbDEtgd/ITeOj.jmUZpuz1i4gt51YzetW/iKY2O3bqa", true);
Insert into users values (2, "Juliane", "Ruß", "juliane.russ@gmx.de", "juliane.russ", "1998-01-21", 40,"$2a$12$DlfnjD4YgCNbDEtgd/ITeOj.jmUZpuz1i4gt51YzetW/iKY2O3bqa", false);


create table zeiteintrag(
	eintragNr Int(6) auto_increment primary key,
    datum date not null,
    von datetime,
    bis datetime,
    kommentar varchar(100),
    personal_Nr int(6),
    foreign key (personal_Nr) references users(personal_Nr));

##insert into zeiteintrag values(1, "20.04.21", "9.00.00", "15.00.00","", 1);

Select * from users;
Select * from zeiteintrag;