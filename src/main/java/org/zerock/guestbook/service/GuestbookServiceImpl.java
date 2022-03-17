package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor//의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("dto(register)-----------");
        log.info("dto:"+dto);

        Guestbook entity=dtoToEntity(dto);
        log.info("entity:"+entity);
        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        log.info("service_getList실행");
        Pageable pageable=requestDTO.getPageable(Sort.by("gno").descending());//getPageable()을 통해 pageable에 담는다
        Page<Guestbook> result=repository.findAll(pageable);//findAll을 해서 result에 담는다
        Function<Guestbook,GuestbookDTO> fn = (entity -> entityToDto(entity));//entity가 들어오면 entity->dto로 변환 해서 fn에 담는다
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(long gno) {
        Optional<Guestbook> result=repository.findById(gno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }
}
