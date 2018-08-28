package model.requests;

import model.BankContext;
import model.BankRequest;

import java.util.HashSet;
import java.util.Set;

public class TransferRequest extends BankRequest {
    @Override
    public BankContext handle(final String command, final BankContext context) {

        final String[] args = command.split(" ");
        int accountFromIndex = Integer.valueOf(args[1]);
        int accountToIndex = Integer.valueOf(args[2]);
        int sum = Integer.valueOf(args[3]);

        final String withdrawRequest = "withdraw " + accountFromIndex + " " + sum;
        final String depositRequest = "deposit " + accountToIndex + " " + sum;

        final BankContext contextAfterWithdraw = new WithdrawRequest().handle(withdrawRequest, context);
        return new DepositRequest().handle(depositRequest, contextAfterWithdraw);
    }

    @Override
    public boolean isValid(String command, BankContext context) {

        final String[] args = command.split(" ");
        int accountFromIndex = Integer.valueOf(args[1]);
        int accountToIndex = Integer.valueOf(args[2]);
        int sum = Integer.valueOf(args[3]);

        if (!(args.length == 4)) {
            return false;
        }

        Set<Integer> accountArgs = new HashSet<>();
        accountArgs.add(1);
        accountArgs.add(2);

        for (final Integer accArg : accountArgs) {
            if (Integer.valueOf(args[accArg]) > context.getAccountNumber()) {
                return false;
            }
        }

        final String withdrawRequest = "withdraw " + accountFromIndex + " " + sum;
        final String depositRequest = "deposit " + accountToIndex + " " + sum;

        return new WithdrawRequest().isValid(withdrawRequest, context)
                &&
                new DepositRequest().isValid(depositRequest, context);
    }

    @Override
    public boolean isType(String command) {
        return command.contains("transfer");
    }
}
