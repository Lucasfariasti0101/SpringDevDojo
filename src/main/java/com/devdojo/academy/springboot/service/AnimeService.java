package com.devdojo.academy.springboot.service;

import com.devdojo.academy.springboot.domain.Anime;
import com.devdojo.academy.springboot.repository.AnimeRepository;
import com.devdojo.academy.springboot.request.AnimePostRequestBody;
import com.devdojo.academy.springboot.request.AnimePutRequestBody;
import com.devdojo.academy.springboot.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnimeService {

    @Autowired
    AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public Anime findByIdOrThrowNotFoundException(long id) {
        return animeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Anime not found with id: " + id));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowNotFoundException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowNotFoundException(animePutRequestBody.getId());
        Anime anime = Anime.builder()
                .id(savedAnime.getId())
                .name(animePutRequestBody.getName())
                .build();
        animeRepository.save(anime);
    }

    public List<Anime> findAnimeByName(String name) {
        return animeRepository.findByName(name);
    }

}

