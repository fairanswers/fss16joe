import second
from time import gmtime, strftime

print("Starting at "+strftime("%Y-%m-%d %H:%M:%S",gmtime()))

newString = second.right_justify("Must Be Thursday", 70)
newLen = len(newString)

if(newLen != 70):
        raise Exception("Didnget right len.  Should be 70 but got "+str(newLen) )
print ( newString )