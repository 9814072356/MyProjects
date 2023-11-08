# target = __import__("solution.py")
# s = target.eliminateMaximum

import unittest
from solution import Solution
import time
class TestSolution(unittest.TestCase):
    def test_elimMax(self):
        start_time = time.time()
        self.assertEqual(Solution.eliminateMaximum(self, [8,9,8,6,7,6], [1,1,1,2,1,1]), 6)
        self.assertEqual(Solution.eliminateMaximum(self, [1,1,2,3], [1,1,1,1]), 1)
        self.assertEqual(Solution.eliminateMaximum(self, [4,2,3], [2,1,1]), 3)
        end_time = time.time()
        print("Execution time: ----%s ms----" % ((end_time-start_time)*10**3))
    
if __name__ == '__main__':
    unittest.main()