#  Summary 
For Ase16 Week 6 An exploratory study of the impact of code smells on software change-proneness

## Paper

2009 "An exploratory study of the impact of code smells on software change-proneness" <em>16th Working Conference on Reverse Engineering WCRE 2009</em> pp. 75-84 2009. 
Cited 123 times


## Key Words
 
ii1 DECOR (De-fect dEtection for CORrection) - a domain-specific language to specify code smells and antipatterns and methods to detect their occurrences au- tomatically.

ii2  

ii3 

ii4 

## Notes

iii1 Goal - to investigate the relation be- tween the presence of smells in classes and class change- proneness. 

iii2 Quality focus - the increase of maintenance effort and cost due to the presence of code smells.

iii3 Context - the change history of two systems, Azureus (an open source BitTorrent client written in Java.) and Eclipse (java IDE)

iii4 RQ1: What is the relation between smells and change proneness?  For classes with smells to change is six times higher or more than for classes without smells.

iii5 RQ2: What is the relation between the number of smells in a class and its change-proneness?  For all the analysed releases change-prone classes are those with a higher number of smells. For Eclipse, results are significant (although with a small effect size), except for the 3.0 release series, where differences are not significant,

iii6 RQ3: What is the relation between particular kinds of smells and change proneness? In Azureus, only the smell NotAbstract has a significant impact on change proneness in more than 75% of releases. AbstractClass and LargeClass resulted to be significant in more than 50% of the releases (5 out of 9). In Eclipse, the smells that have a significant effect on change-proneness for 75% of the releases or more are HasChildren, MessageChain- sClass, and NotComplex. In summary, although results sometimes depend on the particular context—e.g., system analysed and particular release—we can reject H03, i.e., there are smells that are more related to others to changeproneness.

iii7 Odds ratio (OR) - indicates the likeli hood for an event to occur. The odds ratio is defined as the ratio of the odds p of an event occurring in one sample, i.e., the odds that classes with some smells underwent a change (experimental group), to the odds q of the same event occur- ring in the other sample, i.e., the odds that classes with no smell underwent a change (control group):

iii8

## Improvements and Connections

iv1 They go on a lot about their method of detecting code smells (DECOR), but there isn't a discussion of what that means.   There is also no analysis done with a competing tool.

iv2 This method of ferriting out code smells (DECOR) is mentioned in several of the later papers.  It woudl be good to find out more about it.

