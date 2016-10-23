#  The relationship between design patterns and code smells
For Ase16 Week 7

## Paper

Walter, Bartosz, and Tarek Alkhaeir. "The relationship between design patterns and code smells: An exploratory study." Information and Software Technology 74 (2016): 127-142.
Cited 0 times (Published this month).

## Key Words
 
ii1 Strategy pattern defines a family of objects representing algorithms, which are separated from the data. This separation could result in introducing Feature Envy smell in the dependent classes that hold the data, as the decoupled algorithm objects still needs the data

ii2 Factory Method pattern encapsulates the process of creating objects in a method; in particular, it could also include passing a bunch of parameters to a created instance of a foreign object in order to set it up, which could relate the pattern to Feature Envy or Long Parameter List smells

ii3 Observer pattern is founded on the relation between a subject class and observers that are notified if the subject changes its state. Gradual evolution of the subject may lead to increasing the number of notifications, and, eventually, making the subject a God Class.

ii4 Proxy design pattern the proxy class is a smart facade to another class, which can also introduce a God Class smell to the proxy. Additionally, a stack of proxies that includes several intermediate layers could be also a manifestation of a Middle Man smell.

## Notes

iii1 Motovation:  See if code smells are linked to design patterns.

iii2 Study instruments:  Searching two java projects over a period of time to see if classes with code smells are more or less likely to have design patterns.

iii3 Future Work: Suggest methods for improving coe smell detectors by adding design pattern detectors that counter-indicate code smells.

iii4 Results confirm that smell code usually isn't related to a design pattern, and vice-versa.

iii5 compared versions of Apache Maven (32 releases) and JFreeChart (54 releases) over 

## Improvements and Connections

iv1 I thought it was interesting in one JFreeChart release the authors found a blip of Data Clump smells.  This increased the R value briefly, but was refactored in the next few releases.

iv2 This paper includes one of the concepts that related back to the original paper, that of detecitng code smells not just in one version, but using the history of the projects through several releases to get at code smells that relate to concurrent changes or shotgun surgery, which are harder to check in just one release.

iv3 



