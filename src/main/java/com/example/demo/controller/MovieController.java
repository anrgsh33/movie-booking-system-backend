package com.example.demo.controller;

import com.example.demo.response.ResponseModel;
import com.example.demo.dto.MovieRequestDto;
import com.example.demo.dto.MovieResponseDto;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //Get List of movies
    @GetMapping(value = "/movies-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<MovieResponseDto>>> getAllMovies() {
        ResponseModel<List<MovieResponseDto>> response = movieService.getAllMovies();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Get movie by id
    @GetMapping(value="/get-movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<MovieResponseDto>> getMovieById(@PathVariable int id) {
        ResponseModel<MovieResponseDto> response = movieService.getMovieById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Add a movie
    @PostMapping(value="/add-movie", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> addMovie(@RequestBody @Valid MovieRequestDto movieDto) {
        log.info("movie title : "+movieDto.getTitle());
        ResponseModel response = movieService.addMovie(movieDto);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    //Update movie by id
    @PutMapping(value = "/update-movie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> updateMovie(@PathVariable int id, @Valid @RequestBody MovieRequestDto movieDto) {
       ResponseModel response = movieService.updateMovie(id, movieDto);
       return new ResponseEntity<>(response, response.getStatusCode());
    }

    //Delete movie
    @DeleteMapping(value = "/delete-movie/{id}")
    public ResponseEntity<ResponseModel> deleteMovie(@PathVariable int id) {
        ResponseModel response = movieService.deleteMovie(id);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

}
