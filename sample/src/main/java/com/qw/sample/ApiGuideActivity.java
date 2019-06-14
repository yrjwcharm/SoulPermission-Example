package com.qw.sample;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.qw.sample.utils.Utils;
import com.qw.soul.permission.Constants;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.bean.Special;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.qw.soul.permission.callbcak.SpecialPermissionListener;

/**
 * if your project based on Activity
 */
public class ApiGuideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_guide);
    }

    public void checkSinglePermission(View view) {
        //you can also use checkPermissions() for a series of permissions
        Permission checkResult = SoulPermission.getInstance().checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION);
        Utils.showMessage(view, checkResult.toString());
    }

    public void requestSinglePermission(final View view) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Utils.showMessage(view, permission.toString() + "\n is ok , you can do your operations");
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        Utils.showMessage(view, permission.toString() + " \n is refused you can not do next things");
                    }
                });
    }

    public void requestPermissions(final View view) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        Utils.showMessage(view, allPermissions.length + "permissions is ok" + " \n  you can do your operations");
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        Utils.showMessage(view, refusedPermissions[0].toString() + " \n is refused you can not do next things");
                    }
                });
    }

    public void requestSinglePermissionWithRationale(final View view) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_CONTACTS,
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Utils.showMessage(view, permission.toString() + "\n is ok , you can do your operations");
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        // see CheckPermissionWithRationaleAdapter
                        if (permission.shouldRationale()) {
                            Utils.showMessage(view, permission.toString() + " \n you should show a explain for user then retry ");
                        } else {
                            Utils.showMessage(view, permission.toString() + " \n is refused you can not do next things");
                        }
                    }
                });
    }

    public void checkNotification(View view) {
        boolean checkResult = SoulPermission.getInstance().checkSpecialPermission(Special.NOTIFICATION);
        Utils.showMessage(view, checkResult ? "Notification is enable" :
                "Notification is disable \n you may invoke checkAndRequestPermission and enable notification");
    }

    public void checkAndRequestNotification(final View view) {
        //if you want do noting or no need all the callbacks you may use SimpleSpecialPermissionAdapter instead
        SoulPermission.getInstance().checkAndRequestPermission(Special.NOTIFICATION, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Utils.showMessage(view, "Notification is enable now ");
            }

            @Override
            public void onDenied(Special permission) {
                Snackbar.make(view, "Notification is disable yet ", Snackbar.LENGTH_LONG)
                        .setAction("retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAndRequestNotification(v);
                            }
                        }).show();
            }
        });
    }

    public void checkAndRequestSystemAlert(final View view) {
        //if you want do noting or no need all the callbacks you may use SimpleSpecialPermissionAdapter instead
        SoulPermission.getInstance().checkAndRequestPermission(Special.SYSTEM_ALERT, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Utils.showMessage(view, "System Alert is enable now ");
            }

            @Override
            public void onDenied(Special permission) {
                Utils.showMessage(view, "System Alert is disable yet ");
            }
        });
    }

    public void checkAndRequestUnKnownSource(final View view) {
        //if you want do noting or no need all the callbacks you may use SimpleSpecialPermissionAdapter instead
        SoulPermission.getInstance().checkAndRequestPermission(Special.UNKNOWN_APP_SOURCES, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Utils.showMessage(view, "install unKnown app  is enable now ");
            }

            @Override
            public void onDenied(Special permission) {
                Utils.showMessage(view, "install unKnown app  is disable yet");
            }
        });
    }

    public void goApplicationSettings(View view) {
        SoulPermission.getInstance().goApplicationSettings();
    }

    public void getTopActivity(View view) {
        Activity activity = SoulPermission.getInstance().getTopActivity();
        if (null != activity) {
            Utils.showMessage(view, activity.getClass().getSimpleName() + " " + activity.hashCode());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.DEFAULT_CODE_APPLICATION_SETTINGS) {
            Utils.showMessage(findViewById(R.id.content), "onActivityResult from goApplicationSettings");
        }
    }
}
