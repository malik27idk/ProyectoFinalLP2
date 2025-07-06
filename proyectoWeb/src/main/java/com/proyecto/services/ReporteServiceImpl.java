package com.proyecto.services;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReporteServiceImpl implements IReporteService {

    private final DataSource dataSource;

    public ReporteServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void generarBoletaVenta(Integer idVenta) {
        try {
            // 1. Cargar el archivo .jrxml desde src/main/resources
            InputStream jrxmlStream = new ClassPathResource("reportes/boleta_venta.jrxml").getInputStream();

            // 2. Compilar el reporte en tiempo de ejecución
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // 3. Crear parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idVenta", idVenta);

            // 4. Conectar a la base de datos
            try (Connection conn = dataSource.getConnection()) {
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);

                // 5. Exportar el reporte a PDF (como bytes)
                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

                // 6. Crear la carpeta si no existe y guardar el archivo
                Path boletaPath = Paths.get("boletas");
                Files.createDirectories(boletaPath);
                Path filePath = boletaPath.resolve("boleta_" + idVenta + ".pdf");
                Files.write(filePath, pdfBytes);

                System.out.println("✅ Boleta PDF generada en: " + filePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Error al generar la boleta PDF: " + e.getMessage());
        }
    }
}
