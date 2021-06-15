package Mafia;

/**
 * this class is for running server
 * @author ilia
 * @version 1.0
 */
public class ServerMain {
    public static void main(String[] args){
        Server_God serverGod = new Server_God();
        serverGod.Connect();
    }
}
