package br.com.ivansalvadori.ssp.sp.cleaner;

public class NaturezaBoletim {

    private String especie;
    private String descricao;
    private String desdobramentos;

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDesdobramentos() {
        return desdobramentos;
    }

    public void setDesdobramentos(String desdobramentos) {
        this.desdobramentos = desdobramentos;
    }

    @Override
    public String toString() {
        return "NaturezaBoletim [especie=" + especie + ", descricao=" + descricao + ", desdobramentos=" + desdobramentos + "]";
    }

}
