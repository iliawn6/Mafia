package Mafia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * the Player class represents players for mafia
 * @author ilia
 * @version 1.0
 */

public class Player {

    /**
     * using this method client(player) can connect
     * to server(server_God)
     */
    public void connect(){
        try {
            System.out.println("enter port:");
            Scanner sc = new Scanner(System.in);
            int inp;
            while (true){
                inp = sc.nextInt();
                if (inp == 9000){
                    break;
                }
                System.out.println("Your entered port doesn't exist!!");
            }
            Socket socket = new Socket("localhost",inp);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println(dataInputStream.readUTF());
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            //String server;
            while (true){
                dataOutputStream.writeUTF(input);
                if (input.equals("exit") || input.equals("thanks for playing :)")){
                    break;
                }
                System.out.println(dataInputStream.readUTF());
                input = scanner.nextLine();
            }
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
