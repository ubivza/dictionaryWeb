create table eng_word(id int primary key generated always as identity, word int not null unique); --int(100000)
create table eng_translate(row_id int primary key generated always as identity,
            eng_word_id int references eng_word(id) on delete cascade, translate_word varchar(50));


create table spanish_word(id int primary key generated always as identity, word varchar(4) not null unique);
create table spanish_translate(row_id int primary key generated always as identity,
            spanish_word_id int references spanish_word(id) on delete cascade, translate_word varchar(50));


insert into eng_word(word) values(12351);
insert into eng_word(word) values(12352);
insert into eng_word(word) values(12353);
insert into eng_word(word) values(12354);
insert into eng_word(word) values(12355);
insert into eng_word(word) values(12356);

insert into eng_translate(eng_word_id, translate_word) values(1, 'derevo');
insert into eng_translate(eng_word_id, translate_word) values(2, 'kopiya');
insert into eng_translate(eng_word_id, translate_word) values(2, 'kop');
insert into eng_translate(eng_word_id, translate_word) values(2, 'kopiya');
insert into eng_translate(eng_word_id, translate_word) values(1, 'kopiya');
insert into eng_translate(eng_word_id, translate_word) values(3, 'bobi');

insert into spanish_word(word) values('have');
insert into spanish_word(word) values('name');

insert into spanish_translate(spanish_word_id, translate_word) values(1, 'imet');
insert into spanish_translate(spanish_word_id, translate_word) values(2, 'imya');

