package eus.aimar.healthy;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.List;

import eus.aimar.healthy.Model.Medico;
import eus.aimar.healthy.ViewModel.MedicoViewModel;
import eus.aimar.healthy.databinding.SignupBinding;

public class SignUp extends Fragment {
    String name,surname,username, password, passwordHash;
    boolean primera = true;
    SignupBinding binding;
    NavController navController;
    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return(binding = SignupBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MedicoViewModel medicoViewModel = new ViewModelProvider(requireActivity()).get(MedicoViewModel.class);
        navController = Navigation.findNavController(view);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {    primera = true;
                    username = binding.signupUsername.getText().toString();
                    name = binding.signupName.getText().toString();
                    surname = binding.signupSurname.getText().toString();
                    password = binding.signupPassword.getText().toString();
                    HashFunction hf = Hashing.sha256();
                    HashCode code = hf.newHasher()
                            .putString(password, Charsets.UTF_8)
                            .hash();
                    passwordHash = code.toString();
                    if(username.isEmpty()||name.isEmpty()||surname.isEmpty()||password.isEmpty()){
                        Toast.makeText(getContext(), "All the areas must be filled", Toast.LENGTH_SHORT).show();
                    }
                    // Check if a Medico with the same username already exists
                    else{
                       // List<Medico> medicos = medicoViewModel.getMedicosByUsername(username).getValue();
                        medicoViewModel.getMedicosByUsername(username).observe(getViewLifecycleOwner(),medicos->{



                            if (primera) {
                                if (!medicos.isEmpty()) {
                                    medicoViewModel.getMedicosByUsername(username).removeObservers(getViewLifecycleOwner());

                                    Toast.makeText(getContext(), "The account already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    // medicoViewModel.getMedicosByUsername(username).removeObservers(getViewLifecycleOwner());
                                    Medico newMedico = new Medico(name, surname, username, passwordHash);
                                    medicoViewModel.add(newMedico);
                                    Log.println(Log.DEBUG, "hola", "else");
                                    primera = false;
                                    Toast.makeText(getContext(), "The new medico account of: " + username + " has been created", Toast.LENGTH_SHORT).show();
                                    // medicoViewModel.getMedicosByUsername(username).removeObserver((Observer<? super List<Medico>>) medicos);


                                }
                            }
                        });

                    }
                            // Username is unique, so proceed with registration
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.signupBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signup_to_login);
            }
        });
        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle password visibility
                if (isChecked) {
                    // Show the password
                    binding.signupPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Hide the password
                    binding.signupPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // Move the cursor to the end of the text
                binding.signupPassword.setSelection(binding.signupPassword.getText().length());
            }

        });

    }

}
