package lk.demo.project.my_mechanic_app.model;

public class mechanic_profile {
    String shop_name,shop_regno,shop_start_date,shop_address,shop_city,shop_postal_code,shop_contact,shop_email,shop_web,shop_open,shop_close,shop_poya_state,shop_special_holiday,shop_visite_service,shop_sevice;
    String owner_fname,owner_sname,owner_nic,owner_dob,owner_gender,owner_address,owner_city,owner_contact;

    public mechanic_profile(String shop_name, String shop_regno, String shop_start_date, String shop_address, String shop_city, String shop_postal_code, String shop_contact, String shop_email, String shop_web, String shop_open, String shop_close, String shop_poya_state, String shop_special_holiday, String shop_visite_service, String shop_sevice, String owner_fname, String owner_sname, String owner_nic, String owner_dob, String owner_gender, String owner_address, String owner_city, String owner_contact) {
        this.shop_name = shop_name;
        this.shop_regno = shop_regno;
        this.shop_start_date = shop_start_date;
        this.shop_address = shop_address;
        this.shop_city = shop_city;
        this.shop_postal_code = shop_postal_code;
        this.shop_contact = shop_contact;
        this.shop_email = shop_email;
        this.shop_web = shop_web;
        this.shop_open = shop_open;
        this.shop_close = shop_close;
        this.shop_poya_state = shop_poya_state;
        this.shop_special_holiday = shop_special_holiday;
        this.shop_visite_service = shop_visite_service;
        this.shop_sevice = shop_sevice;
        this.owner_fname = owner_fname;
        this.owner_sname = owner_sname;
        this.owner_nic = owner_nic;
        this.owner_dob = owner_dob;
        this.owner_gender = owner_gender;
        this.owner_address = owner_address;
        this.owner_city = owner_city;
        this.owner_contact = owner_contact;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_regno() {
        return shop_regno;
    }

    public void setShop_regno(String shop_regno) {
        this.shop_regno = shop_regno;
    }

    public String getShop_start_date() {
        return shop_start_date;
    }

    public void setShop_start_date(String shop_start_date) {
        this.shop_start_date = shop_start_date;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_city() {
        return shop_city;
    }

    public void setShop_city(String shop_city) {
        this.shop_city = shop_city;
    }

    public String getShop_postal_code() {
        return shop_postal_code;
    }

    public void setShop_postal_code(String shop_postal_code) {
        this.shop_postal_code = shop_postal_code;
    }

    public String getShop_contact() {
        return shop_contact;
    }

    public void setShop_contact(String shop_contact) {
        this.shop_contact = shop_contact;
    }

    public String getShop_email() {
        return shop_email;
    }

    public void setShop_email(String shop_email) {
        this.shop_email = shop_email;
    }

    public String getShop_web() {
        return shop_web;
    }

    public void setShop_web(String shop_web) {
        this.shop_web = shop_web;
    }

    public String getShop_open() {
        return shop_open;
    }

    public void setShop_open(String shop_open) {
        this.shop_open = shop_open;
    }

    public String getShop_close() {
        return shop_close;
    }

    public void setShop_close(String shop_close) {
        this.shop_close = shop_close;
    }

    public String getShop_poya_state() {
        return shop_poya_state;
    }

    public void setShop_poya_state(String shop_poya_state) {
        this.shop_poya_state = shop_poya_state;
    }

    public String getShop_special_holiday() {
        return shop_special_holiday;
    }

    public void setShop_special_holiday(String shop_special_holiday) {
        this.shop_special_holiday = shop_special_holiday;
    }

    public String getShop_visite_service() {
        return shop_visite_service;
    }

    public void setShop_visite_service(String shop_visite_service) {
        this.shop_visite_service = shop_visite_service;
    }

    public String getShop_sevice() {
        return shop_sevice;
    }

    public void setShop_sevice(String shop_sevice) {
        this.shop_sevice = shop_sevice;
    }

    public String getOwner_fname() {
        return owner_fname;
    }

    public void setOwner_fname(String owner_fname) {
        this.owner_fname = owner_fname;
    }

    public String getOwner_sname() {
        return owner_sname;
    }

    public void setOwner_sname(String owner_sname) {
        this.owner_sname = owner_sname;
    }

    public String getOwner_nic() {
        return owner_nic;
    }

    public void setOwner_nic(String owner_nic) {
        this.owner_nic = owner_nic;
    }

    public String getOwner_dob() {
        return owner_dob;
    }

    public void setOwner_dob(String owner_dob) {
        this.owner_dob = owner_dob;
    }

    public String getOwner_gender() {
        return owner_gender;
    }

    public void setOwner_gender(String owner_gender) {
        this.owner_gender = owner_gender;
    }

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public String getOwner_city() {
        return owner_city;
    }

    public void setOwner_city(String owner_city) {
        this.owner_city = owner_city;
    }

    public String getOwner_contact() {
        return owner_contact;
    }

    public void setOwner_contact(String owner_contact) {
        this.owner_contact = owner_contact;
    }

    @Override
    public String toString() {
        return "mechanic_profile{" +
                "shop_name='" + shop_name + '\'' +
                ", shop_regno='" + shop_regno + '\'' +
                ", shop_start_date='" + shop_start_date + '\'' +
                ", shop_address='" + shop_address + '\'' +
                ", shop_city='" + shop_city + '\'' +
                ", shop_postal_code='" + shop_postal_code + '\'' +
                ", shop_contact='" + shop_contact + '\'' +
                ", shop_email='" + shop_email + '\'' +
                ", shop_web='" + shop_web + '\'' +
                ", shop_open='" + shop_open + '\'' +
                ", shop_close='" + shop_close + '\'' +
                ", shop_poya_state='" + shop_poya_state + '\'' +
                ", shop_special_holiday='" + shop_special_holiday + '\'' +
                ", shop_visite_service='" + shop_visite_service + '\'' +
                ", shop_sevice='" + shop_sevice + '\'' +
                ", owner_fname='" + owner_fname + '\'' +
                ", owner_sname='" + owner_sname + '\'' +
                ", owner_nic='" + owner_nic + '\'' +
                ", owner_dob='" + owner_dob + '\'' +
                ", owner_gender='" + owner_gender + '\'' +
                ", owner_address='" + owner_address + '\'' +
                ", owner_city='" + owner_city + '\'' +
                ", owner_contact='" + owner_contact + '\'' +
                '}';
    }
}
