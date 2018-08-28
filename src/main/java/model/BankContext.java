package model;

import java.util.ArrayList;
import java.util.List;

public class BankContext {

    private List<Integer> accounts;

    public BankContext() {
        this.accounts = new ArrayList<>();
    }

    public List<Integer> getAccounts() {
        return accounts;
    }

    public void addAccount(Integer amount) {
        this.accounts.add(amount);
    }

    public void addAccount(Integer... amounts) {
        for (Integer amount : amounts) {
            this.accounts.add(amount);
        }
    }

    public void changeAccountDeposit(Integer accountIndex, Integer newDeposit) {
        this.accounts.set(accountIndex, newDeposit);
    }

    public int getAccountNumber() {
        return this.accounts.size();
    }

    public static BankContext getInvalidContext(int invalidCommandIndex) {
        final BankContext bankContext = new BankContext();
        bankContext.addAccount(-invalidCommandIndex);
        return bankContext;
    }

}
