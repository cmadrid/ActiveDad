package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileThirdActivity extends AppCompatActivity {

    private static Activity activity;
    private Button btnFinalize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_third);
        this.activity = this;
        btnFinalize = (Button)findViewById(R.id.btnFinalize);
        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileThirdActivity.this, MainActivity.class);
                startActivity(intent);
                if(ProfileFirstActivity.activity != null)
                {
                    ProfileFirstActivity.activity.finish();
                }
                if(ProfileSecondActivity.activity != null)
                {
                    ProfileSecondActivity.activity.finish();
                }
                finish();
            }
        });
    }
}
