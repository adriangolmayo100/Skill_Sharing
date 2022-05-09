package es.uji.ei1027.SkillSharing.model;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Offer {

    private int idOffer;
    private int idStudent;
    private int idSkillType;
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date start;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date finish;
    private int duration;
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "idOffer=" + idOffer +
                ", idStudent=" + idStudent +
                ", idSkillType=" + idSkillType +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", duration=" + duration +
                ", valid=" + valid +
                '}';
    }

    private boolean valide;

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
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
    public void setSkillType(String name){
        SkillTypeDao s = new SkillTypeDao();
        List<SkillType> l = s.getSkillTypes();
        for  (SkillType sk : l){
            if (sk.getName().equals(name)){
                setIdSkillType(sk.getIdSkillType());
            }
        }
    }
    public void createOfferForRequest(Request request){
        this.description=request.getDescription();
        this.duration=request.getDuration();
        this.start=request.getStart();
        this.finish=request.getFinish();
        this.idSkillType=request.getIdSkillType();
        this.valid=request.isValid();
    }
    public void updateOffer(Offer offer){
        this.setDescription(offer.getDescription());
        this.setFinish(offer.getFinish());
        this.setStart(offer.getStart());
        this.setDuration(offer.getDuration());
        this.setIdSkillType(offer.getIdSkillType());
    }

}
