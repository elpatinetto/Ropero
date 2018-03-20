package app.ropero.com.ropero.booking;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by noellodou on 19/07/2017.
 */

public class AirlineReservation {

    /**
     * @param args
     */

    private int passagerCount;
    private String originVol = "CDG";
    private String destVol = "NYC";
    private String dateVol;
    private static final String APPLICATION_NAME = "Triipee";

    private static final String API_KEY = "AIzaSyCSLskkXCb4Qa3bAviK121OCjXOFH4Ag9k";

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    public AirlineReservation (){

    }
    public AirlineReservation(String origin, String dest, String date){
        this.originVol = origin;
        this.dateVol = date;
        this.destVol = dest;
    }
    public static void main(String[] args) throws Exception {
       /* GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                "Paris,France").await();
        System.out.println(results[0].geometry.location.lat + " long:"+results[0].geometry.location.lng);
        System.out.println("test");*/
       getVol("CDG","NYC","2018-03-29");
    }
    public static void getVol(String orgVolUser, String destVolUser, String dateVolUser) {
        // TODO Auto-generated method stub

        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();


            PassengerCounts passengers= new PassengerCounts();
            passengers.setAdultCount(1);

            List<SliceInput> slices = new ArrayList<SliceInput>();

            SliceInput slice = new SliceInput();
            slice.setOrigin(orgVolUser);
            slice.setDestination(destVolUser);
            slice.setDate(dateVolUser);
            slices.add(slice);

            TripOptionsRequest request= new TripOptionsRequest();
            request.setSolutions(10);
            request.setPassengers(passengers);
            request.setSlice(slices);

            TripsSearchRequest parameters = new TripsSearchRequest();
            parameters.setRequest(request);
            QPXExpress qpXExpress= new QPXExpress.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();

            TripsSearchResponse list= qpXExpress.trips().search(parameters).execute();
            List<TripOption> tripResults=list.getTrips().getTripOption();

            String id;

            for(int i=0; i<tripResults.size(); i++){
                //Trip Option ID
                id= tripResults.get(i).getId();
                System.out.println("id "+id);

                //Slice
                List<SliceInfo> sliceInfo= tripResults.get(i).getSlice();
                for(int j=0; j<sliceInfo.size(); j++){
                    int duration= sliceInfo.get(j).getDuration();
                    System.out.println("duration "+duration);
                    List<SegmentInfo> segInfo= sliceInfo.get(j).getSegment();
                    for(int k=0; k<segInfo.size(); k++){
                        String bookingCode= segInfo.get(k).getBookingCode();
                        System.out.println("bookingCode "+bookingCode);
                        FlightInfo flightInfo=segInfo.get(k).getFlight();
                        String flightNum= flightInfo.getNumber();
                        System.out.println("flightNum "+flightNum);
                        String flightCarrier= flightInfo.getCarrier();
                        System.out.println("flightCarrier "+flightCarrier);
                        List<LegInfo> leg=segInfo.get(k).getLeg();
                        for(int l=0; l<leg.size(); l++){
                            String aircraft= leg.get(l).getAircraft();
                            System.out.println("aircraft "+aircraft);
                            String arrivalTime= leg.get(l).getArrivalTime();
                            System.out.println("arrivalTime "+arrivalTime);
                            String departTime=leg.get(l).getDepartureTime();
                            System.out.println("departTime "+departTime);
                            String dest=leg.get(l).getDestination();
                            System.out.println("Destination "+dest);
                            String destTer= leg.get(l).getDestinationTerminal();
                            System.out.println("DestTer "+destTer);
                            String origin=leg.get(l).getOrigin();
                            System.out.println("origun "+origin);
                            String originTer=leg.get(l).getOriginTerminal();
                            System.out.println("OriginTer "+originTer);
                            int durationLeg= leg.get(l).getDuration();
                            System.out.println("durationleg "+durationLeg);
                            int mil= leg.get(l).getMileage();
                            System.out.println("Milleage "+mil);

                        }

                    }
                }

                //Pricing
                List<PricingInfo> priceInfo= tripResults.get(i).getPricing();
                for(int p=0; p<priceInfo.size(); p++){
                    String price= priceInfo.get(p).getSaleTotal();
                    System.out.println("Price "+price);
                }

            }
            return;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);

    }
}