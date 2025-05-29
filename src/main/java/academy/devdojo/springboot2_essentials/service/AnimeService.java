package academy.devdojo.springboot2_essentials.service;

import academy.devdojo.springboot2_essentials.domain.Anime;
import academy.devdojo.springboot2_essentials.repository.AnimeRepository;
import academy.devdojo.springboot2_essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2_essentials.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        Anime anime = Anime.builder().name(animePostRequestBody.getName()).build();
        return animeRepository.save(anime);
    }

    public void delete(long id) {
        animeRepository.deleteById(id);
    }

    public Anime replace(AnimePutRequestBody animePutRequestBody) {
        Anime animeApdated = findByIdOrThrowBadRequestException(animePutRequestBody.getId());

        Anime anime = Anime.builder()
                .id(animeApdated.getId())
                .name(animePutRequestBody.getName())
                .build();

        animeRepository.save(anime);

        return anime;
    }
}
