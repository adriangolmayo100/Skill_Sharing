package es.uji.ei1027.SkillSharing.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class Collaboration{
    private int idRequest;
    private int idOffer;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date start;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date finish;
    private int rating;
    private String comments;


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
                "id_offer=" + idOffer +
                ", start=" + start +
                ", finish=" + finish +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", idRequest=" + idRequest +
                '}';
    }
    public void createCollaboration(Offer offer,Request request){
        this.setIdOffer(offer.getIdOffer());
        this.setIdRequest(request.getIdRequest());
        this.setStart(offer.getStart());
        this.setFinish(offer.getFinish());
        this.setComments("");
    }
}
