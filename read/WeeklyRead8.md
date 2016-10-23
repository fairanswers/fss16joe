#  When and Why Your Code Starts to Smell Bad
For Ase16 Week 8

Note:  This paper is from 2015, but it builds directly onthe week 0 paper, has the same authors, and I've already done a 2016 paper.

## Paper

Tufano, Michele, et al. "When and why your code starts to smell bad." Proceedings of the 37th International Conference on Software Engineering-Volume 1. IEEE Press, 2015.

Cited 30 times.

## Key Words
 
ii1 Context:  Size of project, likely number of developers, target environment (mobile, plugin, or stand alone app).

ii2 Workload tag measures how busy a developer was when introducing the bad smell. In particular, we measured the Workload of each developer involved in a project using time windows of one month, starting from the date in which the developer joined the project (i.e., performed the first commit). The Workload of a developer during one month is measured in terms of the number of commits she performed in that month.

ii3 Interestingly, this paper uses Cliffs Delta, with size settings of small < .33, medium .33 to .47, and large > .47

## Notes

iii1 Motovation:  Review over 200 open source projects to see when code smells are introduced, manually examine over 9,000 commits and determine why they are introduced.

iii2 Study instruments:  The contexts that they use are diverse:(i) different sizes, e.g., Android apps are by their nature smaller than projects in Apache’s and Eclipse’s ecosystems, (ii) different architectures, e.g., we have Android mobile apps, Apache libraries, and plug-in based architectures in Eclipse projects, and (iii) different development bases, e.g., Android apps are often developed by small teams whereas several Apache projects are carried out by dozens of developers

iii3 Conclusions: smells are generally introduced in the last month before issuing a deadline, while there is a considerable number of instances introduced in the first year from the project startup. Finally, developers that introduce smells are generally the owners of the file and they are more prone to introducing smells when they have higher workloads 

## Improvements and Connections

iv1 It's interesting that they attribute the cause of the change (under pressure of a deadline or a result of long-term decisions) 

iv2 Uses DECOR, which the earlier papers also mention. I think this must be one of the projects Massimiliano Di Penta is involved with, because I think that's where I saw his name in the first paper.

iv3 I wonder if the limitations they use to select projects (Java only, GIT only) would have an effect on what code smells they find, and how much they matter.  This is probably the best improvement I've found so far in any of these papers.


