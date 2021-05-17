package eric.labonte.projetfinalrdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Vector;

public class ArrayAdapterPerso extends ArrayAdapter<Defi> {
    public ArrayAdapterPerso(@NonNull Context context, Vector<Defi> liste) {
        super(context,0, liste);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Defi defi = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.un_item, parent, false);
        }

        TextView texte = (TextView) convertView.findViewById(R.id.texteUnItem);
        CheckBox check = (CheckBox) convertView.findViewById(R.id.checkBox);

        texte.setText(defi.getTheme());
        check.setChecked(defi.isReussi());

        return convertView;
    }
}
