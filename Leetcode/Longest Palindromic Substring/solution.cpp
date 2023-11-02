class Solution {
private:
    string replaceAll(string str, const string &from, const string &to){
        size_t start_pos = 0;
        while ((start_pos = str.find(from, start_pos)) != string::npos)
        {
            str.replace(start_pos, from.length(), to);
            start_pos += to.length(); // Handles case where 'to' is a substring of 'from'
        }
        return str;
    }
    /*bool check(string s){
        int start = 0;
        int end = s.length() - 1;
        while(start != end){
            if(s.at(start) == s.at(end)){
                if(start + 1 == end) break;
                else{
                    start += 1;
                    end -= 1;
                }
            }else{
                return false;
            }
        }
        return true;
    }*/
public:
        /*      naive approach      */
    string longestPalindrome(string s) {
        /*      naive approach      */
        /*
                if(s.length() == 1)return s;
                int start = 0;
                int offset = 1;
                int end = start + offset;
                string Palin;
                Palin = s.at(0);
                while(start < s.length() - 1){
                    if(end >= s.length())break;
                    string tmpPalin = s.substr(start,end-start+1);
                    bool isPalin = check(tmpPalin);
                    if(isPalin && tmpPalin.length() >= Palin.length()){
                        Palin.assign(tmpPalin);
                    }
                    end++;
                    if(end == s.length()){
                        start++;
                        offset = Palin.length() - 1;
                        end = start + offset;
                    }
                }
                return Palin;                                             */

        /*      Manacher's algorithm        */
        int slen = s.length();
        if(slen <= 1)return s;
		int C = 0;
		int R = 0;
		string s_w_hash = "";
		int track = 0;
		for(int i = 0; i < 2 * slen + 1; i++) {
			if(i % 2 == 0) {
				s_w_hash += '#';
			}else {
				s_w_hash += s.at(track);
				track++;
			}
		}
		int len = s_w_hash.length();	
		int P[len];
		for(int j = 0; j < len; j++) {																				
			int mirror = C - (j - C);
			
            P[j] = j < R ? std::min(P[mirror], R - j) : 0;
			
			if(j + P[j] + 1 < len && j - P[j] - 1 >= 0) {
				while(s_w_hash.at(j + P[j] + 1) == s_w_hash.at(j - P[j] - 1)) {
					P[j]++;
					if(j + P[j] + 1 >= len || j - P[j] - 1 < 0)break;
				}
			}
			
			if(j + P[j] > R) {
				C = j;
				R = j + P[j];
			}
		}
		int longest_len = 0;
		int index = 0;
		for(int k = 0; k < len; k++){
			if(P[k] > longest_len) {
				longest_len = P[k];
				index = k;
			}
		}
		//string longest_palin = replaceAll(s_w_hash.substr(index-longest_len, 2*longest_len+1), "#", "");
		return replaceAll(s_w_hash.substr(index-longest_len, 2*longest_len+1), "#", "");
    }
};