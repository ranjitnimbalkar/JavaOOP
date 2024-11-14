package reactiv.end.to.end.shared.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.proxy.ProxyConnectionFactory;
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactiv.end.to.end.shared.util.EnvironmentUtils;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseConfig {
    private static final int MAX_LOGGER_MSG_SIZE = 500;
    // R2dbcAutoConfiguration will work out of the box for you with connection pooling
    // but sometimes you need logic around how password or other detail retrieve then you this own configurations

    private final R2dbcProperties r2dbcProperties;

    @Bean
    public ConnectionPool connectionPool() {
        DbLogger.LOGGER.info("Getting Connection Pool...");
        ConnectionPoolConfiguration.Builder connectionPoolConfigBuilder = ConnectionPoolConfiguration.builder(connectionFactory());
        R2dbcProperties.Pool poolProps = r2dbcProperties.getPool();
        if(poolProps != null) {
            connectionPoolConfigBuilder.initialSize(poolProps.getInitialSize()) //
                    .maxSize(poolProps.getMaxSize()); //
        }
        ConnectionPool connectionPool = new ConnectionPool(connectionPoolConfigBuilder.build());
        DbLogger.LOGGER.info("Connection Pool Details : allocatedSize : {}, acquiredSize : {}" , connectionPool.getMetrics().get().allocatedSize(),
                connectionPool.getMetrics().get().acquiredSize());
        return  connectionPool;
    }
    
    private ConnectionFactory connectionFactory() {

        QueryExecutionInfoFormatter queryExecutionInfoFormatter = QueryExecutionInfoFormatter.showAll();

        ConnectionFactory connectionFactory = ConnectionFactories.get(connectionFactoryOptions());

        return ProxyConnectionFactory.builder(connectionFactory)
                .onAfterQuery(queryExecutionInfo -> {

                })
                .onAfterQuery(queryExecutionInfo -> {
                    var msg = queryExecutionInfoFormatter.format(queryExecutionInfo);
                    if(msg.length() > MAX_LOGGER_MSG_SIZE){
                        msg = msg.substring(0, MAX_LOGGER_MSG_SIZE) + " ....";
                    }
                    DbLogger.LOGGER.info(msg);
                }).build();
    }

    private ConnectionFactoryOptions connectionFactoryOptions() {

        Boolean isCyberArk = EnvironmentUtils.getAsBoolean("IS_CYBERARK");
        log.info("IsCyberArk: {}" + isCyberArk );
        final String password = isCyberArk ? getCyberarkPass() : r2dbcProperties.getPassword();

        return ConnectionFactoryOptions.parse(r2dbcProperties.getUrl()) //
                         .mutate() //
                         .option(ConnectionFactoryOptions.USER, r2dbcProperties.getUsername()) //
                         .option(ConnectionFactoryOptions.PASSWORD, password) //
                         .option(ConnectionFactoryOptions.DATABASE, r2dbcProperties.getName())
                         .build();
    }

    private String getCyberarkPass() {
        return "codespyder";
    }

    private static final class DbLogger {
        private static final String LOGGER_NAME;
        static {
            String pkg = DatabaseConfig.class.getPackageName();
            LOGGER_NAME = pkg.substring(0, pkg.lastIndexOf(".")) + ".logger." + DbLogger.class.getSimpleName();
        }
        protected static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_NAME);
    }
}
