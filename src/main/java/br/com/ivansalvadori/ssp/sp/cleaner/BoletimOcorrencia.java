package br.com.ivansalvadori.ssp.sp.cleaner;

import java.util.List;

public class BoletimOcorrencia {
    private String numero;
    private String idDelegacia;
    private String dependencia;
    private String local;
    private String tipoLocal;
    private String circunscricao;
    private String dataOcorrencia;
    private String dataComunicacao;
    private String horaComunicacao;
    private String dataElaboracao;
    private String horaElaboracao;
    private String flagrante;
    private List<NaturezaBoletim> naturezas;
    private String examesRequisitados;
    private String solucao;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getCircunscricao() {
        return circunscricao;
    }

    public void setCircunscricao(String circunscricao) {
        this.circunscricao = circunscricao;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDataComunicacao() {
        return dataComunicacao;
    }

    public void setDataComunicacao(String dataComunicacao) {
        this.dataComunicacao = dataComunicacao;
    }

    public String getHoraComunicacao() {
        return horaComunicacao;
    }

    public void setHoraComunicacao(String horaComunicacao) {
        this.horaComunicacao = horaComunicacao;
    }

    public String getDataElaboracao() {
        return dataElaboracao;
    }

    public void setDataElaboracao(String dataElaboracao) {
        this.dataElaboracao = dataElaboracao;
    }

    public String getHoraElaboracao() {
        return horaElaboracao;
    }

    public void setHoraElaboracao(String horaElaboracao) {
        this.horaElaboracao = horaElaboracao;
    }

    public String getFlagrante() {
        return flagrante;
    }

    public void setFlagrante(String flagrante) {
        this.flagrante = flagrante;
    }

    public List<NaturezaBoletim> getNaturezas() {
        return naturezas;
    }

    public void setNaturezas(List<NaturezaBoletim> naturezas) {
        this.naturezas = naturezas;
    }

    public String getExamesRequisitados() {
        return examesRequisitados;
    }

    public void setExamesRequisitados(String examesRequisitados) {
        this.examesRequisitados = examesRequisitados;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    @Override
    public String toString() {
        return "BoletimOcorrencia [numero=" + numero + ", dependencia=" + dependencia + ", local=" + local + ", tipoLocal=" + tipoLocal + ", circunscricao=" + circunscricao + ", dataOcorrencia=" + dataOcorrencia + ", dataComunicacao=" + dataComunicacao + ", horaComunicacao=" + horaComunicacao
                + ", dataElaboracao=" + dataElaboracao + ", horaElaboracao=" + horaElaboracao + ", flagrante=" + flagrante + ", naturezas=" + naturezas + ", examesRequisitados=" + examesRequisitados + ", solucao=" + solucao + "]";
    }

    public String getIdDelegacia() {
        return idDelegacia;
    }

    public void setIdDelegacia(String idDelegacia) {
        this.idDelegacia = idDelegacia;
    }

}
