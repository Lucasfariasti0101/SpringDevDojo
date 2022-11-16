package com.devdojo.academy.springboot.controller;

import com.devdojo.academy.springboot.domain.Anime;
import com.devdojo.academy.springboot.request.AnimePostRequestBody;
import com.devdojo.academy.springboot.request.AnimePutRequestBody;
import com.devdojo.academy.springboot.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/animes")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public Page<Anime> listAll(Pageable pageable) {
        return animeService.listAll(pageable);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        Anime anime = animeService.findByIdOrThrowNotFoundException(id);
        return ResponseEntity.ok(anime);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam(name = "name") String name) {
        List<Anime> animes = animeService.findAnimeByName(name);
        return ResponseEntity.ok(animes);
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
        Anime anime = animeService.save(animePostRequestBody);
        return new ResponseEntity<>(anime, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return ResponseEntity.noContent().build();
    }

}
