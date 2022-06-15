package woowacourse.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DataSource dataSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logError();
            throw new RuntimeException(e);
        }
        if (connection == null) {
            logError();
            throw new RuntimeException();
        }
        logSuccess();
    }

    private void logSuccess() {
        log.info("[DB Connection Success] ", this.getClass().getSimpleName());
    }

    private void logError() {
        log.info("[DB Connection Fail] ", this.getClass().getSimpleName());
    }
}
