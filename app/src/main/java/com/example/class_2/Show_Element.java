package com.example.class_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Show_Element extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;

    RecyclerView recyclerView;
    Adapter getDataAdapter1;
    List<Data_Model> getList;
    DocumentReference documentReference;



    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__element);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.recycler);
        getList=new ArrayList<>();
        documentReference=firebaseFirestore.collection("Test1").document();
        getDataAdapter1=new Adapter(getList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();

    }

    private void reciveData() {
        firebaseFirestore.collection("Test1").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds:queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType()== DocumentChange.Type.ADDED) {
                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Data_Model get=ds.getDocument().toObject(Data_Model.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}