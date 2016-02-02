package ec.edu.espol.integradora.dadtime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

/**
 * Created by user
 * on 16/01/2016.
 */
public class Entertainment implements Parcelable{

    private int idActivity;
    private String title;
    private String company;
    private String category;
    private String day;
    private String schedule;
    private String price;
    private String description;
    private int minimumAge;
    private Bitmap image;
    private boolean checked=false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Entertainment()
    {

    }

    protected Entertainment(Parcel in) {
        idActivity = in.readInt();
        title = in.readString();
        company = in.readString();
        category = in.readString();
        day = in.readString();
        schedule = in.readString();
        price = in.readString();
        description = in.readString();
        minimumAge = in.readInt();
        checked = in.readInt()==1;//CM
        byte[] imageAsBytes = new byte[in.readInt()];
        in.readByteArray(imageAsBytes);
        image = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static final Creator<Entertainment> CREATOR = new Creator<Entertainment>() {
        @Override
        public Entertainment createFromParcel(Parcel in) {
            return new Entertainment(in);
        }

        @Override
        public Entertainment[] newArray(int size) {
            return new Entertainment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idActivity);
        dest.writeString(title);
        dest.writeString(company);
        dest.writeString(category);
        dest.writeString(day);
        dest.writeString(schedule);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeInt(minimumAge);
        dest.writeInt(checked?1:0);//CM
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        dest.writeInt(byteArray.length);
        dest.writeByteArray(byteArray);
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        String str = "Entertaiment{\n";
        str +="\tid: "+idActivity+"\n";
        str +="\ttitle: "+title+"\n";
        str +="\tcompany: "+company+"\n";
        str +="\tcategory: "+category+"\n";
        str +="\tday: "+day+"\n";
        str +="\tschedule: "+schedule+"\n";
        str +="\tprice: "+price+"\n";
        str +="\tdescription: "+description+"\n";
        str +="\tminimumAge: "+minimumAge+"\n";
        str +="\tchecked: "+checked+"\n";
        str +="\timage: "+image+"\n";
        str +="}";
        return str;
    }
}
