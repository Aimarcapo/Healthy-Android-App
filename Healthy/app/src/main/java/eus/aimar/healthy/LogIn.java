package eus.aimar.healthy;


import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eus.aimar.healthy.Model.Medico;
import eus.aimar.healthy.ViewModel.MedicoViewModel;
import eus.aimar.healthy.databinding.LoginBinding;
import kotlin.text.Charsets;


public class LogIn extends Fragment {
    LoginBinding binding;
    NavController navController;
    MedicoViewModel medicoViewModel;
    HashFunction hf;

    Executor executor;

    public LogIn() {
        // Required empty public constructor
    }

    public static LogIn newInstance(String param1, String param2) {
        LogIn fragment = new LogIn();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        //medicoViewModel.add(new Medico(1,"Aimar","Alonso","aimaralonso","123"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = LoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        medicoViewModel = new ViewModelProvider(requireActivity()).get(MedicoViewModel.class);
        executor = Executors.newSingleThreadExecutor();

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_to_signup);
            }
        });


        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hf = Hashing.sha256();
                String userIn = binding.editTextUser.getText().toString();
                String pass = binding.editTextPassword.getText().toString();

                HashCode hashIn = hf.newHasher()
                        .putString(pass, Charsets.UTF_8)
                        .hash();

                // Observe the logged-in Medico and handle the login
                medicoViewModel.getMedicosByUsername(userIn).observe(getViewLifecycleOwner(),medicos->{
                    if (!medicos.isEmpty()) {
                        Medico medico = medicos.get(0);
                        String hashDb = medico.getPassword();
                        if (hashIn.toString().equals(hashDb)) {
                            // Set the logged-in Medico in the ViewModel
                            medicoViewModel.setLoggedInMedico(medico);

                            // Navigate to the menu fragment
                            navController.navigate(R.id.action_login_to_show);
                        } else {
                            Toast t = Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG);
                            t.show();
                            return;
                        }
                    } else {
                        Toast t = Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG);
                        t.show();
                        return;
                    }
                });

            }
        });
        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle password visibility
                if (isChecked) {
                    // Show the password
                    binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Hide the password
                    binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // Move the cursor to the end of the text
                binding.editTextPassword.setSelection(binding.editTextPassword.getText().length());
            }

        });
    }
}

