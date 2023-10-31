public class Fibonacci {					

    /**
     * main
     */
    public static void main(String[] args) {
    	System.out.println((String)null);
        for (int n = 1; n <= 20; n++){
            int f = fibonacci(n);
            System.out.println("The fibonacci number "+ n + " is " + f);      	
        }
    }

    /**
     * This method recursively computes the nth fibonacci number.
     *
     * @param n - The number in the fibonacci sequence to compute.
     *            Must be greater than 0.
     *
     * @return	The nth fibonacci number.
     */
    private static int fibonacci(int n)  {
        if (n <= 1) {						
            return 1;								
        }

        return fibonacci(n-1) + fibonacci(n-2);		
    }
}												