#  Mining Version Histories for Detecting Code Smells
For Ase16 Week 6

## Paper

Published in: IEEE Transactions on Software Engineering ( Volume: 41, Issue: 5, May 1 2015 ) Mining Version Histories for Detecting Code Smells
Cited 27 times.

## Key Words
 
ii1 HIST - an approach exploiting change history information to detect instances of five different code smells - this study's propriatery software to detect Divergent Change, Shotgun Surgery, Parallel Inheritance, Blob, and Feature Envy.

ii2 

ii3 

ii4 

## Notes

iii1 Motovation:  

iii2 Study instruments:  Firs a group of 20 open source projects.  2nd a group of developers to asses whether the code smells actually cause problems in the design, or are they appropriate.

iii3 Vizualizations: A good one representing the process of code change, code extraction, analysis.  Also a really good one of shotgun surgury.

iii4 Future Work:  About 1/4 of the smells identified by HIST were NOT identified by the developers as problems.  Thats probably an interesting area to study, but it would be tough because you need to create the test cases and recruite the developers.

iii5 Parallel Inheritance means that two or more class hierarchies evolve by adding code to both classes at the same time.

iii6 Feature Envy may manifest itself when a method of a class tends to change more frequently with methods of other classes rather than with those of the same class.

iii7 Divergent Change, Shotgun Surgery, and Parallel Inheritance—are symptoms that can be intrinsically observed from the project’s history

iii8 Blob - Is Also known as God Class - one class monopolizes the processing, and other classes primarily encapsulate data


## Improvements and Connections

iv1 Some of the visualizations of the first study were really boring.  I don't need to see the outline of a class that had code smells.  It's just a list of functions.

iv2 The second study seemed to be a pretty subjective measure, but it was mitigated by using 20 developers (more than the last several papers Ive seen) and by recognizing the potential problems in the Threats to Validity.

iv3 Not sure how much use the Related Work section is, unless it's just a list to show that the authors have done their reading.  I'm going to use it for clues to fill in details around my own paper, so maybe that's the point:  Provide context for the reader.

