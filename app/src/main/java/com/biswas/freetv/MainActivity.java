package com.biswas.freetv;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.*;


public class MainActivity extends ActionBarActivity {
    private Button button;
    private TextView textView;
    private WebView webView;
    private static final String REGEX = "rtsp:";
    private static final String URL ="http://robitv.mobi/"; // "file:///android_asset/index.html";
    private Pattern mPattern;
    public static String logTag = "-----Key Event-----";
    public static String  intentID="com.biswas.freetv.url";
    //private Matcher mMatcher;
    @SuppressWarnings("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPattern=Pattern.compile(REGEX);
        webView=(WebView)findViewById(R.id.webView);
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String user = textView.getText().toString();
                if (user.isEmpty()) {
                    user = "World";
                }
                String javascript = "javascript:document.getElementById('msg').innerHTML='Hello " + user + "!';";
                //view.loadUrl(javascript);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("-----Key Event-----", url);
                //view.stopLoading();
                Log.d(logTag, String.valueOf(isStreaming(url,mPattern)));
                if(isStreaming(url,mPattern)){
                    view.stopLoading();
                    Intent i=new Intent(getBaseContext(),Streaming.class);
                    i.putExtra(intentID,url);
                    startActivity(i);
                    Log.d("-----Key Event-----","Streaming link pound");
                    Toast.makeText(getApplicationContext(), "Streaming link pound!", Toast.LENGTH_LONG).show();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        refreshWebView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshWebView();
            }
        });
        


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void refreshWebView() {
        webView.loadUrl(URL);
    }
public boolean isStreaming(String url,Pattern p){
    Matcher m=p.matcher(url);
    return m.find(0);
    }
}
