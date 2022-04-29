package es.uji.ei1027.SkillSharing.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

public class Offer {

    private int idOffer;
    private int idStudent;
    private int idSkillType;
    private String description;
    private LocalTime start;
    private LocalTime finish;
    private int duration;

    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdSkillType() {
        return idSkillType;
    }

    public void setIdSkillType(int idSkillType) {
        this.idSkillType = idSkillType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getFinish() {
        return finish;
    }

    public void setFinish(LocalTime finish) {
        this.finish = finish;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNameSkillType(List<SkillType> skillTypeList){
        for(SkillType skillType: skillTypeList){
            if(idSkillType==skillType.getIdSkillType())
                return skillType.getName();
        }
        return "";
    }

    @Override
    public String toString() {
        return "offer{" +
                "idOffer=" + idOffer +
                ", idStudent=" + idStudent +
                ", idSkillType=" + idSkillType +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", duration=" + duration +
                '}';
    }
}
