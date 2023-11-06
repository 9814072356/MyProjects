import math
class SeatManager(object):

    def __init__(self, n):
        """
        :type n: int
        """
        self.list = []
        for i in range(n):
            self.list.append(i+1)

        

    def reserve(self):
        """
        :rtype: int
        """
        return heapq.heappop(self.list)
        

    def unreserve(self, seatNumber):
        """
        :type seatNumber: int
        :rtype: None
        """
        heapq.heappush(self.list, seatNumber)

obj = SeatManager(5)
print(obj.list)
print(obj.reserve())
print(obj.list)
print(obj.reserve())
print(obj.list)
obj.unreserve(2)
print(obj.list)