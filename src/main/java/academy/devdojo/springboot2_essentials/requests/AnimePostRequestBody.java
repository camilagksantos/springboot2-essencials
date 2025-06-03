package academy.devdojo.springboot2_essentials.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePostRequestBody {

    @NotNull(message="The anime name cannot be null ")
    @NotEmpty(message="The anime name cannot be empty ")
    private String name;
}
