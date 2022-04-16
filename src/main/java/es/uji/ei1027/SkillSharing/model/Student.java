package es.uji.ei1027.SkillSharing.model;

public class Student {
    private int idStudent;
    private String name;
    private String email;
    private int postalCode;
    private int balance;
    private String degree;
    private int course;
    private boolean skp;
    private int phoneNumber;
    private String gender;

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
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

    public boolean getSkp() {
        return skp;
    }

    public void setSkp(boolean skp) {
        this.skp = skp;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", postalCode=" + postalCode +
                ", balance=" + balance +
                ", degree='" + degree + '\'' +
                ", course=" + course +
                ", skp=" + skp +
                ", phoneNumber=" + phoneNumber +
                ", gender='" + gender + '\'' +
                '}';
    }
}
