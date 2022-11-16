package com.devdojo.academy.springboot.repository;

import com.devdojo.academy.springboot.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Test for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Save created Anime when successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBySave = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBySave);

        Assertions.assertThat(savedAnime).isNotNull();

        Assertions.assertThat(savedAnime.getId()).isNotNull();

        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBySave.getName());
    }

    @Test
    @DisplayName("Save updated Anime when successful")
    void update_UpdatedAnime_WhenSuccessful() {
        Anime animeToBySave = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBySave);

        savedAnime.setName("Cavaleiros do Zodiaco");

        Anime updatedAnime = this.animeRepository.save(savedAnime);

        Assertions.assertThat(updatedAnime).isNotNull();

        Assertions.assertThat(updatedAnime.getId()).isNotNull();

        Assertions.assertThat(updatedAnime.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("Delete Anime when successful")
    void delete_DeletedAnime_WhenSuccessful() {
        Anime animeToBySave = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBySave);

        this.animeRepository.delete(savedAnime);

        Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find anime by name when successful")
    void findByName_FindNameAnime_WhenSuccessful() {
        Anime animeToBySave = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBySave);

        String name = savedAnime.getName();

        List<Anime> animeList = this.animeRepository.findByName(name);

        Assertions.assertThat(animeList).isNotEmpty().contains(savedAnime);
    }


    @Test
    @DisplayName("Find anime by name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        String name = "DGZ";

        List<Anime> animeList = this.animeRepository.findByName(name);

        Assertions.assertThat(animeList).isEmpty();
    }

    @Test
    @DisplayName("Save throw MethodArgumentNotValidException when name is empty")
    void Save_ThrowsMethodArgumentNotValidException_WhenNameIsEmpty() {
        Anime anime = new Anime();

        Assertions.assertThatExceptionOfType(MethodArgumentNotValidException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("Bad request, invalid fields.");
    }


    private Anime createAnime() {
        return Anime.builder().name("Naruto").build();
    }
}
