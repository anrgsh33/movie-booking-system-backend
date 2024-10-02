package com.example.demo.service.serviceImpl;

import com.example.demo.dto.MovieRequestDto;
import com.example.demo.dto.MovieResponseDto;
import com.example.demo.entity.MovieEntity;
import com.example.demo.exception.CustomServiceException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.response.ResponseModel;
import com.example.demo.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public ResponseModel<List<MovieResponseDto>> getAllMovies() {
        try {
            log.info("Get all movies Called");
            List<MovieResponseDto> movies = movieRepository.findAll().stream()
                    .map(this::convertToDto)
                    .toList();
            return new ResponseModel<>(HttpStatus.OK, movies, "Movies retrieved successfully");
        } catch (Exception e) {
            throw new CustomServiceException("Failed to retrieve movies: " + e.getMessage());
        }
    }

    @Override
    public ResponseModel<MovieResponseDto> getMovieById(int id) {
        log.info("Find movie by id: " + id);

        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new CustomServiceException("Movie not found with id: " + id));
        MovieResponseDto movieDto = convertToDto(movie);
        return new ResponseModel<>(HttpStatus.OK, movieDto, "Movie retrieved successfully");
    }

    @Override
    public ResponseModel addMovie(MovieRequestDto movieDto) {
        try {
            MovieEntity movie = convertToEntity(movieDto);
            movieRepository.save(movie);
            ResponseModel response=new ResponseModel();
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Movie Added Successfully");

            return response;
        } catch (Exception e) {
            throw new CustomServiceException("Failed to add movie: " + e.getMessage());
        }
    }

    @Override
    public ResponseModel updateMovie(int id, MovieRequestDto movieDto) {

           try {

              Optional<MovieEntity> movie= movieRepository.findById(id);

              if(movie.isPresent()) {

                  movie.get().setTitle(movieDto.getTitle());
                  movie.get().setGenre(movieDto.getGenre());
                  movie.get().setReleaseDate(movieDto.getReleaseDate());
                  ResponseModel response = new ResponseModel();
                  response.setStatusCode(HttpStatus.OK);
                  response.setMessage("Movie Updated Successfully");
                  return response;
              }else{

                  throw new CustomServiceException("Movie not found with id:"+id);
              }

           }catch (Exception e){
               log.info(e.getMessage());
               throw new CustomServiceException("Some Error Occurred while updating movie");
           }

    }

    @Override
    public ResponseModel deleteMovie(int id) {

            if (!movieRepository.existsById(id)) {
                throw new CustomServiceException("Movie not found with id: " + id);
            }
            movieRepository.deleteById(id);
        ResponseModel response=new ResponseModel();
        response.setStatusCode(HttpStatus.OK);
        response.setMessage("Movie Deleted Successfully");

        return response;
    }



// entity to dto conversion
    private MovieResponseDto convertToDto(MovieEntity movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .releaseDate(movie.getReleaseDate())
                .build();
    }

    //dto to entity conversion
    private MovieEntity convertToEntity(MovieRequestDto movieDto) {
        return MovieEntity.builder()
                .title(movieDto.getTitle())
                .genre(movieDto.getGenre())
                .releaseDate(movieDto.getReleaseDate())
                .build();
    }
}
