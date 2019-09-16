package orange.transactional.service;

import orange.transactional.model.Transaction;
import orange.transactional.repository.AccountRepository;
import orange.transactional.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static orange.transactional.utils.UtilsObjects.getTransaction;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersistServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private PersistService persistService = new PersistService(transactionRepository, accountRepository);

    @Test
    public void testPersistTransactionWhenNeitherPayeeOrPayerArePrevioslySavedToDB(){
        Transaction transaction = getTransaction();
        when(accountRepository.findAccountByCnpAndIban(anyString(), anyString())).thenReturn(null);
        persistService.persistTransaction(transaction);
        verify(accountRepository, times(1)).findAccountByCnpAndIban(transaction.getPayee().getCnp(), transaction.getPayee().getIban());
        verify(accountRepository, times(1)).saveAndFlush(transaction.getPayee());

        verify(accountRepository, times(1)).findAccountByCnpAndIban(transaction.getPayer().getCnp(), transaction.getPayer().getIban());
        verify(accountRepository, times(1)).saveAndFlush(transaction.getPayer());

        verify(transactionRepository,times(1)).save(transaction);
        verifyNoMoreInteractions(transactionRepository);
        verifyNoMoreInteractions(accountRepository);
    }


    @Test
    public void testPersistTransactionWhenPayeeAndPayerWasPrevioslySavedToDB(){
        Transaction transaction = getTransaction();
        when(accountRepository.findAccountByCnpAndIban(anyString(), anyString())).thenReturn(transaction.getPayee());
        persistService.persistTransaction(transaction);
        verify(accountRepository, times(1)).findAccountByCnpAndIban(transaction.getPayee().getCnp(), transaction.getPayee().getIban());
        verify(accountRepository, times(1)).findAccountByCnpAndIban(transaction.getPayer().getCnp(), transaction.getPayer().getIban());
        verify(transactionRepository,times(1)).save(transaction);
        verifyNoMoreInteractions(transactionRepository);
        verifyNoMoreInteractions(accountRepository);
    }
}
