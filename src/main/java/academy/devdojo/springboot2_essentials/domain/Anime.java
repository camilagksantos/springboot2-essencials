package academy.devdojo.springboot2_essentials.domain;

import lombok.Data;

@Data
public class Anime {
    private String name;

    public Anime() {
    }

    public Anime(String name) {
        this.name = name;
    }
}
