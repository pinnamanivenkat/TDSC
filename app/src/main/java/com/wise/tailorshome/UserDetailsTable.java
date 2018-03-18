package com.wise.tailorshome;

public class UserDetailsTable {
    String NAME, PHONE_NO, EMAIL, ADDRESS, PINCODE, PICKUPDATE, PICKUPTIME;

    public UserDetailsTable(String NAME, String PHONE_NO,
                            String EMAIL, String ADDRESS, String PINCODE, String PICKUPDATE, String PICKUPTIME) {
        super();
        this.NAME = NAME;
        this.PHONE_NO = PHONE_NO;
        this.EMAIL = EMAIL;
        this.ADDRESS = ADDRESS;
        this.PINCODE = PINCODE;
        this.PICKUPDATE = PICKUPDATE;
        this.PICKUPTIME = PICKUPTIME;
    }

    public UserDetailsTable() {
        super();
        this.NAME = null;
        this.PHONE_NO = null;
        this.EMAIL = null;
        this.ADDRESS = null;
        this.PINCODE = null;
        this.PICKUPDATE = null;
        this.PICKUPTIME = null;


    }


    public String getNAME() {
        return NAME;
    }

    public void setName(String NAME) {
        this.NAME = NAME;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getPINCODE() {
        return PINCODE;
    }

    public void setPINCODE(String PINCODE) {
        this.PINCODE = PINCODE;
    }


    public String getPICKUPDATE() {
        return PICKUPDATE;
    }

    public void setPICKUPDATE(String PICKUPDATE) {
        this.PICKUPDATE = PICKUPDATE;
    }

    public String getPICKUPTIME() {
        return PICKUPTIME;
    }

    public void setPICKUPTIME(String PICKUPTIME) {
        this.PICKUPTIME = PICKUPTIME;
    }

}  