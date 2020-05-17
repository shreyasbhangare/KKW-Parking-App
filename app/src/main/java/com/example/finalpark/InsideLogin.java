package com.example.finalpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsideLogin extends AppCompatActivity {

    private TextView welcome;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String userID;
    private RadioButton rbtwo;
    private RadioButton rbfour;
    private TextView showDetails;
    private Button checkin;
    private Button checkout;
    private RadioButton insideM;
    private RadioButton outsideM;
    private RadioButton insideF;
    private TextView tvPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_login);
        welcome=(TextView)findViewById(R.id.tvInside);
        rbtwo=(RadioButton)findViewById(R.id.rbtnTwo);
        rbfour=(RadioButton)findViewById(R.id.rbtnFour);
        showDetails=(TextView)findViewById(R.id.tvPark);
        checkin=(Button)findViewById(R.id.btnCheckin);
        checkout=(Button)findViewById(R.id.btnCheckout);
        insideF=(RadioButton) findViewById(R.id.rbtnInsideF);
        outsideM=(RadioButton)findViewById(R.id.rbtnOutsideM);
        insideM=(RadioButton)findViewById(R.id.rbtnInsideM);
        tvPP=(TextView)findViewById(R.id.tvParkSpace);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("People");

        FirebaseUser user=firebaseAuth.getCurrentUser();
        assert user != null;
        userID=user.getUid();

        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    final People p = dataSnapshot.getValue(People.class);
                    String s=p.getS_Name();
                    welcome.setText("Welcome "+s);
                    s=p.getS_gender();
                    final String t=p.getS_prn();
                    String status=p.getS_parked();
                    char ch=t.charAt(0);
                    if(s.equals("Female")&&(ch=='s'||ch=='S'))
                    {
                        rbtwo.setChecked(true);
                        insideF.setChecked(true);
                        insideM.setEnabled(false);
                        outsideM.setEnabled(false);
                        rbfour.setEnabled(false);
                        if(status.equals(""))
                        {
                            checkout.setEnabled(false);
                            //while (true) {
                                checkin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FemaleInside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z = getEmpty("FemaleInside",x)+"-Female Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                });
                                checkout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String tmp=showDetails.getText().toString().substring(10);
                                        showDetails.setText("");
                                        updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                        cancel(tmp,"FemaleInside");
                                        checkin.setEnabled(true);
                                        checkout.setEnabled(false);
                                    }
                                });
                            //}
                        }
                        else if(!status.equals(""))
                        {
                            showDetails.setText("Location: " + status);

                            checkin.setEnabled(false);
                            //while (true) {
                                checkout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String tmp=showDetails.getText().toString().substring(10);
                                        showDetails.setText("");
                                        updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                        cancel(tmp,"FemaleInside");
                                        checkin.setEnabled(true);
                                        checkout.setEnabled(false);
                                    }
                                });
                                checkin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FemaleInside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z = getEmpty("FemaleInside",x)+"-Female Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });

                            //}
                        }

                    }
                    else if(s.equals("Male")&&(ch=='s'||ch=='S'))
                    {
                        rbtwo.setChecked(true);
                        insideF.setEnabled(false);
                        rbfour.setEnabled(false);
                        //String z=getEmpty(a);
                        //showDetails.setText("Location: "+z);
                        if(status.equals(""))
                        {
                            checkout.setEnabled(false);
                            //while (true) {
                            checkin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(insideM.isChecked())
                                    {
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleInside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z = getEmpty("MaleInside",x)+"-Male Inside Parking";
                                                outsideM.setEnabled(false);
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else if(outsideM.isChecked())
                                    {
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleOutside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z =getEmpty("MaleOutside",x)+"-Male Outside Parking";
                                                insideM.setEnabled(false);
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                    else
                                    {

                                        tvPP.setError("Select Proper Parking Space");
                                        //checkin.setEnabled(true);
                                    }


                                }
                            });
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tmp=showDetails.getText().toString().substring(10);
                                    showDetails.setText("");
                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                    if(insideM.isChecked())
                                    {
                                        cancel(tmp,"MaleInside");
                                        outsideM.setEnabled(true);
                                    }
                                    else if(outsideM.isChecked())
                                    {
                                        cancel(tmp,"MaleOutside");
                                        insideM.setEnabled(true);
                                    }
                                    checkin.setEnabled(true);
                                    checkout.setEnabled(false);
                                }
                            });
                            //}
                        }
                        else if(!status.equals(""))
                        {
                            showDetails.setText("Location: " + status);
                            int ss=status.indexOf('-');
                            if(status.substring(ss).equals("-Male Outside Parking"))
                            {
                                insideM.setChecked(false);
                                insideM.setEnabled(false);
                                outsideM.setChecked(true);
                            }
                            if(status.substring(ss).equals("-Male Inside Parking"))
                            {
                                insideM.setChecked(true);
                                outsideM.setChecked(false);
                                outsideM.setEnabled(false);
                            }
                            checkin.setEnabled(false);
                            //while (true) {
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tmp=showDetails.getText().toString().substring(10);
                                    showDetails.setText("");
                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                    //cancel(tmp,a);
                                    if(insideM.isChecked())
                                    {
                                        cancel(tmp,"MaleInside");
                                        outsideM.setEnabled(true);
                                    }
                                    else if(outsideM.isChecked())
                                    {
                                        cancel(tmp,"MaleOutside");
                                        insideM.setEnabled(true);
                                    }
                                    checkin.setEnabled(true);
                                    checkout.setEnabled(false);
                                }
                            });
                            checkin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //String z = getEmpty(a);

                                    if(insideM.isChecked())
                                    {
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleInside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z = getEmpty("MaleInside",x)+"-Male Inside Parking";
                                                outsideM.setEnabled(false);
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else if(outsideM.isChecked())
                                    {
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleOutside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z =getEmpty("MaleOutside",x)+"-Male Outside Parking";
                                                insideM.setEnabled(false);
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else
                                    {
                                        tvPP.setError("Select Proper Parking Space");
                                        //checkin.setEnabled(true);
                                    }



                                }
                            });

                            //}
                        }

                    }

                    else if(s.equals("Male")&&(ch=='t'||ch=='T'))
                    {
                        insideF.setEnabled(false);
                        if(status.equals(""))
                        {
                            checkout.setEnabled(false);
                            //while (true) {
                            checkin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //String z="";
                                    if(rbtwo.isChecked())
                                    {
                                        rbfour.setEnabled(false);
                                        if (insideM.isChecked())
                                        {
                                            progressDialog.setMessage("Checking in..");
                                            progressDialog.show();
                                            DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleInside");
                                            gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String area=dataSnapshot.getValue(String.class);
                                                    int [][]x=new int[5][5];
                                                    x=decode(area);
                                                    String z = getEmpty("MaleInside",x)+"-Male Inside Parking";
                                                    outsideM.setEnabled(false);
                                                    progressDialog.dismiss();
                                                    showDetails.setText("Location: " + z);
                                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                    checkin.setEnabled(false);
                                                    checkout.setEnabled(true);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else if (outsideM.isChecked())

                                        {
                                            progressDialog.setMessage("Checking in..");
                                            progressDialog.show();
                                            DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleOutside");
                                            gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String area=dataSnapshot.getValue(String.class);
                                                    int [][]x=new int[5][5];
                                                    x=decode(area);
                                                    String z =getEmpty("MaleOutside",x)+"-Male Outside Parking";
                                                    insideM.setEnabled(false);
                                                    progressDialog.dismiss();
                                                    showDetails.setText("Location: " + z);
                                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                    checkin.setEnabled(false);
                                                    checkout.setEnabled(true);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        rbtwo.setEnabled(false);
                                        outsideM.setEnabled(false);
                                        insideM.setEnabled(false);
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FourWheeler");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int d[]=new int[10];
                                                d=fromString(area);
                                                String z=get4Empty(d,"FourWheeler")+"-Four Wheeler Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else
                                    {
                                        tvPP.setError("Select Proper Parking Space");
                                    }


                                }
                            });
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tmp=showDetails.getText().toString().substring(10);
                                    showDetails.setText("");
                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                    if(rbtwo.isChecked())
                                    {
                                        if (insideM.isChecked()) {
                                            cancel(tmp, "MaleInside");
                                            outsideM.setEnabled(true);
                                        } else if (outsideM.isChecked()) {
                                            cancel(tmp, "MaleOutside");
                                            insideM.setEnabled(true);
                                        }
                                        rbfour.setEnabled(true);
                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        cancel4(tmp, "FourWheeler");
                                        insideM.setEnabled(true);
                                        outsideM.setEnabled(true);
                                        rbtwo.setEnabled(true);
                                    }
                                    checkin.setEnabled(true);
                                    checkout.setEnabled(false);
                                }
                            });
                            //}
                        }
                        else if(!status.equals(""))
                        {
                            showDetails.setText("Location: " + status);
                            int ss=status.indexOf('-');
                            if(status.substring(ss).equals("-Male Outside Parking"))
                            {
                                insideM.setChecked(false);
                                insideM.setEnabled(false);
                                outsideM.setChecked(true);
                                rbtwo.setChecked(true);
                                rbfour.setEnabled(false);
                            }
                            if(status.substring(ss).equals("-Male Inside Parking"))
                            {
                                rbtwo.setChecked(true);
                                rbfour.setEnabled(false);
                                insideM.setChecked(true);
                                outsideM.setChecked(false);
                                outsideM.setEnabled(false);
                            }
                            if(status.substring(ss).equals("-Four Wheeler Parking"))
                            {
                                rbtwo.setEnabled(false);
                                rbfour.setChecked(true);
                                outsideM.setEnabled(false);
                                insideM.setEnabled(false);
                            }
                                checkin.setEnabled(false);
                            //while (true) {
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tmp=showDetails.getText().toString().substring(10);
                                    showDetails.setText("");
                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                    //cancel(tmp,a);
                                    if(rbtwo.isChecked())
                                    {
                                        if (insideM.isChecked()) {
                                            cancel(tmp, "MaleInside");
                                            outsideM.setEnabled(true);
                                        } else if (outsideM.isChecked()) {
                                            cancel(tmp, "MaleOutside");
                                            insideM.setEnabled(true);
                                        }
                                        rbfour.setEnabled(true);
                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        cancel4(tmp, "FourWheeler");
                                        insideM.setEnabled(true);
                                        outsideM.setEnabled(true);
                                        rbtwo.setEnabled(true);
                                    }
                                    checkin.setEnabled(true);
                                    checkout.setEnabled(false);
                                }
                            });
                            checkin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //String z = getEmpty(a);

                                    if(rbtwo.isChecked())
                                    {
                                        rbfour.setEnabled(false);
                                        if (insideM.isChecked())
                                        {
                                            progressDialog.setMessage("Checking in..");
                                            progressDialog.show();
                                            DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleInside");
                                            gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String area=dataSnapshot.getValue(String.class);
                                                    int [][]x=new int[5][5];
                                                    x=decode(area);
                                                    String z = getEmpty("MaleInside",x)+"-Male Inside Parking";
                                                    outsideM.setEnabled(false);
                                                    progressDialog.dismiss();
                                                    showDetails.setText("Location: " + z);
                                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                    checkin.setEnabled(false);
                                                    checkout.setEnabled(true);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                        else if (outsideM.isChecked())

                                        {
                                            progressDialog.setMessage("Checking in..");
                                            progressDialog.show();
                                            DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("MaleOutside");
                                            gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String area=dataSnapshot.getValue(String.class);
                                                    int [][]x=new int[5][5];
                                                    x=decode(area);
                                                    String z =getEmpty("MaleOutside",x)+"-Male Outside Parking";
                                                    insideM.setEnabled(false);
                                                    progressDialog.dismiss();
                                                    showDetails.setText("Location: " + z);
                                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                    checkin.setEnabled(false);
                                                    checkout.setEnabled(true);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        rbtwo.setEnabled(false);
                                        outsideM.setEnabled(false);
                                        insideM.setEnabled(false);
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FourWheeler");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int d[]=new int[10];
                                                d=fromString(area);
                                                String z=get4Empty(d,"FourWheeler")+"-Four Wheeler Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else
                                    {
                                        tvPP.setError("Select Proper Parking Space");

                                    }



                                }
                            });

                            //}
                        }





                    }
                    else if(s.equals("Female")&&(ch=='t'||ch=='T'))
                    {
                        insideM.setEnabled(false);
                        outsideM.setEnabled(false);
                        if(status.equals(""))
                        {
                            checkout.setEnabled(false);
                            //while (true) {
                            checkin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String z="";
                                    if(rbtwo.isChecked()) {
                                        insideF.setChecked(true);
                                        rbfour.setEnabled(false);
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FemaleInside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z = getEmpty("FemaleInside",x)+"-Female Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        insideF.setEnabled(false);
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FourWheeler");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int d[]=new int[10];
                                                d=fromString(area);
                                                String z=get4Empty(d,"FourWheeler")+"-Four Wheeler Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                    else
                                    {
                                        tvPP.setError("Select Proper Parking Space");
                                    }



                                }
                            });
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tmp=showDetails.getText().toString().substring(10);
                                    showDetails.setText("");
                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                    //cancel(tmp,a);

                                    if(rbtwo.isChecked())
                                    {
                                        cancel(tmp, "FemaleInside");
                                        rbfour.setEnabled(true);
                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        cancel4(tmp, "FourWheeler");
                                        rbtwo.setEnabled(true);
                                    }

                                    checkin.setEnabled(true);
                                    checkout.setEnabled(false);
                                }
                            });
                            //}
                        }
                        else if(!status.equals(""))
                        {
                            showDetails.setText("Location: " + status);
                            int ss=status.indexOf('-');
                            if(status.substring(ss).equals("-Female Parking"))
                            {
                                rbtwo.setChecked(true);
                                insideF.setChecked(true);
                                rbfour.setEnabled(false);
                            }
                            if(status.substring(ss).equals("-Four Wheeler Parking"))
                            {
                                rbfour.setChecked(true);
                                rbtwo.setEnabled(false);
                                insideF.setEnabled(false);
                            }

                            checkin.setEnabled(false);
                            //while (true) {
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tmp=showDetails.getText().toString().substring(10);
                                    showDetails.setText("");
                                    updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), "");
                                    //cancel(tmp,a);
                                    if(rbtwo.isChecked())
                                    {
                                        cancel(tmp, "FemaleInside");
                                        rbfour.setEnabled(true);
                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        cancel4(tmp, "FourWheeler");
                                        rbtwo.setEnabled(true);
                                        insideF.setEnabled(true);
                                    }
                                    checkin.setEnabled(true);
                                    checkout.setEnabled(false);
                                }
                            });
                            checkin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String z ="";
                                    if(rbtwo.isChecked()) {
                                        insideF.setChecked(true);
                                        rbfour.setEnabled(false);
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FemaleInside");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int [][]x=new int[5][5];
                                                x=decode(area);
                                                String z = getEmpty("FemaleInside",x)+"-Female Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else if(rbfour.isChecked())
                                    {
                                        insideF.setEnabled(false);
                                        progressDialog.setMessage("Checking in..");
                                        progressDialog.show();
                                        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child("FourWheeler");
                                        gg.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String area=dataSnapshot.getValue(String.class);
                                                int d[]=new int[10];
                                                d=fromString(area);
                                                String z=get4Empty(d,"FourWheeler")+"-Four Wheeler Parking";
                                                progressDialog.dismiss();
                                                showDetails.setText("Location: " + z);
                                                updateStatus(userID, p.getS_Name(), p.getS_gender(), p.getS_email(), p.getS_password(), p.getS_prn(), z);
                                                checkin.setEnabled(false);
                                                checkout.setEnabled(true);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                    else
                                    {
                                        tvPP.setError("Select Proper Parking Space");

                                    }


                                }
                            });

                            //}
                        }

                    }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(InsideLogin.this,MainActivity.class));


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit KKW PARK")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //super.onBackPressed();
                        //Or used finish();
                        finishAffinity();
                        System.exit(0);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.aboutus:{
                startActivity(new Intent(InsideLogin.this,AboutUs.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private static int[] fromString(String string) {
        String[] strings = string.replace("[", "").replace("]", "").split(", ");
        int result[] = new int[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }


    static public int[][] decode(String temp)
    {
        String[] strings = temp.replace("[", "").replace("]", ">").split(", ");
        List<String> stringList = new ArrayList<>();
        List<String[]> tempResult = new ArrayList<>();
        for(String str : strings) {
            if(str.endsWith(">")) {
                str = str.substring(0, str.length() - 1);
                if(str.endsWith(">")) {
                    str = str.substring(0, str.length() - 1);
                }
                stringList.add(str);
                tempResult.add(stringList.toArray(new String[stringList.size()]));
                stringList = new ArrayList<>();
            } else {
                stringList.add(str);
            }
        }
        String[][] originalArray = tempResult.toArray(new String[tempResult.size()][]);
        int[][] rarray =new int[5][5];
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                int pp=Integer.parseInt(originalArray[i][j]);           
                
                rarray[i][j]=pp;
            }

        }
        return rarray;
    }

    static private String getEmpty(final String type,int x[][])
    {

        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child(type);

        String s_i,s_j,s_res ="";;
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                if(x[i][j]==0)
                {
                    s_i=Integer.toString(i);
                    s_j=Integer.toString(j);
                    x[i][j]=1;
                    s_res=s_i+" "+s_j;
                    String temp = Arrays.deepToString(x);
                    //DatabaseReference nt=FirebaseDatabase.getInstance().getReference().child(type);
                    gg.setValue(temp);
                    return s_res;

                }
            }
        }

        return "NO SPACE REMAINS";

    }

    static private void cancel(final String sts, final String type)
    {
        final DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child(type);
        gg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String area=dataSnapshot.getValue(String.class);
                int [][]ttt=new int[5][5];
                ttt=decode(area);
                int temp=sts.indexOf(' ');
                int temp2=sts.indexOf('-');
                String s_i=sts.substring(0,temp);
                String s_j=sts.substring(temp+1,temp2);

                int i=Integer.parseInt(s_i);

                int y=Integer.parseInt(s_j);

                ttt[i][y]=0;
                String revert = Arrays.deepToString(ttt);
               // DatabaseReference nt=FirebaseDatabase.getInstance().getReference().child(type);
                gg.setValue(revert);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    static private void cancel4(final String sts, String type)
    {
        final DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child(type);
        gg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String area=dataSnapshot.getValue(String.class);
                int ttt[]=new int[10];
                ttt=fromString(area);
                int temp=sts.indexOf('-');
                String s_i=sts.substring(0,temp);
                int x=Integer.parseInt(s_i);
                ttt[x]=0;
                String revert = Arrays.toString(ttt);
                // DatabaseReference nt=FirebaseDatabase.getInstance().getReference().child(type);
                gg.setValue(revert);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    static  private String get4Empty(int a[],String type)
    {
        DatabaseReference gg = FirebaseDatabase.getInstance().getReference().child(type);
        for(int i=0;i<10;i++)
        {
            if(a[i]==0)
            {
                a[i]=1;
                String s_i=Integer.toString(i);
                String temp = Arrays.toString(a);
                //DatabaseReference nt=FirebaseDatabase.getInstance().getReference().child(type);
                gg.setValue(temp);
                return s_i;
            }
        }
        return "NO EMPTY SAPCE";
    }



    private void updateStatus(String ID,String n,String g,String e,String p,String pr,String sts)
    {
        DatabaseReference dref=FirebaseDatabase.getInstance().getReference("People").child(ID);
        People pp=new People(n,pr,e,g,p,sts);
        dref.setValue(pp);
    }

}
