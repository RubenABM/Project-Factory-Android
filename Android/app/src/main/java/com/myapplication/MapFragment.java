package com.myapplication;

import static android.content.ContentValues.TAG;

import static com.myapplication.TinyWebServer.endTripFlag;
import static com.myapplication.ActivityActivity.linestr;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.*;


public class MapFragment extends Fragment {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable cameraUpdateRunnable;
    private boolean coordflag = false;
    private final float zoomLevel = 25.0f;

    public static List<LatLng> latLngList = new ArrayList<>();
    private Polyline polyline;

    private boolean firstPoint;

    public static int startDateNtime;

    public static int endDateNtime;
    public static boolean firstMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MAP_FRAG);


        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                if (firstMap){
                    String previouslatstr = DataHolder.getInstance().getDataMap().get("gpslat");
                    String previouslongstr = DataHolder.getInstance().getDataMap().get("gpslong");
                    if(!DataHolder.getInstance().getDataMap().isEmpty()){
                        Log.d(TAG,"Dataholder was not empty!");
                        double previouslat = Double.parseDouble(previouslatstr);
                        double previouslong = Double.parseDouble(previouslongstr);
                        LatLng latLng = new LatLng(previouslat, previouslong);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude +"KG"+ latLng.longitude);
                        googleMap.addMarker(markerOptions);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
                        latLngList.add(new LatLng(previouslat, previouslong));
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(latLngList)
                                .width(5) // Set the line width
                                .color(Color.RED); // Set the line color
                        polyline = googleMap.addPolyline(polylineOptions);

                    }else {
                        Log.d(TAG, "FALLBACK POSITION");
                        double latitude = 38.7073618;
                        previouslatstr = "38.7073618";
                        double longitude = -9.152648;
                        previouslongstr = "-9.152648";
                        LatLng latLng = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("fallback position");
                        googleMap.addMarker(markerOptions);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
                    }
                    String finalPreviouslatstr = previouslatstr;
                    String finalPreviouslongstr = previouslongstr;

                    cameraUpdateRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG,"position was updated!");
                            String currentlatitudestr = DataHolder.getInstance().getDataMap().get("gpslat");
                            String currentlongitudestr = DataHolder.getInstance().getDataMap().get("gpslong");
                            if(currentlatitudestr == null && currentlongitudestr == null){
                                //Log.d(TAG, "FALLBACK POSITION");
                                double latfallback = 38.7073618;
                                double longfallback = -9.152648;
                                LatLng latLng = new LatLng(latfallback, longfallback);
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title("fallback position");
                                googleMap.addMarker(markerOptions);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }else{
                                checkCoordinates(finalPreviouslatstr, finalPreviouslongstr,currentlatitudestr,currentlongitudestr);
                                long currentTimeMillis = System.currentTimeMillis();
                                int dateNtime = (int) (currentTimeMillis / 1000);
                                if(coordflag) {
                                    Log.d(TAG, "Position was different!");
                                    assert currentlatitudestr != null;
                                    double currentlatitude = Double.parseDouble(currentlatitudestr);
                                    assert currentlongitudestr != null;
                                    double currentlongitude = Double.parseDouble(currentlongitudestr);
                                    LatLng latLng = new LatLng(currentlatitude, currentlongitude);
                                    addPointToLine(latLng);
                                    if (firstPoint){
                                        startDateNtime = dateNtime;
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        markerOptions.position(latLng);
                                        markerOptions.title("Origin!");
                                        googleMap.addMarker(markerOptions);
                                        latLngList.add(latLng);
                                        PolylineOptions polylineOptions = new PolylineOptions()
                                                .addAll(latLngList)
                                                .width(5) // Set the line width
                                                .color(Color.RED); // Set the line color
                                        polyline = googleMap.addPolyline(polylineOptions);
                                    } else if (endTripFlag) {
                                        endDateNtime = dateNtime;
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        markerOptions.position(latLng);
                                        markerOptions.title("Destination!");
                                        googleMap.addMarker(markerOptions);
                                    }
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                }
                            }
                            handler.postDelayed(this, 5000); // Update every 5 seconds
                        }
                    };
                    handler.post(cameraUpdateRunnable);
                } else if (!firstMap) {
                    Log.d(TAG, "Going for linestring!");
                    String hexString = linestr;
                    //String hexString ="010200000040010000056C0723F65D434035ED629AE94E22C0056C0723F65D434035ED629AE94E22C0056C0723F65D434035ED629AE94E22C0C3482F6AF75D4340F67B629D2A4F22C0C3482F6AF75D4340F67B629D2A4F22C0AD889AE8F35D434038312427134F22C04EB4AB90F25D4340FFB1101D024F22C0011764CBF25D43400282397AFC4E22C0C61858C7F15D43409B5434D6FE4E22C0C61858C7F15D43409B5434D6FE4E22C0910BCEE0EF5D4340F624B039074F22C0D32EA699EE5D4340B82231410D4F22C06E693524EE5D43409301A08A1B4F22C06E693524EE5D43409301A08A1B4F22C06E693524EE5D43409301A08A1B4F22C09373620FED5D4340A7255646234F22C0041BD7BFEB5D4340A912656F294F22C0041BD7BFEB5D4340A912656F294F22C04D2F3196E95D43402CF180B2294F22C018EE5C18E95D4340217711A6284F22C018EE5C18E95D4340217711A6284F22C018EE5C18E95D4340217711A6284F22C018EE5C18E95D4340217711A6284F22C0DC9E20B1DD5D4340BA490C022B4F22C0DC9E20B1DD5D4340BA490C022B4F22C0DC9E20B1DD5D4340BA490C022B4F22C0361E6CB1DB5D43405F96766A2E4F22C0361E6CB1DB5D43405F96766A2E4F22C0D28C45D3D95D4340B2BCAB1E304F22C0D28C45D3D95D4340B2BCAB1E304F22C0D28C45D3D95D4340B2BCAB1E304F22C0D28C45D3D95D4340B2BCAB1E304F22C033FD12F1D65D43401EA7E8482E4F22C0105B7A34D55D4340CE6DC2BD324F22C0105B7A34D55D4340CE6DC2BD324F22C0E82FF488D15D4340C3F352B1314F22C0E82FF488D15D4340C3F352B1314F22C0A7406667D15D4340B2D991EA3B4F22C0BF9CD9AED05D434070EA03C93B4F22C0BF9CD9AED05D434070EA03C93B4F22C0F511F8C3CF5D43403788D68A364F22C0F511F8C3CF5D43403788D68A364F22C09609BFD4CF5D4340D42AFA43334F22C0145FED28CE5D4340849A2155144F22C0145FED28CE5D4340849A2155144F22C08A8F4FC8CE5D4340354415FE0C4F22C08A8F4FC8CE5D4340354415FE0C4F22C059A31EA2D15D43409697FC4FFE4E22C059A31EA2D15D43409697FC4FFE4E22C059A31EA2D15D43409697FC4FFE4E22C059A31EA2D15D43409697FC4FFE4E22C03B56293DD35D4340BB9BA73AE44E22C0164CFC51D45D43407F69519FE44E22C0164CFC51D45D43407F69519FE44E22C0164CFC51D45D43407F69519FE44E22C067D65240DA5D4340EB538EC9E24E22C08D5F7825C95D4340AA0D4E44BF4E22C08D5F7825C95D4340AA0D4E44BF4E22C06C257497C45D434096E99788B74E22C00FB9196EC05D4340FA298E03AF4E22C00FB9196EC05D4340FA298E03AF4E22C08D429259BD5D4340C784984BAA4E22C08D429259BD5D4340C784984BAA4E22C017467A51BB5D434016BEBED6A54E22C017467A51BB5D434016BEBED6A54E22C017467A51BB5D434016BEBED6A54E22C0A83AE466B85D43405B7D7555A04E22C085984BAAB65D434055C03DCF9F4E22C03F541A31B35D434066F7E461A14E22C052F355F2B15D4340085740A19E4E22C052F355F2B15D4340085740A19E4E22C0CF488446B05D43403352EFA99C4E22C0CF488446B05D43403352EFA99C4E22C01E1A16A3AE5D43402AA8A8FA954E22C01E1A16A3AE5D43402AA8A8FA954E22C0BABC395CAB5D43406C7A50508A4E22C0BABC395CAB5D43406C7A50508A4E22C0FD135CACA85D4340CAFD0E45814E22C09E3F6D54A75D434006137F14754E22C09E3F6D54A75D434006137F14754E22C09E3F6D54A75D434006137F14754E22C05E8429CAA55D43408BC1C3B46F4E22C05E8429CAA55D43408BC1C3B46F4E22C05E8429CAA55D43408BC1C3B46F4E22C05E8429CAA55D43408BC1C3B46F4E22C05E8429CAA55D43408BC1C3B46F4E22C05E8429CAA55D43408BC1C3B46F4E22C05E8429CAA55D43408BC1C3B46F4E22C0F705F4C29D5D4340E700C11C3D4E22C080D591239D5D43409A97C3EE3B4E22C080D591239D5D43409A97C3EE3B4E22C0AA9CF6949C5D43400F0F61FC344E22C0B77EFACF9A5D434009522976344E22C00B410E4A985D43403FC7478B334E22C09B012EC8965D434086730D33344E22C09B012EC8965D434086730D33344E22C0B5C5353E935D434014CC9882354E22C0B5C5353E935D434014CC9882354E22C0331B6492915D4340DE567A6D364E22C0331B6492915D4340DE567A6D364E22C0331B6492915D4340DE567A6D364E22C0812040868E5D4340C59272F7394E22C0ABE7A4F78D5D4340ECBDF8A23D4E22C0ABE7A4F78D5D4340ECBDF8A23D4E22C0B30C71AC8B5D434080F0A1444B4E22C0B30C71AC8B5D434080F0A1444B4E22C01F2BF86D885D434022A7AFE76B4E22C0670B08AD875D43403C889D29744E22C050E3DEFC865D434008008E3D7B4E22C0FD885FB1865D43405B26C3F17C4E22C0B610E4A0845D434022E17B7F834E22C0B610E4A0845D434022E17B7F834E22C0B610E4A0845D434022E17B7F834E22C04DF6CFD3805D4340A8ACA6EB894E22C04DF6CFD3805D4340A8ACA6EB894E22C0541B9C887E5D4340C45DBD8A8C4E22C0541B9C887E5D4340C45DBD8A8C4E22C09772BED87B5D4340587380608E4E22C020425C397B5D4340D594641D8E4E22C020425C397B5D4340D594641D8E4E22C0DBFD2AC0775D43408EE89E758D4E22C0B85B9203765D43402D95B7239C4E22C0B85B9203765D43402D95B7239C4E22C0A0336953755D4340D89E5912A04E22C0A0336953755D4340D89E5912A04E22C02025766D6F5D434091F2936A9F4E22C0A437DC476E5D43400E1478279F4E22C0A437DC476E5D43400E1478279F4E22C0F33CB83B6B5D434069C70DBF9B4E22C0F33CB83B6B5D434069C70DBF9B4E22C09B594B01695D4340B343FCC3964E22C06C09F9A0675D4340446CB070924E22C06C09F9A0675D4340446CB070924E22C0791F4773645D4340B056ED9A904E22C0791F4773645D4340B056ED9A904E22C0791F4773645D4340B056ED9A904E22C0986C3CD8625D43407711A628974E22C0986C3CD8625D43407711A628974E22C0986C3CD8625D43407711A628974E22C081785DBF605D434022382EE3A64E22C081785DBF605D434022382EE3A64E22C0DBF7A8BF5E5D4340FD169D2CB54E22C0DBF7A8BF5E5D4340FD169D2CB54E22C0DBF7A8BF5E5D4340FD169D2CB54E22C096B377465B5D4340134548DDCE4E22C0AF433525595D43407C5F5CAAD24E22C0AF433525595D43407C5F5CAAD24E22C0AF433525595D43407C5F5CAAD24E22C0F7578FFB565D434030F65E7CD14E22C02844C021545D4340AD174339D14E22C0AC5626FC525D4340E94999D4D04E22C083C30B22525D43405534D6FECE4E22C083C30B22525D43405534D6FECE4E22C0D8B969334E5D4340693BA6EECA4E22C0D8B969334E5D4340693BA6EECA4E22C055DB4DF04D5D4340134548DDCE4E22C055DB4DF04D5D4340134548DDCE4E22C0D2FC31AD4D5D43404CA7751BD44E22C0209A79724D5D4340DDEC0F94DB4E22C0B64B1B0E4B5D4340711FB935E94E22C0810A47904A5D4340E883656CE84E22C0810A47904A5D4340E883656CE84E22C0713B342C465D434060E811A3E74E22C0713B342C465D434060E811A3E74E22C0713B342C465D434060E811A3E74E22C01503249A405D434077DCF0BBE94E22C0C899266C3F5D4340994A3FE1EC4E22C0C899266C3F5D4340994A3FE1EC4E22C0C899266C3F5D4340994A3FE1EC4E22C04BAC8C463E5D43402F4D11E0F44E22C0C310397D3D5D4340F14A92E7FA4E22C0DCA0F65B3B5D4340F16778B3064F22C0DCA0F65B3B5D4340F16778B3064F22C06B6116DA395D43402CB7B41A124F22C030630AD6385D434040DB6AD6194F22C01F2C6343375D4340B85CFDD8244F22C01F2C6343375D4340B85CFDD8244F22C09D819197355D4340541C075E2D4F22C0CE397826345D4340514CDE00334F22C0B6114F76335D43403788D68A364F22C0B6114F76335D43403788D68A364F22C0B1886187315D434001309E41434F22C0FF25A94C315D434018247D5A454F22C00BD462F0305D4340F9A23D5E484F22C0F3AB3940305D4340DC0E0D8B514F22C0F3AB3940305D4340DC0E0D8B514F22C0064B75012F5D4340815B77F3544F22C0064B75012F5D4340815B77F3544F22C0064B75012F5D4340815B77F3544F22C0C00644882B5D43401B2E724F574F22C0C00644882B5D43401B2E724F574F22C015FDA199275D4340957F2DAF5C4F22C0821B295B245D434092AF0452624F22C03CA3AD4A225D434067B45549644F22C08BA8893E1F5D4340AC730CC85E4F22C08BA8893E1F5D4340AC730CC85E4F22C08BA8893E1F5D4340AC730CC85E4F22C0E1D231E7195D4340070ABC934F4F22C0E1D231E7195D4340070ABC934F4F22C0E1D231E7195D4340070ABC934F4F22C0E1D231E7195D4340070ABC934F4F22C0B3EA73B5155D4340685DA3E5404F22C0B3EA73B5155D4340685DA3E5404F22C0B55208E4125D43404E7CB5A3384F22C0567E198C115D4340514CDE00334F22C0567E198C115D4340514CDE00334F22C0567E198C115D4340514CDE00334F22C0567E198C115D4340514CDE00334F22C0058C2E6F0E5D4340E831CA332F4F22C0058C2E6F0E5D4340E831CA332F4F22C0A0C6BDF90D5D4340797764AC364F22C03410CB660E5D4340D717096D394F22C0A583F57F0E5D434095287B4B394F22C0A583F57F0E5D434095287B4B394F22C05ED72FD80D5D4340D177B7B2444F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C040BE840A0E5D43405186AA984A4F22C0354415FE0C5D43407825C9737D4F22C0354415FE0C5D43407825C9737D4F22C0BE13B35E0C5D43404E2A1A6B7F4F22C0BE13B35E0C5D43404E2A1A6B7F4F22C0BE13B35E0C5D43404E2A1A6B7F4F22C0E21DE0490B5D4340C58EC6A17E4F22C0E21DE0490B5D4340C58EC6A17E4F22C0E21DE0490B5D4340C58EC6A17E4F22C0E21DE0490B5D4340C58EC6A17E4F22C09DD9AED0075D434056B77A4E7A4F22C09DD9AED0075D434056B77A4E7A4F22C0F437A110015D4340AF7D01BD704F22C006D7DCD1FF5C43408F19A88C7F4F22C006D7DCD1FF5C43408F19A88C7F4F22C0E2AFC91AF55C43407BF5F1D0774F22C0CBBBEA01F35C434092E9D0E9794F22C0CBBBEA01F35C434092E9D0E9794F22C0AED689CBF15C4340D6C56D34804F22C0AED689CBF15C4340D6C56D34804F22C09C6B98A1F15C43405C9198A0864F22C09C6B98A1F15C43405C9198A0864F22C09C6B98A1F15C43405C9198A0864F22C0F85278D0EC5C43409DBAF2599E4F22C046240A2DEB5C4340BC581822A74F22C046240A2DEB5C4340BC581822A74F22C046240A2DEB5C4340BC581822A74F22C046240A2DEB5C4340BC581822A74F22C085436FF1F05C4340BCE9961DE24F22C0A35C1ABFF05C43409D685721E54F22C08B34F10EF05C4340DB87BCE5EA4F22C08B34F10EF05C4340DB87BCE5EA4F22C08B34F10EF05C4340DB87BCE5EA4F22C08B34F10EF05C4340DB87BCE5EA4F22C08B34F10EF05C4340DB87BCE5EA4F22C0F7EAE3A1EF5C4340C1E09A3BFA4F22C0390EBC5AEE5C434047ACC5A7005022C0390EBC5AEE5C434047ACC5A7005022C06AFAEC80EB5C4340EC1516DC0F5022C06AFAEC80EB5C4340EC1516DC0F5022C06AFAEC80EB5C4340EC1516DC0F5022C06AFAEC80EB5C4340EC1516DC0F5022C06AFAEC80EB5C4340EC1516DC0F5022C06AFAEC80EB5C4340EC1516DC0F5022C073BB97FBE45C4340A5A31CCC265022C073BB97FBE45C4340A5A31CCC265022C06284F068E35C4340CAFE791A305022C039252026E15C434011C8258E3C5022C022FDF675E05C434055A4C2D8425022C022FDF675E05C434055A4C2D8425022C022FDF675E05C434055A4C2D8425022C022FDF675E05C434055A4C2D8425022C05872158BDF5C434027D9EA724A5022C04016A243E05C4340381092054C5022C0ABCC94D6DF5C4340E31934F44F5022C0E141B3EBDE5C4340C9552C7E535022C0EF5701BEDB5C4340CF126404545022C0EF5701BEDB5C4340CF126404545022C0EF5701BEDB5C4340CF126404545022C02BBEA1F0D95C43403BE0BA62465022C02BBEA1F0D95C43403BE0BA62465022C02BBEA1F0D95C43403BE0BA62465022C02BBEA1F0D95C43403BE0BA62465022C02BBEA1F0D95C43403BE0BA62465022C0B5C189E8D75C434025B20FB22C5022C0DAFF006BD55C43400514EAE9235022C0753A90F5D45C43403B8908FF225022C0753A90F5D45C43403B8908FF225022C028D192C7D35C43407A8B87F71C5022C028D192C7D35C43407A8B87F71C5022C028D192C7D35C43407A8B87F71C5022C028D192C7D35C43407A8B87F71C5022C028D192C7D35C43407A8B87F71C5022C028D192C7D35C43407A8B87F71C5022C0BE310400C75C4340CA501553E94F22C0BE310400C75C4340CA501553E94F22C0BE310400C75C4340CA501553E94F22C0BE310400C75C4340CA501553E94F22C0B55373B9C15C43407DAD4B8DD04F22C0B55373B9C15C43407DAD4B8DD04F22C0FD67CD8FBF5C4340E1ED4108C84F22C0522AE109BD5C434039B4C876BE4F22C02997C62FBC5C43405378D0ECBA4F22C02997C62FBC5C43405378D0ECBA4F22C0B2666490BB5C434037C7B94DB84F22C0B857E6ADBA5C43408600E0D8B34F22C018946934B95C434020B6F468AA4F22C06631B1F9B85C4340DEC66647AA4F22C037E15E99B75C43406A32E36DA54F22C0971DE21FB65C4340758F6CAE9A4F22C0971DE21FB65C4340758F6CAE9A4F22C051A5660FB45C4340FE0DDAAB8F4F22C051A5660FB45C4340FE0DDAAB8F4F22C051A5660FB45C4340FE0DDAAB8F4F22C0D0622992AF5C4340BDE47FF2774F22C0D0622992AF5C4340BDE47FF2774F22C0D0622992AF5C4340BDE47FF2774F22C08FA7E507AE5C4340CEFE40B96D4F22C0";
                    // Create a WKBReader to parse the hex string
                    WKBReader reader = new WKBReader();
                    try {
                        // Parse the hex string into a Geometry object
                        byte[] wkb = WKBReader.hexToBytes(hexString);
                        Geometry geometry = reader.read(wkb);
                        // Check if the geometry is a LineString
                        if (geometry instanceof LineString) {
                            LineString lineString = (LineString) geometry;
                            Coordinate[] coordinates = lineString.getCoordinates();
                            int count = 0;
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng firstCoord = null;
                            for (Coordinate coordinate : coordinates) {
                                LatLng latLng = new LatLng(coordinate.x, coordinate.y);
                                latLngList.add(latLng);
                                if (count == 0){
                                    markerOptions.position(latLng);
                                    markerOptions.title("Origin!");
                                    googleMap.addMarker(markerOptions);
                                    firstCoord = latLng;
                                    count ++;
                                } else if (count == coordinates.length-1) {
                                    markerOptions.position(latLng);
                                    markerOptions.title("Destination!");
                                    googleMap.addMarker(markerOptions);
                                } else if (count > 0 &&  count < coordinates.length) {
                                    markerOptions.position(latLng);
                                    count ++;
                                }
                            }
                            System.out.println(latLngList);
                            PolylineOptions polylineOptions = new PolylineOptions()
                                    .addAll(latLngList)
                                    .width(5) // Set the line width
                                    .color(Color.RED); // Set the line color
                            polyline = googleMap.addPolyline(polylineOptions);
                            polyline.setPoints(latLngList);
                            float zoomLevel2 = 17.0f;;
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstCoord,zoomLevel2));

                        } else {
                            System.out.println("The input geometry is not a LineString.");
                        }
                    } catch (ParseException e) {
                        Log.d(TAG, "went for catch!");
                        e.printStackTrace();
                    }
                }

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {

                        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude +", "+ latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }
    private void checkCoordinates(String previouslatstr, String previouslongstr,String currentlatitudestr, String currentlongitudestr){

        double previouslat = Double.parseDouble(previouslatstr);
        double previouslong = Double.parseDouble(previouslongstr);
        double currentlatitude = Double.parseDouble(currentlatitudestr);
        double currentlongitude = Double.parseDouble(currentlongitudestr);
        if(currentlatitude != previouslat && currentlongitude != previouslong){
            coordflag = true;
        } else if (currentlatitude == previouslat && currentlongitude == previouslong) {
            coordflag = false;
        }
    }

    private boolean addPointToLine(LatLng latLng) {
        if(!latLngList.isEmpty()){
            latLngList.add(latLng);
            polyline.setPoints(latLngList);
            return firstPoint = false;
        }
        return firstPoint = true;

    }
}