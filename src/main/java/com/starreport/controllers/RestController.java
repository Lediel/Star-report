package com.starreport.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.starreport.models.*;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestController {

    private PeopleReport convertToPeopleReport(Person person, List<Films> filmes, Planets planeta) {

        int altura = Integer.parseInt(person.getHeight());
        int peso = Integer.parseInt(person.getMass());

        PeopleReport peopleReport = new PeopleReport();

        peopleReport.setName(person.getName());
        peopleReport.setHeight(altura);
        peopleReport.setMass(peso);
        peopleReport.setHair_color(person.getHair_color());
        peopleReport.setSkin_color(person.getSkin_color());
        peopleReport.setEye_color(person.getEye_color());
        peopleReport.setGender(person.getGender());
        peopleReport.setHomeworldName(planeta.getName().toUpperCase());
        peopleReport.setFilms(filmes);
        peopleReport.setQttFilms(filmes.size());
        return peopleReport;
    }

    @GetMapping(value = "/people")
    public @ResponseBody byte[] getPeople( HttpServletResponse response ) throws Exception {

        try {

            String url = "https://swapi.dev/api/people";
            RestTemplate restTemplate = new RestTemplate();

            People people = restTemplate.getForObject(url, People.class);

            List<Person> personList = people.getResults();

            List<PeopleReport> peopleReports = new ArrayList<>();
            for (Person person : personList) {

                List<Films> filmes = new ArrayList<>();

                for (String link : Objects.requireNonNull(person).getFilms()) {
                    Films films = restTemplate.getForObject(link, Films.class);
                    filmes.add(films);
                }

                Planets planeta = restTemplate.getForObject(person.getHomeworld(), Planets.class);

                peopleReports.add(convertToPeopleReport(person, filmes, planeta));
            }
            peopleReports.sort(Comparator.comparing(PeopleReport::getHomeworldName)
                    .thenComparing(PeopleReport::getName));

            return pdfReport(response, new JRBeanCollectionDataSource(peopleReports));
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new Exception("Ocorreu um erro ao gerar o relatório");
        }

    }

    private byte[] pdfReport(HttpServletResponse response, JRBeanCollectionDataSource resultadoConsulta) throws Exception {
        JasperPrint jasperPrint = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            String name = "/relatorios/pessoasReport.jasper";

            InputStream resourceAsStream = getClass().getResourceAsStream(name);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resourceAsStream);
            jasperPrint = JasperFillManager.fillReport( jasperReport, new HashMap<>(), resultadoConsulta);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=pessoasReport.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
            return outStream.toByteArray();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new Exception("Ocorreu um erro ao gerar o relatório");
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
                throw new Exception("Ocorreu um erro ao serializar o relatório");
            }
        }
    }
}
