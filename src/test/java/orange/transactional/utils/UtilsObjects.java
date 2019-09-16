package orange.transactional.utils;

import orange.transactional.model.Account;
import orange.transactional.model.Transaction;
import orange.transactional.model.TransactionDetails;

public class UtilsObjects {

    public static Transaction getTransaction(){
        Transaction transaction = new Transaction();
        transaction.setPayee(new Account("1791116090027", "RO13RNCB0076006386030001", "Lupascu Liviu"));
        transaction.setPayer(new Account("1761116090028", "RO23INGB0001000000000222", "Popescu Lucian"));
        transaction.setTransactionDetails(new TransactionDetails(1L, "returnare imprumut", 400, "2"));
        return transaction;
    }



}
