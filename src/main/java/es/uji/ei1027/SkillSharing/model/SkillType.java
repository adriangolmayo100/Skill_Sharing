package es.uji.ei1027.SkillSharing.model;




public class SkillType {
     private int idSkillType;
     private String name;
     private String description;
     private int level;

     private int numberOfOffers;

    private int numberOfRequest;

    @Override
    public String toString() {
        return "SkillType{" +
                "idSkillType=" + idSkillType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", numberOfOffers=" + numberOfOffers +
                ", numberOfRequest=" + numberOfRequest +
                ", valid=" + valid +
                '}';
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private boolean valid;
    public int getNumberOfOffers() {
        return numberOfOffers;
    }

    public void setNumberOfOffers(int numberOfOffers) {
        this.numberOfOffers = numberOfOffers;
    }

    public int getNumberOfRequest() {
        return numberOfRequest;
    }

    public void setNumberOfRequest(int numberOfRequest) {
        this.numberOfRequest = numberOfRequest;
    }



    public int getIdSkillType() {
        return idSkillType;
    }

    public void setIdSkillType(int idSkillType) {
        this.idSkillType = idSkillType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void actualizar(SkillType skillType){
        this.setName(skillType.getName());
        this.setDescription(skillType.getDescription());
        this.setLevel(skillType.getLevel());
    }
}
