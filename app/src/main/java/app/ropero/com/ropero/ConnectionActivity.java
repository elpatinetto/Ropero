package app.ropero.com.ropero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ConnectionActivity extends AppCompatActivity {

    private Button btninsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        btninsc = (Button) findViewById(R.id.btn_inscr);

        btninsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnectionActivity.this, RoperoMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
