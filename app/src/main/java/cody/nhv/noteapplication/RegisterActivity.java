package cody.nhv.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edtEmail, edtPassword;
    Button btnSignUp, btnBack;
    Database database = new Database (this, "noteApplication.sql", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Map variable to XML's component
        Mapping();
        //Insert data to SQLite file when user register
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get User Email from EditText
                String userEmail    = edtEmail.getText().toString();
                //Check if user have already existed or not
                if(isUserExist(userEmail))
                {
                    //if yes, pop up message
                    Toast.makeText(RegisterActivity.this, "User đã tồn tại.", Toast.LENGTH_LONG).show();
                }
                else{
                    //else get password and Register for user
                    String userPassword     = edtPassword.getText().toString();
                    Toast.makeText(RegisterActivity.this,
                            "Đăng ký thành công, chuyển về màn hình đăng nhập",
                            Toast.LENGTH_LONG).show();
                    database.QueryData("INSERT INTO User VALUES(" + null + ", '" + userEmail + "', '" + userPassword + "');");
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        //Get back to SignIn Activity when hit button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //Check if User exist or not
    private Boolean isUserExist(String userEmail)
    {
        //Get all user Email from table User
        Cursor userEmailList = database.GetData("SELECT User_email FROM User");

        while(userEmailList.moveToNext())
        {
            String userTemp = userEmailList.getString(0);
            //if there is any user email the same in database, return true
            if(userTemp.equals(userEmail))
                return true;
        }
        //else return false
        return false;
    }
    //Map variable to XML's component
    private void Mapping()
    {
        edtEmail        = (TextInputEditText) findViewById(R.id.edtEmail);
        edtPassword     = (TextInputEditText) findViewById(R.id.edtPassword);
        btnSignUp       = (Button) findViewById(R.id.btnSignUp);
        btnBack         = (Button) findViewById(R.id.btnBack);
    }
}