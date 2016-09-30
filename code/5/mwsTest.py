from MaxWalkSat import *
import unittest
sys.dont_write_bytecode=True

seed = 1
class TestMaxWalkSat(unittest.TestCase):

  def setUp(self):
    random.seed(seed)
    #print("Seed="+str(seed))

  def make_decisions(self):
    x1 = Decision('x1', 0, 10)
    x2 = Decision('x2', 0, 10)
    x3 = Decision('x3', 1, 5)
    x4 = Decision('x4', 1, 5)
    x5 = Decision('x5', 0, 6)
    x6 = Decision('x6', 0, 10)
    decisions = [x1, x2, x3, x4, x5, x6]
    return decisions

  def make_problem(self):
    Osyczka2( )

  def test_decision(self):
    decisions = self.make_decisions()
    x1 = decisions[0]
    self.assertTrue(x1.is_valid(2))
    self.assertFalse(x1.is_valid(100) )
    print("Passed decision")

  def test_point(self):
    p1 = Point([1, 1.1, 1.2])
    p2 = Point([1, 1.1, 1.2]) #Same
    p3 = Point([1, 1.2, 1.2]) #Diff
    self.assertTrue(p1 == p2)
    self.assertFalse(p1 == p3)
    p1.ovals= [1,2,3,4]
    p2.ovals=[1,1,1]
    self.assertFalse(p1.is_better_than(p2))
    self.assertTrue(p2.is_better_than(p1))
    print("Passed point")

  def test_problem(self):
    pr=Osyczka2()
    p1=pr.generate_one()
    p2=pr.generate_one()
    self.assertFalse(p1 == p2)
    p2 = p1.clone()
    print("p1 "+str(p1.dvals)+" p2 "+str(p2.dvals) )
    self.assertTrue(p1 == p2)
    p2=pr.generate_neighbor(p1)
    print("p1 "+str(p1.dvals)+" p2 "+str(p2.dvals) )
    self.assertFalse(p1 == p2)

    print("Passed problem")

  def test_validate(self):
    pr=Osyczka2()
    p1=pr.generate_one()
    #print(p1)
    self.assertTrue(pr.is_valid(p1) )
    p1.dvals[0]=-1
    self.assertFalse(pr.is_valid(p1) )
    print("Passed validate")

  def test_calculate(self):
    pr = Osyczka2()
    p1 = pr.generate_one()
    #print(p1)
    pr.calc_objectives(p1)
    #print(p1)
    self.assertTrue(pr.is_valid(p1))
    self.assertTrue(len(p1.ovals ) == 2)
    print("Passed calculate")

  def test_populate(self):
    pr = Osyczka2()
    pop = populate(pr, 10)
    [pr.calc_objectives(p) for p in pop]
    print(pop)
    self.assertTrue(len(pop) == 10)

  def test_find_incremental_value_for_local_search(self):
    pr = Osyczka2()
    p1 = Point([10.0, 1.01, 1.0, 1.0, 0.49, 0.06])
    self.assertTrue(pr.find_incremental_value_for_local_search(pr.decisions[1], 10/20.0) == 5.0 )
    self.assertTrue(pr.find_incremental_value_for_local_search(pr.decisions[3], 2/10.0) == 1.8)
    self.assertTrue(pr.find_incremental_value_for_local_search(pr.decisions[5], 9 / 9.0) == 10.0)
    print("Passed test_find_incremental_value_for_local_search")

  def test_runMax(self):
    pr = Osyczka2()
    solution = runMaxWalkSat(1000, 22)
    print("\nSolution "+str(solution  ))


  def test_runMaxLong(self):
    pr = Osyczka2()
    solutions = [ runMaxWalkSat(1000, x) for x in range(20) ]
    for x in solutions:
      print("\nSolution Point = " + str(x))


if __name__ == '__main__':
  if len(sys.argv) < 2:
    seed=1
  else:
    seed=int(sys.argv[1])
  print("Starting tests with seed = "+str(seed) )
  unittest.main()
  print("End test.")