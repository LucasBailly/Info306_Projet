package com.example.info306;

import android.Manifest;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Activity3 extends AppCompatActivity implements LocationListener {

    private static final  int PERMS_CALL_ID = 1234;

    private LocationManager lm;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private double latitudePokemon = 49.346545;
    private double longitudePokemon = 3.527055;
    private double latitude_;
    private double longitude_;

    private DatabaseManager databaseManager;

    private Marker m1 ;
    private Marker m2 ;
    private Marker m3 ;
    private Marker m4 ;

    ArrayList<LatLng>coordonnéesMarkers=new ArrayList<>();
    LatLng l0 = new LatLng(49.346545, 3.527055);
    LatLng l1 = new LatLng(49.6, 3.7);
    LatLng l2 = new LatLng(49.4, 3.6);
    LatLng l3 = new LatLng(49.7, 3.7);
    LatLng l4 = new LatLng(49.1, 3.5);
    LatLng l5 = new LatLng(49.2, 3.4);
    LatLng l6 = new LatLng(49.3, 3.2);
    LatLng l7 = new LatLng(49.01, 3.01);
    LatLng l8 = new LatLng(50.1, 3.5);
    LatLng l9 = new LatLng(48.9, 3.5);
    LatLng l10 = new LatLng(49.3, 2.9);
    LatLng l11= new LatLng(49.3, 4.01);
    LatLng l12= new LatLng(49.4, 3.45);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        //Remplissage des coordonnées
        coordonnéesMarkers.add(l0);
        coordonnéesMarkers.add(l1);
        coordonnéesMarkers.add(l2);
        coordonnéesMarkers.add(l3);

        //BD
        databaseManager = new DatabaseManager(this);
//        databaseManager.setCapturable();

//        Récupération du fragment créé dans la vue
        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);

    }

    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu3,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Activity 2", Toast.LENGTH_SHORT).show();
                openMainActivity();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Activity 3", Toast.LENGTH_SHORT).show();
                openActivity2();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPermissions();
    }

    private void checkPermissions(){
        //        Vérifier et/ou demander la permission des 2 types de permissions (Fine & Coarse)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);

            return;
        }

//        Si j'ai la permission
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }

        if(lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0,this);
        }

        if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0,this);
        }
//        On load la map seulement si les permissions sont données
        loadMap();
    }

//    se déclanche lors d'une demande de permission (cf : checkPermission() )
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        On redemande jusqu'a ce que l'utilisateur autorise l'utilisation de la position
        if(requestCode == PERMS_CALL_ID){
            checkPermissions();
        }
    }

    protected void onPause() {
        super.onPause();

//        Si lm est initialisé
        if(lm != null){
            lm.removeUpdates(this);
        }
    }

//    On se permet de supprimer les warnings puisqu'on appelle loadMap après avoir check les permissions (dans checkPermissions() )
    @SuppressWarnings("MissingPermission")
    private void loadMap(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {

//          Récupération de la google map
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onMapReady(GoogleMap googleMap) {

                List<PokemonData> pokemonsAttrapables = databaseManager.readPokemonsAttrapable();
                Activity3.this.googleMap = googleMap;
                googleMap.clear();
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));

                if(pokemonsAttrapables.size()!=0) {
                    for (int i = 0; i < 2 + (int)(Math.random() * ((coordonnéesMarkers.size() - 2) + 1)) ; i++) {
                        //Pokemon Random parmis ceux qu'il nous reste à attraper
                        String pokemon = null;
                        while (pokemon == null) {
                            pokemon =  pokemonsAttrapables.get(Math.abs(new Random().nextInt(pokemonsAttrapables.size()))).getName();
                        }
                        switch (pokemon) {
                            case "Pikachu":
                                googleMap.addMarker(new MarkerOptions().position(coordonnéesMarkers.get(i))
                                        .title(pokemon)
                                        .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pokemon)));
                                break;

                            case "Dracaufeu":
                                googleMap.addMarker(new MarkerOptions().position(coordonnéesMarkers.get(i))
                                        .title(pokemon)
                                        .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pokemon_darcaufeu)));
                                break;

                            case "Tortank":
                                googleMap.addMarker(new MarkerOptions().position(coordonnéesMarkers.get(i))
                                        .title(pokemon)
                                        .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pokemon_tortank)));
                                break;

                            case "Torterra":
                                googleMap.addMarker(new MarkerOptions().position(coordonnéesMarkers.get(i))
                                        .title(pokemon)
                                        .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pokemon_torterra)));
                                break;
                        }
                    }

// -----------------------------------------------------------------------------------------------------
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            double latitudeM = marker.getPosition().latitude;
                            double longitudeM = marker.getPosition().longitude;
                            double distance = getDistance(latitudeM, longitudeM, latitude_, longitude_);
                            if (distance < 0.05) {
                                databaseManager.setEtatOn(marker.getTitle());
                                Toast.makeText(getApplicationContext(), "Vous avez capturé " + marker.getTitle(), 3).show();


                                NotificationCompat.Builder builder = new NotificationCompat.Builder(Activity3.this, "My Notification");
                                builder.setContentTitle("Nouveau Pokemon :");
                                builder.setContentText(marker.getTitle());
                                builder.setSmallIcon(R.drawable.ic_message);
                                builder.setAutoCancel(true);

                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Activity3.this);
                                managerCompat.notify(1, builder.build());

                                marker.setVisible(false);
                            } else {
                                Toast.makeText(getApplicationContext(), "Vous etes trop loin pour capturer " + marker.getTitle(), 3).show();
                            }
                            return false;
                        }

                    });
                }
                else
                    Toast.makeText(getApplicationContext(), "Il n'y a plus de pokemon à capturer", 3).show();
                googleMap.setMyLocationEnabled(true);
            }
        });
    }

    private double getDistance(double latitudeM, double longitudeM, double latitude_, double longitude_){
        double latitude = (double)(((double)(latitudeM - latitude_))*((double)(latitudeM - latitude_)));
        double longitude = (double)(((double)(longitudeM - longitude_))*((double)(longitudeM - longitude_)));
        double res = Math.sqrt((double)(latitude + longitude));
        return res;
    }

    //Ajouter une icone à un marker
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude_ = location.getLatitude();
        longitude_ = location.getLongitude();

       // Toast.makeText(this, "Location : "+ latitude + "/"+longitude, Toast.LENGTH_LONG).show();


//        Si la google Map est bien initialisée
        if(googleMap != null){
//            on déplace la caméra en fonction des changement de position
            LatLng googleLocation = new LatLng(latitude_, longitude_);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
        }
    }
}
