package academy.devdojo.springboot2_essentials.controller;

import academy.devdojo.springboot2_essentials.domain.Anime;
import academy.devdojo.springboot2_essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2_essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2_essentials.service.AnimeService;
import academy.devdojo.springboot2_essentials.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@RequiredArgsConstructor
@Log4j2
public class AnimeController {

    private final DateUtil dateUtil;

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        return new ResponseEntity<>(animeService.findByName(name), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        return new ResponseEntity<Anime>(animeService.replace(animePutRequestBody), HttpStatus.OK);
    }
}
