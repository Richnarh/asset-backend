package com.khoders.asset.dto;

public class CompanyDto
{
    private String id;
    private String companyName;
    private String telephone;
    private String emailAddress;
    private String website;
    private String companyAddress;
    private String userAccountName;
    private String userAccountId;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getCompanyAddress()
    {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
    }

    public String getUserAccountName()
    {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName)
    {
        this.userAccountName = userAccountName;
    }

    public String getUserAccountId()
    {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId)
    {
        this.userAccountId = userAccountId;
    }
}
