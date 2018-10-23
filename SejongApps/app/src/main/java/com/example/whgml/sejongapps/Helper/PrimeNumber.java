package com.example.whgml.sejongapps.Helper;

import java.util.ArrayList;

public class PrimeNumber {

    public static boolean isPrime(long n)
    {
        if (n < 0)
            n *= -1;
        if(n <= 3 && n >= 0)
            return true;

        if(n % 2 == 0)
            return false;
        else if(n % 3 == 0)
            return false;

        long stop = (long)Math.sqrt(n);
        long di = 2;

        for(long i = 5; i<=stop; i+=di, di=6-di)
        {
            if(n%i == 0)
                return false;
        }
        return true;
    }

    public static boolean isOdd(int n)
    {
        return n % 2 != 0;
    }

    public static int[] dividedBy(int n)
    {
        ArrayList<Integer> numList = new ArrayList<>();

        if (n < 0)
            n *= -1;

        for(int i = 1; i <= n; i++)
        {
            if(isPrime(i) && (n % i == 0))
            {
                numList.add(i);
            }
        }

        int[] dividedArray = new int[numList.size()];
        int index = 0;
        for(Integer value : numList)
        {
            dividedArray[index] = value;
            index++;
        }

        return dividedArray;
    }
}
