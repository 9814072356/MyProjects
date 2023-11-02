class Solution {
public:
    bool init = true;
    int kthGrammar(int n, int k) {
        if(init && k > pow(2, n-1)){
            init = false;
            return -1;
        }
        if(k == 1)return 0;
        if(k == pow(2, n-1))return (n % 2 == 0 ? 1 : 0);

        int div = 1;
        int exp = 0;
        int res = 0;
        do{
            div *= 2;
            exp += 1;
        }
        while(div <= k && div * 2 <= k);
        int nextK = k % div;
        if(nextK == 0)res = kthGrammar(exp + 1, k);
        else{
            res = kthGrammar(exp + 1, nextK) ^ 1;
        }
        return res;
    }
};