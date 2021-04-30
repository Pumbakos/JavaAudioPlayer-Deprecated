package audio.controler;

import audio.player.AudioPlayer;

import java.util.Scanner;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);
    private AudioPlayer player;
    private String command;

    public Controller(AudioPlayer player){
        this.player = player;
    }

    public void menu() {
        System.out.println("\t\t\tMENU");
        System.out.println("Enter 'play' to start playing.");
        System.out.println("Enter 'stop' to stop playing.");
        System.out.println("Enter 'next' for next song.");
        System.out.println("Enter 'previous' for previous song.");
        System.out.println("Enter 'list' for listing all songs.");
        System.out.println("Enter 'help' for help");
//        System.out.println("Enter 'queue' to see current queue");

        System.out.println("\nHere are songs from your folder, choose one:");
        player.list();
        player.setCurrentSong();
    }

    public void help() {
        System.out.println("Enter 'help' for help");
    }

    public void simpleCmd() {
        do{
            System.out.print(">> ");
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "play": {
                    player.play();
                }
                break;

                case "stop": {
                    player.stop();
                }
                break;

                case "next": {
                    player.next();
                }
                break;

                case "previous": {
                    player.previous();
                }
                break;

                case "list": {
                    player.list();
                }
                break;

                default:
                    help();
                    break;
            }
        }while(!command.equalsIgnoreCase("exit"));
    }
}
