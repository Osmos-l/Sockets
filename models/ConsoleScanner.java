package models;

import java.util.Scanner;

public abstract class ConsoleScanner {

    /**
     * Le mot qui permet de mettre fin à l'execution
     */
    private final String BREAKPOINT = ".fin";

    private Scanner scanner;

    /**
     * État initial de l'objet
     */
    public ConsoleScanner() {
        scanner = new Scanner(System.in);
    }

    /**
     * Demande à l'utilisateur de saisir une phrase ou, de quitter le system
     */
    public void askUserInput() {
        System.out.println("Tapez vos phrases ou "+ BREAKPOINT +" pour arrêter :");
        String input = "";
        while (!input.equalsIgnoreCase(BREAKPOINT)) {
            input = scanner.nextLine();
            if (!input.equalsIgnoreCase(BREAKPOINT)) {
                onUserInput(input);
            } else {
                onUserLeave();
            }
        }
    }

    /**
     * Fonction lancé automatiquement lors de la saisie d'une phrase par un utilisateur
     * @param input la phrase saisie
     */
    public abstract void onUserInput(String input);

    /**
     * Lorsque l'utilisateur décide de quitter
     */
    public abstract void onUserLeave();
}
