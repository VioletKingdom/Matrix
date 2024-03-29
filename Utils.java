package com.laioffer.matrix;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
* Util class stores util static method
*/
public class Utils {
   /**
    * Md5 encryption, encode string
    * @param input the string to be encoded
    * @return encoded string
    */
   public static String md5Encryption(final String input){
       String result = "";
       try{
           MessageDigest messageDigest = MessageDigest.getInstance("MD5");
           messageDigest.reset();
           messageDigest.update(input.getBytes(Charset.forName("UTF8")));
           byte[] resultByte = messageDigest.digest();
           result = new String(Hex.encodeHex(resultByte));
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return result;
   }
   
/**
* Download an Image from the given URL, then decodes and returns a Bitmap object.
* @param imageUrl the url fetching from the remote
* @return the bitmap object
*/
public static Bitmap getBitmapFromURL(String imageUrl) {
   Bitmap bitmap = null;

   if (bitmap == null) {
       try {
           URL url = new URL(imageUrl);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setDoInput(true);
           connection.connect();
           InputStream input = connection.getInputStream();
           bitmap = BitmapFactory.decodeStream(input);
       } catch (IOException e) {
           e.printStackTrace();
           Log.e("Error: ", e.getMessage().toString());
       }
   }

   return bitmap;
}

   /**
* Get distance between two locations
* @param currentLatitude current latitude
* @param currentLongitude current longitude
* @param destLatitude destination latitude
* @param destLongitude destination longitude
* @return the distance between two locations by miles
*/
public static int distanceBetweenTwoLocations(double currentLatitude,
                                             double currentLongitude,
                                             double destLatitude,
                                             double destLongitude) {

   Location currentLocation = new Location("CurrentLocation");
   currentLocation.setLatitude(currentLatitude);
   currentLocation.setLongitude(currentLongitude);
   Location destLocation = new Location("DestLocation");
   destLocation.setLatitude(destLatitude);
   destLocation.setLongitude(destLongitude);
   double distance = currentLocation.distanceTo(destLocation);

   double inches = (39.370078 * distance);
   int miles = (int) (inches / 63360);
   return miles;
}

   
/**
* Resize bitmap to corresponding height and width
* @param bm input bitmap
* @param newWidth new width
* @param newHeight new height
* @return refactored image
*/
public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
   int width = bm.getWidth();
   int height = bm.getHeight();
   float scaleWidth = ((float) newWidth) / width;
   float scaleHeight = ((float) newHeight) / height;
   // CREATE A MATRIX FOR THE MANIPULATION
   Matrix matrix = new Matrix();

   // RESIZE THE BIT MAP
   matrix.postScale(scaleWidth, scaleHeight);

   // "RECREATE" THE NEW BITMAP
   Bitmap resizedBitmap = Bitmap.createBitmap(
           bm, 0, 0, width, height, matrix, false);
   bm.recycle();
   return resizedBitmap;
}

}
