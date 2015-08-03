insert into image(name, people, description) values 
  ('family-lowSens.png', 'Frank,Christopher,Dan', 'Moores brothers and their parents, wives and children take part in a photoshoot. Three of the brothers (Frank, Christopher, and Dan) use social media. Frank wants to upload the best photo from the photoshoot to his social media account. He talks with Christopher and Dan about the privacy policy they should employ for the photo.'),
  ('family-medSens.png', 'Aaina\'s father,Aaina\'s mother,Aaina\'s grandfather', 'Aaina\'s father took a photo during her birthday, showing Aiana, her mother, grapdfather, neices, and nephews. Few days after, Aaina\'s father wants to share this photo on social media. He discusses with Aaina\'s mother and grandfather what privacy policy they should employ for the photo.'),
  ('family-medSens.png', 'Mary,Sophia,Charles', 'Mary, Sophia, and Charles attend their mother\'s funeral. Another family member takes some photos and circulates them among the family members. Mary wants to share this photo on social media and asks her siblings about the appropriate privacy policy for the photo.'),
  ('friends-lowSens.png', 'Tim,Ashley,Jerry', 'Tim, Ashley, and Jerry just graduated. Tim\'s father took this photo after the graduation ceremony. Tim wants to upload this photo to his social media profile. He asks his two friends what privacy policy they should apply to the photo.'),
  ('friends-medSens.png', 'Paul,Tom,Tyler', 'Paul, Tom, and Tyler took a photo moments before a concert of their favorite band. Paul wants to share that photo and asks the others what privacy policy they should apply to the photo.'),
  ('friends-highSens.png', 'Mark (the groom),Alex,John', 'Three friends, Mark (the groom), Alex, and John, go on a boat in Ibiza during a bachelor party. They get drunk and meet some girls. Alex took a photo during that party that he wants to share on social media. The friends discuss what should be the privacy policy for the photo.'),
  ('colleagues-lowSens.png', 'Maria,Bonita,Felipe', 'Maria, Bonita, and Felipe, three junior employees in a company, attend a business lunch in which they meet their seniors. One of the other employees took the following photo and sent it to Maria. Maria wants to share this photo on social media and asks Bonita and Felipe what privacy policy they think she should apply to the photo.'),
  ('colleagues-medSens.png', 'Laura,Mary,April', 'Laura, Mary, and April are having a smoke break during their office hours. Another colleague takes a photo of them and sends it to Laura. Laura likes the photo and wants to share it on social media. She asks Mary and April about the privacy policy she should apply to the photo.'),
  ('colleagues-highSens.png', 'Jerry,Laura,Sabrina', 'Jerry, Laura, and Sabrina work together in a company. They were asked to attend the Christmas party dressed. However, a guy in their company (the one in pink dress) brought the whole dressing to a new level. They took a photo at the party and a few days after the party, Jerry wants to upload the following photo to the social media. Laura, Sabrina, and Jerry discuss what privacy policy is appropriate for the photo.');
  
insert into policy(name, description) values
  ('all', 'Share with all'),
  ('common', 'Share with common friends of A, B, and C'),
  ('self', 'Share among themselves (A, B, and C) only');
  
insert into argument(name, description) values 
  ('positive consequence', 'An argument from positive consequence...'),
  ('negative consequence', 'An argument from negative consequence...'),
  ('analogy', 'An argument from analogy...');
