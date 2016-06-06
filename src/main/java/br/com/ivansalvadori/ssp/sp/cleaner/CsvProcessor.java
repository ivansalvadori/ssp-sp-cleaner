package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvProcessor {

	public void gravarCSV(List<BoletimOcorrencia> boletinsProcessados, File folderPathOutput) throws IOException {

		gravarBoletim(boletinsProcessados, folderPathOutput);
		gravarPartes(boletinsProcessados, folderPathOutput);
		gravarNaturezas(boletinsProcessados, folderPathOutput);

	}

	private void gravarBoletim(List<BoletimOcorrencia> boletinsProcessados, File folderPathOutput) throws IOException {
		try (CSVWriter writer = new CSVWriter(new FileWriter(new File(folderPathOutput, "boletins.csv")), '\t')) {
			String[] titulos = "idBO,numeroBO,idDelegacia,tipoBO,dependencia,local,tipoLocal,circunscricao,dataOcorrencia,turnoOcorrencia,dataComunicacao,horaComunicacao,dataElaboracao,horaElaboracao,flagrante,examesRequisitados,solucao,numeroBoPrincipal,anoBoPrincipal,nomeDelegaciaBoPrincipal".split(",");
			writer.writeNext(titulos);

			for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
				writer.writeNext(boletimOcorrencia.printCSV().split("#"));
			}
			writer.close();
		}
	}

	private void gravarPartes(List<BoletimOcorrencia> boletinsProcessados, File folderPathOutput) throws IOException {

		try (CSVWriter writer = new CSVWriter(new FileWriter(new File(folderPathOutput, "partesEnvolvidas.csv")), '\t')) {
			String[] titulos = "IdBO,tipoBO,nome,tipoEnvolvimento,rg,naturalidade,nacionalidade,sexo,dataNascimento,idade,estadoCivil,instrucao,profissao,cutis,naturezasEnvolvidas".split(",");
			writer.writeNext(titulos);

			for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
				List<ParteEnvolvida> partesEnvolvidas = boletimOcorrencia.getPartesEnvolvidas();
				for (ParteEnvolvida parteEnvolvida : partesEnvolvidas) {
					String idBO = boletimOcorrencia.getIdBO() + "#";
					String tipoBo = boletimOcorrencia.getTipoBoletim() + "#";
					String partes = idBO + tipoBo + parteEnvolvida.printCSV();
					writer.writeNext(partes.split("#"));
				}
			}
			writer.close();
		}
	}

	private void gravarNaturezas(List<BoletimOcorrencia> boletinsProcessados, File folderPathOutput) throws IOException {

		try (CSVWriter writer = new CSVWriter(new FileWriter(new File(folderPathOutput, "naturezas.csv")), '\t')) {
			String[] titulos = "IdBO,tipoBO,especie,descricao,desdobramentos".split(",");
			writer.writeNext(titulos);

			for (BoletimOcorrencia boletimOcorrencia : boletinsProcessados) {
				List<NaturezaBoletim> naturezaBoletims = boletimOcorrencia.getNaturezas();
				for (NaturezaBoletim naturezaBoletim : naturezaBoletims) {
					String idBO = boletimOcorrencia.getIdBO() + "#";
					String tipoBo = boletimOcorrencia.getTipoBoletim() + "#";
					String naturezas = idBO + tipoBo + naturezaBoletim.printCSV();
					writer.writeNext(naturezas.split("#"));
				}
			}
			writer.close();
		}
	}
}
