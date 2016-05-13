package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvProcessor {

    public void gravarCSV(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {

        gravarBoletim(boletinsProcessados, folderPathOutput);
        gravarPartes(boletinsProcessados, folderPathOutput);

    }

    private void gravarBoletim(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(folderPathOutput + "boletim.csv"), '\t')) {
            String[] titulos = "numeroBO,idDelegacia,dependencia,local,tipoLocal,circunscricao,dataOcorrencia,turnoOcorrencia,dataComunicacao,horaComunicacao,dataElaboracao,horaElaboracao,flagrante,examesRequisitados,solucao".split(",");
            writer.writeNext(titulos);

            for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
                writer.writeNext(boletimOcorrencia.printCSV().split("#"));
            }
            writer.close();
        }
    }

    private void gravarPartes(List<BoletimOcorrencia> boletinsProcessados, String folderPathOutput) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(folderPathOutput + "partesEnvolvidas.csv"), '\t')) {
            String[] titulos = "numeroBO,idDelegacia,dataOcorrencia,nome,tipoEnvolvimento,rg,naturalidade,nacionalidade,sexo,dataNascimento,idade,estadoCivil,instrucao,profissao,cutis,naturezasEnvolvidas".split(",");
            writer.writeNext(titulos);

            for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
                List<ParteEnvolvida> partesEnvolvidas = boletimOcorrencia.getPartesEnvolvidas();
                for (ParteEnvolvida parteEnvolvida : partesEnvolvidas) {
                    String BOid = boletimOcorrencia.getNumero() + "#" + boletimOcorrencia.getIdDelegacia() + "#" + boletimOcorrencia.getDataOcorrencia()+"#";
                    String partes = BOid + parteEnvolvida.printCSV();
                    writer.writeNext(partes.split("#"));
                }
            }
            writer.close();
        }
    }
}
