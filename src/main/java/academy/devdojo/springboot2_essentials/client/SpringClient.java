package academy.devdojo.springboot2_essentials.client;

import academy.devdojo.springboot2_essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/1", Anime.class);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class, 1);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate()
                .exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
        });
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("Kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes",kingdom, Anime.class);
//        log.info("Saved Anime {}", kingdomSaved);

        Anime samuraiChamploo = Anime.builder().name("samuraiChamploo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(samuraiChamploo, createJsonHeader()), Anime.class);
        log.info("Saved Anime {}", samuraiChamplooSaved);

        // PUT usando exchange
        Anime animeToUpdate = samuraiChamplooSaved.getBody();
        animeToUpdate.setName("Samurai Champloo Updated");
        new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.PUT, new HttpEntity<>(animeToUpdate), Void.class);
        log.info("Updated Anime with exchange");

        // PUT usando put()
        new RestTemplate().put("http://localhost:8080/animes", animeToUpdate);
        log.info("Updated Anime with put()");

        // DELETE usando exchange
        new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, null, Void.class, animeToUpdate.getId());
        log.info("Deleted Anime with exchange");

        // DELETE usando delete()
        new RestTemplate().delete("http://localhost:8080/animes/{id}", animeToUpdate.getId());
        log.info("Deleted Anime with delete()");


    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
