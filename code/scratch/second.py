

def getVolume(radius):
    volume = (4/3) * float(radius) * pow(3.14, 3)
    return volume
    
def right_justify(text, count):
    length = len(text)
    if(count < length):
        raise Exception('The string '+text+' is longer than '+count)
    newString = ""
    i=0
    while(i< count - length):
        newString = newString + " "
        i = i + 1

    newString = newString + text
    return newString
    
