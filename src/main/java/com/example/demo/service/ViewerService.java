package com.example.demo.service;

import com.example.demo.dto.ViewerCountResDto;
import com.example.demo.dto.ViewerRequestDto;
import com.example.demo.dto.ViewerResponseDto;
import com.example.demo.response.ResponseModel;


import java.util.List;

public interface ViewerService {
    ResponseModel<List<ViewerResponseDto>> getAllViewers();
    ResponseModel<ViewerCountResDto> getViewerCountByMovieId(int movieId);
    ResponseModel addViewer(ViewerRequestDto viewer);
    ResponseModel updateViewer(int id, ViewerRequestDto viewer);
    ResponseModel deleteViewer(int id);
}
