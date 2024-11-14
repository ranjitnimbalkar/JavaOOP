public class LongestOnes {

    public static void main(String args[]){
        System.out.println(solve("11010110000000000"));
    }


        public static  int solve(String A) {
            int t = 0;
            for(int i = 0; i < A.length(); i++){
                if(A.charAt(i) == '1') t++;
            }
            if(t == A.length()) return A.length();
            int ans = 0;
            for(int i = 0; i < A.length(); i++){
                if(A.charAt(i) == '0') {
                    int lcount = 0;
                    int rcount = 0;
                    for(int l = i-1; l >= 0 ; l--){
                        if(A.charAt(l) == '1') lcount++;
                        else break;
                    }
                    for(int r = i+1; r <  A.length() ; r++){
                        if(A.charAt(r) == '1') rcount++;
                        else break;
                    }
                    System.out.println(lcount + "-" + i + "-" + rcount);
                    if(t == lcount+rcount){
                        ans = Math.max(ans, lcount+rcount);
                    }else {
                        ans = Math.max(ans, lcount+rcount+1);
                    }
                }
            }
            return ans;
        }

}
