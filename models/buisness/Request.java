package models.buisness;

import models.enums.Commands;

public class Request {

    private Commands command;

    private String login;

    private String password;

    public Request(String toTransform) throws Exception {
        String[] splitted = toTransform.split(" ");

        if (splitted.length != 3
                || splitted[0] == null || splitted[1] == null || splitted[2] == null) {
            throw new Exception("Bad format");
        }

        switch (splitted[0].toUpperCase()) {
            case "ADD" -> command = Commands.ADD;
            case "CHK" -> command = Commands.CHECK;
            case "DEL" -> command = Commands.DELETE;
            case "MOD" -> command = Commands.UPDATE;
            default -> throw new Exception("Unknown command");
        }

        login = splitted[1];
        password = splitted[2];
    }

    public Commands getCommand() {
        return command;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
