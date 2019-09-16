package orange.transactional.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import orange.transactional.model.Transaction;
import orange.transactional.service.PersistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static orange.transactional.utils.UtilsObjects.getTransaction;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private PersistService persistService;

    @InjectMocks
    private TransactionController transactionController = new TransactionController();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testEnrollTransaction() throws Exception {
        Gson gson = new GsonBuilder().create();
        mockMvc.perform(post("/orange/enroll/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(getTransaction())))
                .andExpect(status().isOk());
        verify(persistService, times(1)).persistTransaction(Matchers.any(Transaction.class));
        verify(persistService, times(1)).findById(Matchers.anyLong());
        verifyNoMoreInteractions(persistService);
    }
}
