package com.forbitbd.financr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.ui.newFinance.NewFinanceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Project project = new Project();
        project.set_id("5f87633670279032e9ffdf24");
        project.setName("My Project");

        Button start = findViewById(R.id.start);
        final Project finalProject = project;
        final SharedProject sharedProject = new SharedProject(project);
        sharedProject.getFinance().setWrite(true);
        sharedProject.getFinance().setUpdate(true);
        sharedProject.getFinance().setDelete(true);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewFinanceActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable(Constant.PROJECT, sharedProject);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}
