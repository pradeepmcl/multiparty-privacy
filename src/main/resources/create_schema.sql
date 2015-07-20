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
  name VARCHAR(200) NOT NULL,
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
  policy_a_id INT NOT NULL,
  argument_a_id INT NOT NULL,
  policy_b_id INT NOT NULL,
  argument_b_id INT NOT NULL,
  policy_c_id INT NOT NULL,
  argument_c_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (image_id) REFERENCES image(id),
  FOREIGN KEY (policy_a_id) REFERENCES policy(id),
  FOREIGN KEY (argument_a_id) REFERENCES argument(id),
  FOREIGN KEY (policy_b_id) REFERENCES policy(id),
  FOREIGN KEY (argument_b_id) REFERENCES argument(id),
  FOREIGN KEY (policy_c_id) REFERENCES policy(id),
  FOREIGN KEY (argument_c_id) REFERENCES argument(id)
);

CREATE TABLE turker_response (
  id INT NOT NULL auto_increment,
  mturk_id VARCHAR(20) NOT NULL,
  response_time DATETIME NOT NULL,
  scenario_id INT NOT NULL,
  policy VARCHAR(200) NOT NULL,
  policy_other VARCHAR(500),
  policy_justification VARCHAR(2000) NOT NULL,
  completion_code VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (scenario_id) REFERENCES scenario(id)
);