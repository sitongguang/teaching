package jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import util.JwtUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Thanks to https://github.com/oktadeveloper/okta-java-jwt-example.git
 */
public class JwtUtilTest {

    private static final Logger logger = LogManager.getLogger();

    /*
        Create a simple JWT, decode it, and assert the claims
     */
    @Test
    public void createAndDecodeJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JwtUtil.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                0
//                jwtTimeToLive // used to calculate expiration (claim = exp)
        );


        String jwt2 = JwtUtil.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                0
//                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        logger.info(jwt.equalsIgnoreCase(jwt2));
        logger.info(jwt);
        logger.info(jwt2);

        logger.info("jwt = \"" + jwt.toString() + "\"");

        Claims claims = JwtUtil.decodeJWT(jwt);
        Claims claims2 = JwtUtil.decodeJWT(jwt2);



        logger.info("claims = " + claims.toString());
        logger.info("claims2 = " + claims2.toString());

        assertEquals(jwtId, claims.getId());
        assertEquals(jwtIssuer, claims.getIssuer());
        assertEquals(jwtSubject, claims.getSubject());

    }

    /*
        Attempt to decode a bogus JWT and expect an exception
     */
    @Test(expected = MalformedJwtException.class)
    public void decodeShouldFail() {

        String notAJwt = "This is not a JWT";

        // This will fail with expected exception listed above
        Claims claims = JwtUtil.decodeJWT(notAJwt);

    }

    /*
    Create a simple JWT, modify it, and try to decode it
 */
    @Test(expected = SignatureException.class)
    public void createAndDecodeTamperedJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JwtUtil.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        logger.info("jwt = \"" + jwt.toString() + "\"");

        // tamper with the JWT

        StringBuilder tamperedJwt = new StringBuilder(jwt);
        tamperedJwt.setCharAt(22, 'I');

        logger.info("tamperedJwt = \"" + tamperedJwt.toString() + "\"");

        assertNotEquals(jwt, tamperedJwt);

        // this will fail with a SignatureException

        JwtUtil.decodeJWT(tamperedJwt.toString());

    }

    @Test
    public void rolesTest(){
        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JwtUtil.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        Claims claims = JwtUtil.decodeJWT(jwt);


    }
}