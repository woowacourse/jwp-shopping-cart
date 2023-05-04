package cart.service;

import cart.auth.AuthAccount;
import cart.dao.AccountDao;
import cart.dao.AccountEntity;
import cart.service.dto.AccountSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AccountQueryService {

    private final AccountDao accountDao;

    public AccountQueryService(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<AccountSearchResponse> searchAllAccounts() {

        return accountDao.findAll()
                         .stream()
                         .map(entity -> new AccountSearchResponse(
                                 entity.getId(),
                                 entity.getEmail(),
                                 entity.getPassword())
                         )
                         .collect(Collectors.toList());
    }

    public AccountEntity searchByEmailAndPassword(final AuthAccount authAccount) {
        return accountDao.findByEmailAndPassword(
                authAccount.getEmail(),
                authAccount.getPassword()
        );
    }
}
