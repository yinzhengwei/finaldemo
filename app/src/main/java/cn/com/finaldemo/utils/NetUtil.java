package cn.com.finaldemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetWork Utils
 */
public class NetUtil {
    // 未知网络(获取失败)
    private static final int NETWORK_UNKNOWN = -1;
    // 没有网络
    private static final int NETWORK_NONE = 0;
    // 移动网络
    public static final int NETWORK_MOBILE = 1;
    // 无线网络
    public static final int NETWORK_WIFI = 2;

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {
        if (context == null) {
            return NETWORK_UNKNOWN;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return NETWORK_UNKNOWN;
        }

        @SuppressLint("MissingPermission")
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        }

        return NETWORK_NONE;
    }

    /**
     * Get network type
     *
     * @param context
     * @return
     */
    //TODO 弃用，使用下面方法
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }

        int type = getNetWorkState(context);
        return type == NETWORK_MOBILE || type == NETWORK_WIFI;
    }

    /**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     */
    //TODO 外网ip方案优化
//    public static void getNetIp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String ipLine = "";
//                InputStream inStream = null;
//                HttpURLConnection httpConnection = null;
//                try {
//                    URL infoUrl = new URL("https://pv.sohu.com/cityjson?ie=utf-8");
//                    URLConnection connection = infoUrl.openConnection();
//                    httpConnection = (HttpURLConnection) connection;
//                    int responseCode = httpConnection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        inStream = httpConnection.getInputStream();
//                        BufferedReader reader = new BufferedReader(
//                                new InputStreamReader(inStream, "utf-8"));
//                        StringBuilder stringBuilder = new StringBuilder();
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            stringBuilder.append(line + "\n");
//                        }
//                        Pattern pattern = Pattern
//                                .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
//                        Matcher matcher = pattern.matcher(stringBuilder.toString());
//                        if (matcher.find()) {
//                            ipLine = matcher.group();
//                        }
//
//                        HxdGlobalInfo.INSTANCE.setNetIp(ipLine);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        inStream.close();
//                        httpConnection.disconnect();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
}
