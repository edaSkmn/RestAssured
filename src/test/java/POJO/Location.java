package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Location {
    String postCode;  // bu aslinda post code
    String country;

    String countryAbbreviation;
    ArrayList<Place> places;

    public String getPostCode() {
        return postCode;
    }

    @JsonProperty("post code")
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    @JsonProperty("country abbreviation")
    public void setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}




// Location ve PLace class lari nereden ve nasil geldi?
//class Location{
//    String postCode;
//    String country;
//    String abbreviation;
//    ArrayList<Place> places
//}
//
//
//class Place {
//            String placeName;
//            String longitude;
//            String state;
//            String stateAbbreviation;
//            String latitude;
//        }
//
//{
//    "post code": "90210",
//    "country": "United States",
//    "country abbreviation": "US",
//    "places": [
//        {
//            "place name": "Beverly Hills",
//            "longitude": "-118.4065",
//            "state": "California",
//            "state abbreviation": "CA",
//            "latitude": "34.0901"
//        }
//    ]
//}