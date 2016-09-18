#  “Blending conceptual and evolutionary couplings to support change impact analysis in source code"
For Ase16 Week 4

## Paper

2010  H. Kagdi, M. Gethers, D. Poshyvanyk, and M. Collard, “Blending conceptual and evolutionary couplings to support change impact analysis in source code,” in Reverse Engineering (WCRE), 2010 17th Working Conference on, 2010, pp. 119–128.  
Cited 28 times

## Key Words
 
ii1 Impact Analysis (IA) - the determination of potential effects to a subject system resulting from a proposed software change.

ii2 Conceptual couplings - capture the extent to which domain concepts and software artifacts are related to each other. This information is derived using Information Retrieval based analysis of textual software artifacts that are found in a single version of software (e.g., comments and identifiers in a single snapshot of source code). This analysis focused on a single version

ii3 Information Retrieval (IR) - analysis of textual software artifacts that are found in a single version of software (e.g., comments and identifiers in a single snapshot of source code).

ii4 Ripple Effects - a phenomenon that affects other parts of a system on account of a proposed change.

ii5 Mining Software Repositories (MSR) - a broad class of investigations into the examination of software repositories.  The premise of MSR is that empirical and systematic investigations of repositories will shed new light on the process of software evolution, and the changes that occur over time, by uncovering pertinent information, relationships, or trends about a particular evolutionary characteristic of the system.

## Notes

iii1 Motovational Statement: The core research philosophy behind our approach is that present+past of software systems leads to better IA. For IA, both single (present) and multiple versions (past) analysis methods have been utilized independently, but their combined use has not been previously investigated.

iii2 Hypothosis:  By using analysis on code as it changes, we can get better analysis than a single snapshot in time.

iii3 New Results:  The results of the study show that the disjunctive combination of IR and MSR techniques, across several cut points (impact set sizes), provides statistically significant improvements in accuracy over either of the two standalone techniques. For example, the disjunctive method reported improvements in recall values of up to 20% over the conceptual technique in KOffice and up to 45% improvement over the evolutionary technique in iBatis. These results are encouraging considering that the combinations do not require an overly complex blending of two separate approaches.

iii4 Sampling procedures - CVS logs for hundreds of changes in Apache httpd, ArgoUML, iBatis, and KOffice


## Improvements and Connections

iv1 This statement makes no sense.  "Identifiers used by programmers for names of classes, methods, or attributes in source code or other artifacts contain important information and account for approximately half of the source code in software"  pg 2

iv2  This paper indicates that by looking at changes over time, a more accurate picture of the impact of code changes can be built.  It seems to be the precursor to paper 1 because that paper is concerned with similar review, but the focus is narrowed to specific code smells.

