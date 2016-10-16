# An Exploratory Study of the Impact of Code Smells on Software Change-proneness 
For Ase16 Week 5

## Paper

2009 F. Khomh, M. Di Penta and Y.-G. Guéhéneuc, "An exploratory study of the impact of code smells on software change-proneness" in Proceedings of the 16th Working Conference on Reverse Engineering (WCRE), October 2009, IEEE CS Press.
Cited 126 times

## Key Words
 
ii1 DECOR - Code analysis tool that is used here and in later papers.  Probabaly need ot learn more about that.  Found a paper with the title  "Decor: A method for the specification and detection of code and design smells"

ii2 t-test:  any statistical hypothesis test in which the test statistic follows a Student's t-distribution under the null hypothesis. It can be used to determine if two sets of data are significantly different from each other.  A t-test is most commonly applied when the test statistic would follow a normal distribution if the value of a scaling term in the test statistic were known. When the scaling term is unknown and is replaced by an estimate based on the data, the test statistics (under certain conditions) follow a Student's t distribution.

ii3 Mann - Whitney:  a nonparametric test of the null hypothesis that two samples come from the same population against an alternative hypothesis, especially that a particular population tends to have larger values than the other.  https://en.wikipedia.org/wiki/Mann%E2%80%93Whitney_U_test

ii4 Null hypothese: a general statement or default position that there is no relationship between two measured phenomena, or no association among groups

ii5 Logistic regression:  measures the relationship between the categorical dependent variable and one or more independent variables by estimating probabilities using a logistic function, which is the cumulative logistic distribution.
## Notes

iii1 Goal:  no previous work has contrasted the change-proneness of classes with code smells with this of other classes to study empirically the impact of code smells on this aspect of software evolution.  

iii2 Contribution: We show that code smells do have a negative impact on classes, that certain kinds of smells do impact classes more than others, and that classes with more smells exhibit higher change-proneness.

iii3 Study Instruments:  They are looking at 29(!) code smells using the DECOR model that is mentioned in other papers.

iii4 Informative Graphs:  A great deal of detail is in graphs in the appendix.  I like this way of publishing the results without breaking up the text.  The information in appendix A (Detailled Definitions of the Design Smells) along with D helped describe the details per release. 

iii4 


## Improvements and Connections

iv1 Also uses DECOR, which is referenced in the later papers, too.

iv2 Of the 3 hypothses, the study attempts to reject all 3.  This seems like a clumsey way to communicate the point.  Instead of saying "These three questions should be 'yes'"  it contends "These 3 questions should be 'no'."  Is there a good reason for this?

iv3 Typo:  Note at the end: Data. All data as well as a technical report with more detailed results are available on the Web.  The link points to the project home pages, not anything about a detailed report. The report is at http://www.ptidej.net/downloads/experiments/prop-WCRE09

iv4 It's interesting that there's a plug for one of the funding agenceys.  Makes me wonder if that's common.

