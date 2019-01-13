package com.navid.loggergenerator.example.jdk8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.navid.codegen.LoggerUtils.*;
import static java.util.Arrays.asList;

/**
 * Code taken from https://github.com/TheAlgorithms/Java/blob/master/Dynamic%20Programming/CoinChange.java
 * Original @author Varun Upadhyay (https://github.com/varunu28)
 * Modified to introduce logger only
 */

public class CoinChange {

    //TODO
    //support for varargs in audits

    private static final Logger logger = LoggerFactory.getLogger(CoinChange.class);

    // Driver Program
    public static void main(String[] args) {

        int amount = 12;
        int[] coins = {2, 4, 5};
        
        //non audit lines also accepted
        logger.info("Input (using array) {} {}", kvAmount(12), aCoins(coins));
        logger.info("Input (using iterable) {} {}", kvAmount(12), aCoins(asList(9,9,9)));

        //System.out.println("Number of combinations of getting change for " + amount + " is: " + change(coins, amount));
        auditResultCombinations(logger::warn, amount, change(coins, amount));

        //System.out.println("Minimum number of coins required for amount :" + amount + " is: " + minimumCoins(coins, amount));
        auditResultMinimum(logger, amount, minimumCoins(coins, amount));
    }

    /**
     * This method finds the number of combinations of getting change for a given amount and change coins
     *
     * @param coins The list of coins
     * @param amount The amount for which we need to find the change
     * Finds the number of combinations of change
     **/
    public static int change(int[] coins, int amount) {

        int[] combinations = new int[amount+1];
        combinations[0] = 1;

        for (int coin : coins) {
            for (int i=coin; i<amount+1; i++) {
                combinations[i] += combinations[i-coin];
            }
            // Uncomment the below line to see the state of combinations for each coin
            // printAmount(combinations);
        }

        return combinations[amount];
    }
    /**
     * This method finds the minimum number of coins needed for a given amount.
     *
     * @param coins The list of coins
     * @param amount The amount for which we need to find the minimum number of coins.
     * Finds the the minimum number of coins that make a given value.
     **/
    public static int minimumCoins(int[] coins, int amount) {
        //minimumCoins[i] will store the minimum coins needed for amount i
        int[] minimumCoins = new int[amount+1];

        minimumCoins[0] = 0;

        for(int i=1;i<=amount;i++){
            minimumCoins[i]=Integer.MAX_VALUE;
        }
        for(int i=1;i<=amount;i++){
            for (int coin :coins){
                if(coin <=i){
                    int sub_res = minimumCoins[i-coin];
                    if (sub_res != Integer.MAX_VALUE && sub_res + 1 < minimumCoins[i])
                        minimumCoins[i] = sub_res + 1;
                }
            }
        }
        // Uncomment the below line to see the state of combinations for each coin
        //printAmount(minimumCoins);
        return minimumCoins[amount];
    }

}