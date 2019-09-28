public class MatrixApplication extends Application {
   @Override
   public void onCreate() {
       super.onCreate();
       Stetho.initializeWithDefaults(this);
       FirebaseMessaging.getInstance().subscribeToTopic("android");

   }
}
