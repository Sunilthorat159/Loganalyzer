package br.com.jonyfs.parser;

import br.com.jonyfs.parser.log.LogRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.shell.result.DefaultResultHandler;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestApplicationRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParserApplicationTests {

    @Resource
    ApplicationContext applicationContext;

    @Resource
    LogRepository  logRepository;

    @Resource
    private Shell shell;

    @Test
    public void test_1_verifyIfApplicationContextIsNotNull() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    public void test_2_verifyIfInitialCountIsZero(){
        assertThat(shell.evaluate(() -> "count")).isEqualTo(0L);
    }

    @Test
    public void test_3_verifyIfAccessLogIsLoaded(){
        assertThat(shell.evaluate(() -> "load access.log")).isEqualTo("Loaded");

    }

    @Test
    public void test_4_verifyIfDataInDataBaseIsLoaded(){
        assertThat(shell.evaluate(() -> "count")).isEqualTo(logRepository.count());
    }

    @Test
    public void test_5_verifyIfDataInDataBaseIsLoaded(){
        assertThat(shell.evaluate(() -> "query 2017-01-01.13:00:00 hourly 100")).isNotNull();
    }

    @Test
    public void test_6verifyIfCleanCommandWorks(){
        assertThat(shell.evaluate(() -> "clean")).isEqualTo("OK!");
        test_4_verifyIfDataInDataBaseIsLoaded();
    }

    @Test
    public void test_7verifyIfParseCommandWorks(){
        assertThat(shell.evaluate(() -> "parse access.log 2017-01-01.13:00:00 hourly 100")).isNotNull();
    }
}
