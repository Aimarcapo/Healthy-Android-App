package eus.aimar.healthy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Collections;

import eus.aimar.healthy.Model.Paciente;
import eus.aimar.healthy.ViewModel.MedicoViewModel;
import eus.aimar.healthy.ViewModel.PacienteViewModel;
import eus.aimar.healthy.databinding.FragmentEditPacienteBinding;

public class EditPacientes extends Fragment {
    FragmentEditPacienteBinding binding;
    NavController navController;
    PacienteViewModel pacienteViewModel;
    Paciente paciente;
    MedicoViewModel medicoViewModel;
    public static int pacienteID;

    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Ibuprofeno", "Paracetamol", "Antibioticos"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentEditPacienteBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medicoViewModel = new ViewModelProvider(requireActivity()).get(MedicoViewModel.class);
        navController = Navigation.findNavController(view);
        pacienteViewModel = new ViewModelProvider(requireActivity()).get(PacienteViewModel.class);
        selectedLanguage = new boolean[langArray.length];

        binding.textViewMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

                // set title
                builder.setTitle("Select Language");

                // set dialog non-cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox is selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort the array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox is unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use a for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value is not equal
                                // to lang list size - 1
                                // add a comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        binding.textViewMedicamento.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss the dialog
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use a for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear the language list
                            langList.clear();
                            // clear the text view value
                            binding.textViewMedicamento.setText("");
                        }
                    }
                });
                // show the dialog
                builder.show();
            }
        });




        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve updated data from EditText fields
                int id = pacienteID;
                int medico = medicoViewModel.getLoggedInMedico().getId();
                String updatedName = binding.editName.getText().toString();
                String updatedSurname = binding.editSurname.getText().toString();
                String updatedMedicamento = binding.textViewMedicamento.getText().toString();

                if (updatedName.isEmpty() || updatedSurname.isEmpty() || updatedMedicamento.isEmpty()) {
                    Toast.makeText(getContext(), "You must fill all the required fields", Toast.LENGTH_SHORT).show();
                } else if (langList.isEmpty()) {
                    // Language selection validation
                    Toast.makeText(getContext(), "Select at least one language for medicamento", Toast.LENGTH_SHORT).show();
                } else {
                    Paciente updatedPaciente = new Paciente(id, medico, updatedName, updatedSurname, updatedMedicamento);
                    pacienteViewModel.update(updatedPaciente);
                    navController.navigate(R.id.action_edit_to_shpw);
                }
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve updated data from EditText fields
                int id = pacienteID;




                    pacienteViewModel.deletePaciente(id);
                    navController.navigate(R.id.action_edit_to_shpw);

            }
        });

        // Observe the selected Paciente from the view model
        pacienteViewModel.selected().observe(getViewLifecycleOwner(), new Observer<Paciente>() {
            @Override
            public void onChanged(Paciente selectedPaciente) {
                // Store the selected Paciente
                paciente = selectedPaciente;

                // Populate the EditText fields with the Paciente's information
                binding.editName.setText(paciente.getNombre());
                binding.editSurname.setText(paciente.getApellidos());
                // Populate other fields as needed
            }
        });
    }
}
