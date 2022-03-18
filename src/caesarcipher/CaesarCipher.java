/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caesarcipher;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;
// The purpose of this project is to encode, decode, or brute force a string entered by the user, by shifting the letters in the string by the amount specified 
//Arman Hojjatoleslami

public class CaesarCipher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        char choice;
        do { // This Loop repeats itself until the user enters q to quit
            int shift = 0;
            String phrase;
            String[] brute = new String[26];
            int bestNum;
            boolean rightFormat = false;
            String gunk;

            do { // Loop repeats itself until the correct values are entered
                System.out.print("Encode(e) Decode(d) BruteForce (b) Quit (q): "); // taking input for mode to choose
                choice = keyboard.nextLine().toLowerCase().charAt(0);

                if (choice == 'e' || choice == 'd' || choice == 'b' || choice == 'q') {//Checking to see if the letter typed corresponds to a mode
                    rightFormat = true;
                } else { // if the letter entered is not what it wants it asks again
                    System.out.println("Please enter e, d, b, or q ");
                }
            } while (rightFormat != true);

            rightFormat = false;
            switch (choice) {
                case 'e': // if user choses encoding mode
                    do { //repeats if the input is empty
                        System.out.print("Phrase to encode: ");// prompt the user for the string they want encoded
                        phrase = keyboard.nextLine();
                    } while (phrase.length() < 0);
                    do { // this loop repeats itself until the right format is entered
                        try { // makes sure that an error does not occur from a wrong format of input
                            rightFormat = true;
                            System.out.print("Shift right by?(0-25): "); //prompts the user for the shift value 
                            shift = keyboard.nextInt();
                            gunk = keyboard.nextLine();
                        } catch (InputMismatchException e) { // if a letter or symbol was typed in, and error occured, do this
                            rightFormat = false;
                            System.out.println("Please enter a valid shift.");
                            gunk = keyboard.nextLine();
                        }
                        if (shift <= 0 || shift >= 25) { // makes sure that the shift inputed is between 0 and 25
                            rightFormat = false;
                            System.out.println("Please enter a positive shift between 0 and 25");
                        }
                    } while (rightFormat != true);
                    String encPhrase = encoder(phrase, shift); // calls the method to encode the string the user entered
                    System.out.println("Output: " + encPhrase); // prints the return from the encode method
                    break;

                case 'd': // if the user choses the decoding mode
                    do {//repeats if the input is empty
                        System.out.print("Phrase to decode: ");// prompt the user for the string they want encoded
                        phrase = keyboard.nextLine();
                    } while (phrase.length() < 0);
                    do {// this loop repeats itself until the right format is entered
                        try {// makes sure that an error does not occur from a wrong format of input
                            rightFormat = true;
                            System.out.print("Shift left by?(0-25): ");//prompts the user for the shift value 
                            shift = keyboard.nextInt();
                            gunk = keyboard.nextLine();
                        } catch (InputMismatchException e) {// if a letter or symbol was typed in, and error occured, do this
                            rightFormat = false;
                            System.out.println("Please enter a valid shift.");
                            gunk = keyboard.nextLine();
                        }
                        if (shift <= 0 || shift >= 25) {// makes sure that the shift inputed is between 0 and 25
                            rightFormat = false;
                            System.out.println("Please enter a positive shift between 0 and 25");
                        }
                    } while (rightFormat != true);
                    String decPhrase = encoder(phrase, -shift);// calls the method to decode the string the user entered by sending a negative shift to the encoder
                    System.out.println(decPhrase);// prints the return from the decode method
                    break;

                case 'b':// if the user choses the brute force mode
                    do {//repeats if the input is empty
                        System.out.print("Phrase to Brute Force: "); // prompts the user for the string they want to be brute forced
                        phrase = keyboard.nextLine();
                    } while (phrase.length() < 0);
                    for (int i = 0; i < 26; i++) {//the loop reapeats this 26 times to try every type of decode
                        brute[i] = encoder(phrase, -i); // calls the encoder method with a different shift value everytime, and saves the returned value in an array for later
                        System.out.println("For shift of " + i + " is:" + brute[i]);// displaying the current shift value and the returned decoded string
                    }
                    bestNum = engCheck(brute); // this calls the english checking method to find out which shift decodes the string as most english and saves the best attempt
                   if (bestNum != -1){
                    System.out.println("The best decode was with key: " + bestNum); // this prints out the best shift brute force attempt
                    System.out.println("Decoded message is: " + brute[bestNum]); // displays what the program deems as the best decode of the string
                   }else {
                       System.out.println("Could not find the best decode");
                   }
                    break;
            }

        } while (choice != 'q');
        System.out.println("Thanks for Ciphering!");
    }

    private static String encoder(String phrase, int shift) {

        String encPhrase = "";

        for (int i = 0; i < phrase.length(); i++) { // repeat until there are no more characters in the string to shift

            char encChar = 0;
            char letter = phrase.charAt(i);

            if (letter == ' ') { //if the curent character is a space or a punctuation mark it will take it into consideration and add it to the encoded phrase
                encPhrase += ' ';
            } else if (letter == ',') {
                encPhrase += ',';
            } else if (letter == '.') {
                encPhrase += '.';
            } else if (letter == '!') {
                encPhrase += '!';
            } else if (letter == '?') {
                encPhrase += '?';

            } else if ((letter >= 'a') && (letter <= 'z')) { // if the character is a lower case letter

                encChar += (char) (letter + shift); // add the inputed shift to the character
                if (encChar > (int) 'z') { //if the new ASCII value is no longer a letter by going too high, subtract 26 so that it can loop back to a
                    encChar -= 26;
                } else if (encChar < (int) 'a') {//if the new ASCII value is no longer a letter by going too low, add 26 so that it can loop back into the range
                    encChar += 26;
                }
            } else if ((letter >= 'A') && (letter <= 'Z')) {// if the character is a lower case letter
                encChar += (char) (letter + shift);
                if (encChar > (int) 'Z') {//if the new ASCII value is no longer a letter by going too high, subtract 26 so that it can loop back into the range
                    encChar -= 26;
                } else if (encChar < (int) 'A') {//if the new ASCII value is no longer a letter by going too low, add 26 so that it can loop back into the range
                    encChar += 26;
                }
            }
            encPhrase += encChar;//adding the newly shifted character to the encoded/decoded string
        }
        return encPhrase; // return the encoded/decoded string to main
    }

    private static int engCheck(String[] brute) {

        int[] points = new int[brute.length];
        String curToken;
        int bestNum = -1;
        int mostEng = 0;

        for (int x = 0; x < brute.length; x++) { // repeats until there are no more decoded messages to analyze

            StringTokenizer theTokens = new StringTokenizer(brute[x]);
            while (theTokens.hasMoreTokens()) { // repeat while there are more tokens in the string
                curToken = theTokens.nextToken();
                
                switch (curToken.toLowerCase().trim()) { // this checks the current token to see if it is one of the most common english words
                    case "the":                   // if it is, it will gain 3 points to find out which word is the most english
                        points[x] = points[x] + 3;
                        break;
                    case "to":
                        points[x] = points[x] + 3;
                        break;
                    case "of":
                        points[x] = points[x] + 3;
                        break;
                    case "and":
                        points[x] = points[x] + 3;
                        break;
                    case "in":
                        points[x] = points[x] + 3;
                        break;
                    case "be":
                        points[x] = points[x] + 3;
                        break;
                    case "for":
                        points[x] = points[x] + 3;
                        break;
                    case "at":
                        points[x] = points[x] + 3;
                        break;
                    case "it":
                        points[x] = points[x] + 3;
                        break;
                    case "by":
                        points[x] = points[x] + 3;
                        break;
                    case "on":
                        points[x] = points[x] + 3;
                        break;
                    case "is":
                        points[x] = points[x] + 3;
                        break;
                }
             
            }
            if (mostEng < points[x]) {//Finding the most "English" Bruteforce output was by finding the one with the most points
                bestNum = x;
                mostEng = points[x];
            }
        }
        return bestNum; // return the best shift that was the most english
    }
}
