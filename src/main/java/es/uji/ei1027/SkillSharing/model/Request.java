package es.uji.ei1027.SkillSharing.model;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Request {

    private int idRequest;
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate  start;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate  finish;
    private int duration;
    private int idStudent;
    private int idSkillType;
    private boolean valid;

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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate  start) {
        this.start = start;
    }

    public LocalDate  getFinish() {
        return finish;
    }

    public void setFinish(LocalDate  finish) {
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
    public void setSkillType(String name){
        SkillTypeDao s = new SkillTypeDao();
        List<SkillType> l = s.getSkillTypes();
        for  (SkillType sk : l){
            if (sk.getName().equals(name)){
                setIdSkillType(sk.getIdSkillType());
            }
        }
    }
    public List<String> getNameSkillTypes(){
        SkillTypeDao s = new SkillTypeDao();
        List<SkillType> l = s.getSkillTypes();
        List<String> names = new ArrayList<>();
        for (SkillType sk : l){
            names.add(sk.getName());
        }
        return names;
    }
    public void createRequestForOffer(Offer offer){
        this.description=offer.getDescription();
        this.duration=offer.getDuration();
        this.start=offer.getStart();
        this.finish=offer.getFinish();
        this.idSkillType=offer.getIdSkillType();
        this.valid=offer.isValid();
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
                ", valid=" + valid +
                '}';
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public void updateRequest(Request request){
        this.setDescription(request.getDescription());
        this.setFinish(request.getFinish());
        this.setStart(request.getStart());
        this.setDuration(request.getDuration());
        this.setIdSkillType(request.getIdSkillType());
    }
}
