#  Summary An exploratory study of the impact of antipatterns on class change- and fault-proneness
For Ase16 Week 2

## Paper

Khomh, Foutse, et al. "An exploratory study of the impact of antipatterns on class change-and fault-proneness." Empirical Software Engineering 17.3 (2012): 243-275.
Cited 95 times.

## Key Words
 
ii1 Anti-Pattern:  A common design choice that is often used, but is actually a poor choice.  Patterns are reusable best practices.  Anti-patterns are oftne-found worst patterns.

ii2 Change-Prone (or fault-prone):  A section of software that is more common to change over time.  Well-written software can change as it is iproved or expanded, but poorly written software must be revisted more often to work out the bugs that come up later.

ii3 Odds Ratio (OR):  The likelyhood of an event to occur in two samples.  OR<1 means it's more likely to occur in the first same, OR>1 means it's more likely in the second sample, and OR=1 means it's equally likely in both.

ii4 Construct Validity:  conserns the relation between theory and obervation.  An invalid construct will not represent the actualy facts gathered.  The map is not the terrain.

## Notes

iii1 Motovation:  Study whether classes with anti patterns are more likely to need software fixes as time goes on.  Also looks at the effects of class size.

iii2 Study instruments:  Code Repos for 4 Java projects:  Eclipse, Rhino, Mylyn, and ArgoUML.  Comparisons are made between each of the major point releases.

iii3 Vizualizations: Good graph of the number of different  classes participating in anti patterns.  Eclipse was high (80%) The whole time, but the others were consistantly lower.  Mylyn steadily increased over 18 releases.

iii4 Future Work:  There can be more research by using differnt types of project (non open-source), different languages, and different anti-patterns.  This study limited itself to 13 antipatterns.


## Improvements and Connections

iv1 The week one paper referenced the DECOR model that seems to be a project from the people who wront this paper.

iv2 Although they both are trying ot detect bad code smells, there doesn't seem to be an idea of which ones are really bad, and which ones are irritating.  It would be interesting to see how big a code change is needed for eachof these smells, or to compare the major bug vs minor bug impact per code smell found and fixed.

