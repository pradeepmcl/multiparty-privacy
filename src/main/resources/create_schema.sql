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

CREATE TABLE policy (
  id INT NOT NULL auto_increment, 
  description VARCHAR(512) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE argument (
  id INT NOT NULL auto_increment,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE scenario (
  id INT NOT NULL auto_increment,
  image_id INT NOT NULL,
  policy_id INT NOT NULL,
  argument_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (image_id) REFERENCES image(id),
  FOREIGN KEY (policy_id) REFERENCES policy(id),
  FOREIGN KEY (argument_id) REFERENCES argument(id)
);