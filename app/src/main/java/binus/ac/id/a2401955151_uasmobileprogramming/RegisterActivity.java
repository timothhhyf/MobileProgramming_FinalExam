package binus.ac.id.a2401955151_uasmobileprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText registeredUsername, registeredName, registeredPhone, registeredAddress, registeredEmail, registeredPassword;
    private Button btnRegister;
    private TextView goToLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = firebaseAuth.getInstance();

        registeredName = findViewById(R.id.registerName);
        registeredUsername = findViewById(R.id.registerUsername);
        registeredPhone = findViewById(R.id.registerPhone);
        registeredAddress = findViewById(R.id.registerAddress);
        registeredEmail = findViewById(R.id.registerEmail);
        registeredPassword = findViewById(R.id.registerPassword);

        btnRegister = findViewById(R.id.buttonRegister);
        goToLogin = findViewById(R.id.goToLoginPage);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String name = registeredName.getText().toString().trim();
        String username = registeredUsername.getText().toString().trim();
        String phone = registeredPhone.getText().toString().trim();
        String address = registeredAddress.getText().toString().trim();
        String email = registeredEmail.getText().toString().trim();
        String password = registeredPassword.getText().toString().trim();

        if (name.isEmpty()){
            registeredName.setError("Name is required");
            registeredName.requestFocus();
            return;
        }

        if (username.isEmpty()){
            registeredUsername.setError("Username is required");
            registeredUsername.requestFocus();
            return;
        }

        if (username.length() < 6){
            registeredUsername.setError("Username length must be more than 6");
            registeredUsername.requestFocus();
            return;
        }

        if (email.isEmpty()){
            registeredEmail.setError("E-mail is required");
            registeredEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registeredEmail.setError("Fill valid E-mail");
            registeredEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            registeredPassword.setError("Password is required");
            registeredPassword.requestFocus();
            return;
        }

        if (password.length() < 8){
            registeredPassword.setError("Password length must be more than 8");
            registeredPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()){
            registeredPhone.setError("Phone number must be filled");
            registeredPhone.requestFocus();
            return;
        }

        if (address.isEmpty()){
            registeredAddress.setError("Address must be filled");
            registeredAddress.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("username", username);
                    user.put("phone", phone);
                    user.put("address", address);
                    user.put("email", email);

                    FirebaseDatabase.getInstance("https://uasmobprogramming-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("person").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();

                                try {
                                final String senderEmail="noreply.timfinalexammoprog@gmail.com";
                                final String senderPassword="##############[prevent leaked]";

                                Properties props = System.getProperties();
                                props.put("mail.smtp.auth", "true");
                                props.put("mail.smtp.starttls.enable", "true");
                                props.put("mail.smtp.host", "smtp.gmail.com");
                                props.put("mail.smtp.port", "587");

                                javax.mail.Session session = Session.getInstance(props,
                                        new Authenticator(){
                                            @Override
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(senderEmail, senderPassword);
                                            }
                                        });

                                MimeMessage mimeMessage = new MimeMessage(session);

                                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                                    mimeMessage.setSubject("Welcome to the application!");
                                    mimeMessage.setText("Dear " + name +",\nWelcome to our application, your account\nUsername : " + username + "\nPassword : " + password + "\nThank you\n");

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Transport.send(mimeMessage);
                                            } catch (MessagingException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();

                                } catch (AddressException e) {
                                    e.printStackTrace();
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }

                                Intent backToLogin = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(backToLogin);
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Register Failed!\nEmail is already registered!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}