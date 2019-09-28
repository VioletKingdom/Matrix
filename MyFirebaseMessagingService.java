/**
* Create and show a simple notification containing the received FCM message.
*/
/**
* Create and show a simple notification containing the received FCM message.
*/
private void sendNotification(String title, String body) {
private void sendNotification(RemoteMessage remoteMessage) {
   String type = remoteMessage.getData().get("type");
   String description = remoteMessage.getData().get("description");
   Bitmap icon =  BitmapFactory.decodeResource(mContext.getResources(),
           Config.trafficMap.get(type));

   Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

   Intent intent = new Intent(getApplicationContext(), ControlPanel.class);
   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
   PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

   NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
   String NOTIFICATION_CHANNEL_ID = "101";

   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
       NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);

       //Configure Notification Channel
       notificationChannel.setDescription("Matrix Notfication");
       notificationChannel.enableLights(true);
       notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
       notificationChannel.enableVibration(true);

       notificationManager.createNotificationChannel(notificationChannel);
   }

   NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
           .setSmallIcon(R.drawable.boy)
           .setContentTitle(type)
           .setAutoCancel(true)
           .setSound(defaultSound)
           .setLargeIcon(icon)
           .setContentText(description)
           .setContentIntent(pendingIntent)
           .setWhen(System.currentTimeMillis())
           .setPriority(Notification.PRIORITY_MAX);

   notificationManager.notify(1, notificationBuilder.build());
}


@Override
public void onMessageReceived(RemoteMessage remoteMessage) {
   // TODO(developer): Handle FCM messages here.
   Log.d(TAG, "From: " + remoteMessage.getFrom());
   if (mContext == null) {
       mContext = getBaseContext();
   }
   // Check if message contains a data payload.
   if (remoteMessage.getData().size() > 0) {
       Log.d(TAG, "Message data payload: " + remoteMessage.getData());
   }

   // Check if message contains a notification payload.
   sendNotification(remoteMessage);
   if (remoteMessage.getNotification() != null) {
       Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
   }
}


}
