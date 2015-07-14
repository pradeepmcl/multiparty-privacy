CREATE TABLE employee (
  id INT NOT NULL auto_increment, 
  name VARCHAR(50) NOT NULL,
  joining_date DATE NOT NULL,
  salary DOUBLE NOT NULL,
  ssn VARCHAR(30) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE image (
  id INT NOT NULL auto_increment, 
  name VARCHAR(50) NOT NULL,
  description VARCHAR(2048) NOT NULL,
  PRIMARY KEY (id)
);

insert into image(name, description) values 
  ('family-lowSens.png', 'Three members of a family (A, B, and C) took the picture below...'),
  ('family-medSens.png', 'Three members of a family (A, B, and C) took the picture below...'),
  ('family-medSens.png', 'Three members of a family (A, B, and C) took the picture below...'),
  ('friends-lowSens.png', 'Three friends (A, B, and C) took the picture below...'),
  ('friends-medSens.png', 'Three friends (A, B, and C) took the picture below...'),
  ('friends-highSens.png', 'Three friends (A, B, and C) took the picture below...'),
  ('colleagues-lowSens.png', 'Three colleagues (A, B, and C) took the picture below...'),
  ('colleagues-medSens.png', 'Three colleagues (A, B, and C) took the picture below...'),
  ('colleagues-highSens.png', 'Three colleagues (A, B, and C) took the picture below...');