package reactiv.end.to.end.shared.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@UtilityClass
@Slf4j
public class EnvironmentUtils {

    private static final String IS_LOCAL = "IS_LOCAL";
    private static final String ENV_KEY = "ENV";
    private static final String ENV_KEY_UNKNOWN = "LOCAL";
    private static final String HOSTNAME_KEY = "HOSTNAME";
    private static final String HOSTNAME_UNKNOWN = "UNKNOWN";

    /**
     * Enum is Singleton, thread safe and lazy to calculate and cache Environment variable
     */
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PROTECTED)
    enum EnvCacher {
        INSTANCE;

        private final boolean isLocal;
        private final String env;
        private final String hostname;

        EnvCacher() {
            isLocal = Boolean.TRUE.equals(getAsBoolean(IS_LOCAL));
            env = StringUtils.defaultString(getProp(ENV_KEY), ENV_KEY_UNKNOWN);
            hostname = getHostname();
            log.info("**** EnvCacher initialized : {} {} {} ", isLocal, env, hostname);
        }

    }

    public boolean isLocal() {
        return EnvCacher.INSTANCE.isLocal();
    }

    public String env() {
        return EnvCacher.INSTANCE.env();
    }

    public String hostname() {
        return EnvCacher.INSTANCE.hostname();
    }

    private String getHostname() {
        String hostname = getProp(HOSTNAME_KEY);
        if(hostname == null){
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                //ignore
            }
        }
        return StringUtils.defaultString(hostname, HOSTNAME_UNKNOWN);
    }

    public Boolean getAsBoolean(String key) {
        String value = StringUtils.trim(getProp(key));
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception ex) {
            log.error("Value cannot be read as boolean. Key: {} Value: {}" ,key, value, ex);
        }
        return null;
    }

    /*
      <code>System</code> takes preference over <code>Environment</code>.
     */
    public String getProp(String key){
        var value = getSystemProp(key);
        if(StringUtils.isBlank(value)){
            value = getEnvProp(key);
        }
        return value;
    }

    public String getEnvProp(String key) {
        try {
            return System.getenv().get(key);
        } catch (Exception ex) {
           log.error("NOT allowed to read environment or properties due to security constraints "+ ex);
        }
        return null;
    }

    public String getSystemProp(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception ex) {
            log.error("NOT allowed to read environment or properties due to security constraints "+ ex);
        }
        return null;
    }

}
