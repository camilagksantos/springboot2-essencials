package academy.devdojo.springboot2_essentials.service;

import academy.devdojo.springboot2_essentials.domain.Anime;
import academy.devdojo.springboot2_essentials.exception.BadRequestException;
import academy.devdojo.springboot2_essentials.mapper.AnimeMapper;
import academy.devdojo.springboot2_essentials.repository.AnimeRepository;
import academy.devdojo.springboot2_essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2_essentials.requests.AnimePutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
//        AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
//
//        Anime anime = Anime.builder().name(animePostRequestBody.getName()).build();
//        return animeRepository.save(anime);
        //outro jeito
//        return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());

        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.deleteById(id);
    }

    public Anime replace(AnimePutRequestBody animePutRequestBody) {
        Anime animeUpdated = findByIdOrThrowBadRequestException(animePutRequestBody.getId());

//        Anime anime = Anime.builder()
//                .id(animeApdated.getId())
//                .name(animePutRequestBody.getName())
//                .build();

        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(animeUpdated.getId());

        animeRepository.save(anime);

        return anime;
    }
}
