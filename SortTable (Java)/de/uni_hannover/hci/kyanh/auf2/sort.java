package de.uni_hannover.hci.kyanh.auf2;

import de.uni_hannover.hci.kyanh.auf2.SortMode;

public class sort{
/**
 * Algorithm of quick sort
 * @param mode
 * @param toSort
 * @param begin
 * @param end
 */
    public static void Sort(SortMode mode, ISortTable[] toSort, int begin, int end){
        if(begin < end){
            int partitionIndex = partition(mode,toSort,begin,end);
            Sort(mode,toSort, begin, partitionIndex-1);
            Sort(mode,toSort, partitionIndex+1, end);
        }
    }
/**
 * Locate the point which divides the list into 2 part after each sort, where the the 
 * smaller values will be on the left and the larger ones will be on the right
 * @param mode
 * @param toSort
 * @param begin
 * @param end
 * @return
 */
    public static int partition(SortMode mode, ISortTable[] toSort, int begin, int end){
        int i = begin - 1;                              //index i is the start of the list        
        if(mode == SortMode.PRICE){
            String str = toSort[end].getSortString(SortMode.PRICE);
            int pivot = Integer.parseInt(str.substring(2, str.length() - 1));         //Choose an element of the list to be the partition(in this case is the last one of the list)
            for (int j = begin; j < end; j++){          //Iterate the list
                String s = toSort[j].getSortString(SortMode.PRICE);
                int toCompare = Integer.parseInt(s.substring(2, s.length() - 1));   //The value of the current element wil be compared with the one of the partition
                if (toCompare <= pivot){                //If current value <= value of partition
                    i++;                                //update i
                    ISortTable swapTemp = toSort[i];    //Swap the element at index j with one at index i
                    toSort[i] = toSort[j];              //else the position of the element remains the same
                    toSort[j] = swapTemp;
                }
            }
        }else{
            int pivot = toSort[end].getSortString(mode).length();
            for (int j = begin; j < end; j++){
                if (toSort[j].getSortString(mode).length() <= pivot){
                    i++;
                    ISortTable swapTemp = toSort[i];
                    toSort[i] = toSort[j];
                    toSort[j] = swapTemp;
                }
            }
        }
        ISortTable swapTemp = toSort[i+1];              //After all the smaller elements are in place, swap the pivot point with 
        toSort[i+1] = toSort[end];                      //one at index i+1, which is one index behind the last smaller element
        toSort[end] = swapTemp;
     
        return i+1;                                     //i+1 is now the new index of the partition point
    }
}