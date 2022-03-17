package org.zerock.guestbook.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
@Log4j2
public class PageResultDTO<DTO,EN> {
    /*dto리스트*/
    private List<DTO> dtoList;

    /*총 페이지 번호*/
    private  int totalPage;

    /*현제 페이지 번호*/
    private int page;

    /*목록 사이즈*/
    private int size;

    /*시작 페이지 번호*/
    private int start;

    /*끝 페이지 번호*/
    private int end;

    /*이전*/
    private boolean prev;

    /*다음*/
    private  boolean next;

    /*페이지 번호 목록*/
    private  List<Integer> pageList;

    //Page<엔티티>로 나오는 결과를 DTO의 목록으로 변환하고 결과를 화면까지 전달하기 위해서
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        log.info("PageResultDTO(Page<EN> result, Function<EN,DTO> fn) 실행");
        dtoList=result.stream().map(fn).collect(Collectors.toList());//Entity->dto로 변환
        totalPage=result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        log.info("PageResultDTO_makePageList 실행");
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd: totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
