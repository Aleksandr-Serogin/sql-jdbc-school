package ua.com.foxminded.university;

import ua.com.foxminded.university.comandlineinterface.Coordinator;

import static ua.com.foxminded.university.comandlineinterface.Coordinator.showMenu;

/**
 * Date: Feb 11-2021 Class read at console sentence, give them on processed,
 * take processed sentence and print them.
 *
 * @author Aleksandr Serohin
 * @version 1.0001
 */
public class Main {

    /**
     * The main method off this application.
     *
     * @param args array of string arguments.
     */
    public static void main(String[] args) {

        Coordinator coordinator =new Coordinator();
        showMenu();
        coordinator.startCommandLine();
    }
}