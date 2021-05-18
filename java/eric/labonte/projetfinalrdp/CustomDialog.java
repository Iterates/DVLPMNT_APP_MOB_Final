package eric.labonte.projetfinalrdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog extends Dialog {

    ImageView cdimageView;
    TextView cdtextView;
    Button cdButton;
    private Drawable bg;
    private String txt;
    private Bundle b;

    public CustomDialog(@NonNull Context context, Drawable bg, String txt) {
        super(context);
        this.bg = bg;
        this.txt = txt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        cdimageView = findViewById(R.id.cdimageView);
        cdtextView = findViewById(R.id.cdtextView);
        cdButton = findViewById(R.id.cdButton);

        cdimageView.setBackground(this.bg);
        cdtextView.setText(this.txt);

        cdButton.setOnClickListener(v -> {

        });
    }
}