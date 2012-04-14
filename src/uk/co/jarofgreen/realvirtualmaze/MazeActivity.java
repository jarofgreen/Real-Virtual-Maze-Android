package uk.co.jarofgreen.realvirtualmaze;

import java.util.List;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.util.constants.MapViewConstants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MazeActivity extends Activity implements LocationListener  {
	

    private MapView mapView;
    private MapController mapController;
    private LocationManager mLocMgr;
    private PathOverlay mmapOverlay = null;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        
        mapController = this.mapView.getController();
        mapController.setZoom(15);
        GeoPoint point2 = new GeoPoint(55965139, -3203201);
        mapController.setCenter(point2);
        
        
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);

        
        
        
        mmapOverlay = new PathOverlay(Color.RED, this);
        mmapOverlay.addPoint(new GeoPoint(55964139, -3203201));
        mmapOverlay.addPoint(new GeoPoint(55966139, -3203201));
        
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.add(mmapOverlay);
        
        mapView.invalidate();

        
        
    }
    
    

    public void onLocationChanged(Location location) {

        int lat = (int) (location.getLatitude() * 1E6);
        int lng = (int) (location.getLongitude() * 1E6);
        GeoPoint gpt = new GeoPoint(lat, lng);
        mapController.setCenter(gpt);
        mapView.invalidate();
    }
  
    @Override
    public void onProviderDisabled(String arg0) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
   


}