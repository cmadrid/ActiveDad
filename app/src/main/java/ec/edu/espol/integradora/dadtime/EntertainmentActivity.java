package ec.edu.espol.integradora.dadtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class EntertainmentActivity extends AppCompatActivity {

    ImageView ivImage;
    TextView tvTitle;
    TextView tvCompany;
    TextView tvCategory;
    TextView tvSchedule;
    TextView tvPrice;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        ivImage = (ImageView)findViewById(R.id.ivImage);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvCompany = (TextView)findViewById(R.id.tvCompany);
        tvCategory = (TextView)findViewById(R.id.tvCategory);
        tvSchedule = (TextView)findViewById(R.id.tvSchedule);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        Entertainment entertainment = getIntent().getParcelableExtra("Entertainment");
        ivImage.setImageBitmap(entertainment.getImage());
        tvTitle.setText(entertainment.getTitle());
        tvCompany.setText(entertainment.getCompany());
        tvCategory.setText(entertainment.getCategory());
        tvSchedule.setText(entertainment.getSchedule());
        tvPrice.setText(entertainment.getPrice());
        tvDescription.setText(entertainment.getDescription());
    }
}
