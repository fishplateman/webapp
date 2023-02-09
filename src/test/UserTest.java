import LeiYang.entity.Users;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import LeiYang.util.ExceptionMessage;



@RunWith(JUnit4.class)
@SpringBootTest
public class UserTest {
    private static String adminToken;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void getHealth() throws Exception {
        //Users user = restTemplate.getForOb'dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: junit:junit:jar -> version 4.12 vs (?) @ line 106, column 21
        //It is highly recommended to fix these problems because they threaten the stability of your build.
        //For this reason, future Maven versions might no longer support building such malformed projects.ject("/user/get_user/{id}", Users.class, 1);
        //System.out.println(user);
        System.out.println("Hello");
    }
}

