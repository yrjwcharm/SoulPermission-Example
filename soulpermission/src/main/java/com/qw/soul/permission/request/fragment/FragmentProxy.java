package com.qw.soul.permission.request.fragment;

import android.support.annotation.NonNull;
import com.qw.soul.permission.bean.Special;
import com.qw.soul.permission.callbcak.RequestPermissionListener;
import com.qw.soul.permission.callbcak.SpecialPermissionListener;
import com.qw.soul.permission.debug.PermissionDebug;
import com.qw.soul.permission.request.IPermissionActions;


/**
 * @author cd5160866
 */
public class FragmentProxy implements IPermissionActions {

    private static final String TAG = FragmentProxy.class.getSimpleName();

    private IPermissionActions fragmentImp;

    public FragmentProxy(IPermissionActions fragmentImp) {
        this.fragmentImp = fragmentImp;
    }

    @Override
    public void requestPermissions(@NonNull String[] permissions, RequestPermissionListener listener) {
        this.fragmentImp.requestPermissions(permissions, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " request:" + hashCode());
    }

    @Override
    public void requestSpecialPermission(Special permission, SpecialPermissionListener listener) {
        this.fragmentImp.requestSpecialPermission(permission, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " requestSpecial:" + hashCode());
    }

}
