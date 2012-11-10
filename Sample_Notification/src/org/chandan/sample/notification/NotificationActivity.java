package org.chandan.sample.notification;

import android.app.Activity;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Notification demo activity.
 * @author chandan, Nov 4, 2012, 11:46:29 AM
 */
public class NotificationActivity extends Activity {
	
	private static final String TAG=NotificationActivity.class.getSimpleName();
	
	private static final boolean DEBUG=Boolean.TRUE;
	
	private static final String ACTION_MY_NOTIFICATION_CALLBACK=
				"org.chandan.sample.notification.NOTIFICATION_CALLBACK";
	
	private static final int REQUEST_CODE=0;
	
	public static final String KEY_ID="id";
	
	public static final String KEY_TITLE="title";
	
	public static final String KEY_MESSAGE="message";

	private static final long[] VIBRATE_PATTERN = {100,200,100,200};
	
	private static int NOTIFICATION_COUNT;
	
	private Button mButtonSendNotification;
	private CheckBox mCheckBoxShouldVibrate;
	private EditText mEditTextTitle;
	private EditText mEditTextMessage;
	private TextView mTextViewNotificationId;
	
	private String mStringTitle;
	private String mstringMessage;
	private boolean mBooleanShouldVibrate;
	
	private NotificationManager mNotificationManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_notification);
        
        mTextViewNotificationId=(TextView)findViewById(R.id.textview_notification_id);
        mEditTextTitle=(EditText)findViewById(R.id.edittext_notification_title);
        mEditTextMessage=(EditText)findViewById(R.id.edittext_notification_message);
        mCheckBoxShouldVibrate=(CheckBox)findViewById(R.id.checkbox_should_vibrate);
        mButtonSendNotification=(Button)findViewById(R.id.button_send_notification);
        
        mButtonSendNotification.setOnClickListener(new SendNotificationOnClickListener());
        
        updateNotificationIdView();
    }
    
    /**
     * Updates current notification ID's view..
     * 
     * chandan, Nov 10, 2012, 8:57:02 PM
     */
    private void updateNotificationIdView(){
    	if(mTextViewNotificationId!=null){
    		NOTIFICATION_COUNT++;
    		mTextViewNotificationId.setText("Notification ID: "+NOTIFICATION_COUNT);
    	}
    }
    
    /**
     * Custom click listener for send notification button.
     * 
     * @author chandan, Nov 4, 2012, 12:13:33 PM
     */
    private class SendNotificationOnClickListener implements OnClickListener{

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View view) {
			handleClickOnSendNotification();
		}
    	
    }
    
    /**
     * Resets all input fields present in this view.
     * 
     * chandan, Nov 4, 2012, 12:18:43 PM
     */
    private void resetFields(){
    	if(mEditTextTitle!=null){
    		mEditTextTitle.setText("");
    	}
    	
    	if(mEditTextMessage!=null){
    		mEditTextMessage.setText("");
    	}
    	
    	if(mCheckBoxShouldVibrate!=null){
    		mCheckBoxShouldVibrate.setChecked(false);
    	}
    	//Increase count by 1 and updates view..
    	updateNotificationIdView();
    }
    
    /**
     * Extracts all field's values of this view.
     * 
     * chandan, Nov 4, 2012, 12:22:00 PM
     */
    private void fetchFieldValues(){
    	if(mEditTextTitle!=null){
    		mStringTitle=mEditTextTitle.getText().toString();
    	}
    	
    	if(mEditTextMessage!=null){
    		mstringMessage=mEditTextMessage.getText().toString();
    	}
    	
    	if(mCheckBoxShouldVibrate!=null){
    		mBooleanShouldVibrate=mCheckBoxShouldVibrate.isChecked();
    	}
    }
    
    /**
     * Processes input field values for their validity. i.e null,length check etc..
     * <p>
     * NOTE: Make sure you have done with {@link #fetchFieldValues()} in prior.
     * @return True if all fields are valid else false.
     * chandan, Nov 4, 2012, 12:22:53 PM
     */
    private boolean areAllFieldValuesValid(){
    	boolean areAllFieldsValid=true;
    	//IF-ELSE nested in order to evaluate one by one..
    	if(mStringTitle==null || mStringTitle.length()<4){
    		Toast.makeText(NotificationActivity.this,
    				"Title is invalid!", Toast.LENGTH_SHORT).show();
    	}else if(mstringMessage==null || mstringMessage.length()<4){
    		Toast.makeText(NotificationActivity.this,
    				"Message is invalid!", Toast.LENGTH_SHORT).show();
    	}
    	
    	return areAllFieldsValid;
    }
    
    /**
     * Method which handles further processing of send notification click event.
     * 
     * chandan, Nov 4, 2012, 12:15:04 PM
     */
    private void handleClickOnSendNotification(){
    	fetchFieldValues();
    	prepareAndSendNotification();    	    	
    }
    
    /**
     * Prepares notification.
     * 
     * chandan, Nov 4, 2012, 12:32:34 PM
     */
    private void prepareAndSendNotification(){
    	
    	if(!areAllFieldValuesValid()){
    		return;
    	}
    	
    	if(mNotificationManager==null){
    		mNotificationManager=(NotificationManager)
    					getSystemService(Context.NOTIFICATION_SERVICE);
    	}
    	
    	
    	//Intent intentTargetActivity=new Intent(NotificationActivity.this,NotificationCallbackActivity.class);
    	Intent intentTargetActivity=new Intent();
    	intentTargetActivity.setAction(ACTION_MY_NOTIFICATION_CALLBACK);
    	//IntentFilter targetIntentFilter=new IntentFilter();
    	//intentTargetActivity.addCategory(ACTION_MY_NOTIFICATION_CALLBACK);
    	
    	//For future reference..
    	intentTargetActivity.putExtra(KEY_ID,NOTIFICATION_COUNT);
    	intentTargetActivity.putExtra(KEY_TITLE, mStringTitle);
    	intentTargetActivity.putExtra(KEY_MESSAGE, mstringMessage);
    	
    	//Embed target intent in pending intent...
    	PendingIntent pendingIntentTarget=PendingIntent.getActivity(NotificationActivity.this,
    			REQUEST_CODE,intentTargetActivity,/*PendingIntent.FLAG_UPDATE_CURRENT*/
    			android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
    	
    	NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this);
    	notificationBuilder.setSmallIcon(R.drawable.ic_launcher);//Mandatory
    	notificationBuilder.setContentIntent(pendingIntentTarget);//Mandatory
    	notificationBuilder.setWhen(System.currentTimeMillis());//Mandatory
    	notificationBuilder.setTicker("TickerText");//Mandatory
    	notificationBuilder.setContentTitle("ContentTitle");//Mandatory
    	notificationBuilder.setContentText("ContentText");//Mandatory
    	   	
    	//This constructor has became deprecated in pre 3.0 versions. You may have to use Notification.Builder()
    	//which is show below to this implementation:
    	//Notification notificationInfo=new Notification(
    	//		0/*notification icon..*/,mStringTitle,System.currentTimeMillis());
    	//
    	//notificationInfo.setLatestEventInfo(
    	//		NotificationActivity.this,mStringTitle, mstringMessage, pendingIntentTarget);
    	    	
    	Notification notificationInfo=notificationBuilder.build();
    	notificationInfo.flags|=Notification.FLAG_AUTO_CANCEL;//Notification gets cancelled once user clicks on it.
    	
    	if(mBooleanShouldVibrate){
    		notificationInfo.vibrate=VIBRATE_PATTERN;    		
    	}
    	
    	//similarly you may set audio effect to your notification
    	//by setting uri of sound file like:
    	//notificationInfo.sound=soundUri;
    	
    	//Else you may wish to use the default notification behavior by:
    	//notificationInfo.defaults=Notification.DEFAULT_ALL;
    	
    	//Using notification count itself as ID..
    	//Note using this notification ID we can controle post notification behaviour
    	// i.e we may cancel the notification if it exists or check for 
    	//presence of the notification with same id.. etc..
    	mNotificationManager.notify(NOTIFICATION_COUNT, notificationInfo);
    	
    	Toast.makeText(NotificationActivity.this, 
    			"Notification sent with ID:"+NOTIFICATION_COUNT,Toast.LENGTH_SHORT).show();
    	
    	//you may cancel sent notification by using:
    	//mNotificationManager.cancel(NOTIFICATION_COUNT);
    	    	    	
    	//Finally..
    	resetFields();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		finish();
    		return true;
    	}
    	
    	return super.onKeyDown(keyCode, event);
    }
    
}