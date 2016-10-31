

class Schaffer:
  def model(self):
    return None

class Osyczka2:
  def model(self):
    return None

class Kursawe:
  def model(self):
    return None

class sa:
  def __init__(self, prob):
    print("in sa")

class mws:
  def __init__(self, prob):
    print("in mws")

for model in [Schaffer, Osyczka2, Kursawe]:
  for optimizer in [sa, mws]:
    optimizer(model())






