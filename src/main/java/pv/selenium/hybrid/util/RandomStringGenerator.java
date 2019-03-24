package pv.selenium.hybrid.util;

import java.util.Random;
import com.relevantcodes.extentreports.ExtentTest;
import pv.selenium.hybrid.keywords.AppKeywords;

public class RandomStringGenerator extends AppKeywords {

	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 6;

	public RandomStringGenerator(ExtentTest test) {
		super(test);
	}
	/**
     * This method generates random string
     * @return
     */
    public static String generateRandomString(){
         
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
	  
        /**
         * This method generates random numbers
         * @return int
         */
    public static int getRandomNumber() {
            int randomInt = 0;
            Random randomGenerator = new Random();
            randomInt = randomGenerator.nextInt(CHAR_LIST.length());
            if (randomInt - 1 == -1) {
                return randomInt;
            } else {
                return randomInt - 1;
            }
        }
     
   
    
}
