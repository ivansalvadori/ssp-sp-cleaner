package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BoletimOcorrenciasExtractor {

    public void parseDocument(String folderPath) throws IOException {
        BoletimOcorrencia boletimOcorrencia = new BoletimOcorrencia();
        File[] files = new File(folderPath).listFiles();
        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }

            Document doc = Jsoup.parse(file, "UTF-8");

            String dependencia = doc.select("span:matchesOwn(Dependência:)").first().siblingElements().first().html();
            boletimOcorrencia.setDependencia(dependencia);

            String idDelegaciaENumeroBo = file.getName().replace(".html", "");
            String[] split = idDelegaciaENumeroBo.split("-");
            boletimOcorrencia.setIdDelegacia(split[0]);
            boletimOcorrencia.setNumero(split[1]);

            Elements divEspecie = doc.select("div:matchesOwn(Espécie:)");
            Iterator<Element> divEspecieIterator = divEspecie.iterator();
            while (divEspecieIterator.hasNext()) {
                NaturezaBoletim naturezaBoletim = new NaturezaBoletim();

                Element natureza = divEspecieIterator.next();
                String especie = natureza.parent().siblingElements().get(0).child(0).html().trim();
                naturezaBoletim.setEspecie(especie);

                Node nodoNatureza = natureza.parent().parent().nextSibling();
                String descricao = nodoNatureza.childNode(1).childNode(0).childNode(0).toString().trim();
                naturezaBoletim.setDescricao(descricao);

                Node nodoConsumado = nodoNatureza.nextSibling();
                Node nodoDesdobramentos = nodoConsumado.nextSibling();
                if (nodoDesdobramentos.childNode(0).childNode(0).childNodeSize() > 0) {
                    String desdobramentos = nodoDesdobramentos.childNode(1).childNode(0).childNode(0).toString().trim();
                    naturezaBoletim.setDesdobramentos(desdobramentos);
                }
                boletimOcorrencia.getNaturezas().add(naturezaBoletim);
            }

            Element divLocal = doc.select("div:matchesOwn(Local:)").get(0);
            Iterator<Element> iteratorDetalhes = divLocal.parent().parent().parent().getElementsByTag("div").iterator();
            while (iteratorDetalhes.hasNext()) {
                String textoDetalhe = iteratorDetalhes.next().html();
                if (textoDetalhe.equalsIgnoreCase("Local:")) {
                    StringBuilder local = new StringBuilder(iteratorDetalhes.next().html());
                    local.append(" - ");
                    local.append(iteratorDetalhes.next().html());
                    boletimOcorrencia.setLocal(local.toString());
                }
                if (textoDetalhe.equalsIgnoreCase("Tipo de Local:")) {
                    StringBuilder tipoLocal = new StringBuilder(iteratorDetalhes.next().html());
                    boletimOcorrencia.setTipoLocal(tipoLocal.toString());
                }
                if (textoDetalhe.equalsIgnoreCase("Circunscrição:")) {
                    StringBuilder circunscricao = new StringBuilder(iteratorDetalhes.next().html());
                    boletimOcorrencia.setCircunscricao(circunscricao.toString());
                }
                if (textoDetalhe.equalsIgnoreCase("Ocorrência:")) {
                    StringBuilder ocorrencia = new StringBuilder(iteratorDetalhes.next().html());
                    boletimOcorrencia.setDataOcorrencia(ocorrencia.toString());
                }
                if (textoDetalhe.equalsIgnoreCase("Comunicação:")) {
                    StringBuilder comunicacao = new StringBuilder(iteratorDetalhes.next().html());
                    Pattern pattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
                    Matcher matcher = pattern.matcher(comunicacao);
                    if (matcher.find()) {
                        String data = matcher.group();
                        boletimOcorrencia.setDataComunicacao(data);
                        String comunicacaoCompleta = comunicacao.toString();
                        String hora = comunicacaoCompleta.replace(data, "");
                        hora = hora.replace("às", "");
                        hora = hora.replace("horas", "");
                        boletimOcorrencia.setHoraComunicacao(hora.trim());
                    }
                }
                if (textoDetalhe.equalsIgnoreCase("Elaboração:")) {
                    StringBuilder elaboracao = new StringBuilder(iteratorDetalhes.next().html());
                    Pattern pattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
                    Matcher matcher = pattern.matcher(elaboracao);
                    if (matcher.find()) {
                        String data = matcher.group();
                        boletimOcorrencia.setDataElaboracao(data);
                        String elaboracaoCompleta = elaboracao.toString();
                        String hora = elaboracaoCompleta.replace(data, "");
                        hora = hora.replace("às", "");
                        hora = hora.replace("horas", "");
                        boletimOcorrencia.setHoraElaboracao(hora.trim());
                    }
                }
                if (textoDetalhe.equalsIgnoreCase("Flagrante:")) {
                    StringBuilder flagrante = new StringBuilder(iteratorDetalhes.next().html());
                    boletimOcorrencia.setFlagrante(flagrante.toString());
                }
            }

            List<ParteEnvolvida> partesEnvolvidas = parsePartesEnvolvidas(doc);
            boletimOcorrencia.setPartesEnvolvidas(partesEnvolvidas);

            Element elementExames = doc.getElementsContainingText("Exames requisitados:").last();
            if (elementExames != null) {
                boletimOcorrencia.setExamesRequisitados(elementExames.html().replace("Exames requisitados:", "").trim());
            }

            Element elementSolucao = doc.getElementsContainingText("Solução:").last();
            if (elementSolucao != null) {
                boletimOcorrencia.setSolucao(elementSolucao.html().replace("Solução:", "").trim());
            }

            try (Writer writer = new FileWriter(file.getCanonicalFile() + ".json")) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(boletimOcorrencia, writer);
            }
        }

    }

    private List<ParteEnvolvida> parsePartesEnvolvidas(Document doc) {
        List<ParteEnvolvida> partes = new ArrayList<>();
        Elements elementVitimas = doc.getElementsMatchingOwnText("(Vítima)");
        elementVitimas.remove(0);
        Object[] vitimasRaw = elementVitimas.toArray();
        for (Object vitimaRaw : vitimasRaw) {
            String html = ((Element) vitimaRaw).html();
            List<String> fragmentos = Arrays.asList(html.split(" - "));

            ParteEnvolvida parteEnvolvida = new ParteEnvolvida();

            String nomeAndTipoEnvolvimento = fragmentos.get(0);

            Matcher macherTipoEnvolvimento = Pattern.compile("\\(([^)]+)\\)").matcher(nomeAndTipoEnvolvimento);
            while (macherTipoEnvolvimento.find()) {
                String tipoEnvolvimento = macherTipoEnvolvimento.group(1);
                parteEnvolvida.setTipoEnvolvimento(tipoEnvolvimento);
                String nome = nomeAndTipoEnvolvimento.replace("(" + tipoEnvolvimento + ")", "").trim();
                parteEnvolvida.setNome(nome);
            }

            for (String fragmento : fragmentos) {
                if (fragmento.startsWith("RG:")) {
                    parteEnvolvida.setRg(fragmento.replace("RG:", "").trim());
                } else if (fragmento.startsWith("Natural de:")) {
                    parteEnvolvida.setNaturalidade(fragmento.replace("Natural de:", "").trim());
                } else if (fragmento.startsWith("Nacionalidade:")) {
                    parteEnvolvida.setNacionalidade(fragmento.replace("Nacionalidade:", "").trim());
                } else if (fragmento.startsWith("Sexo:")) {
                    parteEnvolvida.setSexo(fragmento.replace("Sexo:", "").trim());
                } else if (fragmento.startsWith("Nascimento:")) {
                    String dataEidade = fragmento.replace("Nascimento:", "").trim();
                    Pattern pattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
                    Matcher matcher = pattern.matcher(dataEidade);
                    if (matcher.find()) {
                        String data = matcher.group().trim();
                        parteEnvolvida.setDataNascimento(data);
                        String idade = dataEidade.replace(data, "").trim();
                        parteEnvolvida.setIdade(idade);
                    }
                } else if (fragmento.startsWith("Estado Civil:")) {
                    parteEnvolvida.setEstadoCivil(fragmento.replace("Estado Civil:", "").trim());
                } else if (fragmento.startsWith("Profissão:")) {
                    parteEnvolvida.setProfissao(fragmento.replace("Profissão:", "").trim());
                } else if (fragmento.startsWith("Instrução:")) {
                    parteEnvolvida.setInstrucao(fragmento.replace("Instrução:", "").trim());
                } else if (fragmento.startsWith("Cutis:")) {
                    parteEnvolvida.setCutis(fragmento.replace("Cutis:", "").trim());
                } else if (fragmento.startsWith("Naturezas Envolvidas:")) {
                    parteEnvolvida.setNaturezasEnvolvidas(fragmento.replace("Naturezas Envolvidas:", "").trim());
                }
            }
            partes.add(parteEnvolvida);
        }

        return partes;
    }

}
