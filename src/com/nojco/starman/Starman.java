package com.nojco.starman;

// Imports
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.*;
 
// Main Class
public class Starman extends Activity {
	
	// Class variables
	private Boolean firstRun = true; 			// Are we starting up, or just rotating
	private Context context;					// Context variable for various window methods
	private MediaPlayer mediaPlayer;			// MediaPlayer is global for rotating
	private Boolean isPlaying;					// Are we currently playing (rotating)
	private AnimationDrawable starmanAnimation; // Animation
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Set context and boolean variables
        context = getBaseContext();
        
        if (firstRun)
        {
        	mediaPlayer = null;
        	isPlaying = false;
        	firstRun = false;
        }
        firstRun = false;
    }
    
    // Option Menu Method
    // Defines the menu that comes up when the menu button is pressed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    // Options Selection Method
    // Determines what to do for the menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	// In the full version, give the user a settings menu
        	// Since this is the demo, inform them such
	        case R.id.settings:
	            Toast.makeText(getBaseContext(), "This feature is only available in the Donate version", 5).show();
	            return true;
	        // Quit gracefully (stopping music and animation before exiting)
	        case R.id.quit:
	            stop();
	        	finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    // Catches the WindowFocusChanged action 
    // Make sure everything stops when we lose focus
    // More importantly, keep the animation going on rotation
    public void onWindowFocusChanged(boolean hasFocus) 
    {
        if (hasFocus)
        {
        	ImageView ivStarman = (ImageView)findViewById(R.id.ivStarman);
        	ivStarman.setBackgroundResource(R.drawable.starman_animated);

			// Get the background, which has been compiled to an AnimationDrawable object.
			starmanAnimation = (AnimationDrawable) ivStarman.getBackground();
        }
        else
        {   
        	starmanAnimation.stop();
        }
    }

    // Start/Stop method
    // If the music/animation is playing, stop it, otherwise, start it
	public void startStop(View v) {
		if (isPlaying) {
			stop();
		}
		else {
			start();
		}
	}
	
	// Start method
	// Starts the animation then the music
	public void start() {
		start_image();
		start_media();
	    isPlaying = true;
	}
	
	// Start Image method
	// Starts the animation
	public void start_image() {
		ImageView ivStarman = (ImageView)findViewById(R.id.ivStarman);
		ivStarman.setImageDrawable(null);
	    starmanAnimation.start();
	    
		if (mediaPlayer != null && mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
	// Start Media method
	// Starts the music, also trapping the onComplete to stop the music
	public void start_media() {
		mediaPlayer = MediaPlayer.create(context, R.raw.starman);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop ();
			}
		});
	    mediaPlayer.start();
	}
	
	// Stop
	// Stops both the music and animation
	public void stop() {
		starmanAnimation.stop();
		ImageView ivStarman = (ImageView)findViewById(R.id.ivStarman);
    	ivStarman.setImageResource(R.drawable.starman_static);
    	
		// Get the background, which has been compiled to an AnimationDrawable object.
		starmanAnimation = (AnimationDrawable) ivStarman.getBackground();

		if (mediaPlayer != null && mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		isPlaying = false;
	}
}