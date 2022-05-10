package es.uji.ei1027.SkillSharing.model;

public class Student {
    private int idStudent;
    private String name;
    private String email;
    private String username;
    private String password;
    private int codePostal;
    private int hoursGiven;
    private int hoursReceived;
    private String degree;
    private int course;
    private boolean skp;
    private int numberPhone;
    private String gender;

    private boolean unavailable;

    public boolean isUnavailable() {
        return unavailable;
    }

    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public int getHoursGiven() {
        return hoursGiven;
    }

    public void setHoursGiven(int hoursGiven) {
        this.hoursGiven = hoursGiven;
    }

    public int getHoursReceived() {
        return hoursReceived;
    }

    public void setHoursReceived(int hoursReceived) {
        this.hoursReceived = hoursReceived;
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

    public boolean isSkp() {
        return skp;
    }

    public void setSkp(boolean skp) {
        this.skp = skp;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
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
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", codePostal=" + codePostal +
                ", hoursGiven=" + hoursGiven +
                ", hoursReceived=" + hoursReceived +
                ", degree='" + degree + '\'' +
                ", course=" + course +
                ", skp=" + skp +
                ", numberPhone=" + numberPhone +
                ", gender='" + gender + '\'' +
                ", unavailable=" + unavailable +
                '}';
    }
}
