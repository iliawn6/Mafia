package Mafia;

/**
 * this class is for running client(player)
 * @author ilia
 * @version 1.0
 */

public class PlayerMain {
    public static void main(String[] args){
        Player player = new Player();
        player.connect();
    }
}
