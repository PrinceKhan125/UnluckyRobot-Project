package unluckyrobot;
import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author Muhammad Huzaifa Alam Khan
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int x = 0;
        int y = 0;
        int reward;
        int itrCount = 0;
        int totalScore = 300;
        int sanctionDmg = -2000;
        int impact1 = -10;
        int impact2 = -50;
        do{
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();
            
            if (doesExceed(x, y, direction)){
                System.out.println("Exceed boundry, -2000 damage applied");
                totalScore += sanctionDmg;   
            }
            else {
                switch (direction) {
                    case 'u':
                        y++;
                        break;
                    case 'd':
                        y--;
                        break;
                    case 'l':
                        x--;
                        break;
                    case 'r':
                        x++;
                        break;
                }   
            }
            if (direction == 'u')
                totalScore += impact1;
            else 
                totalScore += impact2;
            
            reward = punishOrMercy(direction, reward());
            totalScore += reward;
            System.out.println("");
            itrCount++;
            
        } while (!isGameOver(x, y, totalScore, itrCount));
        System.out.print("Please enter your name (only two words): ");
        String name = toTitleCase(console.nextLine());
        evaluation(totalScore, name);
    }
    /**
     * print a message on the screen reporting the current x and y coordinate of the robot,
     * the total score and the number of iterations made so far.
     * @param x is the coordinate "X" for which the robot moves left or right
     * @param y is the coordinate "Y" for which the robot moves up or down
     * @param itrCount keeps track of the number of iterations 
     * @param totalScore keeps track of the total number of points obtained, initialized as 300
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){
        System.out.printf("%s (X=%d, Y=%d) %s: %d %s: %d", "For point", x,
                y, "at iteration", itrCount, "the total score is", totalScore);
        System.out.println("");
    }/**
     * Check if the robot exceeds the limit after moving up/down/left/right
     * @param x is the inputed x left/right
     * @param y is the inputed y up/down
     * @param direction is the direction which the user implements for the robot to move "u/d/l/r"
     * @return the statement is true if the robot exceeds the grid limit after moving a step
     * otherwise the statement is false
     */
    public static boolean doesExceed(int x, int y, char direction){
        return (y == 4 && Character.toLowerCase(direction) == 'u' || x == 0 &&
                Character.toLowerCase(direction) == 'l' || y == 0 &&
                Character.toLowerCase(direction) == 'd' || x == 4 &&
                Character.toLowerCase(direction) == 'r');
    }
    /**
     * When the robot moves "u/d/l/r" this method is called to either gain or lose points 
     * with a reward system
     * @return will return a number as a reward "-100, -200, -300, 300, 400 or 600"
     */
    public static int reward(){
        Random random = new Random();
        int dice = random.nextInt(6) + 1;
        int reward;
        
        switch (dice){
            case 1: 
                reward = -100;
                System.out.println("Dice: 1, reward: " + reward);
                break;
            case 2:
                reward = -200;
                System.out.println("Dice: 2, reward: " + reward);
                break;
            case 3:
                reward = -300;
                System.out.println("Dice: 3, reward: " + reward);
                break;
            case 4:
                reward = 300;
                System.out.println("Dice: 4, reward: " + reward);
                break;
            case 5:
                reward = 400;
                System.out.println("Dice: 5, reward: " + reward);
                break;
            default:
                reward = 600;
                System.out.println("Dice: 6, reward: " + reward);
        }
        return reward;
    }
    /**
     * Determines if the punish or mercy "method" will be applied to the reward
     * @param direction is the direction which the user implements for the robot to move "u/d/l/r"
     * @param reward is the amount of points the player will receive 
     * @return will either return your reward with the punishment or with the mercy
     */
    public static int punishOrMercy(char direction, int reward){
        if (reward < 0 && direction == 'u'){
            Random flip = new Random();
            int coin = flip.nextInt(2);
            
            if (coin == 0){
                reward = 0;
                System.out.printf("%s: %s | %s\n", "Coin", "tail", "Mercy, "
                        + "the negative reward is removed.");
            }
            else{
                System.out.printf("%s: %s | %s\n", "Coin", "tail", "No mercy,"
                        + " the negative reward is applied.");
            }
        }
        return reward;
    }
    /**
     * Convert the player's name into title case
     * @param str is the name you will input "YOUR NAME"
     * @return will return the player's name with the title case
     */
    public static String toTitleCase(String str){
        str = str.toLowerCase();
        int idx = str.indexOf(" ");
        String str1 = Character.toTitleCase(str.charAt(0)) + str.substring(1, idx);
        String str2 = Character.toTitleCase(str.charAt(idx + 1)) + str.substring(idx + 2);
        return str1 + " " + str2;
    }
    /**
     * Print the final message based on the value of your total score
     * @param totalScore keeps track of the total number of points obtained, initialized as 300
     * @param name is the player's name
     */
    public static void evaluation(int totalScore, String name){
        name = toTitleCase(name);
        
        if (totalScore >= 2000)
            System.out.printf("%s %s, %s %d", "Victory!", name, 
                    "your score is", totalScore);
        else
            System.out.printf("%s %s, %s %d", "Mission failed!", name,
                    "your score is", totalScore);
    }
    /**
     * Choose a valid direction for your robot to move "u/d/l/r"
     * @return the direction the user chooses for the robot to move "u/d/l/r"
     */
    public static char inputDirection(){
        Scanner console = new Scanner(System.in);
        char direction;
        do{
            System.out.print("Please input a valid direction: ");
            direction = console.next().charAt(0);
        } while (!isDirectionValid(direction));
        
        return direction;
    }
    /**
     * Checks if the imputed direction is valid or not
     * @param direction is the direction which the user implements for the robot to move "u/d/l/r"
     * @return if the direction is valid the return true otherwise return false
     */
    public static boolean isDirectionValid(char direction){
        return (direction == 'r' || direction == 'l' || direction == 'u' || direction == 'd');
    }
    /**
     * Check if the game is over, according to the win/lose condition implemented
     * @param x is the coordinate "X" for which the robot moves left or right
     * @param y is the coordinate "Y" for which the robot moves up or down
     * @param totalScore keeps track of the total number of points obtained, initialized as 300
     * @param itrCount keeps track of the number of iterations 
     * @return true if one of the conditions are met otherwise false
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        char direction = 0;
        return (totalScore <= -1000 || totalScore >= 2000 || itrCount > 20
                || doesExceed(x, y, direction));
    }
}