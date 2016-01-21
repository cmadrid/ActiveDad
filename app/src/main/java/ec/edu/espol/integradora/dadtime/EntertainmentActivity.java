package ec.edu.espol.integradora.dadtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EntertainmentActivity extends AppCompatActivity {

    ImageView ivImage;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        ivImage = (ImageView)findViewById(R.id.ivImage);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        Entertainment entertainment = getIntent().getParcelableExtra("Entertainment");
        ivImage.setImageBitmap(entertainment.getImage());
        tvTitle.setText(entertainment.getTitle());
    }
}
