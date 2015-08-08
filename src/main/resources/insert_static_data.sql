insert into image(name, stakeholders, image_description, sharer, sharing_description) values
  ('family-lowSens.png', 'Frank,Christopher,Dan', 'Moore brothers and their parents, wives and children took part in a pictureshoot. The following is the best picture from the pictureshoot.', 'Frank', 'Frank wants to upload the picture above to his social media account.'),
  ('family-medSens.png', 'Aaina''s father,Aaina''s mother,Aaina''s grandfather', 'Aaina''s father took the picture below during her birthday, showing Aiana and her mother, grandfather, nieces, and nephews.', 'Aaina''s father', 'Few days after the picture was taken, Aaina''s father wants to share this picture on social media.'),
  ('family-medSens.png', 'Mary,Sophia,Charles', 'Mary, Sophia, and Charles attend their mother''s funeral. Another family member takes some pictures and circulates them among the family members.', 'Mary', 'Mary wants to share the above picture on social media.'),
  ('friends-lowSens.png', 'Tim,Ashley,Jerry', 'Tim, Ashley, and Jerry just graduated. Tim''s father took the picture below after the graduation ceremony.', 'Tim', 'Tim wants to upload this picture to his social media profile.'),
  ('friends-medSens.png', 'Paul,Tom,Tyler', 'Paul, Tom, and Tyler took the following picture moments before a concert of their favorite band.', 'Paul', 'Paul wants to share that picture on social media.'),
  ('friends-highSens.png', 'Mark,Alex,John', 'Three friends, Mark (the groom), Alex, and John, go on a boat in Ibiza during a bachelor party. They get drunk and meet some girls. The following is one of the pictures Alex took during that party.', 'Alex', 'Alex wants to share this picture on social media.'),
  ('colleagues-lowSens.png', 'Maria,Bonita,Felipe', 'Maria, Bonita, and Felipe, three junior employees in a company, attend a business lunch in which they meet their seniors. One of the other employees took the following picture and sent it to Maria.', 'Maria', 'Maria wants to share this picture on social media.'),
  ('colleagues-medSens.png', 'Laura,Mary,April', 'Laura, Mary, and April are having a smoke break during their office hours. Another colleague takes a picture of them and sends it to Laura.', 'Laura', 'Laura likes the picture and wants to share it on social media.'),
  ('colleagues-highSens.png', 'Jerry,Laura,Sabrina', 'Jerry, Laura, and Sabrina work together in a company. They were asked to attend the Christmas party dressed. However, a guy in their company (the one in pink dress) brought the whole dressing to a new level. They took the following picture at the party.', 'Jerry', 'A few days after the party, Jerry wants to share this picture on social media.');

insert into policy(name, description) values
  ('all', 'Share with all (anyone on or off social media can see the picture).'),
  ('common', 'Share with common friends (only common friends of stakeholders can see the picture).'),
  ('self', 'Share among themselves (only stakeholders can see the picture).');

insert into argument(image_id, name, description) values
  (1, 'positive_consequence', 'We took this picture so that our loved ones can see it an remember us.'),
  (1, 'negative_consequence', 'Our children appear in this picture; what others can do with this picture concerns me.'),
  (1, 'exceptional_case', 'This is not like any other picture; we hired a photographer to take it.'),  
  (2, 'positive_consequence', 'Everyone had a great time in the party; everyone who knows us will enjoy seeing this picture.'),
  (2, 'negative_consequence', 'Our children appear in this picture; what others can do with this picture concerns me.'),
  (2, 'exceptional_case', 'This is not like any other photo; it was Aaina''s party!'),  
  (3, 'positive_consequence', 'Many people knew our mother and loved her, including our friends. When people see this photo they will remember her and see that we all were there to say goodbye.'),
  (3, 'negative_consequence', 'This photo may seem very inappropriate for many people.'),
  (3, 'exceptional_case', 'This isn''t like any other photo, we were saying goodbye to mom.'),  
  (4, 'positive_consequence', 'People we know will be happy to see that we are finally done with college.'),
  (4, 'negative_consequence', 'Our gestures are not appropriate for a moment like this; people might think that we did not take our college time seriously.'),
  (4, 'exceptional_case', 'This isn''t like any of our other photos. It was our graduation, which happens only once in our lifetimes.'),  
  (5, 'positive_consequence', 'Everyone is talking about this concert; people should know that we were there.'),
  (5, 'negative_consequence', 'We skipped classes to go to the concert; we do not want our parents to find it.'),
  (5, 'exceptional_case', 'This isn''t like any of our other photos. This was taken at the concert of our favorite band.'),  
  (6, 'positive_consequence', 'This was one of the best day of our lives; we should share these good memories with others.'),
  (6, 'negative_consequence', 'There were some girls in the party; people might understand things the wrong way.'),
  (6, 'exceptional_case', 'This isn''t like any of our other photos. This was Mark''s bachelor party!'),
  (7, 'positive_consequence', 'This picture shows that we are making good progress in our careers.'),
  (7, 'negative_consequence', 'This was a professional event and our seniors might want to keep it private.'),
  (7, 'exceptional_case', 'This is an exceptional event since we attended a professional party for the first time.'),  
  (8, 'positive_consequence', 'This was a relaxing moment during a stressful day at the office; this photo shows how good the atmosphere is at our office.'),
  (8, 'negative_consequence', 'Bosses don''t like us having smoke breaks. If we share it with many people, there is a risk that one of our bosses will see it.'),
  (8, 'exceptional_case', 'This is an exceptional event; April was smoking for the first time!'),  
  (9, 'positive_consequence', 'People think that I have a boring life because I work at a boring place; this will prove them wrong.'),
  (9, 'negative_consequence', 'This is embarrassing; people will pick on us because of this picture.'),
  (9, 'exceptional_case', 'This is an exceptional event since a Christmas party happens only once a year.');  

 insert into spotcheck_question(name, question, options, options_type, answer) values
   ('which_not_sns', 'Which of the following is not a social network site?', 'Facebook,Google Plus,Windows,Twitter', 'radio', 'Windows'),
   ('which_not_browser', 'Which of the following is not a Web browser?', 'PowerPoint,Chrome,Internet Explorer,Safari', 'radio', 'PowerPoint'),
   ('which_not_mobileOs', 'Which of the following is not a mobile operating system', 'Android,Oracle,Blackberry,iOS', 'radio', 'Oracle');
   