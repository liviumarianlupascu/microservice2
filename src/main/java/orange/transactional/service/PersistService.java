package orange.transactional.service;

import orange.transactional.model.Account;
import orange.transactional.model.Transaction;
import orange.transactional.repository.AccountRepository;
import orange.transactional.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersistService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Autowired
    PersistService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction findById(Long id){
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        return transactionOptional.isPresent() ? transactionOptional.get() : null;
    }

    public void persistTransaction(Transaction transaction){
        Account payee = transaction.getPayee();
        if (!Optional.ofNullable(accountRepository.findAccountByCnpAndIban(payee.getCnp(), payee.getIban())).isPresent()){
            accountRepository.saveAndFlush(payee);
        }
        Account payer = transaction.getPayer();
        if (!Optional.ofNullable(accountRepository.findAccountByCnpAndIban(payer.getCnp(), payer.getIban())).isPresent()){
            accountRepository.saveAndFlush(payer);
        }
        transactionRepository.save(transaction);
    }

    public Account loadAccount(Account account){
        accountRepository.save(account);
        return account;
    }

}
