package com.example.demo.service;


import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ReportService {
    void exportToExcel(HttpServletResponse response) throws IOException;
}

