package es.uji.ei1027.SkillSharing.model;

import java.sql.Date;
import java.util.List;

public class Request {

    private int idRequest;
    private String description;
    private Date start;
    private Date finish;
    private int duration;
    private int idStudent;
    private int idSkillType;

    public Request(){}

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
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

    public String getNameSkillType(List<SkillType> skillTypeList){
        for(SkillType skillType: skillTypeList){
            if(idSkillType==skillType.getIdSkillType())
                return skillType.getName();
        }
        return "";
    }
    public void createRequestForOffer(Offer offer){
        this.description=offer.getDescription();
        this.duration=offer.getDuration();
        this.start=offer.getStart();
        this.finish=offer.getFinish();
        this.idSkillType=offer.getIdSkillType();
    }

    @Override
    public String toString() {
        return "Request{" +
                "idRequest=" + idRequest +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", duration=" + duration +
                ", idStudent=" + idStudent +
                ", idSkillType=" + idSkillType +
                '}';
    }
}
