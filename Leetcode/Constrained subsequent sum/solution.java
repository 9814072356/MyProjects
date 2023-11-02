class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        Map<Integer, Integer> largestSumsR = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        Map<Integer, Integer> largestSumsL = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        int numsLen = nums.length;
        boolean allNeg = true;
        int largestValueIn = 0;
        for(int i = 0; i < numsLen; i++){
			if(allNeg)allNeg = allNeg && (nums[i] < 0);
			if(nums[i] > nums[largestValueIn]) {
				largestValueIn = i;
			}
        }
        if(allNeg)return nums[largestValueIn];
		largestSumsR.put(nums[largestValueIn], largestValueIn);
		largestSumsL.put(nums[largestValueIn], largestValueIn);
        for(int i = largestValueIn + 1, j = largestValueIn - 1; i < numsLen || j >= 0; i++, j--) {
        	if(i < numsLen) {
	        	for (Integer key : largestSumsR.keySet()) {
	        		if(i - largestSumsR.get(key) <= k) {
	                   	largestSumsR.put(key + nums[i], i);
	                   	break;
	                }
	            }
        	}
        	if(j >= 0) {
            	for (Integer key : largestSumsL.keySet()) {
            		if(largestSumsL.get(key) - j <= k) {
                       	largestSumsL.put(key + nums[j], j);
                       	break;
                    }
                }
        		
        	}
        }	
        int sum = ((TreeMap<Integer, Integer>) largestSumsL).firstKey() + ((TreeMap<Integer, Integer>) largestSumsR).firstKey() - nums[largestValueIn];
        if(sum < ((TreeMap<Integer, Integer>) largestSumsL).firstKey())return ((TreeMap<Integer, Integer>) largestSumsL).firstKey();
        else if(sum < ((TreeMap<Integer, Integer>) largestSumsR).firstKey())return ((TreeMap<Integer, Integer>) largestSumsR).firstKey();
		return sum;
    }
}