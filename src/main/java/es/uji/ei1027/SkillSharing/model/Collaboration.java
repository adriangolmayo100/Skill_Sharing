package es.uji.ei1027.SkillSharing.model;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Collaboration{
    private int idRequest;
    private int idOffer;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate start;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate  finish;
    private int rating;
    private String comments;
    private int idSkillType;
    private String description;
    private int duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "Collaboration{" +
                "idRequest=" + idRequest +
                ", idOffer=" + idOffer +
                ", start=" + start +
                ", finish=" + finish +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", idSkillType=" + idSkillType +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }

    public void createCollaboration(Offer offer,Request request){
        this.setIdOffer(offer.getIdOffer());
        this.setIdRequest(request.getIdRequest());
        this.setComments("");
    }
    public String getNameSkillType(List<SkillType> skillTypeList){
        for(SkillType skillType: skillTypeList){
            if(idSkillType==skillType.getIdSkillType())
                return skillType.getName();
        }
        return "";
    }
}
