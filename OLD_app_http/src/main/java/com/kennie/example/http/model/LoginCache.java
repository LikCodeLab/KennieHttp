package com.kennie.example.http.model;


import com.kennie.example.http.AppApplication;
import com.kennie.example.http.utils.ACache;

public class LoginCache {
    private final static String fileName = "logincache";
    private static LoginCache instance = null;
    private ACache mAcache = null;

    public LoginCache() {
        if (AppApplication.getAppContext() != null) {
            mAcache = ACache.get(AppApplication.getAppContext(), fileName);
        }
    }

    public static LoginCache getInstance() {
        if (instance == null) {
            synchronized (LoginCache.class) {
                if (instance == null) {
                    instance = new LoginCache();
                }
            }
        }
        return instance;
    }

    public void save(String username, String password) {
        if (mAcache != null) {
            mAcache.put(this.getClass().getSimpleName(), new LoginInfo(username, password));
        } else {
            if (AppApplication.getAppContext() != null) {
                mAcache = ACache.get(AppApplication.getAppContext(), fileName);
                mAcache.put(this.getClass().getName(), new LoginInfo(username, password));
            }
        }
    }

    public LoginInfo get() {
        if (mAcache == null)
            return null;
        return (LoginInfo) mAcache.getAsObject(this.getClass().getSimpleName());
    }
}
