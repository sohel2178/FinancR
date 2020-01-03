package com.forbitbd.financr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.ui.finance.FinanceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Project project = new Project();
        project.set_id("5dbe73b388262501774c4efa");
        project.setName("My Project");

        Button start = findViewById(R.id.start);
        final Project finalProject = project;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FinanceActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable(Constant.PROJECT, project);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}
