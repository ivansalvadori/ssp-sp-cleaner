package br.com.ivansalvadori.ssp.sp.cleaner.main;

import java.io.File;
import java.io.IOException;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.ivansalvadori.ssp.sp.cleaner.BoletimOcorrenciasExtractor;
import br.com.ivansalvadori.ssp.sp.cleaner.ListaOcorrenciasPoliciaisExtractor;

@ComponentScan("br.com.ivansalvadori.ssp")
@SpringBootApplication
public class Main implements CommandLineRunner {
	@Option(name = "--baixar", aliases = {"-b"})
	private boolean baixar = false;

	@Option(name = "--processar", aliases = {"-p"})
	private boolean processar = false;

	@Option(name = "--processados")
	private File processados = null;
	private boolean givenProcessados = false;

	@Option(name = "--baixados")
	private File baixados = null;
	private boolean givenBaixados = false;

	@Option(name = "--listagem")
	private File listagem = null;
	private boolean givenListagem = false;

	@Option(name = "--session-id")
	private String sessionId;

	@Argument(usage = "Caminho opcional de um diretório contendo uma subpasta ListasBOs com os " +
			"htmls de listas de BOs. Os demais diretórios são inferidos como subdiretórios deste. " +
			"As --processados, --baixados e --listagem precedem os diretórios inferidos por " +
			"esse argumento")
	private File toplevelDir;

	@Autowired
	private Config config;

	@Autowired
	private ListaOcorrenciasPoliciaisExtractor listaOcorrenciasPoliciaisExtractor;

	@Autowired
	private BoletimOcorrenciasExtractor boletimOcorrenciasExtractor;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		sessionId = config.getSessionId();
		System.out.printf("sessionId=%1$s\n", sessionId);

		CmdLineParser parser = new CmdLineParser(this);
		parser.parseArgument(args);

		if (!(givenBaixados = baixados != null) && config.getPastaLeituraBOs() != null)
			baixados = new File(config.getPastaLeituraBOs());
		if (!(givenListagem = listagem != null) && config.getPastaPaginasListagem() != null)
			listagem = new File(config.getPastaPaginasListagem());
		if (!(givenProcessados = processados != null) && config.getPastaGravacaoBOs() != null)
			processados = new File(config.getPastaGravacaoBOsProcessados());

		if (toplevelDir != null) guessDirs();

		if (baixar) {
			createDir(baixados);
			listaOcorrenciasPoliciaisExtractor.parseListaEdownloadBOs(listagem, baixados, sessionId);
		}
		if (processar) {
			createDir(processados);
			boletimOcorrenciasExtractor.parseDocument(baixados, processados);
		}

		System.out.println("Fim...");
	}

	private void createDir(File dir) throws IOException {
		if (dir.exists() && !dir.isDirectory())
			throw new IOException(String.format("Non-directory %1$s already exists.", dir));
		if (!dir.exists() && !dir.mkdirs())
			throw new IOException(String.format("Couldn't mkdir %1$s.", dir));
	}

	private void guessDirs() {
		if (!givenBaixados) baixados = new File(toplevelDir, "BOs");
		if (!givenListagem) listagem = new File(toplevelDir, "ListasBOs");
		if (!givenProcessados) processados = new File(toplevelDir, "BOsProcessados");

		System.out.printf("Inferido de %1$s:\n" +
				"\t--listagem %2$s\n" +
				"\t--baixados %3$s\n" +
				"\t--processados %4$s\n", toplevelDir, listagem, baixados, processados);
	}
}
