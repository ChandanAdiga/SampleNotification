package org.chandan.sample.notification;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity called when user clicks on sent notification..
 * @author chandan, Nov 4, 2012, 12:55:44 PM
 */
public class NotificationCallbackActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.screen_notification_callback);
		
		TextView textViewReceivedNotificationId=(TextView)findViewById(R.id.textview_received_notification_id);
		TextView textViewReceivedNotificationTitle=(TextView)findViewById(R.id.textview_received_notification_title);
		TextView textViewReceivedNotificationMessage=(TextView)findViewById(R.id.textview_received_notification_message);
		
		Button buttonDismiss=(Button)findViewById(R.id.button_dismiss_notification);
	
		Bundle notificationData=getIntent().getExtras();
		textViewReceivedNotificationId.setText("ID: "+notificationData.getInt(NotificationActivity.KEY_ID));
		textViewReceivedNotificationTitle.setText("TITLE: "+notificationData.getString(NotificationActivity.KEY_TITLE));
		textViewReceivedNotificationMessage.setText("MESSAGE: "+notificationData.getString(NotificationActivity.KEY_MESSAGE));
		
		buttonDismiss.setOnClickListener(new DismissNotificationOnClickListener());
		
	}
	
	
	/**
     * Custom click listener for dismiss notification button.
     * 
     * @author chandan, Nov 4, 2012, 12:13:33 PM
     */
    private class DismissNotificationOnClickListener implements OnClickListener{

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View view) {
			finish();
		}
    	
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
