package uk.co.jarofgreen.realvirtualmaze;

import java.util.List;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.Overlay;
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
    private MapOverlay mmapOverlay = null;

	
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
        
        
       // mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
       // mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);

        
        
        mmapOverlay = new MapOverlay(this);
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.add(mmapOverlay);
        mapView.invalidate();

        
        
    }
    
    
    
    public class MapOverlay extends org.osmdroid.views.overlay.Overlay {

        public MapOverlay(Context ctx) {
            super(ctx);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void draw(Canvas pC, MapView pOsmv, boolean shadow) {
            if (shadow)
                return;

            Paint lp3;
            lp3 = new Paint();
            lp3.setColor(Color.RED);
            lp3.setAntiAlias(true);
            lp3.setStyle(Style.STROKE);
            lp3.setStrokeWidth(1);
            lp3.setTextAlign(Paint.Align.LEFT);
            lp3.setTextSize(12);
            final Rect viewportRect = new Rect();
            final Projection projection = pOsmv.getProjection();
            viewportRect.set(projection.getScreenRect());
            // Draw a line from one corner to the other
            pC.drawLine(viewportRect.left, viewportRect.top,
                    viewportRect.right, viewportRect.bottom, lp3);
        }

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