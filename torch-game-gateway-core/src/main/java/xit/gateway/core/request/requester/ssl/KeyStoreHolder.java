package xit.gateway.core.request.requester.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author Knifer
 * Date: 2020/05/05
 * Description: 证书Holder，持有客户端证书（如果需要的话）
 */
@Component
public class KeyStoreHolder {
    private static KeyStore keyStore;
    private static String password;
    private static boolean useClientAuth;
    private static final Logger logger = LoggerFactory.getLogger(KeyStoreHolder.class);

    public KeyStoreHolder(
            @Value("${torch.gateway.ssl.password}")
            String password,
            @Value("${torch.gateway.ssl.key-store-path}")
            String keyStorePath,
            @Value("${torch.gateway.ssl.use-client-auth}")
            boolean useClientAuth
    ) {
        KeyStoreHolder.useClientAuth = useClientAuth;

        if (!useClientAuth) {
            return;
        }

        KeyStoreHolder.password = password;
        try(InputStream inputStream = KeyStoreHolder.class.getClassLoader().getResourceAsStream(keyStorePath)) {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, password.toCharArray());
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException | IOException e) {
            KeyStoreHolder.useClientAuth = false;
            logger.warn("读取客户端证书信息失败【{}】，将采用单向认证，异常信息：{}", keyStorePath, e.getMessage());
        }
    }

    public static KeyStore getKeyStore(){
        return useClientAuth ? keyStore : null;
    }

    public static char[] getPassword(){
        return useClientAuth ? password.toCharArray() : null;
    }

    public static boolean hasKeyStore(){
        return useClientAuth;
    }
}
