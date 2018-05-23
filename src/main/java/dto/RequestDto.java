package dto;

import entity.Type;

import java.util.*;

public class RequestDto {

    private int id;
    private String locationName;
    private List<TypeDto> types;
    private Map<String,Integer> totalTypes;
    private String totalTypesString;
    public RequestDto(){
        types = new ArrayList<TypeDto>();
        totalTypes = new HashMap<String,Integer>();
    }

    public Map<String, Integer> getTotalTypes() {
        return totalTypes;
    }

    public void setTotalTypes(Map<String, Integer> totalTypes) {
        this.totalTypes = totalTypes;
    }

    public String getTotalTypesString() {
        return totalTypesString;
    }

    public void setTotalTypesString(String totalTypesString) {
        this.totalTypesString = totalTypesString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<TypeDto> getTypes() {
        return types;
    }

    public void setTypes(List<TypeDto> types) {
        this.types = types;
    }

    public void totalTypesToString(){
        String string = "";
        Iterator it = totalTypes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            string = string + pair.getKey()+" "+ pair.getValue();
        }
        totalTypesString = string;
    }
}
