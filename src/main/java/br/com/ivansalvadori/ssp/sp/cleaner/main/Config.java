package br.com.ivansalvadori.ssp.sp.cleaner.main;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "configuracoes")
public class Config {

    private String pastaPaginasListagem;
    private String pastaGravacaoBOs;
    private String sessionId;
    private String pastaLeituraBOs;
    private String pastaGravacaoBOsProcessados;
    private String funcao;

    public String getPastaPaginasListagem() {
        return pastaPaginasListagem;
    }

    public void setPastaPaginasListagem(String pastaPaginasListagem) {
        this.pastaPaginasListagem = pastaPaginasListagem;
    }

    public String getPastaGravacaoBOs() {
        return pastaGravacaoBOs;
    }

    public void setPastaGravacaoBOs(String pastaGravacaoBOs) {
        this.pastaGravacaoBOs = pastaGravacaoBOs;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPastaLeituraBOs() {
        return pastaLeituraBOs;
    }

    public void setPastaLeituraBOs(String pastaLeituraBOs) {
        this.pastaLeituraBOs = pastaLeituraBOs;
    }

    public String getPastaGravacaoBOsProcessados() {
        return pastaGravacaoBOsProcessados;
    }

    public void setPastaGravacaoBOsProcessados(String pastaGravacaoBOsProcessados) {
        this.pastaGravacaoBOsProcessados = pastaGravacaoBOsProcessados;
    }

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

}
