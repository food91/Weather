package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xiekun.myapplication.R;

import data.MycareViewModel;


public class MycareFragment extends Fragment {

    private MycareViewModel mycareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mycareViewModel =
                ViewModelProviders.of(this).get(MycareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycare, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        mycareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}