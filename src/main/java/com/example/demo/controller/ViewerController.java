package com.example.demo.controller;

import com.example.demo.response.ResponseModel;
import com.example.demo.dto.ViewerCountResDto;
import com.example.demo.dto.ViewerRequestDto;
import com.example.demo.dto.ViewerResponseDto;
import com.example.demo.service.ViewerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ViewerController {

    @Autowired
    private ViewerService viewerService;

    //Get all Viewers
    @GetMapping(value = "/get-viewers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<ViewerResponseDto>>> getViewers() {
        return ResponseEntity.ok(viewerService.getAllViewers());
    }

    //Add a viewer
    @PostMapping(value = "/add-viewer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> addViewer(@Valid @RequestBody ViewerRequestDto viewerDto) {
    ResponseModel response = viewerService.addViewer(viewerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //Get movie viewer count
    @GetMapping(value = "/movie/viewer-count/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<ViewerCountResDto>> getViewerCountByMovieId(@PathVariable int id) {
        return ResponseEntity.ok(viewerService.getViewerCountByMovieId(id));
    }

    //Update a viewer by id
    @PutMapping(value = "/update-viewer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> updateViewer(@PathVariable int id,@Valid @RequestBody ViewerRequestDto viewerDto) {
        ResponseModel response = viewerService.updateViewer(id, viewerDto);
        return ResponseEntity.ok(response);
    }

    //Delete viewer
    @DeleteMapping(value = "/delete-viewer/{id}")
    public ResponseEntity<ResponseModel> deleteViewer(@PathVariable int id) {
        ResponseModel response = viewerService.deleteViewer(id);
        return ResponseEntity.ok(response);
    }

}
