package model.requests;

import model.BankContext;
import model.BankRequest;
import model.factory.RequestFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WithdrawRequest extends BankRequest {

    @Override
    public BankContext handle(final String command, final BankContext context) {

        final String[] args = command.split(" ");
        int accountIndex = Integer.valueOf(args[1]) - 1;
        int sum = Integer.valueOf(args[2]);

        List<Integer> accounts = context.getAccounts();
        final int currentValue = accounts.get(accountIndex);

        context.changeAccountDeposit(accountIndex, currentValue - sum);
        return context;
    }

    @Override
    public BankRequest getType(String command) {
        return RequestFactory.getRequest(command);
    }

    @Override
    public boolean isValid(String command, BankContext context) {
        final String[] args = command.split(" ");
        int accountIndex = Integer.valueOf(args[1]) - 1;
        int sum = Integer.valueOf(args[2]);

        if (!(args.length == 3)) {
            return false;
        }

        Set<Integer> accountArgs = new HashSet<>();
        accountArgs.add(1);

        for (final Integer accArg : accountArgs) {
            if (Integer.valueOf(args[accArg]) > context.getAccountNumber()) {
                return false;
            }
        }

        return context.getAccounts().get(accountIndex) >= sum;
    }

    @Override
    public boolean isType(String command) {
        return command.contains("withdraw");
    }

}
