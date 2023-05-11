package binus.ac.id.a2401955151_uasmobileprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    private TextInputEditText inputtedEmail;
    private Button btnSubmit;
    private TextView backToLoginPage;
    private FirebaseAuth firebaseAuth;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backToLoginPage = findViewById(R.id.backToLogin);
        inputtedEmail = findViewById(R.id.inputEmail);
        btnSubmit = findViewById(R.id.buttonForget);
        notificationManager = NotificationManagerCompat.from(this);

        firebaseAuth = firebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputtedEmail.getText().toString().trim();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "channelName";
                    String description = "channelDescription";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel("channelId", name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                if (email.isEmpty()){
                    inputtedEmail.setError("Email is required!");
                    inputtedEmail.requestFocus();
                }

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                Toast.makeText(ForgotPassword.this, "testing", Toast.LENGTH_SHORT).show();
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(ForgotPassword.this, "channelId")
                                        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                        .setContentTitle("Forget Password Notification")
                                        .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText("Sorry, we cannot give you your password since the password inside the firebase is ENCRYPTED by bcrypt hashing, but don't worry!\nWe have sent you a password reset link to your e-mail!"))
                                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                                // Show the notification
                                notificationManager.notify(1, builder.build());
                                Toast.makeText(ForgotPassword.this, "We sent you a password reset link to your email", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ForgotPassword.this, "Failed to send password reset email: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                });

            }
        });

        backToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}