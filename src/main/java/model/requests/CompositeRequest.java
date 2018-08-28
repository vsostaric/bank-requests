package model.requests;

import model.BankContext;
import model.BankRequest;
import model.factory.RequestFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CompositeRequest extends BankRequest {

    private Collection<BankRequest> requests;

    public CompositeRequest() {
        this.requests = new HashSet<>();
    }

    public void addRequest(BankRequest request) {
        this.requests.add(request);
    }

    @Override
    public BankRequest getType(String command) {
        for (BankRequest req : requests) {
            if(req.isType(command)) {
                return req;
            }
        }
        return RequestFactory.getRequest(command);
    }


    public BankContext handle(List<String> commands, BankContext context) {
        BankContext newContext = context;
        for (int i = 0; i < commands.size(); i++) {
            for (BankRequest req : requests) {
                if (req.isType(commands.get(i))) {
                    if (!req.isValid(commands.get(i), context)) {
                        return BankContext.getInvalidContext(i + 1);
                    }
                    newContext = req.handle(commands.get(i), context);
                }
            }
        }
        return newContext;
    }

    @Override
    public BankContext handle(String command, BankContext context) {
        for (BankRequest req : requests) {
            if (req.isType(command)) {
                if (!req.isValid(command, context)) {
                    return BankContext.getInvalidContext(1);
                }
                context = req.handle(command, context);
            }
        }
        return context;
    }

    @Override
    public boolean isValid(String command, BankContext context) {
        for (BankRequest req : requests) {
            if (req.isType(command) && !req.isValid(command, context)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isType(String command) {
        for (BankRequest req : requests) {
            if (req.isType(command)) {
                return true;
            }
        }
        return false;
    }
}
