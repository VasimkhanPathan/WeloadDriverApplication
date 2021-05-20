package com.vasim.driverapplication.JobDescription;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.vasim.driverapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobDescriptionFragment extends Fragment {
    @BindView(R.id.tvViewImage)
    TextView tvViewImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_description, container, false);
        ButterKnife.bind(this,view);


        return view;
    }
    @OnClick(R.id.tvViewImage)
    public void viewImage(){
        final Dialog dialog = new Dialog(JobDescriptionFragment.this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_image_view);
        dialog.setCancelable(false);
        ImageView imageView = dialog.findViewById(R.id.ivImage);
        ImageView ivCloseDialog = dialog.findViewById(R.id.ivImageClose);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}