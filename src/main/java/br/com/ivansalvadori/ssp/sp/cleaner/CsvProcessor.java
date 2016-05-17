package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvProcessor {

    public void gravarCSV(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {

        gravarBoletim(boletinsProcessados, folderPathOutput);
        gravarPartes(boletinsProcessados, folderPathOutput);
        gravarNaturezas(boletinsProcessados, folderPathOutput);

    }

    private void gravarBoletim(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(folderPathOutput + "boletins.csv"), '\t')) {
            String[] titulos = "idBO,numeroBO,idDelegacia,dependencia,local,tipoLocal,circunscricao,dataOcorrencia,turnoOcorrencia,dataComunicacao,horaComunicacao,dataElaboracao,horaElaboracao,flagrante,examesRequisitados,solucao".split(",");
            writer.writeNext(titulos);

            for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
                writer.writeNext(boletimOcorrencia.printCSV().split("#"));
            }
            writer.close();
        }
    }

    private void gravarPartes(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(folderPathOutput + "partesEnvolvidas.csv"), '\t')) {
            String[] titulos = "IdBO,nome,tipoEnvolvimento,rg,naturalidade,nacionalidade,sexo,dataNascimento,idade,estadoCivil,instrucao,profissao,cutis,naturezasEnvolvidas".split(",");
            writer.writeNext(titulos);

            for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
                List<ParteEnvolvida> partesEnvolvidas = boletimOcorrencia.getPartesEnvolvidas();
                for (ParteEnvolvida parteEnvolvida : partesEnvolvidas) {
                    String idBO = boletimOcorrencia.getIdBO() + "#";
                    String partes = idBO + parteEnvolvida.printCSV();
                    writer.writeNext(partes.split("#"));
                }
            }
            writer.close();
        }
    }

    private void gravarNaturezas(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(folderPathOutput + "naturezas.csv"), '\t')) {
            String[] titulos = "IdBO,especie,descricao,desdobramentos".split(",");
            writer.writeNext(titulos);

            for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
                List<NaturezaBoletim> naturezaBoletims = boletimOcorrencia.getNaturezas();
                for (NaturezaBoletim naturezaBoletim : naturezaBoletims) {
                    String idBO = boletimOcorrencia.getIdBO() + "#";
                    String partes = idBO + naturezaBoletim.printCSV();
                    writer.writeNext(partes.split("#"));
                }
            }
            writer.close();
        }
    }
}
