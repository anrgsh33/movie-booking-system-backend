package com.example.demo.service.serviceImpl;

import com.example.demo.dto.MovieResponseDto;
import com.example.demo.dto.ViewerCountResDto;
import com.example.demo.dto.ViewerRequestDto;
import com.example.demo.dto.ViewerResponseDto;
import com.example.demo.entity.MovieEntity;
import com.example.demo.entity.RefundEntity;
import com.example.demo.entity.ViewerEntity;
import com.example.demo.exception.CustomServiceException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RefundRepository;
import com.example.demo.repository.ViewerRepository;
import com.example.demo.response.ResponseModel;
import com.example.demo.service.ViewerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ViewerServiceImpl implements ViewerService {

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public ResponseModel<List<ViewerResponseDto>> getAllViewers() {
        try {
            List<ViewerEntity> viewerList = viewerRepository.findAll();
            List<ViewerResponseDto> responseList = new ArrayList<>();
            for (ViewerEntity viewer : viewerList) {
                ViewerResponseDto responseDto = ViewerResponseDto.builder()
                        .id(viewer.getId())
                        .name(viewer.getName())
                        .watchedDate(viewer.getMovieDate())
                        .movie(MovieResponseDto.builder()
                                .id(viewer.getMovie().getId())
                                .title(viewer.getMovie().getTitle())
                                .releaseDate(viewer.getMovie().getReleaseDate())
                                .genre(viewer.getMovie().getGenre())
                                .build())
                        .build();
                responseList.add(responseDto);
            }
            return new ResponseModel<>(HttpStatus.OK, responseList, "Viewers retrieved successfully");
        } catch (Exception e) {
            throw new CustomServiceException("Failed to retrieve viewers");
        }
    }

    @Override
    public ResponseModel<ViewerCountResDto> getViewerCountByMovieId(int movieId) {
        MovieEntity movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new CustomServiceException("Movie not found with id: " + movieId));

        Optional<Integer> viewerCount = viewerRepository.countByMovieId(movieEntity.getId());

        if (viewerCount.isEmpty()) {
            throw new CustomServiceException("Viewer Count not found");
        }
        log.info("Viewer Count " + viewerCount.get());

        MovieResponseDto movieDto = MovieResponseDto.builder()
                .id(movieEntity.getId())
                .title(movieEntity.getTitle())
                .releaseDate(movieEntity.getReleaseDate())
                .genre(movieEntity.getGenre())
                .build();

        ViewerCountResDto viewerCountResDto = ViewerCountResDto.builder()
                .movie(movieDto)
                .viewerCount(viewerCount.get())
                .build();

        return new ResponseModel<>(HttpStatus.OK, viewerCountResDto, "Viewer count retrieved successfully");
    }

    @Override
    public ResponseModel addViewer(ViewerRequestDto viewerDto) {
        MovieEntity movieEntity = movieRepository.findById(viewerDto.getMovieId())
                .orElseThrow(() -> new CustomServiceException("Movie not found with id: " + viewerDto.getMovieId()));

        ViewerEntity viewer = ViewerEntity.builder()
                .name(viewerDto.getName())
                .movieDate(viewerDto.getWatchedDate())
                .movie(movieEntity)
                .build();

        viewerRepository.save(viewer);

        ResponseModel response=new ResponseModel();
        response.setStatusCode(HttpStatus.CREATED);
        response.setMessage("Viewer Added Successfully");

        return response;
    }

    @Override
    public ResponseModel updateViewer(int id, ViewerRequestDto viewerDto) {
        ViewerEntity viewer = viewerRepository.findById(id)
                .orElseThrow(() -> new CustomServiceException("Viewer not found with id: " + id));

        MovieEntity movieEntity = movieRepository.findById(viewerDto.getMovieId())
                .orElseThrow(() -> new CustomServiceException("Movie not found with id: " + viewerDto.getMovieId()));

        viewer.setName(viewerDto.getName());
        viewer.setMovieDate(viewerDto.getWatchedDate());
        viewer.setMovie(movieEntity);

        viewerRepository.save(viewer);

      //  return new Response(HttpStatus.OK, "Viewer updated successfully");
        // setting only two fields, data will be null automatically
        ResponseModel response=new ResponseModel();
        response.setStatusCode(HttpStatus.OK);
        response.setMessage("Viewer Updated Successfully");

        return response;


    }

    @Override
    public ResponseModel deleteViewer(int id) {
        log.info("Cancel Ticket Service Started");
        ViewerEntity viewer = viewerRepository.findById(id)
                .orElseThrow(() -> new CustomServiceException("Viewer not found with id: " + id));
        ResponseModel response=new ResponseModel();
        if(viewer.getTicketCancelled() == false){
            viewer.setTicketCancelled(true);
            RefundEntity refund=new RefundEntity();
            refund.setRefundStatus("pending");
            refund.setViewer(viewer);
            refundRepository.save(refund);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Viewer Deleted Successfully");
        }else{
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            response.setMessage("Ticket already cancelled");
        }


        return response;
    }
}
