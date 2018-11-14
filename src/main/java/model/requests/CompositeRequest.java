package model.requests;

import model.BankContext;
import model.BankRequest;
import model.factory.RequestFactory;

import java.util.List;

public class CompositeRequest implements BankRequest {

    public BankContext handle(List<String> commands, BankContext context) {
        BankContext newContext = context;
        for (int i = 0; i < commands.size(); i++) {
            BankRequest req = RequestFactory.getRequest(commands.get(i));
            if (!req.canHandle(commands.get(i), context)) {
                return BankContext.getInvalidContext(i + 1);
            }
            newContext = req.handle(commands.get(i), newContext);

        }
        return newContext;
    }

    @Override
    public BankContext handle(String command, BankContext context) {
        BankRequest req = RequestFactory.getRequest(command);
        if (!req.canHandle(command, context)) {
            return BankContext.getInvalidContext(1);
        }

        return req.handle(command, context);
    }

    @Override
    public boolean canHandle(String command, BankContext context) {
        BankRequest req = RequestFactory.getRequest(command);
        if (!req.canHandle(command, context)) {
            return false;
        }
        return true;
    }

}
