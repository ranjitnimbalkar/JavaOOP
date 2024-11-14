package reactiv.end.to.end.query.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;
import reactiv.end.to.end.shared.security.jwt.JwtVerifier;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String keyPath = "cert/private_key.der";
    private final JwtVerifier jwtVerifier;

    private PrivateKey privateKey;

    @PostConstruct
    private void init() throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeySpecException, JoseException {
        this.privateKey = loadSingingKey(keyPath);
//        Mono<String> jwt = generateJwt();
//        jwt.subscribe(s -> {
//            try {
//                jwtVerifier.verify(s);
//            } catch (JwtVerifierException e) {
//                e.printStackTrace();
//            }
//        });
    }

    private PrivateKey loadSingingKey(String keyPath) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeySpecException {
        final URL resource = getClass().getClassLoader().getResource(keyPath);
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.toURI()));
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public Mono<String> generateJwt() throws JoseException {
        JwtClaims jwtClaims = new JwtClaims();
        jwtClaims.setSubject("client1");
        jwtClaims.setIssuedAtToNow();

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(jwtClaims.toJson());
        jws.setKey(privateKey);
        jws.setAlgorithmHeaderValue("RS256");

        String jwt = jws.getCompactSerialization();
        return Mono.just(jwt);
    }

}
