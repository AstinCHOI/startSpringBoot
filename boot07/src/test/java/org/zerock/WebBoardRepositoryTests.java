package org.zerock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class WebBoardRepositoryTests {
    @Autowired
    WebBoardRepository repo;

    @Test
    public void insertBoardDummies() {
        IntStream.range(0, 300).forEach(i -> {
           WebBoard board = new WebBoard();

           board.setTitle("Sample Board Title " + i);
           board.setContent("Content Sample ..." + i + " of Board ");
           board.setWriter("user0" + (i%10));

           repo.save(board);
        });
    }
}
