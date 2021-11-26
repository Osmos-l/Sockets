package models;

import enums.TypeResponse;
import utils.ListeAuth;

public class RequestHandler {

    private Request request;

    private final ListeAuth db;

    public RequestHandler() {
        db = ListeAuth.getInstance();
    }

    public String execute() {
        String login = request.getLogin();
        String password = request.getPassword();

        String response = "";
        switch (request.getCommand()) {
            case CHECK -> response = login(login, password);
            case ADD -> response = add(login, password);
            case DELETE -> response = delete(login, password);
            case UPDATE -> response = edit(login, password);
        }

        return response;
    }

    private String add(String login, String password) {
        return db.creer(login, password) ? TypeResponse.CREATED.getMessage()
                : TypeResponse.ERROR.getMessage() + " La pair existe déjà";
    }

    private String delete(String login, String password) {
        return db.supprimer(login, password) ? TypeResponse.CREATED.getMessage()
                : TypeResponse.ERROR.getMessage() + " La pair existe pas";
    }

    private String edit(String login, String password) {
        return db.mettreAJour(login, password) ? TypeResponse.CREATED.getMessage()
                : TypeResponse.ERROR.getMessage() + " La pair existe pas";
    }

    private String login(String login, String password) {
        return db.tester(login, password) ? TypeResponse.FOUND.getMessage()
                : TypeResponse.NOT_FOUND.getMessage();
    }

    public void setRequest(Request req) {
        request = req;
    }
}
