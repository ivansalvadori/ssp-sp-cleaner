package br.com.ivansalvadori.ssp.sp.cleaner;

import java.util.ArrayList;
import java.util.List;

public class BoletimOcorrencia {
    protected String idBO;
    protected String numero;
    protected String idDelegacia;
    protected String tipoBoletim; 
    protected String dependencia;
    protected String local;
    protected String tipoLocal;
    protected String circunscricao;
    protected String dataOcorrencia;
    protected String turnoOcorrencia;
    protected String dataComunicacao;
    protected String horaComunicacao;
    protected String dataElaboracao;
    protected String horaElaboracao;
    protected String flagrante;
    protected String examesRequisitados;
    protected String solucao;
    protected List<ParteEnvolvida> partesEnvolvidas = new ArrayList<>();
    protected List<NaturezaBoletim> naturezas = new ArrayList<>();

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
    
    public void setTipoBoletim(String tipoBoletim) {
		this.tipoBoletim = tipoBoletim;
	}
    
    public String getTipoBoletim() {
		return tipoBoletim;
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

    public String getIdDelegacia() {
        return idDelegacia;
    }

    public void setIdDelegacia(String idDelegacia) {
        this.idDelegacia = idDelegacia;
    }

    public List<ParteEnvolvida> getPartesEnvolvidas() {
        return partesEnvolvidas;
    }

    public void setPartesEnvolvidas(List<ParteEnvolvida> partesEnvolvidas) {
        this.partesEnvolvidas = partesEnvolvidas;
    }

    public List<NaturezaBoletim> getNaturezas() {
        return naturezas;
    }

    public void setNaturezas(List<NaturezaBoletim> naturezas) {
        this.naturezas = naturezas;
    }

    public String getTurnoOcorrencia() {
        return turnoOcorrencia;
    }

    public void setTurnoOcorrencia(String turnoOcorrencia) {
        this.turnoOcorrencia = turnoOcorrencia;
    }

    public void setIdBO(String idBO) {
        this.idBO = idBO;
    }

    public String getIdBO() {
        return idBO;
    }

    public String printCSV() {
        return idBO + "#" + numero + "#" + idDelegacia + "#" + tipoBoletim + "#" + dependencia + "#" + local + "#" + tipoLocal + "#" + circunscricao + "#" + dataOcorrencia + "#" + turnoOcorrencia + "#" + dataComunicacao + "#" + horaComunicacao + "#" + dataElaboracao + "#" + horaElaboracao + "#" + flagrante + "#"
                + examesRequisitados + "#" + solucao;
    }

}
