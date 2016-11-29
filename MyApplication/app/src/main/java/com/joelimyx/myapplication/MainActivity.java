package com.joelimyx.myapplication;

import android.database.Cursor;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.joelimyx.myapplication.MainActivity.REQUEST_CALENDAR;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private CalendarAdapter mAdapter;
    public static final int CALENDAR_LOADER = 1;
    public static final int REQUEST_CALENDAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new CalendarAdapter(new ArrayList<Date>());
        recyclerview.setAdapter(mAdapter);

        getPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @AfterPermissionGranted(REQUEST_CALENDAR)
    private void getPermission(){
        String [] perms= {android.Manifest.permission.READ_CALENDAR, android.Manifest.permission.WRITE_CALENDAR};
        if (!EasyPermissions.hasPermissions(this,perms)){
            EasyPermissions.requestPermissions(this,"Get Calendar",REQUEST_CALENDAR,perms);
        }else{
            getSupportLoaderManager().initLoader(CALENDAR_LOADER,null,this);
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case CALENDAR_LOADER:
                return new CursorLoader(this, CalendarContract.Events.CONTENT_URI,
                        new String[]{CalendarContract.Events._ID,CalendarContract.Events.TITLE,CalendarContract.Events.DTSTART},
                        null,
                        null,
                        String.valueOf(CalendarContract.Events.DTSTART)+" DESC");
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapdata(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapdata(null);

    }
}
