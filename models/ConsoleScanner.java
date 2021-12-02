package models;

import java.util.Scanner;

public abstract class ConsoleScanner {

    private final String BREAKPOINT = ".fin";

    private Scanner scanner;

    public ConsoleScanner() {
        scanner = new Scanner(System.in);
    }

    public void askUserInput() {
        System.out.println("Tapez vos phrases ou "+ BREAKPOINT +" pour arrêter :");
        String input = "";
        while (!input.equalsIgnoreCase(BREAKPOINT)) {
            input = scanner.nextLine();
            if (!input.equalsIgnoreCase(BREAKPOINT)) {
                onUserInput(input);
            }
        }
    }

    /**
     * Fonction lancé automatiquement lors de la saisie d'une phrase par un utilisateur
     * @param input la phrase saisie
     */
    public abstract void onUserInput(String input);
}
