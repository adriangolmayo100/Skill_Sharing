package es.uji.ei1027.SkillSharing.model;

import javax.xml.crypto.Data;

public class Collaboration{
    private int idRequest;
    private int id_offer;
    private Data start;
    private Data finish;
    private int rating;
    private String comments;


    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getId_offer() {
        return id_offer;
    }

    public void setId_offer(int id_offer) {
        this.id_offer = id_offer;
    }

    public Data getStart() {
        return start;
    }

    public void setStart(Data start) {
        this.start = start;
    }

    public Data getFinish() {
        return finish;
    }

    public void setFinish(Data finish) {
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
                "id_offer=" + id_offer +
                ", start=" + start +
                ", finish=" + finish +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", idRequest=" + idRequest +
                '}';
    }
}
