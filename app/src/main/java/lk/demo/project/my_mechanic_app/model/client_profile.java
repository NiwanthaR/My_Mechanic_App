package lk.demo.project.my_mechanic_app.model;

public class client_profile {

    public String fname,lname,nic,dob,gender,address,city,contact;

    public client_profile(String fname, String lname, String nic, String dob, String gender, String address, String city, String contact) {
        this.fname = fname;
        this.lname = lname;
        this.nic = nic;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.contact = contact;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "client_profile{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", nic='" + nic + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
