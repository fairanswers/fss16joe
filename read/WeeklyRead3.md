#  Summary An empirical study of the impact of two antipatterns blob and spaghetti code on program comprehension
For Ase16 Week 3

## Paper

2011  "An empirical study of the impact of two antipatterns blob and spaghetti code on program comprehension"15th European Conference on Software Maintenance and Reengineering CSMR 2011pp. 181-190 2011
Cited 95 times

## Key Words
 
ii1 Blob Anti-pattern:  a large and complex class that centralises the behavior of a portion of a system and only uses other classes as data holders, i.e., data classes.  The main characteristic of a Blob class are: a large size, a low cohesion, some method names recalling procedural programming, and its association with data classes, which only provide fields and–or accessors to their fields.

ii2 Spaghetti Code Anti-pattern:  classes have little structure, declare long methods with no parameters, and use global variables; their names and their methods names may suggest procedural programming. They do not exploit and may prevent the use of object-orientation mechanisms: polymorphism and inheritance.

ii3 Independent Variable:  Variable that the tester has control over.  In this case, the presence of Blob, Spagetti Code, or both anti patterns.

ii4 Dependent Variable:  Measurable results from the test.  For this paper, they are:  Effort (measured by NASA Task Load Index (TLX) ), time taken, and percent correct.

## Notes

iii1 Motovation : Antipatterns are “poor” solutions to recurring design problems which are conjectured in the literature to make object-oriented systems harder to maintain. However, little quantitative evidence exists to support this conjecture.  We performed an empirical study to investigate whether the occurrence of antipatterns does indeed affect the understandability of systems by developers during comprehension and maintenance tasks. 

iii2 This study re-implements the DECOR rules to find Blob and Spagetti code and calls this version DEX.

iii3 Methodology:  Have different subjects look at code that performs the same tasks, but some sets of code have one or two anti-patterns.  Score whether the people are able to understand the code.

iii4 New Results:  It turns out that having ONE anti-apttern is not much more confusing than having none, but having TWO anti patterns reduces the ability to comprehend by all three measures. "We notice that systems with antipatterns are more time consuming, need more effort, and lead subjects to answer less correctly"


## Improvements and Connections

iv1 It seems like the process of going from code WITH an anti pattern to the code WITHOUT and anti pattern was error-prone.  It sounds like the authors found some classes in different open source projects and either A) added in anti patterns or B) removed anti patterns.  Then they showed either set to participates to check comprehension.  It would be better to find an anti-pattern in the wild that was fixed by the developers, in order to represent the changes we see in the real world.

iv2 Mitigating variables are included (Java knowledge, Eclipse Knowledge, SE knowledge) but none of these variables seem to have an impact on the results.  The discussion and grapghic is poorly done, and I can't understand the difference from the specific "p-value" given.

