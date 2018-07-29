package com.marionageh.bakingapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marionageh.bakingapp.Adapters.FoodsAdapter;
import com.marionageh.bakingapp.Adapters.IngridentsAdapter;
import com.marionageh.bakingapp.Adapters.StepsAdapter;
import com.marionageh.bakingapp.CheckingUI;
import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Steps;
import com.marionageh.bakingapp.R;
import com.marionageh.bakingapp.Retrofit.ApiClient;
import com.marionageh.bakingapp.Retrofit.RetrofitApiInterface;
import com.marionageh.bakingapp.VideoActivity;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFoodsFragment extends Fragment implements StepsAdapter.ListItemClick, IngridentsAdapter.ListItemClickIngrients {
    //Adapteres
    StepsAdapter stepsAdapter;
    IngridentsAdapter ingridentsAdapter;
    //The Lists
    List<Steps> steps;
    //Recycler Views
    @BindView(R.id.recyler_steps)
    RecyclerView recyclerView_stpes;
    @BindView(R.id.recycler_ingrident)
    RecyclerView recyclerView_ingidents;
    FragmentManager fragmentManager ;
    //TextView For Tablet Mode
    @BindView(R.id.txt_video_f)
    TextView txt_descrption;

    public ViewFoodsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //ini views
        View view = inflater.inflate(R.layout.fragment_view_foods, container, false);
        ButterKnife.bind(this, view);
        //Recycler For Steps , Adapter ,Layoutmanger
        recyclerView_stpes.setHasFixedSize(true);
        stepsAdapter = new StepsAdapter(Constant.CONSTAT_STEPS, this);
        recyclerView_stpes.setAdapter(stepsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView_stpes.setLayoutManager(layoutManager);
        steps = Constant.CONSTAT_STEPS;


        //This Receyler Will disappera in Table Mode will CheckIit
        if (CheckingUI.GetWhichScreen(getContext()).equals(CheckingUI.Phone_Tablet)) {
            txt_descrption.setText(steps.get(0).getDescription());
            Constant.CONSTAT_Video_URl = steps.get(0).getVideoURL();
            if (!Constant.CONSTAT_Video_URl.equals("")) {
                VideoFragment fragment = new VideoFragment();
                fragmentManager= getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.video_frame, fragment).commit();
            } else {
             view.   findViewById(R.id.video_frame).setVisibility(View.GONE);
            }

        } else {
            //Recycler For Ingidetns , Adapter ,Layoutmanger
            recyclerView_ingidents.setHasFixedSize(true);
            ingridentsAdapter = new IngridentsAdapter(Constant.CONSTAT_Ingredients, this);
            recyclerView_ingidents.setAdapter(ingridentsAdapter);
            RecyclerView.LayoutManager layoutManagerin = new LinearLayoutManager(getContext());
            recyclerView_ingidents.setLayoutManager(layoutManagerin);
        }


        return view;
    }

    @Override
    public void OnItemClick(int Postion) {
        Constant.CONSTAT_Video_URl = steps.get(Postion).getVideoURL();
        Constant.Last_Step = Postion;
        Constant.Max_Steps = steps.size();
        if (CheckingUI.GetWhichScreen(getContext()).equals(CheckingUI.Phone_Tablet)) {
            VideoFragment fragment = new VideoFragment();
            fragmentManager.beginTransaction().replace(R.id.video_frame, fragment).commit();
            txt_descrption.setText(steps.get(Postion).getDescription());
        }else {
            startActivity(new Intent(getContext(), VideoActivity.class));
        }
    }

    @Override
    public void OnItemClickIN(int Postion) {

    }

}
