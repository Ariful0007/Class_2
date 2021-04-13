package com.example.class_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Searching extends AppCompatActivity {
    EditText data;
    Button submit;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        firebaseFirestore=FirebaseFirestore.getInstance();

        data=findViewById(R.id.email);
        submit=findViewById(R.id.login);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data1=data.getText().toString();
                if (TextUtils.isEmpty(data1)) {
                    data.setError("No Data Found");
                    return;
                }
                else {

                    firebaseFirestore.collection("Test1").document(data1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                           firebaseFirestore.collection("Test1").document(data1).delete();
                                            String get_data=task.getResult().getString("input1");
                                            Toast.makeText(Searching.this, ""+get_data, Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(Searching.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Searching.this, "No Data Found : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    }
