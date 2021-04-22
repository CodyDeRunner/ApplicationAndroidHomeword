package cody.nhv.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    public Database database = new Database (this, "noteApplication.sql", null, 1);;
    TextInputEditText edtEmail, edtPassword;
    CheckBox cbRememberMe;
    Button btnSignin, btnExit;
    FloatingActionButton btnRegister;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create Sqlite Database named noteApplication and add data into it
        //Create Sharedpreferences
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        //Mapping to xml file
        Mapping();
        //Construct database for testing
        //ConstructDatabase();

        //Get values from Sharepreferences
        edtEmail.setText(sharedPreferences.getString("user", ""));
        edtPassword.setText(sharedPreferences.getString("password", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean("checked",false))  ;
        //set on click for Sign in
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get values from Edit Text
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                //Check USer and Password are correct
                if(CheckUser(email, password)>=0)
                {
                    int userId = CheckUser(email, password);
                    //Go to Note Activity and seng UserId
                    Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                    intent.putExtra("UserId",userId );

                    //Set preferences when checkbox is checked
                    if(cbRememberMe.isChecked())
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user", email);
                        editor.putString("password", password);
                        editor.putBoolean("checked", true);
                        editor.commit();
                    }
                    else
                    {
                        //Remove sharepreferences when checkbox is unchecked
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("user");
                        editor.remove("password");
                        editor.remove("checked");
                        editor.commit();
                    }
                    startActivity(intent);

                }
                else if(CheckUser(email, password)==-1)
                {
                    Toast.makeText(MainActivity.this, "Mật khẩu chưa chính xác", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Không tìm thấy User", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Move to RegisterActivity when click on button register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    //Mapping to xml file
    public  void Mapping()
    {
        edtEmail        = (TextInputEditText) findViewById(R.id.edtEmail);
        edtPassword     = (TextInputEditText) findViewById(R.id.edtPassword);
        cbRememberMe    = (CheckBox) findViewById(R.id.cbRememberMe);
        btnSignin       = (Button) findViewById(R.id.btnSignIn);
        btnExit         = (Button)   findViewById(R.id.btnExit);
        btnRegister     = (FloatingActionButton) findViewById(R.id.btnRegister);
    }
    //return 1: email and password are correct, 2:email correct but wrong pass, 3: can not find email
    public int CheckUser(String userName, String password)
    {
        Cursor dataUser =   database.GetData("Select * from User");
        int condition=-2;
        //Check all User userName and passWord
        while(dataUser.moveToNext())
        {
            String email = dataUser.getString(1).toString();
            String pass = dataUser.getString(2).toString();
            int userId = dataUser.getInt(0);
            Log.i("UserID", String.valueOf(email));
            if(email.equals(userName)) {
                if(pass.equals(password))
                    //Return userId if userName and passWord are correct
                   return userId;
                else
                    //Return -1 if userName is right but wrong password
                    condition=-1;
            }
        }
        //Return -2 if userName is wrong
        return condition;
    }
    //This field is for creating data for Testing
    public void ConstructDatabase()
    {
        database.QueryData("CREATE table if not EXISTS User(User_id integer PRIMARY key AUTOINCREMENT,\n" +
                "                                User_email text,\n" +
                "                                User_password text);");
        database.QueryData("Insert into User values(null, 'admin', '123456a@');");
        database.QueryData("Insert into User values(null, 'user', '123456a@');");
        database.QueryData("CREATE TABLE IF not EXISTS Note(Note_id integer PRIMARY key AUTOINCREMENT,\n" +
                "                                Note_name varchar(127),\n" +
                "                \t\t\t\tNote_description Varchar(255),\n" +
                "                \t\t\t\tNote_date text,\n" +
                "                               \tUser_id integer,\n" +
                "                                FOREIGN key (User_id) REFERENCES User On Update CASCADE);");
        database.QueryData("Insert into Note values (NULL,'Lam Bai Tap'," +
            "'Lam bai tap chuong III DSTT', " +
            "DATEtime('now'), 1);");
        database.QueryData("Insert into Note values (NULL,'Du hoi thao'," +
                "'Hoi thao ky nang mem cho sinh vien', " +
                "DATEtime('now'), 1);");
        database.QueryData("Insert into Note values (NULL,'Nop bai tap'," +
                "'Nop Bai tap mon Lap Trinh Di Dong', " +
                "DATEtime('now'), 2);");
    }
}