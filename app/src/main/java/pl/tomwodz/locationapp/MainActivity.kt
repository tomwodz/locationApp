package pl.tomwodz.locationapp

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var locationOne: Location? = Location("One")
        var locationTwo: Location? = Location("Two")

        val b = findViewById<Button>(R.id.b)
        val b2 = findViewById<Button>(R.id.b2)

        b.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                val client: FusedLocationProviderClient
                        = LocationServices.getFusedLocationProviderClient(applicationContext)
                try {
                    client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener {
                            val longitude = it.longitude
                            val latitude = it.latitude
                            locationOne?.longitude = longitude
                            locationOne?.latitude = latitude
                            Toast.makeText(applicationContext,"$longitude $latitude", Toast.LENGTH_SHORT).show()
                        }
                } catch (e: Exception) {
                        println(e.message)
                }
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1
                )
            }
        }

        b2.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                val client: FusedLocationProviderClient
                        = LocationServices.getFusedLocationProviderClient(applicationContext)
                try {
                    client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener {
                            val longitude = it.longitude
                            val latitude = it.latitude
                            locationTwo?.longitude = longitude
                            locationTwo?.latitude = latitude
                            Toast.makeText(applicationContext,"$longitude $latitude", Toast.LENGTH_SHORT).show()
                            if(locationOne?.latitude != null && locationOne?.longitude != null
                                && locationTwo?.latitude != null && locationTwo?.longitude != null) {
                                var distance = locationOne.distanceTo(locationTwo)
                                Toast.makeText(applicationContext, "$distance", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                } catch (e: Exception) {
                    println(e.message)
                }
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}