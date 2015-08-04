insert into image(name, people, image_description, sharer, sharing_description) values 
  ('family-lowSens.png', 'Frank,Christopher,Dan', 'Moore brothers and their parents, wives and children took part in a photoshoot. The following is the best photo from the photoshoot.', 'Frank', 'Frank wants to upload the photo above to his social media account.'),
  ('family-medSens.png', 'Aaina\'s father,Aaina\'s mother,Aaina\'s grandfather', 'Aaina\'s father took the photo below during her birthday, showing Aiana and her mother, grandfather, nieces, and nephews.', 'Aaina\'s father', 'Few days after the photo was taken, Aaina\'s father wants to share this photo on social media.'),
  ('family-medSens.png', 'Mary,Sophia,Charles', 'Mary, Sophia, and Charles attend their mother\'s funeral. Another family member takes some photos and circulates them among the family members.', 'Mary', 'Mary wants to share the above photo on social media.'),
  ('friends-lowSens.png', 'Tim,Ashley,Jerry', 'Tim, Ashley, and Jerry just graduated. Tim\'s father took the photo below after the graduation ceremony.', 'Tim', 'Tim wants to upload this photo to his social media profile.'),
  ('friends-medSens.png', 'Paul,Tom,Tyler', 'Paul, Tom, and Tyler took the following photo moments before a concert of their favorite band.', 'Paul', 'Paul wants to share that photo on social media.'),
  ('friends-highSens.png', 'Mark (the groom),Alex,John', 'Three friends, Mark (the groom), Alex, and John, go on a boat in Ibiza during a bachelor party. They get drunk and meet some girls. The following is one of the photos Alex took during that party.', 'Alex', 'Alex wants to share this photo on social media.'),
  ('colleagues-lowSens.png', 'Maria,Bonita,Felipe', 'Maria, Bonita, and Felipe, three junior employees in a company, attend a business lunch in which they meet their seniors. One of the other employees took the following photo and sent it to Maria.', 'Maria', 'Maria wants to share this photo on social media.'),
  ('colleagues-medSens.png', 'Laura,Mary,April', 'Laura, Mary, and April are having a smoke break during their office hours. Another colleague takes a photo of them and sends it to Laura.', 'Laura', 'Laura likes the photo and wants to share it on social media.'),
  ('colleagues-highSens.png', 'Jerry,Laura,Sabrina', 'Jerry, Laura, and Sabrina work together in a company. They were asked to attend the Christmas party dressed. However, a guy in their company (the one in pink dress) brought the whole dressing to a new level. They took the following photo at the party.', 'Jerry', 'A few days after the party, Jerry wants to share this photo on social media.');

insert into policy(name, description) values
  ('all', 'Share with all'),
  ('common', 'Share with common friends of A, B, and C'),
  ('self', 'Share among themselves (A, B, and C) only');
  
insert into argument(name, description) values 
  ('positive consequence', 'An argument from positive consequence...'),
  ('negative consequence', 'An argument from negative consequence...'),
  ('analogy', 'An argument from analogy...');
