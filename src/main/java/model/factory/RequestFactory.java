package model.factory;

import model.BankRequest;
import model.requests.DepositRequest;
import model.requests.TransferRequest;
import model.requests.WithdrawRequest;

public class RequestFactory {

    public static BankRequest getRequest(String command) {

        if (command.toLowerCase().contains("transfer")) {
            return new TransferRequest();
        } else if (command.toLowerCase().contains("deposit")) {
            return new DepositRequest();
        } else if (command.toLowerCase().contains("withdraw")) {
            return new WithdrawRequest();
        }
        return null;
    }

}
