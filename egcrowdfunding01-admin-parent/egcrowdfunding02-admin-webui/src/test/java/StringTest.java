import com.egc.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/27 下午10:16
 */
public class StringTest {

    @Test
    public void testMD5(){
        String source = "123123";
        String encoded = CrowdUtil.MD5(source);
        System.out.println(encoded);
    }

}
