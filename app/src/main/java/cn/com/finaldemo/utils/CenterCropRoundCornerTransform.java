package cn.com.finaldemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

public class CenterCropRoundCornerTransform extends CenterCrop {
    private static float radius = 0f;

    public CenterCropRoundCornerTransform(int px) {
        this.radius = px;
        Log.d("CenterCropRoundCorne", px + "");
    }

    //圆角弧度
    private float[] rids = {10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,};

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);

        if (bitmap == null) return null;

        Bitmap result = pool.get(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        if (result == null) {
            result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, bitmap.getWidth(), bitmap.getHeight());

//        Path path = new Path();
//        path.addRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), rids, Path.Direction.CW);
//        canvas.clipPath(path);
//        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        canvas.drawRect(0, radius, radius, 0, paint);

        canvas.drawRoundRect(rectF, radius, radius, paint);

        return result;
    }

    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}