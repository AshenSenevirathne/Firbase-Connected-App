package com.fired.notstudent.firebasek;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;

import Student.Student;

public class MainActivity extends Activity {

    EditText id, name, address, contact;
    Button save, show, update, delete, clear;
    DatabaseReference dbREf;
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact);

        save = findViewById(R.id.save);
        show = findViewById(R.id.show);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        clear = findViewById(R.id.clear);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearControls();
            }
        });

        dbREf = FirebaseDatabase.getInstance().getReference().child("Student");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student = new Student();
                student.setID(id.getText().toString());
                student.setName(name.getText().toString());
                student.setAddress(address.getText().toString());
                student.setContact(Integer.parseInt(contact.getText().toString()));
                dbREf.child(student.getID()).setValue(student);

                Toast.makeText(getApplicationContext(), "Data save Successfully", Toast.LENGTH_SHORT).show();
                clearControls();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredID = id.getText().toString();
                System.out.println(enteredID);
                dbREf = FirebaseDatabase.getInstance().getReference().child("Student").child(enteredID);
                dbREf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            id.setText(dataSnapshot.child("id").getValue().toString());
                            name.setText(dataSnapshot.child("name").getValue().toString());
                            address.setText(dataSnapshot.child("address").getValue().toString());
                            contact.setText(dataSnapshot.child("contact").getValue().toString());
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredID = id.getText().toString();
                dbREf = FirebaseDatabase.getInstance().getReference().child("Student");
                dbREf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(enteredID));
                            student = new Student();
                            student.setID(id.getText().toString());
                            student.setName(name.getText().toString().trim());
                            student.setAddress(address.getText().toString().trim());
                            student.setContact(Integer.parseInt(contact.getText().toString().trim()));
                            dbREf = FirebaseDatabase.getInstance().getReference().child("Student").child(student.getID());
                            dbREf.setValue(student);
                            Toast.makeText(getApplicationContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                            clearControls();

                        try {

                        }catch (NumberFormatException e){
                            Toast.makeText(getApplicationContext(), "Please Enter a valid Id", Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredID = id.getText().toString();
                dbREf = FirebaseDatabase.getInstance().getReference().child("Student");
                dbREf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(enteredID)){
                            dbREf = FirebaseDatabase.getInstance().getReference().child("Student").child(enteredID);
                            dbREf.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Data deleted Successfully", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(), "Please Enter a valid Id", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private void clearControls(){
        id.setText("");
        name.setText("");
        address.setText("");
        contact.setText("");
    }
}
