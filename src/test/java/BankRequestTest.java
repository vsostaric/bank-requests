import model.BankContext;
import model.requests.CompositeRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BankRequestTest {

    private BankContext context;
    private CompositeRequest compositeRequest;

    @Before
    public void init() {
        compositeRequest = new CompositeRequest();

        context = new BankContext();
        context.addAccount(10, 100, 20, 50, 30);

    }

    @Test
    public void testDeposit() {
        BankContext result = compositeRequest.handle("deposit 3 20", context);
        Assert.assertEquals(40, (int) result.getAccounts().get(2));

    }

    @Test
    public void testWithdraw() {
        BankContext result = compositeRequest.handle("withdraw 2 45", context);
        Assert.assertEquals(55, (int) result.getAccounts().get(1));

    }

    @Test
    public void testTransfer() {
        BankContext result = compositeRequest.handle("transfer 1 4 5", context);
        Assert.assertEquals(5, (int) result.getAccounts().get(0));
        Assert.assertEquals(55, (int) result.getAccounts().get(3));

    }

    @Test
    public void testTransfer2() {

        final List<String> commands = new ArrayList<>();
        commands.add("transfer 1 2 3");

        context = new BankContext();
        context.addAccount(42);

        BankContext result = compositeRequest.handle("transfer 1 2 3", context);
        Assert.assertEquals(-1, (int) result.getAccounts().get(0));

    }

    @Test
    public void testComposite() {

        final List<String> commands = new ArrayList<>();
        commands.add("deposit 3 400");
        commands.add("transfer 1 2 30");
        commands.add("withdraw 4 50");

        BankContext result = compositeRequest.handle(commands, context);
        Assert.assertEquals(-2, (int) result.getAccounts().get(0));

    }

    @Test
    public void testComposite2() {

        final List<String> commands = new ArrayList<>();
        commands.add("withdraw 2 10");
        commands.add("transfer 5 1 20");
        commands.add("deposit 5 20");
        commands.add("transfer 3 4 15");

        BankContext result = compositeRequest.handle(commands, context);

        Assert.assertEquals(30, (int) result.getAccounts().get(0));
        Assert.assertEquals(90, (int) result.getAccounts().get(1));
        Assert.assertEquals(5, (int) result.getAccounts().get(2));
        Assert.assertEquals(65, (int) result.getAccounts().get(3));
        Assert.assertEquals(30, (int) result.getAccounts().get(4));

    }

}
