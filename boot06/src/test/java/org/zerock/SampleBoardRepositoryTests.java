package org.zerock;

import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class SampleBoardRepositoryTests {

    @Autowired
    WebBoardRepository repo;

    @Test
    public void testList1() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        Page<WebBoard> result = repo.findAll(repo.makePredicate(null, null), pageable);

        log.info("PAGE: " + result.getPageable());

        log.info("----------------------");
        result.getContent().forEach(board -> log.info("" + board));
    }

    @Test
    public void testList2() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = repo.findAll(
                repo.makePredicate("t", "10"), pageable);

        log.info("PAGE: " + result.getPageable());

        log.info("----------------------");
        result.getContent().forEach(board -> log.info("" + board));
    }
}
