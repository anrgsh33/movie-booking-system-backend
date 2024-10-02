package com.example.demo.service.serviceImpl;

import com.example.demo.entity.MovieEntity;
import com.example.demo.exception.CustomServiceException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ViewerRepository;
import com.example.demo.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ViewerRepository viewerRepository;

    @Override
    public void exportToExcel(HttpServletResponse response) throws IOException {


        //Creating a Workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Movie Report");

        //Setting the style of header
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);


        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setWrapText(true);


        String[] headers = {"Movie ID", "Title", "Genre", "Release Date", "Viewer Count"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }


        List<MovieEntity> movies = movieRepository.findAll();
        int rowIndex = 1;

        //setting cell value
        for (MovieEntity movie : movies) {
            Row row = sheet.createRow(rowIndex++);
            int columnCount = 0;


            row.createCell(columnCount++).setCellValue(movie.getId());
            row.createCell(columnCount++).setCellValue(movie.getTitle());
            row.createCell(columnCount++).setCellValue(movie.getGenre());
            row.createCell(columnCount++).setCellValue(movie.getReleaseDate().toString());


            //Fetching viewer count by movie id
            Optional<Integer> viewerCount = viewerRepository.countByMovieId(movie.getId());

            if(viewerCount.isEmpty()){
                throw new CustomServiceException("Viewer Count not found");
            }
            log.info("Viewer Count "+viewerCount.get());
            row.createCell(columnCount).setCellValue(viewerCount.get());
        }


        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        //setting the file name and type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=movie_report.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
