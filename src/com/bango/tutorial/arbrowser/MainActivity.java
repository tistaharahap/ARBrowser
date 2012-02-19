/* **
 * The MIT License (MIT)
 * 
 * Copyright (c) 2012 Batista Harahap
 * 
 * Permission is hereby granted, free of charge, to any person 
 * obtaining a copy of this software and associated documentation 
 * files (the "Software"), to deal in the Software without 
 * restriction, including without limitation the rights to use, 
 * copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be 
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY 
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * ---------------------------------------------------------------------
 */
package com.bango.tutorial.arbrowser;

import java.util.ArrayList;
import java.util.Collection;

import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/* **
 * Simple activity to launch Wikitude AR Browser with
 * pre-defined coordinates.
 * 
 * @package		com.bango.tutorial
 * @subpackage	arbrowser
 * @category	Tutorial
 * @author		Batista Harahap
 * @link		http://www.bango29.com/
 * @license		MIT License - http://www.opensource.org/licenses/mit-license.php
 * 
 */
public class MainActivity extends Activity {
	protected static final String WIKITUDE_REG_KEY = "GET_FROM_WIKITUDE";
	protected static final String WIKITUDE_REG_NAME = "YOUR_NAME";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Bind click to launch Wikitude
        Button btn = (Button) findViewById(R.id.wkButton);
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				startWikitude();
			}
		});
    }
    
    private final void startWikitude() {
    	WikitudeARIntent wk = new WikitudeARIntent(
        		getApplication(), 		// Application Base Context
        		WIKITUDE_REG_KEY, 		// Get from Wikitude
        		WIKITUDE_REG_NAME		// Get from Wikitude
        );
        Collection<WikitudePOI> pois = new ArrayList<WikitudePOI>();
        
        // Jakarta
        WikitudePOI poi = new WikitudePOI(
        		-6.175446307348086f, 			// Latitude
        		106.82704210281372f, 			// Longitude
        		0.0f, 							// Altitude
        		"Jakarta", 						// Title
        		"Alun-alun: Monumen Nasional"	// Description
        );
        poi.setLink("http://jakarta.urbanesia.com/profile/monumen-nasional");
        poi.setIconuri("http://www.urbanesia.com/img/business/1/9/business-197716_1864346803798_1094832350_32319939_6695741_n.api_super_search_v1.jpg");
        pois.add(poi);
        
        // Manado
        poi = new WikitudePOI(
        		1.491719f,
        		124.842001f,
        		0.0f,
        		"Manado",
        		"Jalan Dotu Lolong Lasut"
        );
        poi.setLink("http://www.jotravelguide.com/ID/indonesia/manado/monumen_dan_tugu.php");
        poi.setIconuri("http://www.jotravelguide.com/images/LolongK.jpg");
        pois.add(poi);
        
        // Papua
        poi = new WikitudePOI(
        		-0.691607f,
        		130.424166f,
        		0.0f,
        		"Papua",
        		"Raja Ampat"
        );
        poi.setLink("http://www.diverajaampat.org/");
        poi.setIconuri("http://www.diverajaampat.org/Images/Homepage/IN.P.Wayag.001e.jpg");
        pois.add(poi);
        
        // Jogja
        poi = new WikitudePOI(
        		-7.812454845221662f,
        		110.36307334899902f,
        		0.0f,
        		"Jogja",
        		"Museum Kraton Jogjakarta"
        );
        poi.setLink("http://jogja.urbanesia.com/profile/museum-keraton-yogyakarta");
        poi.setIconuri("http://static-50.urbanesia.com/img/business/2/0/business-2010-09-13_15.23-.59-.api_super_search_v1.jpg");
        pois.add(poi);
        
        // Add all POIs to Wikitude Intent
        wk.addPOIs(pois);
        
        // Try launching Wikitude app, redirect to Android Market if app is not available
        try {
        	wk.startIntent(MainActivity.this);
        } catch(Exception e) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage(
					"Whoops Wikitude will be downloaded for the Augmented Reality view")
					.setCancelable(true)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									String wtude = "market://search?q=pname:com.wikitude";
									Intent wktd = new Intent(
											Intent.ACTION_VIEW, Uri
													.parse(wtude));
									try {
										startActivity(wktd);
										finish();
									} catch(ActivityNotFoundException e1) {
										Toast.makeText(MainActivity.this, "Cannot launch Android Market, sorry you will need to download Wikitude yourself.", Toast.LENGTH_LONG).show();
									}
								}
							});
			builder.setTitle(MainActivity.this.getString(R.string.app_name)
					.toString());
			builder.setIcon(R.drawable.ic_launcher);
			AlertDialog alert = builder.create();
			alert.show();
        }
    }
}