import com.epam.bankproject.bankproject.domain.impl.CreditAccount;

public class Main {

    public static void main(String[] args) {
        CreditAccount.builder()
                .limit(0.1)
                .balance(1000.0)
                .rate(0.0)
                .build();
    }
}
