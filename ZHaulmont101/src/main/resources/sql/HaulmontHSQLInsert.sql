INSERT INTO groups (number, faculty_name) VALUES 
(601,'Informatics'),
(611,'Informatics'),
(701,'Economics'),
(711,'Economics');	

INSERT INTO students (last_name, first_name, middle_name, date_of_birth, student_group) VALUES
('Ivanov','Petr','Alexandrovich', '1998-03-05', 601),
('Petrov','Ivan','Alexeevich', '1997-09-07', 601),
('Sidorov','Alexey','Andereevich', '1999-05-15', 601),
('Bukin','Semen','Arkadievich', '1998-02-09', 611),
('Ivanova','Olga','Borisovna', '1997-04-22', 611),
('Petrova','Anna','Vasilievna', '1998-10-25', 611),
('Sidorova','Maria','Vitalievna', '1999-03-12', 701),
('Veretennikov','Evgeniy','Gennadievich', '1998-01-05', 701),
('Orlov','Petr','Grigorievich', '1999-07-10', 701),
('Dubov','Ivan','Denisovich', '1999-06-02', 711),
('Sergeev','Oleg','Efimovich', '1998-11-12', 711),
('Plushkin','Sergey','Egorovich', '1997-04-01', 711);
						
COMMIT;

