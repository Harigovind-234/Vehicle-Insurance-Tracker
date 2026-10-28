
package com.example.vehicle_insurance_tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText vehicleNumber, ownerName, insuranceCompany;
    EditText policyNumber, expiryDate;

    TextView txtInsuranceRecords;
    Button btnAddInsurance, btnShowInsurance;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vehicleNumber = findViewById(R.id.vehicleNumber);
        ownerName = findViewById(R.id.ownerName);
        insuranceCompany = findViewById(R.id.insuranceCompany);
        policyNumber = findViewById(R.id.policyNumber);
        expiryDate = findViewById(R.id.expiryDate);

        btnAddInsurance = findViewById(R.id.btnAddInsurance);
        btnShowInsurance = findViewById(R.id.btnShowInsurance);

        txtInsuranceRecords = findViewById(R.id.txtInsuranceRecords);

        dbHelper = new DatabaseHelper(this);
    }

    public void insert(View view) {

        String vehicle = vehicleNumber.getText().toString().trim();
        String owner = ownerName.getText().toString().trim();
        String company = insuranceCompany.getText().toString().trim();
        String policy = policyNumber.getText().toString().trim();
        String expiry = expiryDate.getText().toString().trim();

        if (vehicle.isEmpty() || owner.isEmpty()
                || company.isEmpty() || policy.isEmpty()
                || expiry.isEmpty()) {

            Toast.makeText(this, "Please enter all details",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = dbHelper.addInsurance(
                vehicle, owner, company, policy, expiry);

        if (result) {

            Toast.makeText(this, "Insurance details added successfully",
                    Toast.LENGTH_SHORT).show();

            vehicleNumber.setText("");
            ownerName.setText("");
            insuranceCompany.setText("");
            policyNumber.setText("");
            expiryDate.setText("");

        } else {

            Toast.makeText(this, "Failed to add insurance details",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void show(View view) {

        String insurance = dbHelper.getAllInsurance();
        txtInsuranceRecords.setText(insurance);
    }
}
