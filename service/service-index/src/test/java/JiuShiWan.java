import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class JiuShiWan {

    public static void main(String[] args) {

        String idForEncode = "ssm";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());

        String asd= "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encode = passwordEncoder.encode(asd);
//        System.out.println(encode);

        User user = (User) User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("user")
                .build();
        System.out.println(user.getPassword());

    }

    @Test
    public void asd(){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authenticationToken = new TestingAuthenticationToken("username", "password", "ROLE_USER");
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
        //

    }

}
