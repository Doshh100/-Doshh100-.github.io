package bank;

public class InterestThread extends Thread{

    @Override
    public void start() {
        Bank.calculateMonthlyInterest();
    }
}
