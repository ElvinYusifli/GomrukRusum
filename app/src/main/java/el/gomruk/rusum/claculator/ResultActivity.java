package el.gomruk.rusum.claculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class ResultActivity extends Activity {


    TextView lblAksiz, lblTutulan, lblEmeliyyatHaqqi, lblVesiqeHaqqi, lblEdv, lblTotal, lblPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.result_activity);

        lblAksiz = (TextView) findViewById(R.id.lbAksiz);
        lblTutulan = (TextView) findViewById(R.id.lblTutulan);
        lblEmeliyyatHaqqi = (TextView) findViewById(R.id.lblEmeliyyatHaqqi);
        lblVesiqeHaqqi = (TextView) findViewById(R.id.lblVesiqeHaqqi);
        lblEdv = (TextView) findViewById(R.id.lblEdv);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        lblPosition = (TextView) findViewById(R.id.lblPosition);
        if (!extras.isEmpty()) {
            lblPosition.setText(extras.getString("position") + " üzrə hesablama yekunları : ");
            lblAksiz.setText(String.valueOf(extras.getDouble("aksizout")) + " AZN");
            lblTutulan.setText(String.valueOf(extras.getDouble("tutulan")) + " AZN");
            lblEmeliyyatHaqqi.setText(String.valueOf(extras.getDouble("emeliyyat")) + " AZN");
            lblVesiqeHaqqi.setText(String.valueOf(extras.getDouble("vesiqe")) + " AZN");
            lblEdv.setText(String.valueOf(extras.getDouble("edv")) + " AZN");
            lblTotal.setText(String.valueOf(extras.getDouble("total")) + " AZN");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_veten) {
            Intent i = new Intent(ResultActivity.this, VetenActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
