package model;

import model.factory.RequestFactory;

public interface BankRequest {

    BankContext handle(final String command, final BankContext context);

    boolean canHandle(final String command, final BankContext context);

}
