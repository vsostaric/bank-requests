package model;

import model.factory.RequestFactory;

import java.util.Set;

public abstract class BankRequest {

    public abstract BankContext handle(final String command, final BankContext context);

    public abstract boolean isValid(final String command, final BankContext context);

    public abstract boolean isType(String command);

    public BankRequest getType(String command) {
        return RequestFactory.getRequest(command);
    }

}
