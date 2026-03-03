package com.example.demo.process;

import java.util.List;

public class CreateProcessoDTO {
    
    private String numberCnj;
    private String title;
    private String description;
    private String clientName;
    private String court;
    private String district;
    private List<String> lawyersIds;

    public CreateProcessoDTO() {
    }

    public CreateProcessoDTO(String numberCnj, String title, String description, String clientName,
            String court, String district, List<String> lawyersIds) {
        this.numberCnj = numberCnj;
        this.title = title;
        this.description = description;
        this.clientName = clientName;
        this.court = court;
        this.district = district;
        this.lawyersIds = lawyersIds;
    }

    public String getNumberCnj() {
        return numberCnj;
    }

    public void setNumberCnj(String numberCnj) {
        this.numberCnj = numberCnj;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<String> getLawyersIds() {
        return lawyersIds;
    }

    public void setLawyersIds(List<String> lawyersIds) {
        this.lawyersIds = lawyersIds;
    }
}
