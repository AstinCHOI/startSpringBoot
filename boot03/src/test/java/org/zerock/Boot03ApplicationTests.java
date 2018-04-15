package org.zerock;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot03ApplicationTests {

	@Autowired
	private BoardRepository repo;
	
	@Test
	public void testInsert200() {
		for (int i = 1; i <= 200; i++) {
			Board board = new Board();
			board.setTitle("제목.." + i);
			board.setContent("내용 ...." + i + " 채우기");
			board.setWriter("user0" + (i % 10));
			repo.save(board);
		}
	}
	
	@Test
	public void testByTitle() {
//		List<Board> list = repo.findBoardByTitle("제목..117");
//		for(int i=0, len=list.size() ; i < len ; i++) {
//			System.out.println(list.get(i));
//		}
		repo.findBoardByTitle("제목..117").forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByWriter() {
		Collection<Board> results = repo.findBoardByWriter("user00");
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByWriterContaining() {
		Collection<Board> results = repo.findByWriterContaining("05");
		results.forEach(board -> System.out.println(board));
	}

	@Test
	public void testByTitleAndBno() {
		Collection<Board> results = repo.findByTitleContainingAndBnoGreaterThan("5", 50L);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderBy() {
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(90L);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderByPaging() {
		Pageable paging = PageRequest.of(0,  10);
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoPagingSort() {
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
		
//		Collection<Board> results = repo.findByBnoGreaterThan(0L, paging);
		Page<Board> result = repo.findByBnoGreaterThan(0L, paging);
		
		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT: " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		result.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitle2() {
		repo.findByTitle("17").forEach(board -> System.out.println(board));
	}
}
