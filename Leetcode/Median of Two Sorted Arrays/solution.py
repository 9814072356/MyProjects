import math
from re import I
class Solution(object):
    def findMedianSortedArrays(self, nums1, nums2):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :rtype: float
        """
        l1, l2 = len(nums1), len(nums2)
        if l1 == 0:
            if l2 == 0:
                return -1
            else:
                k = int(l2/2) if l2%2 == 0 else int(math.ceil(float(l2)/2))
                return (float((nums2[k-1] + nums2[k]))/2 if l2%2==0 else nums2[k-1])
        elif l2 == 0:
            if l1 == 0:
                return -1
            else:
                k = int(l1/2) if l1%2 == 0 else int(math.ceil(float(l1)/2))
                return (float((nums1[k-1] + nums1[k]))/2 if l1%2==0 else nums1[k-1])
            
        k = int((l1+l2)/2) if (l1+l2)%2 == 0 else int(math.ceil(float((l1+l2))/2))

        AL, AR = 0, l1 - 1
        AM, AM1 = int(math.floor((AR - AL)/2)) , 0
        if (AR - AL)%2 != 0:
            AM1 = AM + 1
        AMVal = float((nums1[AM] + nums1[AM1]))/2 if AM1 != 0 else float(nums1[AM])
        BL, BR = 0, l2 - 1
        BM, BM1 = int(math.floor((BR - BL)/2)), 0
        if (BR - BL)%2 != 0:
            BM1 = BM + 1
        BMVal = float((nums2[BM] + nums2[BM1]))/2 if BM1 != 0 else float(nums2[BM])

        if AMVal == BMVal:
            return AMVal

        while(l1 > 1 and l2 > 1):
            if AMVal <= BMVal:
                if k <= (l1+l2)/2:
                    BR = BM 
                    BM, BM1 = int(math.floor((BR - BL)/2)) + BL, 0
                    if (BR - BL)%2 != 0:
                        BM1 = BM + 1
                    BMVal = float((nums2[BM] + nums2[BM1]))/2 if BM1 != 0 else float(nums2[BM])
                else:
                    k = k - (AM - AL) - (1 if (AR - AL)%2 != 0 else 0)
                    AL = AM1 if AM1 != 0 else AM
                    AM, AM1 = int(math.floor((AR - AL)/2)) + AL, 0
                    if (AR - AL)%2 != 0:
                        AM1 = AM + 1
                    AMVal = float((nums1[AM] + nums1[AM1]))/2 if AM1 != 0 else float(nums1[AM])
            else:
                if k <= (l1+l2)/2:
                    AR = AM 
                    AM, AM1 = int(math.floor((AR - AL)/2)) + AL, 0
                    if (AR - AL)%2 != 0:
                        AM1 = AM + 1
                    AMVal = float((nums1[AM] + nums1[AM1]))/2 if AM1 != 0 else float(nums1[AM])
                else:
                    k = k - (BM - BL) - (1 if (BR - BL)%2 != 0 else 0)
                    BL = BM1 if BM1 != 0 else BM
                    BM, BM1 = int(math.floor((BR - BL)/2)) + BL, 0
                    if (BR - BL)%2 != 0:
                        BM1 = BM + 1
                    BMVal = float((nums2[BM] + nums2[BM1]))/2 if BM1 != 0 else float(nums2[BM])
            l1, l2 = AR - AL + 1, BR - BL + 1
        
        track = 0
        tmp = []
        for i in range(AL, AR+1):
            print(nums1[i])
        print("-----------------")
        for i in range(BL, BR+1):
            print(nums2[i])

        if l1 == 1:
            for i in range(BL, BR + 1):
                tmp.append(float(nums2[i]))
                if nums2[i] <= nums1[AL]:
                     track += 1
            
            tmp.insert(track, float(nums1[AL]))
        else:
            for i in range(AL, AR + 1):
                tmp.append(float(nums1[i]))
                if nums1[i] <= nums2[BL]:
                     track += 1
            
            tmp.insert(track, float(nums2[BL]))
        
        
        if AR + 1 < len(nums1):
            if nums1[AR + 1] < tmp[track]:
                tmp.insert(track, float(nums1[AR+1]))
            elif track + 1 < len(tmp):
                if nums1[AR + 1] < tmp[track + 1]:
                    tmp.insert(track+1, float(nums1[AR+1]))
        if BR + 1 < len(nums2):
            if nums2[BR + 1] < tmp[track]:
                tmp.insert(track, float(nums2[BR+1]))
            elif track + 1 < len(tmp):
                if nums2[BR + 1] < tmp[track + 1]:
                    tmp.insert(track+1, float(nums2[BR+1]))
        
        res, res1 = tmp[k-1], tmp[k] 
        print(tmp)
        print(k)
        print(res)
        print(res1)
        if (len(nums1) + len(nums2)) % 2 == 0:
            return (res + res1)/2

        
        return res





x = Solution()
nums1 = [1,6]#[1,3,33,68,79,101,399]#[9, 14, 50, 69, 72, 83, 85, 103, 118, 139, 141, 147, 148, 170, 196, 219, 252, 263, 308, 314, 340, 347, 351, 369, 398, 445, 474, 475, 477, 490]#
nums2 = [2,3,4,5,7,8]#[22,34,87,105,411,500, 609]#[2, 10, 46, 69, 76, 84, 90, 173, 190, 193, 209, 223, 241, 248, 257, 273, 280, 284, 295, 304, 319, 324, 331, 387, 421, 429, 454, 473, 480, 491]#
print(x.findMedianSortedArrays(nums1, nums2))