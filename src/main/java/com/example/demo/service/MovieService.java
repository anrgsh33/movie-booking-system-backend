package com.example.demo.service;

import com.example.demo.dto.MovieRequestDto;
import com.example.demo.dto.MovieResponseDto;
import com.example.demo.response.ResponseModel;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface MovieService {
    ResponseModel<List<MovieResponseDto>> getAllMovies();
    ResponseModel<MovieResponseDto> getMovieById(int id);
    ResponseModel addMovie(MovieRequestDto movieDto);
    ResponseModel updateMovie(int id, MovieRequestDto movieDto);
    ResponseModel deleteMovie(int id);
}
