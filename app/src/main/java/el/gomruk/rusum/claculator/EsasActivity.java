package el.gomruk.rusum.claculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class EsasActivity extends Activity {

    Spinner mCountry, gCountry, tStatus;
    EditText valyuta, usdPrice, muherrikHecmi;
    Button btnCalc;
    private Customs customs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esas);

        mCountry = (Spinner) findViewById(R.id.mCountry);
        gCountry = (Spinner) findViewById(R.id.gCountry);
        tStatus = (Spinner) findViewById(R.id.tStatus);
        usdPrice = (EditText) findViewById(R.id.edUSDPrice);
        muherrikHecmi = (EditText) findViewById(R.id.edMuherrikHecmi);
        valyuta = (EditText) findViewById(R.id.valyuteRate);

        muherrikHecmi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCalc.setEnabled(!usdPrice.getText().toString().trim().isEmpty() && !muherrikHecmi.getText().toString().trim().isEmpty() && !valyuta.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        usdPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCalc.setEnabled(!usdPrice.getText().toString().trim().isEmpty() && !muherrikHecmi.getText().toString().trim().isEmpty() && !valyuta.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        valyuta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCalc.setEnabled(!usdPrice.getText().toString().trim().isEmpty() && !muherrikHecmi.getText().toString().trim().isEmpty() && !valyuta.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCalc = (Button) findViewById(R.id.btnCalc);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CaclCustoms();
                    Intent intent = new Intent(EsasActivity.this, ResultActivity.class);
                    intent.putExtra("position", customs.getPosition());
                    intent.putExtra("aksizout", customs.getAsksizVergi());
                    intent.putExtra("tutulan", customs.getMuherrikinIsciHecmi());
                    intent.putExtra("emeliyyat", customs.getEmeliyyatHaqqi());
                    intent.putExtra("edv", customs.getEDV());
                    intent.putExtra("vesiqe", customs.getVesiqeHaqqi());
                    intent.putExtra("total", customs.getTotal());
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(v.getContext(), "Hesablama zamanı səhv oldu.Bütün bölmələri doldurub yenidən yoxlayın.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        valyuta.setText(getUsdRate());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.county_array, R.layout.spinner_textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(adapter);
        gCountry.setAdapter(adapter);
        ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.spinner_textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tStatus.setAdapter(sadapter);

    }

    public double convertDouble(String value) {
        value = value.toString().replace(',', '.');
        return Double.parseDouble(value);
    }

    private void CaclCustoms() {
        double gr = 0;
        double dollar = 0;
        double muherrikhecmi = convertDouble(String.valueOf(muherrikHecmi.getText().toString().trim()));

        if (valyuta.getText().toString().trim().isEmpty())
           throw new IllegalArgumentException();
        else
            dollar = convertDouble(String.valueOf(valyuta.getText().toString().trim()));

        int Country1 = mCountry.getSelectedItemPosition();
        int Country2 = gCountry.getSelectedItemPosition();

        int status = tStatus.getSelectedItemPosition();
        String position = tStatus.getSelectedItem().toString().trim();
        double cvalyuta = convertDouble(String.valueOf(valyuta.getText().toString().trim()));
        double priceAZN = dollar * cvalyuta;

        if ((Country1 == Country2) & Country1 == 1 || Country1 == 2 || Country1 == 3 || Country1 == 4 || Country1 == 5 || Country1 == 6 || Country1 == 7 || Country1 == 8) {
            status = 2;
        } else {
            if (status == 0)
                gr = 0.4;
            if (status == 1)
                gr = 0.7;
        }
        int type = 0;


        if (muherrikhecmi > 5000)
            type = 5;
        if (muherrikhecmi > 4000 && muherrikhecmi <= 5000)
            type = 4;
        if (muherrikhecmi > 3000 && muherrikhecmi <= 4000)
            type = 3;
        if (muherrikhecmi > 2000 && muherrikhecmi <= 3000)
            type = 2;
        if (muherrikhecmi <= 2000)
            type = 1;


        int sbor = 0;

        if (priceAZN == 0)
            sbor = 0;
        if (priceAZN > 100000)
            sbor = 275;
        if (priceAZN <= 100000)
            sbor = 100;
        if (priceAZN <= 10000)
            sbor = 50;
        if (priceAZN <= 1000)
            sbor = 10;

        double aksizout = 0.0;

        switch (type) {
            case 0:
                aksizout = convertDouble(String.valueOf(13900 + (muherrikhecmi - 5000) * 10));
                break;
            case 1:
                aksizout = convertDouble(String.valueOf(muherrikhecmi * 0.20));
                break;
            case 2:
                aksizout = convertDouble(String.valueOf(400 + (muherrikhecmi - 2000) * 1.5));
                break;
            case 3:
                aksizout = convertDouble(String.valueOf(1900 + (muherrikhecmi - 3000) * 4));
                break;
            case 4:
                aksizout = convertDouble(String.valueOf(5900 + (muherrikhecmi - 4000) * 8));
                break;
            case 5:
                aksizout = 0;
                break;
            case 6:
                aksizout = 0;
                break;
        }

        DecimalFormat dformat = new DecimalFormat("##.##");
        customs = new Customs(position, Double.valueOf(convertDouble(dformat.format(aksizout))), Double.valueOf(convertDouble(dformat.format(muherrikhecmi * gr * cvalyuta))), sbor, 20, Double.valueOf(convertDouble(dformat.format((priceAZN + aksizout + (muherrikhecmi * gr * cvalyuta) + sbor + 20) * 0.18))), Double.valueOf(convertDouble(dformat.format(aksizout + (muherrikhecmi * gr * cvalyuta) + sbor + 20 + (priceAZN + aksizout + (muherrikhecmi * gr * cvalyuta) + sbor + 20) * 0.18))));

    }

    private String getUsdRate() {
        try {
            SimpleDateFormat ff = new SimpleDateFormat("dd.MM.yyyy");
            URL url = new URL("http://cbar.az/currencies/" + ff.format(new java.util.Date()) + ".xml");
            InputStream inputStream = url.openConnection().getInputStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Valute");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("Code").equals("USD")) {
                        return eElement.getElementsByTagName("Value").item(0).getTextContent();
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_esas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_about) {
            return true;
        }*/
        if (id == R.id.action_veten) {
            Intent i = new Intent(EsasActivity.this, VetenActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
