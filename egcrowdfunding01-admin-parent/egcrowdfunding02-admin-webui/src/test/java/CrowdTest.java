import com.egc.crowd.bean
        .Admin;
import com.egc.crowd.mapper.AdminMapper;
import com.egc.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/22 下午7:15
 */

// Spring整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/*.xml"})
public class CrowdTest {

    @Resource
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Resource
    private AdminService adminService;

    @Test
    public void test() {
        for (int i = 0; i < 230; i++) {
            adminMapper.insert(new Admin(null, "GDMUDog" + i, "userPswd" + i, "DOG" + i, "DOG" + i + "@email.com", null));
        }
    }

    @Test
    public void testCrowdConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testInsertAdmin() {
        System.out.println(adminMapper.insert(new Admin(1, "jack", "123123", "Jack", "jack@email.com", null)));
    }

    @Test
    public void testLog() {

        // 1.获取logger对象，传入的class对象就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);

        // 2.根据日志级别打印日志
        logger.debug("debug level");
        logger.debug("debug level");
        logger.debug("debug level");

        logger.info("info level");
        logger.info("info level");
        logger.info("info level");

        logger.warn("warn level");
        logger.warn("warn level");
        logger.warn("warn level");

        logger.error("error level");
        logger.error("error level");
        logger.error("error level");
    }

    @Test
    public void testTx() {
        adminService.saveAdmin(new Admin(3, "cook", "123qwe", "cook", "cook@email.com", null));
    }

}
