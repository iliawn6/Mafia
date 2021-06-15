package Mafia;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this project is implementation of mafia which is one of the
 * most popular game in the world
 * the Server_God class have the role of god in mafia
 * @author ilia
 * @version 1.0
 */


public class Server_God {


    //fields

    private ArrayList<String> players;
    //in hashmap shomare bazikon az 1 ta 10 + naghshe har bazikon ro darad
    private HashMap<Integer,Roles> playerroles;
    //in hashmap name bazikon + naghshe har bazikon ro darad
    private HashMap<String,String> playersNameRoles;
    private IntObject votesToBeginVoting = new IntObject(0);
    private boleanObject printVotingResault;
    private IntObject voteAttendant;
    private HashMap<String,Integer> votingSystem;
    private IntObject maxNumberOfVote;
    private ArrayList<String> alive;
    private ArrayList<String> deadsRoles;
    private boleanObject night;
    private String docHeal;
    private String docLecHeal;
    private String mafiaShot;
    private String sniperShot;
    private String psychoAct;
    private boleanObject printNightResault;
    private String playerExitInVote;
    private HashMap<String,String> firstNameSecRole;



    //constructor

    public Server_God(){
        players = new ArrayList<String>();
        playerroles = new HashMap<Integer,Roles>();
        playersNameRoles = new HashMap<String,String>();
        voteAttendant = new IntObject(0);
        votingSystem = new HashMap<String,Integer>();
        maxNumberOfVote = new IntObject(0);
        alive = new ArrayList<String>();
        deadsRoles = new ArrayList<String>();
        night = new boleanObject(false);
        printNightResault = new boleanObject(false);
        printVotingResault = new boleanObject(false);
        firstNameSecRole = new HashMap<String,String>();
        sniperShot = "none";
        docHeal = "none";
        docLecHeal = "none";
        mafiaShot = "none";
        psychoAct = "none";
    }


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public enum Roles{Mafia,GodFather,Dr_lecture,Doctor,Sniper,psychologist,cops,citizen,mayor,die_hard}


    /**
     * this method shuffle the numbers between 1 to 10 for distributing roles
     * @return list of number between 1 to 10 without arrange
     */
    public ArrayList<Integer> Randomgenerator(){
        ArrayList<Integer> first = new ArrayList<Integer>();
        ArrayList<Integer> res = new ArrayList<Integer>();

        for (int i = 0; i < 10 ;i++){
            first.add(i+1);
        }

        Random random= new Random();

        int check;
        for (int i = 10; i > 0 ; i--){
            check = random.nextInt(i);
            //System.out.println(first.get(check)-1);
            res.add(first.get(check));
            //TODO inja havaset bashe age mikhay index ha az 0 ta 9 bashan
            first.remove(check);
        }

        //TODO in jash agar dorost beshe faghat array listesho return kon
        //TODO badesh hashmapo dorost mikoni revale

        return res;
    }


    /**
     * this method distributes roles between players
     */

    public void distributingRoles(){
        Roles roles[] = Roles.values();
        ArrayList<Integer> AllPlayers = Randomgenerator();

        for (int i=0; i<10 ; i++){
            playerroles.put(AllPlayers.get(i),roles[i]);
        }


    }



    /**
     * the connect method is for creating server and
     * starting the game
     */
    public void Connect(){
        {
            distributingRoles();
            //System.out.println(playerroles.toString());

            try {
                ServerSocket  serverSocket = new ServerSocket(9000);
                System.out.println(ANSI_CYAN + "------welcome to mafia------\n" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "waiting for players...  0/10" + ANSI_RESET);
                //int count = 0;
                for (int i = 0; i < 10 ; i++){

                    Socket socket = serverSocket.accept();

                    /*
                    GameHandler gameHandler = new GameHandler(socket,players,playerroles.get(i+1).toString());
                    ExecutorService executorService = Executors.newCachedThreadPool();
                    executorService.execute(gameHandler);

                     */



                    Thread thread = new Thread(new GameHandler(socket,players,playerroles.get(i+1).toString()
                            ,playersNameRoles,votesToBeginVoting,printVotingResault
                            ,voteAttendant,votingSystem,maxNumberOfVote,alive,deadsRoles,night,
                             docHeal, docLecHeal,mafiaShot,sniperShot,
                            psychoAct,printNightResault,playerExitInVote,firstNameSecRole));
                    thread.start();
                    System.out.println(ANSI_CYAN + "players:" + (i+1) + "/10" + ANSI_RESET);
                    /*
                    if (i == 3){
                        executorService.shutdown();
                        break;
                    }
                     */
                }
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
