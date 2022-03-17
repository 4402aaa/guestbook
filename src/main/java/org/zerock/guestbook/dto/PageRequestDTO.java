package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
@Log4j2
/*요청*/
public class PageRequestDTO {
    private int page;
    private int size;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }
    /*jpa repository를 호출하기 위해 중요 */
    public Pageable getPageable(Sort sort){
        log.info("pageRequestDTO_getPageable실행");
        return PageRequest.of(page-1,size,sort);
    }
}
