package es.uji.ei1027.SkillSharing.model;

public class StatisticStudent {
    private int idStudent;

    public int getIdStudent() {
        return idStudent;
    }
    public StatisticStudent(){
        super();
    }
    public StatisticStudent(int idStudent){
        super();
        this.idStudent=idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    private Float valoracionMedia;
    private int ofertasPublicadas;
    private int demandasPublicadas;
    private int colaboracionesCumplidasConExito;

    public float getValoracionMedia() {
        return valoracionMedia;
    }

    @Override
    public String toString() {
        return "StatisticStudent{" +
                "idStudent=" + idStudent +
                ", valoracionMedia=" + valoracionMedia +
                ", ofertasPublicadas=" + ofertasPublicadas +
                ", demandasPublicadas=" + demandasPublicadas +
                ", colaboracionesCumplidasConExito=" + colaboracionesCumplidasConExito +
                '}';
    }

    public void setValoracionMedia(Float valoracionMedia) {
        if (valoracionMedia == null)
            this.valoracionMedia=0.0f;
        else
            this.valoracionMedia = (float) (Math.round(valoracionMedia*100.0)/100.0);
    }

    public int getOfertasPublicadas() {
        return ofertasPublicadas;
    }

    public void setOfertasPublicadas(int ofertasPublicadas) {
        this.ofertasPublicadas = ofertasPublicadas;
    }

    public int getDemandasPublicadas() {
        return demandasPublicadas;
    }

    public void setDemandasPublicadas(int demandasPublicadas) {
        this.demandasPublicadas = demandasPublicadas;
    }

    public int getColaboracionesCumplidasConExito() {
        return colaboracionesCumplidasConExito;
    }

    public void setColaboracionesCumplidasConExito(int colaboracionesCumplidasConExito) {
        this.colaboracionesCumplidasConExito = colaboracionesCumplidasConExito;
    }
}
