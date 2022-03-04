package es.uji.ei1027.SkillSharing.model;

public class Student {
    private int id_student;
    private String name;
    private String email;
    private int postalCode;
    private int balance;
    private String degree;
    private int course;
    private int phoneNumber;
    private boolean skp;
    private String gender;

    public int getIdStudent() {
        return id_student;
    }

    public void setIdStudent(int di_student) {
        this.id_student = di_student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSkp() {
        return skp;
    }

    public void setSkp(boolean skp) {
        this.skp = skp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
