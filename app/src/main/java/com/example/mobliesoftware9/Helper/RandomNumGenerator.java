package com.example.mobliesoftware9.Helper;

import java.util.Random;

public class RandomNumGenerator
{
    public static final Random rand = new Random();

    public static final int GetRandomPositiveInteger()
    {
        return rand.nextInt(Integer.MAX_VALUE);
    }


    public static final int GetRandomInteger()
    {
        return rand.nextInt();
    }

}
