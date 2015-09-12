insert into image(name, stakeholders, image_description, sharer, sharing_description) values
  ('family-lowSens-posSenti.png', 'Frank,Christopher,Dan', 'Moore brothers and their parents, wives and children took part in a photoshoot. The following is the best picture from the pictureshoot.', 'Frank', 'Frank wants to upload the picture to his social media account.'),
  ('family-lowSens-negSenti.jpg', 'Jerry,Timmy''s mother,April''s mother', 'Jerry (the grandfather dressed as Santa), Timmy (Jerry''s grandson), and April (Jerry''s granddaughter) took a picture during Christmas night.', 'Jerry', 'Jerry wants to upload the picture to his social media account.'),
  ('family-highSens-posSenti.jpg', 'Philip,Dolores,Ann', 'Dolores and Philip decide to have their baby, Rose, at home with the help of Ann, who is Dolores'' sister and a doula. They took the picture below during the labor.', 'Philip', 'A few days after Rose was born, Philip wants to upload the picture to his social media account.'),
  ('family-highSens-negSenti.png', 'Mary,Sophia,Charles', 'Mary, Sophia, and Charles attend their mother''s funeral. Another family member takes some pictures and circulates them among the family members.', 'Mary', 'Mary wants to upload the picture to her social media account.'),
  ('friends-lowSens-posSenti.png', 'Tim,Ashley,Jerry', 'Tim, Ashley, and Jerry just graduated. Tim''s father took the picture below after the graduation ceremony.', 'Tim', 'Tim uploads the picture to his social media account.'),
  ('friends-lowSens-negSenti.jpg', 'Santosh,Arun,Nitin', 'Santosh lent his motorbike to his friends Arun and Nitin. They wanted to take photos performing some stunts. Unfortunately, while performing a stunts, Arun and Nitin had a minor accident. Santosh took the photo below at that very moment.', 'Santosh', 'Santosh wants to upload the picture to his social media account.'),
  ('friends-highSens-posSenti.png', 'Mark,Alex,John', 'Three friends, Mark (the groom), Alex, and John, go on a boat in Ibiza during a bachelor party. They get drunk and meet some girls. The following is one of the pictures Alex took during that party.', 'Alex', 'Alex wants to upload the picture to his social media account.'),
  ('friends-highSens-negSenti.jpg', 'Vanessa,', 'The picture below is from an end-of-semester party Vanessa (the girl wearing blue shirt with USA flag on it) hosted. The party went wild and a neighbor called the police. There was some tension after the police arrived and a few people invited to the party were arrested. The picture also shows Vanessa''s friends Natasha (the girl on the floor) and Jessica (the girl at the very left of the picture). Vanessa and her friends felt that the police abused their authority.', 'Vanessa', 'A few days after the incident, Vanessa wants to upload the picture to her social media account.'),
  ('colleagues-lowSens-posSenti.png', 'Maria,Bonita,Felipe', 'Maria, Bonita, and Felipe, three junior employees in a company, attend a business lunch in which they meet their seniors. One of the other employees took the following picture and sent it to Maria.', 'Maria', 'Maria wants to upload the picture to her social media account.'),
  ('colleagues-lowSens-negSenti.jpg', 'Aiko,Ichiro,Katsu', 'Aiko (C in the picture) took the picture below with her friends Ichiro and Katsu and a French volunteer at the tsunami relief center.', 'Aiko', 'After returning home, Aiko wants to upload the picture to her social media account.'),
  ('colleagues-highSens-posSenti.png', 'Jerry,Laura,Sabrina', 'Jerry, Laura, and Sabrina work together in a company. They were asked to attend the Christmas party dressed. However, a guy in their company (the one in pink dress) brought the whole dressing to a new level. They took the following picture at the party.', 'Jerry', 'A few days after the party, Jerry wants to upload the picture to his social media account.'),
  ('colleagues-highSens-negSenti.jpg', 'Bryan,Martin,Sophia', 'The hospital where Bryan, Martin, and Sophia work has recently changed its shift policy making shifts much longer. Doctors complain that these shifts leaves them exhausted. During one such long shifts, at 4am, Bryan takes a photo of his two colleagues Martin and Sophia sleeping while they wait for another patient to come to emergencies.', 'Bryan', 'A few days after the picture was taken, Bryan wants to upload the picture to his social media account.');

insert into policy(name, description) values
  ('all', 'Share with all (anyone on or off social media can see the picture).'),
  ('common', 'Share with common friends (only common friends of stakeholders can see the picture).'),
  ('self', 'Share among themselves (only stakeholders can see the picture).');

insert into argument(image_id, name, description) values
  (1, 'positive_consequence', 'We took this picture so that our loved ones can see it an remember us.'),
  (1, 'negative_consequence', 'Our children appear in this picture; what others can do with this picture concerns me.'),
  (1, 'exceptional_case', 'This is not like any other picture; we hired a photographer to take it.'),
  (2, 'positive_consequence', 'It was a lovely evening and this picture brings back good memories.'),
  (2, 'negative_consequence', 'Poor Timmy got scared. I don''t think it is fair to Timmy to share a photo of him crying and scared of his grandpa.'),
  (2, 'exceptional_case', 'This was the first time Jerry got dressed as Santa Claus.'),
  (3, 'positive_consequence', 'This picture shows that Rose was born peacefully at our home surrounded by those who love us.'),
  (3, 'negative_consequence', 'The idea of giving birth at home was to be at a private place surrounded by only the people we love.'),
  (3, 'exceptional_case', 'This is not like any other family photo; this shows that Rose is coming to our lives.'),
  (4, 'positive_consequence', 'Many people knew our mother and loved her, including our friends. When people see this photo they will remember her and see that we all were there to say goodbye.'),
  (4, 'negative_consequence', 'This photo may seem very inappropriate for many people.'),
  (4, 'exceptional_case', 'This isn''t like any other photo, we were saying goodbye to mom.'),  
  (5, 'positive_consequence', 'People we know will be happy to see that we are finally done with college.'),
  (5, 'negative_consequence', 'Our gestures are not appropriate for a moment like this; people might think that we did not take our college time seriously.'),
  (5, 'exceptional_case', 'This isn''t like any of our other photos. It was our graduation, which happens only once in our lifetimes.'),
  (6, 'positive_consequence', 'Fortunately, none of us got hurt. This picture makes anyone who sees it laugh out loud.'),
  (6, 'negative_consequence', 'People looking at this picture may think that we are reckless drivers, which is not true.'),
  (6, 'exceptional_case', 'Motorbike stunts is not something we do everyday and a perfect shot like this is one in a million.'),
  (7, 'positive_consequence', 'This was one of the best day of our lives; we should share these good memories with others.'),
  (7, 'negative_consequence', 'There were some girls in the party; people might understand things the wrong way.'),
  (7, 'exceptional_case', 'This isn''t like any of our other photos. This was Mark''s bachelor party!'),
  (8, 'positive_consequence', 'Police arrested some of us in the party. This picture shows that police abused their power and arrested some of us for a silly reason.'),
  (8, 'negative_consequence', 'We don''t deserve what happened and this photo brings back those traumatic memories.'),
  (8, 'exceptional_case', 'This pictures reminds us of one of the wildest party we have had.'),
  (9, 'positive_consequence', 'This picture shows that we are making good progress in our careers.'),
  (9, 'negative_consequence', 'This was a professional event and our seniors might want to keep it private.'),
  (9, 'exceptional_case', 'This is an exceptional event since we attended a professional party for the first time.'),
  (10,'positive_consequence', 'The picture shows the difficult situation in which the survivors live. Sharing this can encourage people to help.'),
  (10, 'negative_consequence', 'Tsunami was a disaster and our gestures are not appropriate. People may get the wrong idea.'),
  (10, 'exceptional_case', 'This was one of the worst natural disasters. This picture shows that people from all over the world volunteer to help.'),
  (11, 'positive_consequence', 'People think that I have a boring life because I work at a boring place; this will prove them wrong.'),
  (11, 'negative_consequence', 'This is embarrassing; people will pick on us because of this picture.'),
  (11, 'exceptional_case', 'This is an exceptional event since a Christmas party happens only once a year.'),
  (12, 'positive_consequence', 'This new shift policy is too demanding. A photo like this can convince the management to change the policy.'),
  (12, 'negative_consequence', 'If people think that we sleep on our job, they won''t trust the hospital.'),
  (12, 'exceptional_case', 'A doctor sleeping on a chair is exceptional. This photo shows that what is happening in this hospital is not normal.');

 insert into spotcheck_question(name, question, options, options_type, answer) values
   ('which_not_sns', 'Which of the following is not a social network site?', 'Facebook,Google Plus,Windows,Twitter', 'radio', 'Windows'),
   ('which_not_browser', 'Which of the following is not a Web browser?', 'PowerPoint,Chrome,Internet Explorer,Safari', 'radio', 'PowerPoint'),
   ('which_not_mobileOs', 'Which of the following is not a mobile operating system', 'Android,Oracle,Blackberry,iOS', 'radio', 'Oracle');
   