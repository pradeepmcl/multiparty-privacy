CREATE TABLE image (
  id INT NOT NULL auto_increment, 
  name VARCHAR(50) NOT NULL,
  stakeholders VARCHAR(100) NOT NULL,
  image_description VARCHAR(1024) NOT NULL,
  sharer VARCHAR(50) NOT NULL,
  sharing_description VARCHAR(1024) NOT NULL,
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
  image_id INT NOT NULL,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (image_id) REFERENCES image(id)
);

CREATE TABLE spotcheck_question (
  id INT NOT NULL auto_increment,
  name VARCHAR(200) NOT NULL,
  question VARCHAR(1024) NOT NULL,
  options VARCHAR(1024) NOT NULL,
  options_type VARCHAR(20) NOT NULL,
  answer VARCHAR(256) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE scenario (
  id INT NOT NULL auto_increment,
  image_id INT NOT NULL,
  policy_a_id INT,
  argument_a_id INT NOT NULL,
  policy_b_id INT,
  argument_b_id INT NOT NULL,
  policy_c_id INT,
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

CREATE TABLE scenario_bundle (
  id INT NOT NULL auto_increment,
  scenarios_csv VARCHAR(50) NOT NULL,
  num_completed INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE turker_presurvey_response (
  id INT NOT NULL auto_increment,
  mturk_id VARCHAR(20) NOT NULL,
  response_time DATETIME NOT NULL,
  gender VARCHAR(20) NOT NULL,
  age VARCHAR(20) NOT NULL,
  education VARCHAR(20) NOT NULL,
  socialmedia_frequency VARCHAR(20) NOT NULL,
  sharing_frequency VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE turker_picturesurvey_response (
  id INT NOT NULL auto_increment,
  mturk_id VARCHAR(20) NOT NULL,
  response_time DATETIME NOT NULL,
  scenario_bundle_id INT NOT NULL,
  scenario_id INT NOT NULL,
  image_sensitivity VARCHAR(20) NOT NULL,
  image_sentiment VARCHAR(20) NOT NULL,
  image_relationship VARCHAR(20) NOT NULL,
  image_people_count VARCHAR(20) NOT NULL,
  case1_policy VARCHAR(20) NOT NULL,
  case1_policy_other VARCHAR(500),
  case1_policy_justification VARCHAR(2000) NOT NULL,
  case2_policy VARCHAR(20) NOT NULL,
  case2_policy_other VARCHAR(500),
  case2_policy_justification VARCHAR(2000) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (scenario_bundle_id) REFERENCES scenario_bundle(id),
  FOREIGN KEY (scenario_id) REFERENCES scenario(id)
);

CREATE TABLE turker_postsurvey_response (
  id INT NOT NULL auto_increment,
  mturk_id VARCHAR(20) NOT NULL,
  response_time DATETIME NOT NULL,
  scenario_bundle_id INT NOT NULL,
  sharing_experience VARCHAR(20) NOT NULL,
  conflict_experience VARCHAR(20) NOT NULL,
  conflict_experience_type VARCHAR(20),
  relationship_importance VARCHAR(20) NOT NULL,
  sensitivity_importance VARCHAR(20) NOT NULL,
  sentiment_importance VARCHAR(20) NOT NULL,
  no_preference_confidence VARCHAR(20) NOT NULL,
  preference_confidence VARCHAR(20) NOT NULL,
  preference_argument_confidence VARCHAR(20) NOT NULL,
  additional_attributes VARCHAR(500),
  email VARCHAR(50),
  other_comments VARCHAR(2000),
  completion_code VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (scenario_bundle_id) REFERENCES scenario_bundle(id)
);

ALTER TABLE turker_postsurvey_response ADD INDEX mturk_id (mturk_id);