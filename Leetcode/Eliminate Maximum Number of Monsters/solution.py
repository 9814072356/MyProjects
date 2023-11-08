import math
import time
class Solution(object):
    def eliminateMaximum(self, dist, speed):
        """
        :type dist: List[int]
        :type speed: List[int]
        :rtype: int
        """
        l = len(dist)
        monsStat = {}
        for d, s in zip(dist, speed):
            time = int(math.ceil(float(d)/float(s)))
            if time not in monsStat:
                monsStat[time] = 1
            else:
                monsStat[time] += 1
        monsStat = dict(sorted(monsStat.items()))
        # print(monsStat)
        monsTime = list(monsStat.keys())
        monsCount = list(monsStat.values())
        
        monsCount[0] -= 1
        wait = 0
        killed = 1
        if monsCount[0] == 0:
            monsCount.pop(0)
            monsTime.pop(0)
        # print(monsTime)
        # print(monsCount)
        # for m in monsStat
        for i in range(len(monsTime)):
            monsTime[i] -= wait
            if monsTime[i] <= 0:
                return killed
            elif monsCount[i] < monsTime[i]:
                wait += monsCount[i]
                killed += monsCount[i]
            else:
                killed += monsTime[i] - 1
                return killed
        return killed



s = Solution()
dist = [8,9,8,6,7,6]
speed = [1,1,1,2,1,1]
print(s.eliminateMaximum(dist, speed))