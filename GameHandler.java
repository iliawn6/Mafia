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
    //Hashmap<role,Name>
    private IntObject votesToBeginVoting;
    private IntObject voteAttendant;
    private HashMap<String,Integer> votingSystem;
    private boleanObject printVotingResault;
    private boolean playerVoted = false;
    private IntObject maxNumberOfVote;
    private ArrayList<String> alive;
    private ArrayList<String> deadsRoles;
    private boleanObject night;
    private String docHeal;
    private String docLecHeal;
    private String mafiaShot;
    private String sniperShot;
    private String psychoAct;
    private boolean dieHardAct = false;
    private int inquiryNum = 0;
    private boleanObject printNightResault;
    private String playeExitInVote;
    private HashMap<String,String> firstNameSecRole;



    //constructor
    public GameHandler(Socket socket, ArrayList<String> playersList, String role, HashMap<String,String> playersNameRoles,
                       IntObject votesToBeginVoting,boleanObject printVotingResault,
                       IntObject voteAttendant,HashMap<String,Integer> votingSystem,
                       IntObject maxNumberOfVote,ArrayList<String> alive,
                       ArrayList<String> deadsRoles,boleanObject night,
                       String docHeal,String docLecHeal,String mafiaShot,String sniperShot,
                       String psychoAct,boleanObject printNightResault,String playeExitInVote,
                       HashMap<String,String> firstNameSecRole){


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
        this.docHeal = docHeal;
        this.docLecHeal = docLecHeal;
        this.mafiaShot = mafiaShot;
        this.sniperShot = sniperShot;
        this.psychoAct = psychoAct;
        this.printNightResault = printNightResault;
        this.playeExitInVote = playeExitInVote;
        this.firstNameSecRole = firstNameSecRole;
    }


    /**
     * this method is main method of the game and contain general
     * while of the game.this method handle days and nights
     */
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
            System.out.println(name + " joined the game!");
            Thread.sleep(40000);


            boolean writerole = false;
            String input;

            while (true){
                dataOutputStream.writeUTF("if you are ready enter 'yes' " + ":)");
                input = dataInputStream.readUTF();
                if (input.equals("yes")){
                    if (playersList.size() == 10 && writerole == false && role.equals("Mafia")){


                        dataOutputStream.writeUTF(ANSI_CYAN + "your role is: " + role + "\n" + " godfather is:"
                                + playersNameRoles.get("GodFather") + "\n" + "Dr lecture is: "
                                + playersNameRoles.get("Dr_lecture") + ANSI_RESET);


                        writerole = true;

                    }
                    else if (playersList.size() == 10 && writerole == false && role.equals("GodFather")){


                        dataOutputStream.writeUTF(ANSI_CYAN + "your role is: " + role + "\n" + "mafia is: "
                                + playersNameRoles.get("Mafia") + "\n" + "Dr lecture is: "
                                + playersNameRoles.get("Dr_lecture") + ANSI_RESET);


                        writerole = true;

                    }
                    else if (playersList.size() == 10 && writerole == false && role.equals("Dr_lecture")){

                        dataOutputStream.writeUTF(ANSI_CYAN + "your role is: " + role + "\n" + "mafia is: "
                                + playersNameRoles.get("Mafia") + "\n" + "godfather is: "
                                + playersNameRoles.get("GodFather") + ANSI_RESET);


                        writerole = true;
                    }
                    else if (playersList.size() == 10 && writerole == false){
                        dataOutputStream.writeUTF( ANSI_CYAN + "your role is: " + role + ANSI_RESET);

                        writerole = true;
                    }
                    break;

                }

                else {
                    Thread.sleep(5000);
                }
            }



            //yani naghshesho hanooz nagoftam
            while (alive.contains(name)){

                if (night.getvalue() == true){
                    if (name.equals(playeExitInVote)){
                        break;
                    }
                    //System.out.println("miad too in");
                    //injaiim
                    nightMode(dataInputStream,dataOutputStream);
                    Thread.sleep(90000);
                    nightRes();
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
                        dataOutputStream.writeUTF("your vote has been registered :)");
                    }
                    else {
                        String[] check = input.split("/");
                        String vote = check[1];
                        if (playersList.contains(vote)){
                            registerVote(vote);
                            dataOutputStream.writeUTF("your vote has been registered :)");
                            voteAttendant.add();
                        }
                        else {
                            dataOutputStream.writeUTF("this player doesn't exist!!!\nplease enter another player:");
                        }

                    }
                    Thread.sleep(30000);



                }


                //TODO agar #voting ha 10 ta shod method voting bayad seda zade beshe
                else {
                    dataOutputStream.writeUTF("your message was sent");
                    System.out.println(name + ":" + input);
                }
                //TODO in ja ham dari roo 4 nafar emtehan mikoni baadan bayad bokonish 10 nafar


                if (votesToBeginVoting.getValue() == 10){
                    System.out.println(ANSI_RED + "\n\n-----------voting time-----------\n" + ANSI_RESET);
                    System.out.println(ANSI_RED  + "please enter your vote(which should be started with'/') in private!!\n" +
                            "if you don't want to vote please enter (/none)" + ANSI_RESET);
                    createVotingSystem();
                    votesToBeginVoting.setValue(0);
                }

                if (voteAttendant.getValue() == 10){
                    votingResault();
                    Thread.sleep(20000);

                    if (printVotingResault.getvalue()){
                        printVotingResault.no();
                        Thread.sleep(15000);
                        System.out.println(ANSI_GREEN + "\n-------night ;)--------\nif you are still alive enter 'ready'\n" +
                                "if you enter wrong input you can not use your ability ;)" + ANSI_RESET);
                        night.yes();
                    }
                    voteAttendant.setValue(0);

                }

            }



            if (alive.contains(name)){
                System.out.println(name + " left the game!");
            }
            else {
                dataOutputStream.writeUTF("thanks for playing :)");
            }



            //System.out.println(name + "left the game!!!");


            dataOutputStream.close();
            dataOutputStream.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * add player name to list of players and alive players
     */
    public synchronized void addName(){
        playersList.add(name);
        alive.add(name);
    }

    /**
     * add name and role to playersNameRoles and firstNameSecRole
     */
    public void addNameAndRole(){
        playersNameRoles.put(role,name);
        firstNameSecRole.put(name,role);
    }

    /**
     * creating voting
     */
    public void createVotingSystem(){

        for (String player:playersList){
            votingSystem.put(player,0);
        }

    }


    /**
     * this method handle night and if player have an
     * ability can use it by this method
     * @param dataInputStream
     * @param dataOutputStream
     */
    public void nightMode(DataInputStream dataInputStream,DataOutputStream dataOutputStream){
        try {
            while (true){
                String check = dataInputStream.readUTF();
                if (check.equals("ready")){
                    break;
                }
                else {
                    Thread.sleep(5000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(role.equals("cops")){
            try {
                dataOutputStream.writeUTF("who do you suspect?");
                String inp = dataInputStream.readUTF();

                if (firstNameSecRole.get(inp).equals("Mafia") || firstNameSecRole.get(inp).equals("Dr_lecture")){
                    dataOutputStream.writeUTF("+");

                }
                else if(playersList.contains(inp)){
                    dataOutputStream.writeUTF("-");

                }
                else {
                    dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                }
                //System.out.println("cops played");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (role.equals("citizen")){
            try {
                dataOutputStream.writeUTF("you don't have ability :)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (role.equals("mayor")){
            try {
                dataOutputStream.writeUTF("you don't have ability :)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if(role.equals("Doctor")){
            try {
                dataOutputStream.writeUTF("who do you want to heal?");
                String inp = dataInputStream.readUTF();
                if (playersList.contains(inp)){
                    dataOutputStream.writeUTF("ok :)");
                }
                else {
                    dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                }
                docHeal = inp;
                //System.out.println("doc played");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if (role.equals("Sniper")){
            try {
                dataOutputStream.writeUTF("who is your shot?");
                String inp = dataInputStream.readUTF();
                if (playersList.contains(inp)){
                    //baraye 10 nafar ok mishe
                    if (firstNameSecRole.get(inp).equals("Mafia") || firstNameSecRole.get(inp).equals("Dr_lecture") ||
                            firstNameSecRole.get(inp).equals("GodFather")){
                        sniperShot = inp;
                    }
                    else {
                        sniperShot = name;
                    }
                    dataOutputStream.writeUTF("ok :)");
                }
                else {
                    dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                }

                //System.out.println("Sniper played");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if (role.equals("psychologist")){
            try {
                dataOutputStream.writeUTF("who do you want to silent?");
                String inp = dataInputStream.readUTF();
                if (playersList.contains(inp)){
                    dataOutputStream.writeUTF("ok :)");
                }
                else {
                    dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                }
                psychoAct = inp;
                //System.out.println("psychologist played");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (role.equals("die_hard")){
            if (inquiryNum < 2){
                try {
                    dataOutputStream.writeUTF("do you want inquiry?\nif you want enter(yes)\n else enter(no)");
                    String inp = dataInputStream.readUTF();
                    if (inp.equals("yes")){
                        inquiryNum++;
                        dieHardAct = true;
                        dataOutputStream.writeUTF("ok :)");
                    }
                    else {
                        dataOutputStream.writeUTF("you didn't use your ability");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else {
                try {
                    dataOutputStream.writeUTF("you can not use your ability becuase you haved used it twice before!!! :)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println("die hard played");
        }

        else if (role.equals("Dr_lecture")){
            try {

                if (deadsRoles.contains("GodFather") && deadsRoles.contains("Mafia")){
                    dataOutputStream.writeUTF("who is your shot?");
                    String inp = dataInputStream.readUTF();
                    if (playersList.contains(inp)){
                        dataOutputStream.writeUTF("ok :)");
                    }
                    else {
                        dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                    }
                    mafiaShot = inp;
                }

                dataOutputStream.writeUTF("who do you want to heal?");
                String inp = dataInputStream.readUTF();
                if (playersList.contains(inp)){
                    dataOutputStream.writeUTF("ok :)");
                }
                else {
                    dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                }
                docLecHeal = inp;
                //System.out.println("doc lecture played");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (role.equals("GodFather")){
            try {
                dataOutputStream.writeUTF("who is your shot?");
                String inp = dataInputStream.readUTF();
                if (playersList.contains(inp)){
                    dataOutputStream.writeUTF("ok :)");
                }
                else {
                    dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                }
                mafiaShot = inp;
                //System.out.println("mafia group played");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else  if (role.equals("Mafia")){
            if (deadsRoles.contains("GodFather")){
                try {
                    dataOutputStream.writeUTF("who is your shot?");
                    String inp = dataInputStream.readUTF();
                    if (playersList.contains(inp)){
                        dataOutputStream.writeUTF("ok :)");
                    }
                    else {
                        dataOutputStream.writeUTF("wrong input and you lost your chance to use ability!!!");
                    }
                    mafiaShot = inp;
                    //System.out.println("mafia group played");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    dataOutputStream.writeUTF("tonight you don't have ability :)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    //TODO in ja havaset bashe agar error dad shayad az ine ke az method nemitooni estefade koni va bayad to khode run benvisi


    /**
     * print night resault
     */
    public synchronized void nightRes(){
        //TODO hatman havaset bashe hameye boolean ha va int haii ke vase chek kardan dari be halate ghabl bargardooni
        if (!printNightResault.getvalue()){
            printNightResault.yes();
            ArrayList<String> deads = new ArrayList<String>();
            if (mafiaShot.equals(docHeal) || mafiaShot.equals(docLecHeal)){
                //nothing
            }
            else {
                deads.add(mafiaShot);
            }

            if (sniperShot.equals(docHeal) || sniperShot.equals(docLecHeal)){
                //nothing
            }
            else {
                if (playersList.contains(sniperShot)){
                    deads.add(sniperShot);
                }
            }

            if (deads.size() == 2 && deads.get(0).equals(deads.get(1))){
                deads.remove(1);
            }

            System.out.printf("the night deads: ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (deads.size() == 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("nobody!!");
            }


            else {
                for (String dead:deads){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf(dead + ".");
                    deadsRoles.add(playersNameRoles.get(dead));
                    alive.remove(dead);
                    playersList.remove(dead);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (dieHardAct){
                System.out.println("\ndeads: ");
                dieHardAct = false;
                for (String dead:deadsRoles){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(dead + ".");
                }
            }
        }


    }

    /**
     * registering vote
     * @param vote is the vote of player
     */
    public synchronized void registerVote(String vote){
        int check = votingSystem.get(vote);
        votingSystem.remove(vote);
        votingSystem.put(vote,(check + 1));
        if (votingSystem.get(vote) > maxNumberOfVote.getValue()){
            maxNumberOfVote.setValue(votingSystem.get(vote));
        }
    }

    /**
     * print voting resault
     */
    public synchronized void votingResault(){

        if (!printVotingResault.getvalue()){
            printVotingResault.yes();

            System.out.println("\n");
            try {
                for (int i = 0;i < 10;i++){
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
                    //System.out.println(ANSI_RED + "you can speak with your friends for the last time :)" + ANSI_RESET);
                    //System.out.println(dataInputStream.readUTF());
                    //dataOutputStream.writeUTF("your sss");
                    deadsRoles.add(playersNameRoles.get(quit));
                    alive.remove(quit);
                    playersList.remove(quit);
                    playeExitInVote = quit;
                }

                else {
                    System.out.println("no one left the game");
                    playeExitInVote = "none";
                }


            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
        else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }


}
