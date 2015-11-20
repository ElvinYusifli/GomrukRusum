package el.gomruk.rusum.claculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class VetenActivity extends Activity {

    WebView veb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veten);
        veb = (WebView) findViewById(R.id.webView);
        veb.loadUrl("file:///android_asset/customs.html");
    }

}
