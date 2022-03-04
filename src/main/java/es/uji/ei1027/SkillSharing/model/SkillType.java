package es.uji.ei1027.SkillSharing.model;




public class SkillType {
     private int idskillType;
     private  String name;
     private String description;
     private int level;

     public SkillType(){}

    public int getIdskillType() {
        return idskillType;
    }

    public void setIdskillType(int idskillType) {
        this.idskillType = idskillType;
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

    @Override
    public String toString() {
        return "SkillType{" +
                "skillType=" + idskillType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                '}';
    }
}
