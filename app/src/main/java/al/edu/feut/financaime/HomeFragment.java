package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import al.edu.feut.financaime.util.Utilities;

public class HomeFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatTextView date = view.findViewById(R.id.date);
        date.setText(Utilities.format(calendar));
        view.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Utilities.monthIncrementAndDecrement(calendar,-1);
                date.setText(Utilities.format(calendar));
            }
        });
        view.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Utilities.monthIncrementAndDecrement(calendar,+1);
                date.setText(Utilities.format(calendar));
            }
        });
    }
}
