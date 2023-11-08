class Solution(object):
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        numsDict = {}
        for i in range(len(nums)):
            s = target-nums[i]
            if numsDict.get(s) != None:
                return [i, numsDict.get(s)]
            elif numsDict.get(nums[i]) == None:
                numsDict[nums[i]] = i          
        
        print(numsDict)
        return [-1,-1]



nums = [-1,-2,-3,-4,-5]
target = -8
s = Solution()
print(s.twoSum(nums, target))