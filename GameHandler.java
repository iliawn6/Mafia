package Mafia;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class is for handling game with thread
 * @author ilia
 * @version 1.0
 */

public class GameHandler implements Runnable {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //fields
    private String name;
    private Socket socket;
    private ArrayList<String> playersList;
    private String role;
    private HashMap<String,String> playersNameRoles;
    private IntObject votesToBeginVoting;
    private IntObject voteAttendant;
    private HashMap<String,Integer> votingSystem;
    private boolean printVotingResault;
    private boolean playerVoted = false;
    private IntObject maxNumberOfVote;
    private ArrayList<String> alive;
    private ArrayList<String> deadsRoles;
    private boleanObject night;



    //constructor
    public GameHandler(Socket socket, ArrayList<String> playersList, String role, HashMap<String,String> playersNameRoles,
                       IntObject votesToBeginVoting,boolean printVotingResault,
                       IntObject voteAttendant,HashMap<String,Integer> votingSystem,
                       IntObject maxNumberOfVote,ArrayList<String> alive,
                       ArrayList<String> deadsRoles,boleanObject night){
        this.socket = socket;
        this.playersList = playersList;
        this.role = role;
        this.playersNameRoles = playersNameRoles ;
        this.votesToBeginVoting = votesToBeginVoting;
        this.votingSystem = votingSystem;
        this.printVotingResault = printVotingResault;
        this.voteAttendant = voteAttendant;
        this.maxNumberOfVote = maxNumberOfVote;
        this.alive = alive;
        this.deadsRoles = deadsRoles;
        this.night = night;
    }


    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF("please enter your name:");
            while (true){
                name = dataInputStream.readUTF();
                if (playersList.contains(name)){
                    dataOutputStream.writeUTF("this name is already exist!!\nplease enter your name:");
                }
                else{
                    addName();
                    addNameAndRole();
                    //createVotingSystem();
                    break;
                }
            }

            dataOutputStream.writeUTF("thanks for participating " + name + " :)");
            System.out.println(name + " joined the game!");
            //ddddd
            String input;
            boolean writerole = false;
            //yani naghshesho hanooz nagoftam
            while (alive.contains(name)){
                if (playersList.size() == 4 && writerole == false && role.equals("Mafia")){

                    dataOutputStream.writeUTF(ANSI_CYAN + "your role is: " + role + "\n" + " godfather is:"
                            + playersNameRoles.get("GodFather") + "\n" + "Dr lecture is: "
                            + playersNameRoles.get("Dr_lecture") + ANSI_RESET);
                    writerole = true;

                }
                else if (playersList.size() == 4 && writerole == false && role.equals("GodFather")){

                    dataOutputStream.writeUTF(ANSI_CYAN + "your role is: " + role + "\n" + "mafia is: "
                            + playersNameRoles.get("Mafia") + "\n" + "Dr lecture is: "
                            + playersNameRoles.get("Dr_lecture") + ANSI_RESET);
                    writerole = true;

                }
                else if (playersList.size() == 4 && writerole == false && role.equals("Dr_lecture")){
                    dataOutputStream.writeUTF(ANSI_CYAN + "your role is: " + role + "\n" + "mafia is: "
                            + playersNameRoles.get("Mafia") + "\n" + "godfather is: "
                            + playersNameRoles.get("GodFather") + ANSI_RESET);
                    writerole = true;
                }
                else if (playersList.size() == 4 && writerole == false){
                    dataOutputStream.writeUTF( ANSI_CYAN + "your role is: " + role + ANSI_RESET);
                    writerole = true;
                }

                //TODO havaset bashe har ja player ha ro 4 gerefti akharesh bayad dorost koni
                input = dataInputStream.readUTF();



                if (input.equals("exit")){
                    break;
                }

                else if (input.equals("#voting")){
                    votesToBeginVoting.add();
                    dataOutputStream.writeUTF("your message was sent");
                    System.out.println(name + ":" + input);
                }

                else if (input.startsWith("/")){
                    if (input.equals("/none")){
                        voteAttendant.add();
                    }
                    else {
                        String[] check = input.split("/");
                        String vote = check[1];
                        if (playersList.contains(vote)){
                            registerVote(vote);
                            //System.out.println("shod");
                            //bargam rikhte!!!!!!
                            //dataOutputStream.writeUTF("your vote has been registered :)");
                            dataOutputStream.writeUTF("your message was sent");
                            voteAttendant.add();
                        }
                        else {
                            dataOutputStream.writeUTF("this player doesn't exist!!!\nplease enter another player:");
                        }
                    }



                }


                //TODO agar #voting ha 10 ta shod method voting bayad seda zade beshe
                else {
                    dataOutputStream.writeUTF("your message was sent");
                    System.out.println(name + ":" + input);
                }
                //TODO in ja ham dari roo 4 nafar emtehan mikoni baadan bayad bokonish 10 nafar


                if (votesToBeginVoting.getValue() == 4){
                    System.out.println(ANSI_RED + "\n\n-----------voting time-----------\n" + ANSI_RESET);
                    System.out.println(ANSI_RED  + "please enter your vote(which should be start with'/') in private!!\n" +
                            "if you don't want to vote please enter (/none)" + ANSI_RESET);
                    createVotingSystem();
                    votesToBeginVoting.setValue(0);
                }

                if (voteAttendant.getValue() == 4){
                    voteAttendant.setValue(0);
                    votingResault();
                }

                if (night.getvalue() == true){
                    nightMode();
                }



            }

            /*
            if (alive.contains(name)){
                System.out.println(name + " left the game!");
            }
            else {
                dataOutputStream.writeUTF("thanks for playing");
            }

             */
            System.out.println(name + " left the game!");
            dataOutputStream.close();
            dataOutputStream.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public synchronized void addName(){
        playersList.add(name);
        alive.add(name);
    }

    public void addNameAndRole(){
        playersNameRoles.put(role,name);
    }

    public void createVotingSystem(){

        for (String player:playersList){
            votingSystem.put(player,0);
        }

    }


    public void nightMode(){
        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("shod");

         */
    }
    //TODO in ja havaset bashe agar error dad shayad az ine ke az method nemitooni estefade koni va bayad to khode run benvisi


    public synchronized void registerVote(String vote){
        int check = votingSystem.get(vote);
        votingSystem.remove(vote);
        votingSystem.put(vote,(check + 1));
        if (votingSystem.get(vote) > maxNumberOfVote.getValue()){
            maxNumberOfVote.setValue(votingSystem.get(vote));
        }
    }

    public synchronized void votingResault(){

        System.out.println("\n");
        try {
            for (int i = 0;i < 4;i++){
                Thread.sleep(1000);
                System.out.printf(ANSI_RED + playersList.get(i) + ANSI_RESET);
                Thread.sleep(1000);
                System.out.printf(ANSI_RED + "------>" + ANSI_RESET);
                Thread.sleep(1000);
                System.out.printf(ANSI_RED + votingSystem.get(playersList.get(i)) + ANSI_RESET + "\n");
            }
            int check = 0;
            String quit = null;
            for (String player:playersList){
                if (votingSystem.get(player) == maxNumberOfVote.getValue()){
                    check += 1;
                    quit = player;
                }
            }

            if (check == 1 && (maxNumberOfVote.getValue() >= (alive.size()/2))){
                System.out.println(ANSI_RED + "\n" + quit + " leaves the game!!!");
                System.out.println(ANSI_RED + "you can speak with your friends for the last time :)" + ANSI_RESET);
                deadsRoles.add(playersNameRoles.get(quit));
                alive.remove(quit);
                playersList.remove(quit);
            }
            Thread.sleep(15000);
            System.out.println(ANSI_GREEN + "-------night ;)--------" + ANSI_RESET);
            night.yes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
